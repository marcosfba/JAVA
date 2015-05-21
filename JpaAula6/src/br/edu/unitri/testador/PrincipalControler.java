package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import javax.persistence.EntityManager;

import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.JpaUtil;

public class PrincipalControler implements Initializable {

	@FXML private MenuItem menuItemFecharClick;
	@FXML private MenuItem menuItemAboutClick;
	@FXML private MenuItem menuItemLocalClick;
	@FXML private MenuItem menuItemDepartamentoClick;
	@FXML private MenuItem menuItemProjetoClick;
	@FXML private MenuItem menuItemEmpregadoClick;
	@FXML private MenuItem menuItemDependentesClick;
	@FXML private MenuItem menuItemGerenteClick;
	@FXML private MenuItem menuItemLocalDepartamentoClick;
	@FXML private MenuItem menuItemProjetoEmpregadoClick;
	@FXML private MenuItem menuIemGerenteEmpregadoClick;
	@FXML private MenuItem menuItemConsultaAClick;
	@FXML private MenuItem menuItemConsultaBClick;
	@FXML private MenuItem menuItemConsultaCClick;
	@FXML private MenuItem menuItemConsultaDClick;
	@FXML private MenuItem menuItemConsultaEClick;
	@FXML private MenuItem menuItemConsultaFClick;
	@FXML private MenuItem menuItemConsultaGClick;
	@FXML private MenuItem menuItemConsultaHClick;
	@FXML private MenuItem menuItemConsultaIClick;
	@FXML private MenuItem menuItemConsultaJClick;
	@FXML private MenuItem menuItemConsultaKClick;
	@FXML private MenuItem menuItemConsultaLClick;
	@FXML private MenuItem menuItemConsultaMClick;
	@FXML private MenuItem menuItemConsultaNClick;
	@FXML private MenuItem menuItemConsultaOClick;
	@FXML private MenuItem menuItemConsultaPClick;
	@FXML private MenuItem menuItemConsultaQClick;
	@FXML private MenuItem menuItemConsultaRClick;
	@FXML private MenuItem menuItemConsultaSClick;
	@FXML private MenuItem menuItemConsultaTClick;
	@FXML private MenuItem menuItemConsultaUClick;
	@FXML private MenuItem menuItemConsultaVClick;
	@FXML private MenuItem menuItemConsultaXClick;
	@FXML private MenuItem menuItemConsultaYClick;
	@FXML private MenuItem menuItemConsultaZClick;


