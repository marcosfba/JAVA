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
import br.edu.unitri.DTO.Consultas.ConsultaLetraM;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.model.Projeto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaMFX implements Initializable {

	private ObservableList<ConsultaLetraM> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraM> tbDados;
	@FXML private RadioButton rbNomeEmp;
	@FXML private RadioButton rbProjeto;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraM> query = cb.createQuery(ConsultaLetraM.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Projeto> joinPro = root.join("projeto",JoinType.LEFT);
		Join<ProjEmp,Empregado> joinEmp = root.join("empregado", JoinType.LEFT);
		Join<Projeto,Departamento> joinDep = joinPro.join("departamento", JoinType.INNER);	
		query.multiselect(joinEmp.get("nomeEmpregado").alias("nomeEmpregado"), joinDep.get("numDepartamento").alias("numDepartamento"),
				joinDep.get("descLocal").alias("nomeDepartamento"), joinPro.get("descProjeto").alias("nomeProjeto")).distinct(true);
		
		if ((rbNomeEmp.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinEmp.get("nomeEmpregado"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbProjeto.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinPro.get("descProjeto"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbNomeDep.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinDep.get("descLocal"),txtBuscar.getText());
			query.where(where);
		}
		List<ConsultaLetraM> listaM = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaM);
		tbDados.setItems(dados);
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraM());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraM>().tableColunas(ConsultaLetraM.class));
		popularDados();		
	}

}
