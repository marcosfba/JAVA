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

public class PrincipalControler implements Initializable {

	@FXML private MenuItem menuItemClienteClick;
	@FXML private MenuItem menuItemFuncionarioClick;
	@FXML private MenuItem menuItemCategoriaClick;
	@FXML private MenuItem menuItemVeiculoClick;	
	@FXML private MenuItem menuItemEmprestimoClick;
	@FXML private MenuItem menuItemDevolucaoClick;
	@FXML private MenuItem menuItemAvariaClick;
	@FXML private MenuItem menuItemMultaClick;
	@FXML private MenuItem menuItemAboutClick;

	@FXML
	void menuItemFecharClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja encerrar a aplicação?").showDialog();
		if (ok) {
			JpaUtil.closeManager(JpaUtil.getManager());
			System.exit(0);
		}
	}

	@FXML
	void menuItemClienteClick(ActionEvent event) {
		Stage telaCliente = new Stage();
		try {
			new FormFX<ClienteFX>("Cliente.fxml", telaCliente, "Cadastros - Operações com Clientes", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
	}

	@FXML
	void menuItemFuncionarioClick(ActionEvent event) {
		Stage telaFuncionario = new Stage();
		try {
			new FormFX<FuncionarioFX>("Funcionario.fxml", telaFuncionario, "Cadastros - Operações com Funcionários", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
	}
	
	@FXML
	void menuItemCategoriaClick(ActionEvent event) {
		Stage telaCategoria = new Stage();
		try {
			new FormFX<CategoriaFX>("Categoria.fxml", telaCategoria, "Cadastros - Operações com Categoria", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
	}
	
	@FXML
	void menuItemVeiculoClick(ActionEvent event) {
		Stage telaVeiculo = new Stage();
		try {
			new FormFX<VeiculoFX>("Veiculo.fxml", telaVeiculo, "Cadastros - Operações com Veículos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
	}
	
	@FXML
	void menuItemEmprestimoClick(ActionEvent event) {
		Stage telaEmprestimo = new Stage();
		try {
			new FormFX<EmprestimoFX>("Emprestimo.fxml", telaEmprestimo, "Operações - Operações com Empréstimos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
	}

	@FXML
	void menuItemDevolucaoClick(ActionEvent event) {
		Stage telaDevolucao = new Stage();
		try {
			new FormFX<DevolucaoFX>("Devolucao.fxml", telaDevolucao, "Operações - Operações com Devoluções", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
	}

	@FXML
	void menuItemAvariaClick(ActionEvent event) {
		Stage telaAvaria = new Stage();
		try {
			new FormFX<AvariaFX>("Avaria.fxml", telaAvaria, "Operações - Operações com Avaria", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
	}
	
	@FXML
	void menuItemMultaClick(ActionEvent event) {
		Stage telaMulta = new Stage();
		try {
			new FormFX<MultaFX>("Multa.fxml", telaMulta, "Operações - Operações com Multa", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		EntityManager manager = JpaUtil.getManager();
		manager.clear();
		
	}
}
