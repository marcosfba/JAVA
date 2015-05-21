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
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class PessoaFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtNome;
	@FXML private TextField txtTelefone;
	@FXML private TextField txtEmail;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbEmail;
	@FXML private RadioButton rbTelefone;
	@FXML private TableView<Pessoa> tbPessoas;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Pessoa> dados = FXCollections.observableArrayList();
	private int Operacao;
	private PessoaControler pessoaCtr = new PessoaControler();
	private long idPessoa;

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdPessoa() {
		return idPessoa;
	}

	public void setIdPessoa(long idPessoa) {
		this.idPessoa = idPessoa;
	}

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(pessoaCtr.findAll("select p.* from Pessoa p"," where p.nomePessoa like '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbTelefone.isSelected()) {
				dados.addAll(pessoaCtr.findAll("select p.* from Pessoa p"," where p.fonePessoa = '"+ txtBuscar.getText() + "'"));	
			} else if (rbEmail.isSelected()) {
				dados.addAll(pessoaCtr.findAll("select p.* from Pessoa p"," where p.emailPessoa = '"+ txtBuscar.getText() + "'"));		   
			}
			tbPessoas.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbTelefone.isSelected() || rbEmail.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Pessoa pessoa = new Pessoa(txtNome.getText(), txtTelefone.getText(), txtEmail.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (pessoaCtr.save(pessoa) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (pessoaCtr.update(pessoa,(int)getIdPessoa())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome da pessoa!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtTelefone.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o telefone da pessoa!").showDialog();
				txtTelefone.requestFocus();
				ok = false;			
			}
		}
		if (ok) {
			if (txtEmail.getText().isEmpty()) {
				txtEmail.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o email da pessoa!").showDialog();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		txtTelefone.setText("");
		txtEmail.setText("");
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = pessoaCtr.delete((int) getIdPessoa());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionada nenhuma Pessoa!").showDialog();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tbPessoas.getColumns().addAll(new GenericTable<Pessoa>().tableColunas(Pessoa.class));
		try {
			dados.addAll(pessoaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbPessoas.setItems(dados);		
		tbPessoas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setIdPessoa(tbPessoas.getSelectionModel().getSelectedItem().getIdPessoa());
					txtNome.setText(tbPessoas.getSelectionModel().getSelectedItem().getNomePessoa());
					txtTelefone.setText(tbPessoas.getSelectionModel().getSelectedItem().getFonePessoa());
					txtEmail.setText(tbPessoas.getSelectionModel().getSelectedItem().getEmailPessoa());
				}
			}
		});
	}
	
	private void popularDados(){
		dados.clear();
		tbPessoas.getItems().clear();
		try {
			dados.addAll(pessoaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbPessoas.setItems(dados);		
	}


}
