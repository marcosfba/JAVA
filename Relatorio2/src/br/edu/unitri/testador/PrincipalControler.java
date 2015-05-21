package br.edu.unitri.testador;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.persistence.EntityManager;

import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.JpaUtil;
import br.edu.unitri.util.Path;

public class PrincipalControler implements Initializable {

	@FXML private MenuItem menuItemCategorialick;
	@FXML private MenuItem menuItemProdutoClick;
	@FXML private MenuItem menuItemCategoriaRelClick;
	@FXML private MenuItem menuItemProdutoRelClick;
	@FXML private MenuItem menuItemFecharClick;

	@FXML
	void menuItemFecharClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja encerrar a aplicação?").showDialog();
		if (ok) {
			JpaUtil.closeManager(JpaUtil.getManager());
			System.exit(0);
		}
	}

	@FXML
	void menuItemCategorialick(ActionEvent event) {
		Stage telaPessoa = new Stage();
		try {
			new FormFX<CategoriaFX>("Categoria.fxml", telaPessoa, "Operações com Categoria", false);
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
	void menuItemCategoriaRelClick(ActionEvent event) {
		Stage relatorio = new Stage();
		try {
			new FormFX<RelCategoriaFX>("RelCategoria.fxml", relatorio, "Relatório de Categorias", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemProdutoRelClick(ActionEvent event) {
		Stage relatorio = new Stage();
		try {
			new FormFX<RelProdutoFX>("RelProduto.fxml", relatorio, "Relatório de Produtos", false);
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
