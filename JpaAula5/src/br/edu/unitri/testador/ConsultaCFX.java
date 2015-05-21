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
import br.edu.unitri.DTO.Consultas.ConsultaLetraC;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaCFX implements Initializable {

	@FXML private Tab tabConsulta;
 	@FXML private RadioButton rbDepartamento;
 	@FXML private TableView<ConsultaLetraC> tbDados;
	@FXML private RadioButton rbNomeEmpregado;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
 	@FXML private Button btnBuscar;

	private ObservableList<ConsultaLetraC> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		
	   		String qry = "select (d.numDepartamento || '--'|| d.descLocal) as descricao, e.nomeEmpregado from tbDepartamento d"
					+ " join tbEmpregado e on e.departamento_id = d.idDepartamento";
	   		String where ="";
			if (rbDepartamento.isSelected()) {
				where = " where (d.numdepartamento like  '%"+ txtBuscar.getText() + "%') or"
					 + " (d.desclocal like  '%"+ txtBuscar.getText() + "%')";
			}
			if (rbNomeEmpregado.isSelected()) {
				where = " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'";
			}
			
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraC(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbDepartamento.isSelected() || rbNomeEmpregado.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraC());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraC>().tableColunas(ConsultaLetraC.class));
		popularDados();		
	}

}
