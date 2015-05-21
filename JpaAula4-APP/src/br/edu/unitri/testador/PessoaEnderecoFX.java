package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import br.edu.unitri.controler.ConsultasControler;
import br.edu.unitri.controler.EnderecoControler;
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Endereco.TipoEndereco;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.model.PessoaEnderecoDTO;
import br.edu.unitri.model.TipoUF;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

public class PessoaEnderecoFX implements Initializable {
	
	@FXML private Tab tabConsulta;
    @FXML private TextField txtEndereco;
    @FXML private ComboBox<TipoUF> cbEstado;
    @FXML private ComboBox<Pessoa> cbPessoa;
    @FXML private ComboBox<TipoEndereco> cbTipoEndereco;
    @FXML private RadioButton rbCidade;
    @FXML private Tab tabCadastro;
    @FXML private TextField txtPais;
    @FXML private TextField txtBuscar;
    @FXML private RadioButton rbEmail;
    @FXML private TextField txtNumero;
    @FXML private RadioButton rbUF;
    @FXML private TextField txtComplemento;
    @FXML private TableView<PessoaEnderecoDTO> tbPessoas;
    @FXML private Button btnIncluir;
    @FXML private TextField txtCidade;
    @FXML private RadioButton rbNome;
    @FXML private ToggleGroup buscarPor;
    @FXML private TextField txtCEP;
    @FXML private TabPane tabTela;
    @FXML private Button btnBuscar;
    @FXML private Button btnNovo;
    
	private ObservableList<Pessoa> dados = FXCollections.observableArrayList();
	private ObservableList<PessoaEnderecoDTO> listaPessoas = FXCollections.observableArrayList();
	private PessoaControler pessoaCtr = new PessoaControler();
	private EnderecoControler enderecoCtr = new EnderecoControler();
	private ConsultasControler consultaCtr = new ConsultasControler();

	@FXML 
    void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		listaPessoas.clear();
			if (rbNome.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaEnderecos(txtBuscar.getText(),true,false,false,false));			
			} else if (rbEmail.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaEnderecos(txtBuscar.getText(),false,true,false,false));
			} else if (rbCidade.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaEnderecos(txtBuscar.getText(),false,false,true,false));
			} else if (rbUF.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaEnderecos(txtBuscar.getText(),false,false,false,true));
			}
			tbPessoas.setItems(listaPessoas);
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
			Pessoa pessoa = cbPessoa.getSelectionModel().getSelectedItem();
			Endereco endereco = new Endereco(cbTipoEndereco.getSelectionModel().getSelectedItem(),
					  txtPais.getText(), cbEstado.getSelectionModel().getSelectedItem().opcao, txtCidade.getText(),
					  txtEndereco.getText(), Integer.valueOf(txtNumero.getText()), txtComplemento.getText(), Integer.valueOf(txtCEP.getText()));
			
			boolean ok = false;
			for (Endereco endereco2 : pessoa.getEnderecos()) {
				if (endereco2.getTipoEndereco().equals(endereco.getTipoEndereco())) {
					ok = true;
					break;
				}
			}			
			if (!ok){
				try {
					if (enderecoCtr.save(endereco) != null) {
						pessoa.getEnderecos().add(endereco);
						if (pessoaCtr.save(pessoa) != null) {
							new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
						}
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
			} else {
				new FXDialog(Type.WARNING, "Tipo de contato já existente para esta pessoa!").showDialog();
			}
		}
    }

    private boolean isValidaTela() {
		boolean ok = true;
		if (cbPessoa.getSelectionModel().getSelectedItem() == null) {
			new FXDialog(Type.WARNING, "Favor escolha e Pessoa!").showDialog();			
			cbPessoa.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbTipoEndereco.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor escolha o tipo de endereço!").showDialog();
				cbTipoEndereco.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtPais.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencha o País referente ao endereço!").showDialog();
				txtPais.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (cbEstado.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor escolha o estado!").showDialog();
				cbEstado.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtCidade.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a cidade!").showDialog();
				txtCidade.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtEndereco.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o endereço!").showDialog();
				txtEndereco.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtNumero.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o número do endereço!").showDialog();
				txtNumero.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtComplemento.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o complemento do endereço!").showDialog();
				txtComplemento.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtCEP.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o CEP!").showDialog();
				txtCEP.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
    void btnNovoClick(ActionEvent event) {
		cbPessoa.getSelectionModel().select(null);
		cbTipoEndereco.getSelectionModel().select(null);
		cbEstado.getSelectionModel().select(null);
		txtCEP.setText("");
		txtCidade.setText("");
		txtComplemento.setText("");
		txtEndereco.setText("");
		txtNumero.setText("");
		txtPais.setText("");
		cbPessoa.requestFocus();
    }
	
	private void popularDados() {
		dados.clear();
		tbPessoas.getItems().clear();
		try {
			dados.addAll(pessoaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbPessoa.setItems(dados);
		cbTipoEndereco.getItems().setAll(TipoEndereco.values());
		cbEstado.getItems().setAll(TipoUF.values());
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		popularDados();
		tbPessoas.getColumns().addAll(new GenericTable<PessoaEnderecoDTO>().tableColunas(PessoaEnderecoDTO.class));		
	}

}
