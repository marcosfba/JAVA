/**
 * 
 */
package br.edu.unitri.testador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.edu.unitri.controler.ItensPedidoControler;
import br.edu.unitri.controler.PedidoControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.model.ItensPedido;
import br.edu.unitri.model.Pedido;
import br.edu.unitri.model.Produto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * @author marcos.fernando
 *
 */
public class ItensPedidoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<ItensPedido> tbItensPedidos;
	@FXML private Tab tabCadastro;
	@FXML private ComboBox<Produto> cbProduto;
	@FXML private Button btnPedido;
	@FXML private TextField txtBuscar;
	@FXML private TextField txtVlrItem;
	@FXML private TextField txtVlrDesc;
	@FXML private TextField txtQtd;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbProduto;
	@FXML private Button btnProduto;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private ComboBox<Pedido> cbPedido;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	@FXML private RadioButton rbPedido;
	
	private ObservableList<ItensPedido> dados = FXCollections.observableArrayList();
	private ObservableList<Pedido> dadosPed = FXCollections.observableArrayList();
	private ObservableList<Produto> dadosProd = FXCollections.observableArrayList();
	
	private int Operacao;
	private ItensPedido itensPedido;
	
	private PedidoControler pedidoCtr = new PedidoControler();
	private ProdutoControler produtoCtr = new ProdutoControler();
	private ItensPedidoControler itensCtr = new ItensPedidoControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		tbItensPedidos.getItems().clear();
	   		dadosPed.clear();
			if (rbPedido.isSelected()) {
				dados.addAll(itensCtr.findAll("select p.* from tb_ItensPedido p"," where p.pedido_id ="+ txtBuscar.getText()));			
			} else if (rbProduto.isSelected()) {
				dados.addAll(itensCtr.findAll("select p.* from tb_ItensPedido p"," where p.produto_id="+ txtBuscar.getText()));
			}
			tbItensPedidos.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbPedido.isSelected() || rbProduto.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			ItensPedido itensPedido = new ItensPedido(cbPedido.getSelectionModel().getSelectedItem(), cbProduto.getSelectionModel().getSelectedItem(),
					Integer.valueOf(txtQtd.getText()), new BigDecimal(txtVlrItem.getText()), new BigDecimal(txtVlrDesc.getText()));
			switch (getOperacao()) {
			case 0:
				try {
					if (itensCtr.save(itensPedido) != null) {
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
					if (itensCtr.update(itensPedido,(int) getItensPedido().getCodItemPedido())) {
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
		if (cbPedido.getSelectionModel().getSelectedItem() == null) {
			new FXDialog(Type.WARNING, "Favor selecionar o codigo do Pedido!").showDialog();			
			cbPedido.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbProduto.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o Produto!").showDialog();
				cbProduto.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtQtd.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a quantidade!").showDialog();
				txtQtd.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtVlrItem.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o valor do item!").showDialog();
				txtVlrItem.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtVlrDesc.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o valor do desconto!").showDialog();
				txtVlrDesc.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		cbPedido.getSelectionModel().select(null);
		cbProduto.getSelectionModel().select(null);
		txtQtd.setText("");
		txtVlrDesc.setText("");
		txtVlrItem.setText("");
		cbPedido.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = itensCtr.delete(getItensPedido());
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
	void btnPedidoClick(ActionEvent event) {
		Stage telaPedido = new Stage();
		try {
			new FormFX<PedidoFX>("Pedido.fxml", telaPedido, "Operações com Pedido", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbPedido.getItems().clear();
		dadosPed.clear();
		try {
			dadosPed.addAll(pedidoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbPedido.setItems(dadosPed);
	}

	@FXML
	void btnProdutoClick(ActionEvent event) {
		Stage telaProduto = new Stage();
		try {
			new FormFX<ProdutoFX>("Produto.fxml", telaProduto, "Operações com Produto", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbProduto.getItems().clear();
		dadosProd.clear();
		try {
			dadosProd.addAll(produtoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbProduto.setItems(dadosProd);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbItensPedidos.getColumns().addAll(new GenericTable<ItensPedido>().tableColunas(ItensPedido.class));	
		popularDados();
		tbItensPedidos.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);	
					setOperacao(1);
					setItensPedido(tbItensPedidos.getSelectionModel().getSelectedItem());
					SetValoresComponentes(getItensPedido());
				}
			}
		});
	}

	private void SetValoresComponentes(ItensPedido itensPedido) {
		cbPedido.getSelectionModel().select(itensPedido.getPedido());
		cbProduto.getSelectionModel().select(itensPedido.getProduto());
		txtQtd.setText(String.valueOf(itensPedido.getQtdItem()));
		txtVlrDesc.setText(String.valueOf(itensPedido.getVlrDesconto()));
		txtVlrItem.setText(String.valueOf(itensPedido.getVlrItem()));
	}

	private void popularDados() {
		dados.clear();
		dadosPed.clear();
		dadosProd.clear();
		tbItensPedidos.getItems().clear();
		cbPedido.getItems().clear();
		cbProduto.getItems().clear();
		try {
			dados.addAll(itensCtr.findAll());
			dadosPed.addAll(pedidoCtr.findAll());
			dadosProd.addAll(produtoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbItensPedidos.setItems(dados);
		cbPedido.setItems(dadosPed);
		cbProduto.setItems(dadosProd);
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public ItensPedido getItensPedido() {
		return itensPedido;
	}

	public void setItensPedido(ItensPedido itensPedido) {
		this.itensPedido = itensPedido;
	}
	
}
