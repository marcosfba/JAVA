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
import br.edu.unitri.DTO.Consultas.ConsultaLetraC;
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
public class ConsultaCFX implements Initializable {

	@FXML private Tab tabConsulta;
 	@FXML private RadioButton rbDepartamento;
 	@FXML private TableView<ConsultaLetraC> tbDados;
	@FXML private RadioButton rbNomeEmpregado;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
 	@FXML private Button btnBuscar;

	private ObservableList<ConsultaLetraC> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraC> query = cb.createQuery(ConsultaLetraC.class);
		Root<Empregado> root = query.from(Empregado.class);
		Join<Empregado,Departamento> join = root.join("departamento", JoinType.LEFT);
		query.multiselect(root.get("nomeEmpregado").alias("nomeEmpregado"), join.get("descLocal").alias("nomeDepartamento"));
		
		if ((rbDepartamento.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(join.get("descLocal"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbNomeEmpregado.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(root.get("nomeEmpregado"),txtBuscar.getText());
			query.where(where);
		}		
		List<ConsultaLetraC> listaC = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaC);
		tbDados.setItems(dados);
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraC());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraC>().tableColunas(ConsultaLetraC.class));
		popularDados();		
	}

}
