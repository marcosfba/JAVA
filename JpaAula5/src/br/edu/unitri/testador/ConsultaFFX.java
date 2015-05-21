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
import br.edu.unitri.DTO.Consultas.ConsultaLetraF;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaFFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraF> tbDados;
	@FXML private RadioButton rbProjeto;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private RadioButton rbCodDep;

	private ObservableList<ConsultaLetraF> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {	
	   		String qry = "select d.numDepartamento, d.descLocal, p.numProjeto"
					+ " from tbProjeto p join tbDepartamento d on d.idDepartamento = p.departamento_id";
	   		String where ="";
			if (rbProjeto.isSelected()) {
				where = " where p.numProjeto like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbCodDep.isSelected()) {
				where = " where d.numDepartamento like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbNomeDep.isSelected()) {
				where = " where d.descLocal like  '%"+ txtBuscar.getText() + "%'";
			}
			
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraF(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}
	
	private boolean isValidConsulta() {
		boolean ok = rbProjeto.isSelected() || rbCodDep.isSelected() || rbNomeDep.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraF());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraF>().tableColunas(ConsultaLetraF.class));
		popularDados();		
	}

}
