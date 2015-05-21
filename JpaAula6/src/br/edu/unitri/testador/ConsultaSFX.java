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
import br.edu.unitri.DTO.Consultas.ConsultaLetraR;
import br.edu.unitri.controler.ExerciciosControler;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ConsultaSFX implements Initializable {

	private ObservableList<ConsultaLetraR> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML private Tab tabConsulta;
	@FXML private TableView<ConsultaLetraR> tbDados;
	@FXML private RadioButton rbNomeEmp;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraR> query = cb.createQuery(ConsultaLetraR.class);
		Root<ProjEmp> root = query.from(ProjEmp.class);
		Join<ProjEmp,Empregado> joinEmp  = root.join("empregado",JoinType.LEFT);
		query.multiselect(cb.sum(root.get("quantHoras")).alias("qtdHoras"), joinEmp.get("nomeEmpregado").alias("nomeEmpregado"));
		
		if ((rbNomeEmp.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(joinEmp.get("nomeEmpregado"),txtBuscar.getText());
			query.where(where);
		}
		if ((!rbNomeEmp.isSelected())){
			Predicate where =  cb.equal(root.get("empregado"),4);
			query.where(where).distinct(true);
		}
		List<ConsultaLetraR> listaR = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaR);
		tbDados.setItems(dados);
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraS());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraR>().tableColunas(ConsultaLetraR.class));
		popularDados();
	}

}
