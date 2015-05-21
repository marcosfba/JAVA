/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.controler.PedidoControler;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.Pedido;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.ConverterUtil;
import br.edu.unitri.util.GenericTable;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author marcos.fernando
 *
 */
public class PedidoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbCodigo;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private TableView<Pedido> tbPedidos;
	@FXML private ComboBox<Cliente> cbCliente;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbCliente;
	@FXML private TextArea txtObservacao;
	@FXML private Button btnCliente;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	@FXML private DatePicker dtPedido;

	private ObservableList<Cliente> dados = FXCollections.observableArrayList();
	private ObservableList<Pedido> dadosPed = FXCollections.observableArrayList();
	private int Operacao;
	private ClienteControler clienteCtr = new ClienteControler();
	private PedidoControler pedidoCtr = new PedidoControler();
	private Pedido pedido;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		tbPedidos.getItems().clear();
	   		dadosPed.clear();
			if (rbCodigo.isSelected()) {
				dadosPed.addAll(pedidoCtr.findAll("select p.* from tbPedido p"," where p.codPedido ="+ txtBuscar.getText()));			
			} else if (rbCliente.isSelected()) {
				dadosPed.addAll(pedidoCtr.findAll("select p.* from tbPedido p, tbCliente c"," where p.idCliente = c.codCliente and c.nomeCliente like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbPedidos.setItems(dadosPed);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbCodigo.isSelected() || rbCliente.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Pedido pedido = new Pedido(ConverterUtil.localDateToDate(dtPedido.getValue()), cbCliente.getSelectionModel().getSelectedItem(),
					txtObservacao.getText());
			switch (getOperacao()) {
			case 0:
				try {
					if (pedidoCtr.save(pedido) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				;
				popularDados();
				break;
			case 1:
				try {
					if (pedidoCtr.update(pedido,(int) getPedido().getCodPedido())) {
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
		if (dtPedido.getValue() == null) {
			new FXDialog(Type.WARNING, "Favor preencher a data do Pedido!").showDialog();			
			dtPedido.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbCliente.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o Cliente!").showDialog();
				cbCliente.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtObservacao.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a observação!").showDialog();
				txtObservacao.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		dtPedido.setValue(null);
		cbCliente.getSelectionModel().select(null);
		txtObservacao.setText("");
		dtPedido.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = pedidoCtr.delete(getPedido());
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

	@FXML
	void btnClienteClick(ActionEvent event) {
		Stage telaCliente = new Stage();
		try {
			new FormFX<ClienteFX>("Cliente.fxml", telaCliente, "Operações com Cliente", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		
		dados.clear();
		cbCliente.getItems().clear();
		try {
			dados.addAll(clienteCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbCliente.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbPedidos.getColumns().addAll(new GenericTable<Pedido>().tableColunas(Pedido.class));	
		popularDados();
		tbPedidos.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);	
					setOperacao(1);
					setPedido(tbPedidos.getSelectionModel().getSelectedItem());
					SetValoresComponentes(getPedido());
				}
			}
		});
	}

	private void SetValoresComponentes(Pedido pedido) {
		dtPedido.setValue(ConverterUtil.utilDateToLocalDate(pedido.getDtPedido()));
		cbCliente.getSelectionModel().select(pedido.getCliente());
		txtObservacao.setText(pedido.getObservacao());
	}

	private void popularDados() {
		dadosPed.clear();
		dados.clear();
		tbPedidos.getItems().clear();
		cbCliente.getItems().clear();
		try {
			dadosPed.addAll(pedidoCtr.findAll());
			dados.addAll(clienteCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbPedidos.setItems(dadosPed);
		cbCliente.setItems(dados);
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
