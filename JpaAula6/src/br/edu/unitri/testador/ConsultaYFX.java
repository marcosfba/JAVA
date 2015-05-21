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
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
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
import br.edu.unitri.DTO.Consultas.ConsultaLetraY;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Local;
import br.edu.unitri.model.Projeto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaYFX implements Initializable {

	private ObservableList<ConsultaLetraY> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraY> tbDados;
	@FXML private ToggleGroup buscarPor;
	@FXML private RadioButton rbNumLocal;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNumproj;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraY> query = cb.createQuery(ConsultaLetraY.class);
		Root<Projeto> root = query.from(Projeto.class);
		Join<Projeto,Local> joinLoc  = root.join("local",JoinType.LEFT);
		query.multiselect(root.get("descProjeto").alias("descProjeto"), root.get("numProjeto").alias("numProjeto"),
				joinLoc.get("nomLocal").alias("descLocal"), joinLoc.get("descLocal").alias("nomeLocal"));
		
		if ((rbNumLocal.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinLoc.get("nomLocal"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbNumproj.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(root.get("numProjeto"),txtBuscar.getText());
			query.where(where);
		}
		if ((!rbNumLocal.isSelected()) && (!rbNumproj.isSelected())){
			Predicate where =  root.get("departamento").isNull();
			query.where(where).distinct(true);
		}
		List<ConsultaLetraY> listaY = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaY);
		tbDados.setItems(dados);
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraY());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraY>().tableColunas(ConsultaLetraY.class));
		popularDados();
	}

}
