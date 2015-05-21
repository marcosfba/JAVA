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
import br.edu.unitri.Controler.FuncionarioControler;
import br.edu.unitri.model.Funcionario;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class FuncionarioFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbMatricula;
	@FXML private TextField txtEndereco;
	@FXML private TextField txtNome;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private TableView<Funcionario> tbFuncionarios;
	@FXML private Button btnIncluir;
    @FXML private ToggleGroup buscarPor;
    @FXML private RadioButton rbNome;
    @FXML private Button btnExcluir;
    @FXML private TextField txtMatricula;
    @FXML private TabPane tabTela;
    @FXML private Button btnBuscar;
    @FXML private Button btnNovo;

	private ObservableList<Funcionario> dados = FXCollections.observableArrayList();
	private int Operacao;	
	private FuncionarioControler funcionarioCtr = new FuncionarioControler();
	private long idFuncionario;
	private Funcionario funcionario;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbMatricula.isSelected()) {
			   dados.addAll(funcionarioCtr.findAll("select f.* from tbFuncionario f"," where f.matricula = '"+ txtBuscar.getText() + "'"));			
			} else if (rbNome.isSelected()) {
				dados.addAll(funcionarioCtr.findAll("select f.* from tbFuncionario f"," where f.nome like  '%"+ txtBuscar.getText() + "%'"));	
			}
			txtBuscar.setText("");
			tbFuncionarios.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbMatricula.isSelected() || rbNome.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Funcionario funcionario = new Funcionario(txtNome.getText(),txtMatricula.getText(),txtEndereco.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (funcionarioCtr.save(funcionario) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (funcionarioCtr.update(funcionario,(int)getIdFuncionario())) {
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

	private boolean isValidaTela() {
		boolean ok = true;
		if (txtNome.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome do funcionário!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtMatricula.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a Matrícula do funcionário!").showDialog();
				txtMatricula.requestFocus();
				ok = false;			
			}
		}
		if (ok) {
			if (txtEndereco.getText().isEmpty()) {
				txtEndereco.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o endereço do funcionário!").showDialog();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtMatricula.setText("");
		txtNome.setText("");
		txtEndereco.setText("");
		txtMatricula.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = funcionarioCtr.delete(getFuncionario());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR,"Erro ao tentar excluir funcionário ->" + e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.INFO,"Funcionário não pode ser excluido. Existem relações com outras entidades!").showDialog();
				popularDados();
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Funcionário!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tbFuncionarios.getColumns().addAll(new GenericTable<Funcionario>().tableColunas(Funcionario.class));
		try {
			dados.addAll(funcionarioCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro durante a inicialização ->" +  e.getCause().getMessage()).showDialog();
		}
		tbFuncionarios.setItems(dados);		
		
		tbFuncionarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setFuncionario(tbFuncionarios.getSelectionModel().getSelectedItem());					
					setIdFuncionario(tbFuncionarios.getSelectionModel().getSelectedItem().getIdFuncionario());
					txtMatricula.setText(tbFuncionarios.getSelectionModel().getSelectedItem().getMatricula());
					txtEndereco.setText(tbFuncionarios.getSelectionModel().getSelectedItem().getEndereco());
					txtNome.setText(tbFuncionarios.getSelectionModel().getSelectedItem().getNome());
				}
			}
		});
	}

	private void popularDados(){
		dados.clear();
		tbFuncionarios.getItems().clear();
		try {
			dados.addAll(funcionarioCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao buscar dados ->" +  e.getCause().getMessage()).showDialog();
		}
		tbFuncionarios.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(long idFuncionario) {
		this.idFuncionario = idFuncionario;
	}
	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
