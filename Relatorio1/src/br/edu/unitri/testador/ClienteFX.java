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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.ConverterUtil;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ClienteFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtNome;
	@FXML private TextField txtEmail;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbEmail;
	@FXML private ComboBox<String> cbSexo;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private DatePicker dtNascimento;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private TableView<Cliente> tbClientes;
	@FXML private Button btnNovo;
	
	private ObservableList<Cliente> dados = FXCollections.observableArrayList();
	private int Operacao;
	private ClienteControler clienteCtr = new ClienteControler();
	private Cliente cliente;
	private String[] sexos = {"MASCULINO","FEMININO"};

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(clienteCtr.findAll("select c.* from tbCliente c"," where c.nome like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbEmail.isSelected()) {
				dados.addAll(clienteCtr.findAll("select c.* from tbCliente c"," where c.email like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbClientes.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbEmail.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Cliente cliente = new Cliente(txtNome.getText(), cbSexo.getSelectionModel().getSelectedItem(), ConverterUtil.localDateToDate(dtNascimento.getValue()), txtEmail.getText());
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
			if (txtEmail.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o email do Cliente!").showDialog();
				txtEmail.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (dtNascimento.getValue() == null) {
				new FXDialog(Type.WARNING, "Favor preencher a data de nascimento do Cliente!").showDialog();
				dtNascimento.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (cbSexo.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor preencher o Sexo do Cliente!").showDialog();
				cbSexo.requestFocus();
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
		cbSexo.getSelectionModel().select(null);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbClientes.getColumns().addAll(new GenericTable<Cliente>().tableColunas(Cliente.class));	
		cbSexo.getItems().setAll(sexos);
		popularDados();
		tbClientes.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);	
					setOperacao(1);
					setCliente(tbClientes.getSelectionModel().getSelectedItem());
					txtEmail.setText(tbClientes.getSelectionModel().getSelectedItem().getEmail());
					txtNome.setText(tbClientes.getSelectionModel().getSelectedItem().getNome());
					dtNascimento.setValue(ConverterUtil.utilDateToLocalDate(tbClientes.getSelectionModel().getSelectedItem().getDtNascimento()));
					cbSexo.getSelectionModel().select(tbClientes.getSelectionModel().getSelectedItem().getSexo());					
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

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	

}
