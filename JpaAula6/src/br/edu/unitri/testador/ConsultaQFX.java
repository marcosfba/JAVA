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
import br.edu.unitri.DTO.Consultas.ConsultaLetraQ;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Local;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaQFX implements Initializable {

	private ObservableList<ConsultaLetraQ> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraQ> tbDados;
	@FXML private ToggleGroup buscarPor;
	@FXML private RadioButton rbNomeLocal;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraQ> query = cb.createQuery(ConsultaLetraQ.class);
		Root<Local> root = query.from(Local.class);
		Join<Local,Departamento> joinDep  = root.join("departamentos",JoinType.LEFT);
		query.multiselect(root.get("nomLocal").alias("codLocal"), root.get("descLocal").alias("nomeLocal"),
				  		joinDep.get("numDepartamento").alias("codDepartamento"), joinDep.get("descLocal").alias("nomeDepartamento"));
		
		if ((rbNomeLocal.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(root.get("nomLocal"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbNomeDep.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinDep.get("descLocal"),txtBuscar.getText());
			query.where(where);
		}
		if ((!rbNomeLocal.isSelected()) && (!rbNomeDep.isSelected())){
			Predicate where =  cb.equal(joinDep.get("numDepartamento"),"222");
			query.where(where).distinct(true);
		}
		
		List<ConsultaLetraQ> listaQ = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaQ);
		tbDados.setItems(dados);
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraQ());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraQ>().tableColunas(ConsultaLetraQ.class));
		popularDados();
	}

}
