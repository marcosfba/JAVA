/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.controler.FornecedorControler;
import br.edu.unitri.model.Fornecedor;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class FornecedorFX implements Initializable {

	@FXML private TextField txtEndereco;
	@FXML private Button btnIncluir;
	@FXML private Tab tabCadastro;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private TextField txtRazao;
	@FXML private Button btnNovo;
	@FXML private TextField txtCNPJ;
	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbEndereco;
	@FXML private RadioButton rbCnpj;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private Button btnBuscar;
	@FXML private TableView<Fornecedor> tbFornecedores;
	
	private ObservableList<Fornecedor> dados = FXCollections.observableArrayList();
	private int Operacao;
	private FornecedorControler forncedorCtr = new FornecedorControler();
	private long idFornecedor;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbCnpj.isSelected()) {
			   dados.addAll(forncedorCtr.findAll("select f.* from Fornecedor f"," where f.cnpj = '"+ txtBuscar.getText() + "'"));			
			} else if (rbNome.isSelected()) {
				dados.addAll(forncedorCtr.findAll("select f.* from Fornecedor f"," where f.nome like  '%"+ txtBuscar.getText() + "%'"));	
			} else if (rbEndereco.isSelected()) {
				dados.addAll(forncedorCtr.findAll("select f.* from Fornecedor f"," where f.endereco like  '%"+ txtBuscar.getText() + "%'"));		   
			}
			tbFornecedores.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbCnpj.isSelected() || rbNome.isSelected() || rbEndereco.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Fornecedor fornecedor = new Fornecedor(txtRazao.getText(), txtCNPJ.getText(), txtEndereco.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (forncedorCtr.save(fornecedor) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (forncedorCtr.update(fornecedor,(int)getIdFornecedor())) {
						new FXDialog(Type.INFO, "Registro atualizado com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				popularDados();
				break;
			}
		}
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtCNPJ.setText("");
		txtEndereco.setText("");
		txtRazao.setText("");
		txtRazao.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = forncedorCtr.delete((int) getIdFornecedor());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Fornecedor!").showDialog();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbFornecedores.getColumns().addAll(new GenericTable<Fornecedor>().tableColunas(Fornecedor.class));
		try {
			dados.addAll(forncedorCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbFornecedores.setItems(dados);		
		tbFornecedores.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setIdFornecedor(tbFornecedores.getSelectionModel().getSelectedItem().getIdFornecedor());
					txtCNPJ.setText(tbFornecedores.getSelectionModel().getSelectedItem().getCnpj());
					txtEndereco.setText(tbFornecedores.getSelectionModel().getSelectedItem().getEndereco());
					txtRazao.setText(tbFornecedores.getSelectionModel().getSelectedItem().getNome());
				}
			}
		});
	}
	
	private void popularDados(){
		dados.clear();
		tbFornecedores.getItems().clear();
		try {
			dados.addAll(forncedorCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbFornecedores.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(long idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (txtRazao.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome do fornecedor!").showDialog();			
			txtRazao.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtCNPJ.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o CNPJ do fornecedor!").showDialog();
				txtCNPJ.requestFocus();
				ok = false;			
			}
		}
		if (ok) {
			if (txtEndereco.getText().isEmpty()) {
				txtEndereco.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o endereço do fornecedor!").showDialog();
				ok = false;			
			}
		}
		return ok;		 
	}
}
