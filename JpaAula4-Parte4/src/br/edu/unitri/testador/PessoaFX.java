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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class PessoaFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtEndereco;
	@FXML private RadioButton rbCidade;
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtPais;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbEmail;
	@FXML private TextField txtNumero;
	@FXML private RadioButton rbUF;
	@FXML private TextField txtComplemento;
	@FXML private TableView<Pessoa> tbPessoas;
	@FXML private Button btnIncluir;
	@FXML private TextField txtCidade;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private DatePicker dtNascimento;
	@FXML private TextField txtCep;
	@FXML private Button btnExcluir;
	@FXML private TextField txtEstado;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Pessoa> dados = FXCollections.observableArrayList();
	private int Operacao;
	private PessoaControler pessoaCtr = new PessoaControler();
	private Pessoa pessoa;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(pessoaCtr.findAll("select p.* from Pessoa p"," where p.nome like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbEmail.isSelected()) {
				dados.addAll(pessoaCtr.findAll("select p.* from Pessoa p"," where p.email like  '%"+ txtBuscar.getText() + "%'"));
			} else if (rbCidade.isSelected()) {
				dados.addAll(pessoaCtr.findAll("select p.* from Pessoa p"," where p.cidade like  '%"+ txtBuscar.getText() + "%'"));
			} else if (rbUF.isSelected()) {
				dados.addAll(pessoaCtr.findAll("select p.* from Pessoa p"," where p.estado = '"+ txtBuscar.getText() + "'"));
			}
			tbPessoas.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbEmail.isSelected() || rbCidade.isSelected() || rbUF.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Pessoa pessoa = new Pessoa(txtNome.getText(), txtEmail.getText(), dtNascimento.getValue(), 
					new Endereco(txtPais.getText(), txtEstado.getText(), txtCidade.getText(), txtEndereco.getText(),
							     Integer.valueOf(txtNumero.getText()), txtComplemento.getText(), Integer.valueOf(txtCep.getText())));
			switch (getOperacao()) {
			case 0:
				try {
					if (pessoaCtr.save(pessoa) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				;
				popularDados();
				break;
			case 1:
				try {
					if (pessoaCtr.update(pessoa,(int) getPessoa().getId())) {
						new FXDialog(Type.INFO,"Registro atualizado com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				popularDados();
				break;
			}
		}
	}

	private void popularDados() {
		dados.clear();
		tbPessoas.getItems().clear();
		try {
			dados.addAll(pessoaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbPessoas.setItems(dados);
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (txtNome.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome da Pessoa!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtEmail.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o email da Pessoa!").showDialog();
				txtEmail.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (dtNascimento.getValue() == null) {
				new FXDialog(Type.WARNING, "Favor preencher a data de nascimento da Pessoa!").showDialog();
				dtNascimento.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtPais.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o País de origem da Pessoa!").showDialog();
				txtPais.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtEstado.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a UF da Pessoa!").showDialog();
				txtEstado.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtCidade.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a cidade da Pessoa!").showDialog();
				txtCidade.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtEndereco.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o endereço da Pessoa!").showDialog();
				txtEndereco.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtNumero.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o número do endereço da Pessoa!").showDialog();
				txtNumero.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtComplemento.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o complemento do endereço da Pessoa!").showDialog();
				txtComplemento.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtCep.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o Cep da Pessoa!").showDialog();
				txtCep.requestFocus();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		txtEmail.setText("");
		dtNascimento.setValue(null);
		txtPais.setText("");
		txtEstado.setText("");
		txtCidade.setText("");
		txtEndereco.setText("");
		txtNumero.setText("");
		txtComplemento.setText("");
		txtCep.setText("");		
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = pessoaCtr.delete(getPessoa());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.WARNING,"Existem relações com outras entidades!").showDialog();
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhuma Pessoa!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbPessoas.getColumns().addAll(new GenericTable<Pessoa>().tableColunas(Pessoa.class));
		popularDados();
		tbPessoas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setPessoa(tbPessoas.getSelectionModel().getSelectedItem());
					txtEmail.setText(tbPessoas.getSelectionModel().getSelectedItem().getEmail());
					txtNome.setText(tbPessoas.getSelectionModel().getSelectedItem().getNome());
					dtNascimento.setValue(tbPessoas.getSelectionModel().getSelectedItem().getDtNascimento());
					txtPais.setText(tbPessoas.getSelectionModel().getSelectedItem().getEndereco().getPais());
					txtEstado.setText(tbPessoas.getSelectionModel().getSelectedItem().getEndereco().getEstado());
					txtCidade.setText(tbPessoas.getSelectionModel().getSelectedItem().getEndereco().getCidade());
					txtEndereco.setText(tbPessoas.getSelectionModel().getSelectedItem().getEndereco().getLogradouro());
					txtNumero.setText(String.valueOf(tbPessoas.getSelectionModel().getSelectedItem().getEndereco().getNumero()));
					txtComplemento.setText(tbPessoas.getSelectionModel().getSelectedItem().getEndereco().getComplemento());
					txtCep.setText(String.valueOf(tbPessoas.getSelectionModel().getSelectedItem().getEndereco().getCep()));		
				}
			}
		});
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
}
