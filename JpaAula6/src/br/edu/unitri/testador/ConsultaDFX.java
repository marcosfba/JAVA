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
import br.edu.unitri.DTO.Consultas.ConsultaLetraD;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Dependente;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaDFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraD> tbDados;
	@FXML private RadioButton rbNomeEmpregado;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private RadioButton rbDependente;
	@FXML private Button btnBuscar;

	private ObservableList<ConsultaLetraD> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraD> query = cb.createQuery(ConsultaLetraD.class);
		Root<Dependente> root = query.from(Dependente.class);
		Join<Dependente,Empregado> join = root.join("empregado", JoinType.LEFT);
		query.multiselect(root.get("nome").alias("nomeDependente"), join.get("nomeEmpregado").alias("nomeEmpregado"));
		
		if ((rbDependente.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(root.get("nome"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbNomeEmpregado.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(join.get("nomeEmpregado"),txtBuscar.getText());
			query.where(where);
		}		

		List<ConsultaLetraD> listaD = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaD);
		tbDados.setItems(dados);
}
	
	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraD());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraD>().tableColunas(ConsultaLetraD.class));
		popularDados();		
	}

}
