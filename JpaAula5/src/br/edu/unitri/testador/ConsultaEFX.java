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
import br.edu.unitri.DTO.Consultas.ConsultaLetraE;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaEFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbDescricaoLocal;
	@FXML private RadioButton rbCodLocal;
	@FXML private TableView<ConsultaLetraE> tbDados;
	@FXML private RadioButton rbProjeto;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	private ObservableList<ConsultaLetraE> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {	
	   		String qry ="select p.numProjeto, l.nomLocal, l.descLocal from"
					+ " tbProjeto p join tbLocal l on l.idLocal = p.local_id";
	   		String where ="";
			if (rbProjeto.isSelected()) {
				where = " where p.numProjeto like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbCodLocal.isSelected()) {
				where = " where l.nomLocal like  '%"+ txtBuscar.getText() + "%'";
			}
			if (rbDescricaoLocal.isSelected()) {
				where = " where l.descLocal like  '%"+ txtBuscar.getText() + "%'";
			}
			
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraE(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbProjeto.isSelected() || rbCodLocal.isSelected() || rbDescricaoLocal.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraE());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraE>().tableColunas(ConsultaLetraE.class));
		popularDados();		
	}

}
