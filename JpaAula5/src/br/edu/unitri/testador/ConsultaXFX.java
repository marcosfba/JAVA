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
import br.edu.unitri.DTO.Consultas.LetraBDep;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaXFX implements Initializable {

	private ObservableList<LetraBDep> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<LetraBDep> tbDados;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
	   		String qry = "select d.nome, d.dtnascimento, d.idEmpregado,"
					+ " case when d.sexo = 0 then 'Masculino' when d.sexo = 1 then 'Feminino' when d.sexo = 2 then 'Indefinido' end as Sexo,"
					+ " case when d.tipodependente = 0 then 'M�e' when d.tipodependente = 1 then 'Pai' when d.tipodependente = 2 then 'Filho'"
					+ "      when d.tipodependente = 3 then 'Filha' when d.tipodependente = 4 then 'Neto' when d.tipodependente = 5 then 'Neta'"
					+ "      when d.tipodependente = 6 then 'Irm�o' when d.tipodependente = 7 then 'Irm�' when d.tipodependente = 8 then 'Conjuge'"
					+ "      when d.tipodependente = 9 then 'Avos' end as TipoDependente"
					+ " from tbDependente d";
	   		
			String where ="";
			if (rbNomeDep.isSelected()) {
				where = " where d.nome like  '%"+ txtBuscar.getText() + "%'";
			}
			tbDados.getItems().clear();			
			dados.addAll(exerciciosCtr.findLetraX(qry,where));
			tbDados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das op��es para consulta!").showDialog();
			popularDados();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNomeDep.isSelected() ;
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraX());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<LetraBDep>().tableColunas(LetraBDep.class));
		popularDados();
	}

}
