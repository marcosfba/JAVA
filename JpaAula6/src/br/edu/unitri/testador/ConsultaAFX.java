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
import br.edu.unitri.DTO.Consultas.ConsultaLetraA;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaAFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraA> tbDados;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	
	private ObservableList<ConsultaLetraA> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {	
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraA> query = cb.createQuery(ConsultaLetraA.class);
		Root<Departamento> root = query.from(Departamento.class);
		query.multiselect(root.get("numDepartamento").alias("numDepartamento"),root.get("descLocal").alias("descLocal"));		
		if (!txtBuscar.getText().isEmpty()){
			Predicate where = cb.like(root.get("descLocal"),txtBuscar.getText());
			query.where(where);
		}
		List<ConsultaLetraA> listaA = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaA);
		tbDados.setItems(dados);
	}
	
	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraA());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraA>().tableColunas(ConsultaLetraA.class));
		popularDados();		
	}

}
