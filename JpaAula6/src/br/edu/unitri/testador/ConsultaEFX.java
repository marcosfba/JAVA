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
import br.edu.unitri.DTO.Consultas.ConsultaLetraE;
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
public class ConsultaEFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbDescricaoLocal;
	@FXML private RadioButton rbCodLocal;
	@FXML private TableView<ConsultaLetraE> tbDados;
	@FXML private RadioButton rbProjeto;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;

	private ObservableList<ConsultaLetraE> dados = FXCollections.observableArrayList();
	private ExerciciosControler exerciciosCtr = new ExerciciosControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ConsultaLetraE> query = cb.createQuery(ConsultaLetraE.class);
		Root<Projeto> root = query.from(Projeto.class);
		Join<Projeto,Local> join = root.join("local", JoinType.LEFT);
		query.multiselect(root.get("numProjeto").alias("numProjeto"), join.get("nomLocal").alias("codLocal"), 
				join.get("descLocal").alias("nomeLocal"));
		
		if ((rbDescricaoLocal.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(join.get("descLocal"),txtBuscar.getText());
			query.where(where);
		}
		if ((rbCodLocal.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(join.get("nomLocal"),txtBuscar.getText());
			query.where(where);
		}		
		if ((rbProjeto.isSelected()) && (!txtBuscar.getText().isEmpty())){
			Predicate where = cb.like(root.get("numProjeto"),txtBuscar.getText());
			query.where(where);
		}		
		
		List<ConsultaLetraE> listaE = JpaUtil.getManager().createQuery(query).getResultList();
		tbDados.getItems().clear();
		dados.clear();
		dados.addAll(listaE);
		tbDados.setItems(dados);
	}

	private void popularDados() {
		tbDados.getItems().clear();
		dados.clear();
		try {
			dados.addAll(exerciciosCtr.findLetraE());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDados.getColumns().addAll(new GenericTable<ConsultaLetraE>().tableColunas(ConsultaLetraE.class));
		popularDados();		
	}

}
