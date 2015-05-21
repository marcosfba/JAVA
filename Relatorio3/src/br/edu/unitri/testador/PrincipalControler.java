package br.edu.unitri.testador;

import java.net.URL;
import java.util.ResourceBundle;

import javax.persistence.EntityManager;

import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.JpaUtil;
import br.edu.unitri.util.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class PrincipalControler implements Initializable {

	@FXML private MenuItem menuItemCategoriaRel;
	@FXML private MenuItem menuItemCliente;
	@FXML private MenuItem menuItemCategoria;
	@FXML private MenuItem menuItemItensPedido;
	@FXML private MenuItem menuItemClienteRel;
	@FXML private MenuItem menuItemPedidoRel;
	@FXML private MenuItem menuItemProduto;
	@FXML private MenuItem menuItemConsCategoria;
	@FXML private MenuItem menuItemProdutoRel;
	@FXML private MenuItem menuItemConsPedido;
	@FXML private MenuItem menuItemPedido;
	@FXML private MenuItem menuItemConsProduto;
	@FXML private MenuItem menuItemFechar;
	@FXML private MenuItem menuItemConsCliente;
	@FXML private MenuItem menuItemClientePedidoRel;
	@FXML private MenuItem menuItemProdutoQtd;


	@FXML
	void menuItemFecharClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM, "Tem certeza que deseja encerrar a aplicação?").showDialog();
		if (ok) {
			JpaUtil.closeManager(JpaUtil.getManager());
			System.exit(0);
		}
	}

	@FXML
	void menuItemCategorialick(ActionEvent event) {
		Stage telaCategoria = new Stage();
		try {
			new FormFX<CategoriaFX>("Categoria.fxml", telaCategoria, "Operações com Categoria", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemProdutoClick(ActionEvent event) {
		Stage telaProduto = new Stage();
		try {
			new FormFX<ProdutoFX>("Produto.fxml", telaProduto, "Operações com Produto", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemClienteClick(ActionEvent event) {
		Stage telaCliente = new Stage();
		try {
			new FormFX<ClienteFX>("Cliente.fxml", telaCliente, "Operações com Cliente", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemPedidoClick(ActionEvent event) {
		Stage telaPedido = new Stage();
		try {
			new FormFX<PedidoFX>("Pedido.fxml", telaPedido, "Operações com Pedido", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemItensPedidoClick(ActionEvent event) {
		Stage telaItens = new Stage();
		try {
			new FormFX<ItensPedidoFX>("ItensPedido.fxml", telaItens, "Operações com Itens do Pedido", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemConsCategorialick(ActionEvent event) {
		Stage telaCategoria = new Stage();
		try {
			new FormFX<ConsCategoriaFX>("ConsCategoria.fxml", telaCategoria, "Consulta de Categorias", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemConsProdutoClick(ActionEvent event) {
		Stage telaProduto = new Stage();
		try {
			new FormFX<ConsProdutoFX>("ConsProduto.fxml", telaProduto, "Consulta de Produtos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemConsClienteClick(ActionEvent event) {
		Stage telaCliente = new Stage();
		try {
			new FormFX<ConsClienteFX>("ConsCliente.fxml", telaCliente, "Consulta de Clientes", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemConsPedidoClick(ActionEvent event) {
		Stage telaPedido = new Stage();
		try {
			new FormFX<ConsPedidoFX>("ConsPedido.fxml", telaPedido, "Consulta de Pedidos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemCategoriaRelClick(ActionEvent event) {
		Stage telaCategoria = new Stage();
		try {
			new FormFX<RelCategoriaFX>("RelCategoria.fxml", telaCategoria, "Relatório de Categorias", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemProdutoRelClick(ActionEvent event) {
		Stage telaProduto = new Stage();
		try {
			new FormFX<RelProdutoFX>("RelProduto.fxml", telaProduto, "Relatório de Produtos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemClienteRelClick(ActionEvent event) {
		Stage telaCliente = new Stage();
		try {
			new FormFX<RelClienteFX>("RelCliente.fxml", telaCliente, "Relatório de Clientes", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemPedidosRelClick(ActionEvent event) {
		Stage telaPedido = new Stage();
		try {
			new FormFX<RelPedidoFX>("RelPedido.fxml", telaPedido, "Relatório de Pedidos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemPedidosClienteRelClick(ActionEvent event) {
		Stage telaPedidoCliente = new Stage();
		try {
			new FormFX<RelPedidoClienteFX>("RelPedidoCliente.fxml", telaPedidoCliente, "Relatório de Pedidos por Clientes", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemProdutoQtdClick(ActionEvent event) {
		Stage telaProdQtd = new Stage();
		try {
			new FormFX<RelProdutoQtdFX>("RelProdutoQtd.fxml", telaProdQtd, "Relatório de Produtos/Quantidade/Mensal", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemAboutClick(ActionEvent event) {
		Stage telaAbout = new Stage();
		try {
			new FormFX<AboutFX>("About.fxml", telaAbout, "Sobre o Desenvolvedor", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@SuppressWarnings("unused")
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		EntityManager manager = JpaUtil.getManager();
		manager.clear();
		Path path = new Path();

	}
}
