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

	@FXML private MenuItem menuItemPessoaClick;
	@FXML private MenuItem menuItemPessoaFisicaClick;
	@FXML private MenuItem menuItemPessoaJuridiClick;
	@FXML private MenuItem menuItemFecharClick;
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
	void menuItemPessoaClick(ActionEvent event) {
		Stage telaPessoa = new Stage();
		try {
			new FormFX<PessoaFX>("Pessoa.fxml", telaPessoa, "Operações com Pessoa", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemPessoaFisicaClick(ActionEvent event) {
		Stage telaPessoaFisica = new Stage();
		try {
			new FormFX<PessoaFisicaFX>("PessoaFisica.fxml", telaPessoaFisica, "Operações com Pessoa Fisica", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemPessoaJuridiClick(ActionEvent event) {
		Stage telaPessoaJuridica = new Stage();
		try {
			new FormFX<PessoaJuridicaFX>("PessoaJuridica.fxml", telaPessoaJuridica, "Operações com Pessoa Juridica", false);
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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		EntityManager manager = JpaUtil.getManager();
		manager.clear();
		
	}
}
