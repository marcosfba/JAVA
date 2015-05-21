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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import br.edu.unitri.DTO.Consultas.ConsultaLetraN;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaNFX implements Initializable {

	private ObservableList<ConsultaLetraN> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraN> tbDados;
	@FXML private RadioButton rbNomeEmp;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeProj;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		String qry = "select distinct e.nomeempregado, d.numdepartamento, d.desclocal, p.descprojeto,"
					+ " (select e1.nomeempregado from tbEmpregado e1 where e1.codEmpregado = e.gerente_id) as nomeGerente"
					+ " from tbEmpregado e join tbDepartamento d on d.idDepartamento = e.departamento_id"
					+ " join Projeto_Emp pe on pe.empregado_id = e.codEmpregado"
					+ " join tbProjeto p on p.idProjeto = pe.projeto_id"
					+ " join tbGerencia g on g.gerente_id = e.gerente_id";
	   		String where ="";
			if (rbNomeEmp.isSelected()) {
				where = " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbNomeProj.isSelected()) {
				where = " where p.descprojeto like  '%"+ txtBuscar.getText() + "%'";
			}			
			if (rbNomeDep.isSelected()) {
				where = " where d.desclocal like  '%"+ txtBuscar.getText() + "%'";
			}			
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraN(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNomeDep.isSelected() || rbNomeProj.isSelected() || rbNomeEmp.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraN());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraN>().tableColunas(ConsultaLetraN.class));
		popularDados();		
	}

}
