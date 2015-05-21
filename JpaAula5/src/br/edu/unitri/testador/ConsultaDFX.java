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
import br.edu.unitri.DTO.Consultas.ConsultaLetraD;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaDFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraD> tbDados;
	@FXML private RadioButton rbNomeEmpregado;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private RadioButton rbDependente;
	@FXML private Button btnBuscar;

	private ObservableList<ConsultaLetraD> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {	   		
	   		String qry = "select d.nome, e.nomeempregado"
					+ " from tbDependente d join tbEmpregado e on e.codEmpregado = d.idEmpregado";
	   		String where ="";
			if (rbDependente.isSelected()) {
				where = " where d.nome like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbNomeEmpregado.isSelected()) {
				where = " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'";
			}
			
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraD(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}
	
	private boolean isValidConsulta() {
		boolean ok = rbDependente.isSelected() || rbNomeEmpregado.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraD());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraD>().tableColunas(ConsultaLetraD.class));
		popularDados();		
	}

}
