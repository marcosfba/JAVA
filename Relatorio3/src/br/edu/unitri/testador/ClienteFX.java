/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
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

/**
 * @author marcos.fernando
 *
 */
public class ClienteFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtEndereco;
	@FXML private RadioButton rbCidade;
	@FXML private TextField txtNome;
	@FXML private TextField txtTelefone;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtPais;
	@FXML private TextField txtBuscar;
	@FXML private TextField txtProfissao;
	@FXML private TextField txtFax;
	@FXML private Button btnIncluir;
	@FXML private TextField txtCidade;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TextField txtCep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private TableView<Cliente> tbClientes;
	@FXML private Button btnNovo;
	
	private ObservableList<Cliente> dados = FXCollections.observableArrayList();
	private int Operacao;
	private ClienteControler clienteCtr = new ClienteControler();
	private Cliente cliente;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(clienteCtr.findAll("select c.* from tbCliente c"," where c.nomeCliente like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbCidade.isSelected()) {
				dados.addAll(clienteCtr.findAll("select c.* from tbCliente c"," where c.descCidade like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbClientes.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbCidade.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Cliente cliente = new Cliente(txtNome.getText(), txtProfissao.getText(), txtCep.getText(), 
					txtCidade.getText(), txtEndereco.getText(), txtFax.getText(), txtTelefone.getText(), 
					txtPais.getText());			
			switch (getOperacao()) {
			case 0:
				try {
					if (clienteCtr.save(cliente) != null) {
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
					if (clienteCtr.update(cliente,(int) getCliente().getCodCliente())) {
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

	private boolean isValidaTela() {
		boolean ok = true;
		if (txtNome.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome do Cliente!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtProfissao.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a profissão!").showDialog();
				txtProfissao.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtCep.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o cep!").showDialog();
				txtCep.requestFocus();
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
			if (txtFax.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o fax!").showDialog();
				txtFax.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtTelefone.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o telefone!").showDialog();
				txtTelefone.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtPais.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o nome do Pais!").showDialog();
				txtPais.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		txtProfissao.setText("");
		txtCep.setText("");
		txtCidade.setText("");
		txtEndereco.setText("");
		txtFax.setText("");
		txtPais.setText("");
		txtTelefone.setText("");
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = clienteCtr.delete(getCliente());
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
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbClientes.getColumns().addAll(new GenericTable<Cliente>().tableColunas(Cliente.class));	
		popularDados();
		tbClientes.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);	
					setOperacao(1);
					setCliente(tbClientes.getSelectionModel().getSelectedItem());
					SetValoresComponentes(getCliente());
				}
			}
		});
	}
	
	private void SetValoresComponentes(Cliente cliente) {
		txtNome.setText(cliente.getNomeCliente());
		txtProfissao.setText(cliente.getDescCargo());
		txtCep.setText(cliente.getDescCep());
		txtCidade.setText(cliente.getDescCidade());
		txtEndereco.setText(cliente.getDescEndereco());
		txtFax.setText(cliente.getDescFax());
		txtPais.setText(cliente.getDescPais());
		txtTelefone.setText(cliente.getDescTelefone());
	}

	private void popularDados() {
		dados.clear();
		tbClientes.getItems().clear();
		try {
			dados.addAll(clienteCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbClientes.setItems(dados);
	}


	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
