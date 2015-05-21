/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
import br.edu.unitri.DTO.Consultas.ConsultaLetraB;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaBFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbCodEmpregado;
	@FXML private TableView<ConsultaLetraB> tbEmpregados;
	@FXML private ToggleGroup BuscarPor;
	@FXML private RadioButton rbNomeEmpregado;
	@FXML private TextField txtBuscar;
 	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	
	private ObservableList<ConsultaLetraB> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();
	
	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraB> query = cb.createQuery(ConsultaLetraB.class);
		Root<Empregado> root = query.from(Empregado.class);
		query.multiselect(root.get("codEmpregado").alias("codEmpregado"), root.get("nomeEmpregado").alias("nomeEmpregado"), 
				          root.get("endEmpregado").alias("endEmpregado"), root.get("dtNasc").alias("dtnasc"),root.get("sexo").alias("sexo"));
		
		if ((rbCodEmpregado.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.equal(root.get("codEmpregado"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbNomeEmpregado.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(root.get("nomeEmpregado"),txtBuscar.getText());
			query.where(where);
		}
		List<ConsultaLetraB> listaB = JpaUtil.getManager().createQuery(query).getResultList();
		tbEmpregados.getItems().clear();
		dados.clear();
		dados.addAll(listaB);
		tbEmpregados.setItems(dados);
	}


	private void popularDados() {
		tbEmpregados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraB());
		} catch (SQLException e) {
			e.printStackTrace();
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbEmpregados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbEmpregados.getColumns().addAll(new GenericTable<ConsultaLetraB>().tableColunas(ConsultaLetraB.class));
		popularDados();
	}
}