	@FXML
	void menuItemFecharClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja encerrar a aplicação?").showDialog();
		if (ok) {
			JpaUtil.closeManager(JpaUtil.getManager());
			System.exit(0);
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

	@FXML
	void menuItemLocalClick(ActionEvent event) {
		Stage telaLocal = new Stage();
		try {
			new FormFX<LocalFX>("Local.fxml", telaLocal, "Locais", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemDepartamentoClick(ActionEvent event) {
		Stage teladepartamento = new Stage();
		try {
			new FormFX<DepartamentoFX>("Departamento.fxml", teladepartamento, "Departamentos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemProjetoClick(ActionEvent event) {
		Stage telaProjeto = new Stage();
		try {
			new FormFX<ProjetoFX>("Projeto.fxml", telaProjeto, "Projetos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemEmpregadoClick(ActionEvent event) {
		Stage telaempregado = new Stage();
		try {
			new FormFX<DepartamentoFX>("Empregado.fxml", telaempregado, "Empregados", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemDependentesClick(ActionEvent event) {
		Stage teladependente = new Stage();
		try {
			new FormFX<DependenteFX>("Dependente.fxml", teladependente, "Dependente", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemGerenteClick(ActionEvent event) {
		Stage telaGerente = new Stage();
		try {
			new FormFX<GerenteFX>("Gerente.fxml", telaGerente, "Gerente", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemLocalDepartamentoClick(ActionEvent event) {
		Stage telalocalDep = new Stage();
		try {
			new FormFX<LocalDepartamentosFX>("LocalDepartamentos.fxml", telalocalDep, "Locais / Departamentos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemProjetoEmpregadoClick(ActionEvent event) {
		Stage telaProjEmp = new Stage();
		try {
			new FormFX<ProjetoEmpregadoFX>("ProjetoEmpregado.fxml", telaProjEmp, "Projetos / Empregados", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuIemGerenteEmpregadoClick(ActionEvent event) {
		Stage telaGerenteEmpregado = new Stage();
		try {
			new FormFX<GerenteEmpregadoFX>("GerenteEmpregado.fxml", telaGerenteEmpregado, "Gerente / Empregados", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@FXML
	void menuItemConsultaAClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaAFX>("ConsultaA.fxml", telaConsulta, "Exercicios Letra A", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaBClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaBFX>("ConsultaB.fxml", telaConsulta, "Exercicios Letra B", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaCClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaCFX>("ConsultaC.fxml", telaConsulta, "Exercicios Letra C", false);
		} catch (Exception e) {
			e.printStackTrace();
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaDClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaDFX>("ConsultaD.fxml", telaConsulta, "Exercicios Letra D", false);
		} catch (Exception e) {
			e.printStackTrace();
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaEClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaEFX>("ConsultaE.fxml", telaConsulta, "Exercicios Letra E", false);
		} catch (Exception e) {
			e.printStackTrace();
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaFClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaFFX>("ConsultaF.fxml", telaConsulta, "Exercicios Letra F", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaGClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaGFX>("ConsultaG.fxml", telaConsulta, "Exercicios Letra G", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaHClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaHFX>("ConsultaH.fxml", telaConsulta, "Exercicios Letra H", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaIClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaIFX>("ConsultaI.fxml", telaConsulta, "Exercicios Letra I", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaJClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaJFX>("ConsultaJ.fxml", telaConsulta, "Exercicios Letra J", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaKClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaKFX>("ConsultaK.fxml", telaConsulta, "Exercicios Letra K", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaLClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaLFX>("ConsultaL.fxml", telaConsulta, "Exercicios Letra L", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaMClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaMFX>("ConsultaM.fxml", telaConsulta, "Exercicios Letra M", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaNClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaNFX>("ConsultaN.fxml", telaConsulta, "Exercicios Letra N", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaOClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaOFX>("ConsultaO.fxml", telaConsulta, "Exercicios Letra O", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaPClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaPFX>("ConsultaP.fxml", telaConsulta, "Exercicios Letra P", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaQClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaQFX>("ConsultaQ.fxml", telaConsulta, "Exercicios Letra Q", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaRClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaRFX>("ConsultaR.fxml", telaConsulta, "Exercicios Letra R", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaSClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaSFX>("ConsultaS.fxml", telaConsulta, "Exercicios Letra S", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaTClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaTFX>("ConsultaT.fxml", telaConsulta, "Exercicios Letra T", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaUClick(ActionEvent event) throws SQLException {
		ExerciciosControler exercicios = new ExerciciosControler();
		String resultado = exercicios.findLetraU().toString();
		new FXDialog(Type.INFO,"Media das horas é -->" +  resultado).showDialog();
	}
	
	@FXML
	void menuItemConsultaVClick(ActionEvent event) throws SQLException {
		ExerciciosControler exercicios = new ExerciciosControler();
		String resultado = exercicios.findLetraV().toString();
		new FXDialog(Type.INFO,"Quantidade de empregados é-->" +  resultado).showDialog();
	}
	
	@FXML
	void menuItemConsultaXClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaXFX>("ConsultaX.fxml", telaConsulta, "Exercicios Letra X", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaYClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaYFX>("ConsultaY.fxml", telaConsulta, "Exercicios Letra Y", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}
	
	@FXML
	void menuItemConsultaZClick(ActionEvent event) {
		Stage telaConsulta = new Stage();
		try {
			new FormFX<ConsultaZFX>("ConsultaZ.fxml", telaConsulta, "Exercicios Letra Z", false);
		} catch (Exception e) {
			e.printStackTrace();
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		EntityManager manager = JpaUtil.getManager();
		manager.clear();
		
	}
}
