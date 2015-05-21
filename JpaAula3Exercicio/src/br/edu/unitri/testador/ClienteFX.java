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
import br.edu.unitri.Controler.ClienteControler;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ClienteFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtEndereco;
	@FXML private TextField txtNome;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtCPF;
	@FXML private TextField txtBuscar;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private RadioButton rbNome;
	@FXML private Button btnExcluir;
	@FXML private RadioButton rbCPF;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private TableView<Cliente> tbClientes;
	@FXML private Button btnNovo;

	private ObservableList<Cliente> dados = FXCollections.observableArrayList();
	private int Operacao;	
	private ClienteControler clienteCtr = new ClienteControler();
	private long idCliente;
	private Cliente cliente;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbCPF.isSelected()) {
			   dados.addAll(clienteCtr.findAll("select c.* from tbCliente c"," where c.cpf = '"+ txtBuscar.getText() + "'"));			
			} else if (rbNome.isSelected()) {
				dados.addAll(clienteCtr.findAll("select c.* from tbCliente c"," where c.nome like  '%"+ txtBuscar.getText() + "%'"));	
			}
			txtBuscar.setText("");
			tbClientes.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbCPF.isSelected() || rbNome.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Cliente cliente = new Cliente(txtCPF.getText(),txtNome.getText(),txtEndereco.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (clienteCtr.save(cliente) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (clienteCtr.update(cliente,(int)getIdCliente())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome do cliente!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtCPF.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o CPF do cliente!").showDialog();
				txtCPF.requestFocus();
				ok = false;			
			}
		}
		if (ok) {
			if (txtEndereco.getText().isEmpty()) {
				txtEndereco.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o endereço do cliente!").showDialog();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtCPF.setText("");
		txtNome.setText("");
		txtEndereco.setText("");
		txtCPF.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = clienteCtr.delete(getCliente());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR,"Erro ao tentar excluir cliente ->" + e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.INFO,"Cliente não pode ser excluido. Existem relações com outras entidades!").showDialog();
				popularDados();
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Cliente!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tbClientes.getColumns().addAll(new GenericTable<Cliente>().tableColunas(Cliente.class));
		try {
			dados.addAll(clienteCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro durante a inicialização ->" +  e.getCause().getMessage()).showDialog();
		}
		tbClientes.setItems(dados);		
		
		tbClientes.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setCliente(tbClientes.getSelectionModel().getSelectedItem());
					
					setIdCliente(tbClientes.getSelectionModel().getSelectedItem().getIdCliente());
					txtCPF.setText(tbClientes.getSelectionModel().getSelectedItem().getCpf());
					txtEndereco.setText(tbClientes.getSelectionModel().getSelectedItem().getEndereco());
					txtNome.setText(tbClientes.getSelectionModel().getSelectedItem().getNome());
				}
			}
		});
	}
	
	private void popularDados(){
		dados.clear();
		tbClientes.getItems().clear();
		try {
			dados.addAll(clienteCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao buscar dados ->" +  e.getCause().getMessage()).showDialog();
		}
		tbClientes.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	
}
