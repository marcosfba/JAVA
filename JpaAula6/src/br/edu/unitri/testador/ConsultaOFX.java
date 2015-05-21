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
import javax.persistence.criteria.Subquery;

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
import br.edu.unitri.DTO.Consultas.ConsultaLetraO;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaOFX implements Initializable {

	private ObservableList<ConsultaLetraO> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraO> tbDados;
	@FXML private RadioButton rbNomeEmp;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraO> query = cb.createQuery(ConsultaLetraO.class);
		Root<Empregado> root = query.from(Empregado.class);
		Join<Empregado,Departamento> joinDep = root.join("departamento", JoinType.LEFT);
		query.multiselect(root.get("nomeEmpregado").alias("nomeEmpregado"), joinDep.get("numDepartamento").alias("numDepartamento"),
				joinDep.get("descLocal").alias("nomeDepartamento")).distinct(true);
		Subquery<ProjEmp> subQuery = query.subquery(ProjEmp.class);
		Root<ProjEmp> subRoot = subQuery.from(ProjEmp.class);
		subQuery.select(subRoot);
		Predicate where = cb.equal(subRoot.get("empregado"),root);
		subQuery.where(where);
		query.where(cb.not(cb.exists(subQuery)));
		
		if ((rbNomeEmp.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where1 = cb.like(root.get("nomeEmpregado"),txtBuscar.getText());
			query.where(where1);
		}
		if ((rbNomeDep.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where1 = cb.like(joinDep.get("descLocal"),txtBuscar.getText());
			query.where(where1);
		}
		List<ConsultaLetraO> listaO = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaO);
		tbDados.setItems(dados);
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraO());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraO>().tableColunas(ConsultaLetraO.class));
		popularDados();		
	}

}
