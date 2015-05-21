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
import br.edu.unitri.DTO.Consultas.ConsultaLetraK;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaKFX implements Initializable {

	private ObservableList<ConsultaLetraK> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraK> tbDados;
	@FXML private RadioButton rbEmpregado;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private TabPane tabTela;
    @FXML private Button btnBuscar;
	@FXML private RadioButton rbCodDep;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraK> query = cb.createQuery(ConsultaLetraK.class);
		Root<Empregado> root = query.from(Empregado.class);
		Join<Empregado,Departamento> joinDep = root.join("departamento",JoinType.LEFT);
		query.multiselect(joinDep.get("numDepartamento").alias("numDepartamento"),joinDep.get("descLocal").alias("descDepartamento"),
				root.get("nomeEmpregado").alias("nomeEmpregado"));
		
		if ((rbEmpregado.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(root.get("nomeEmpregado"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbNomeDep.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinDep.get("descLocal"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbCodDep.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinDep.get("numDepartamento"),txtBuscar.getText());
			query.where(where);
		}
		if ((!rbEmpregado.isSelected()) && (!rbNomeDep.isSelected()) && (!rbCodDep.isSelected())){
			Predicate where =  cb.equal(root.get("codEmpregado"),7);
			query.where(where);
		}
		
		List<ConsultaLetraK> listaK = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaK);
		tbDados.setItems(dados);
	}
	
	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraK());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraK>().tableColunas(ConsultaLetraK.class));
		popularDados();		
	}

}
