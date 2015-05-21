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
import br.edu.unitri.DTO.Consultas.ConsultaLetraO;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaOFX implements Initializable {

	private ObservableList<ConsultaLetraO> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraO> tbDados;
	@FXML private RadioButton rbNomeEmp;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		String qry = "select distinct e.nomeempregado, d.numdepartamento, d.desclocal"
					+ " from tbEmpregado e join tbDepartamento d on d.idDepartamento = e.departamento_id"
					+ " where e.codEmpregado not in (select empregado_id from Projeto_Emp)";
	   		String where ="";
			if (rbNomeEmp.isSelected()) {
				where = " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbNomeDep.isSelected()) {
				where = " where d.desclocal like  '%"+ txtBuscar.getText() + "%'";
			}			
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraO(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNomeDep.isSelected() || rbNomeEmp.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraO());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraO>().tableColunas(ConsultaLetraO.class));
		popularDados();		
	}

}
