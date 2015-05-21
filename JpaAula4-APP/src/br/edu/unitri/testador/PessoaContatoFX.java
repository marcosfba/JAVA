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
import br.edu.unitri.controler.ContatoControler;
import br.edu.unitri.controler.PessoaControler;
import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Contato.TipoContato;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.model.PessoaContatoDTO;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class PessoaContatoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbTipoContato;
	@FXML private ComboBox<Pessoa> cbPessoa;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbEmail;
	@FXML private ComboBox<TipoContato> cbTipoContato;
	@FXML private RadioButton rbDescrição;
	@FXML private TextField txtComplemento;
	@FXML private TableView<PessoaContatoDTO> tbPessoas;
 	@FXML private Button btnIncluir;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Pessoa> dados = FXCollections.observableArrayList();
	private ObservableList<PessoaContatoDTO> listaPessoas = FXCollections.observableArrayList();
	private PessoaControler pessoaCtr = new PessoaControler();
	private ContatoControler contatoCtr = new ContatoControler();
	private ConsultasControler consultaCtr = new ConsultasControler();
	
	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		listaPessoas.clear();
			if (rbNome.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaContatos(txtBuscar.getText(),true,false,false,false));			
			} else if (rbEmail.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaContatos(txtBuscar.getText(),false,true,false,false));
			} else if (rbDescrição.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaContatos(txtBuscar.getText(),false,false,false,true));
			} else if (rbTipoContato.isSelected()) {
				listaPessoas.addAll(consultaCtr.findPessoaContatos(txtBuscar.getText(),false,false,true,false));
			}
			tbPessoas.setItems(listaPessoas);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbEmail.isSelected() || rbDescrição.isSelected() || rbTipoContato.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Pessoa pessoa = cbPessoa.getSelectionModel().getSelectedItem();
			Contato contato = new Contato(cbTipoContato.getSelectionModel().getSelectedItem(), txtDescricao.getText(), txtComplemento.getText());
			
			boolean ok = false;
			for (Contato contato2 : pessoa.getContatos()) {
				if (contato2.getTipoContato().equals(contato.getTipoContato())) {
					ok = true;
					break;
				}
			}			
			if (!ok){
				try {
					if (contatoCtr.save(contato) != null) {
						pessoa.getContatos().add(contato);
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
			if (cbTipoContato.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor escolha o tipo de contato!").showDialog();
				cbTipoContato.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtDescricao.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencha a descrição da contato!").showDialog();
				txtDescricao.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtComplemento.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o complemento do contato!").showDialog();
				txtComplemento.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		cbPessoa.getSelectionModel().select(null);
		cbTipoContato.getSelectionModel().select(null);
		txtDescricao.setText("");
		txtComplemento.setText("");
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
		cbTipoContato.getItems().setAll(TipoContato.values());
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		popularDados();
		tbPessoas.getColumns().addAll(new GenericTable<PessoaContatoDTO>().tableColunas(PessoaContatoDTO.class));		
	}
}
