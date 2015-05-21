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
import br.edu.unitri.DTO.Consultas.LetraBDep;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Dependente;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.TipoSexo;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

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
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<LetraBDep> query = cb.createQuery(LetraBDep.class);
		Root<Dependente> root = query.from(Dependente.class);
		Join<Dependente,Empregado> joinEmp = root.join("empregado", JoinType.LEFT);
		query.multiselect(root.get("nome").alias("nomeDependente"),root.get("sexo").alias("sexoDependente"),
				root.get("tipoDependente").alias("tipoDependente"),root.get("dtNascimento").alias("dtNascDependente"),
				joinEmp.get("nomeEmpregado").alias("nomeEmpregada"));
		Predicate where =  cb.equal(joinEmp.get("sexo"),TipoSexo.FEMININO);
		query.where(where);
		
		if ((rbNomeDep.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where1 = cb.like(root.get("nome"),txtBuscar.getText());
			query.where(where1);
		}
		List<LetraBDep> listaB = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaB);
		tbDados.setItems(dados);
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
