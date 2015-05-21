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
import br.edu.unitri.DTO.Consultas.ConsultaLetraZ;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaZFX implements Initializable {

	private ObservableList<ConsultaLetraZ> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraZ> tbDados;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private RadioButton rbCodDep;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		String qry = "select d.numDepartamento, d.descLocal, count(ld.local_id)"
					+ " from tbDepartamento d"
					+ " join Local_Dept ld on ld.departamento_id = d.idDepartamento"
					+ " join tbLocal l on l.idLocal = ld.local_id";
	   		
	   		String grb = " group by d.idDepartamento having count(ld.local_id) > 1";

	   		String where ="";
			if (rbCodDep.isSelected()) {
				where = " where d.numDepartamento like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbNomeDep.isSelected()) {
				where = " where d.descLocal like  '%"+ txtBuscar.getText() + "%'";
			}
			if (where.isEmpty()) { where = grb; }
			else {
				where = where + grb;
			}
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraZ(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbCodDep.isSelected() || rbNomeDep.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraZ());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraZ>().tableColunas(ConsultaLetraZ.class));
		popularDados();
	}

}
