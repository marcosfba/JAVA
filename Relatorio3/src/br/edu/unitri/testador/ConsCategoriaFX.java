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
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;
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

/**
 * @author marcos.fernando
 *
 */
public class ConsCategoriaFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<Categoria> tbCategorias;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbDescricao;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnLimpar;

	private ObservableList<Categoria> dados = FXCollections.observableArrayList();
	private CategoriaControler categoriaCtr = new CategoriaControler();

	@FXML
	void btnBuscarClick(ActionEvent event) {

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Categoria> query = cb.createQuery(Categoria.class);
		EntityType<Categoria> type = JpaUtil.getManager().getMetamodel().entity(Categoria.class);
		Root<Categoria> root = query.from(Categoria.class);

		if (rbNome.isSelected()) {
			query.where(cb.or(cb.like(root.get(type.getDeclaredSingularAttribute("nomeCategoria", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		if (rbDescricao.isSelected()) {
			query.where(cb.or(cb.like(root.get(type.getDeclaredSingularAttribute("descCategoria", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		List<Categoria> listaA = JpaUtil.getManager().createQuery(query).getResultList();
		dados.clear();
		tbCategorias.getItems().clear();
		dados.addAll(listaA);
		tbCategorias.setItems(dados);
	}

	@FXML
	void btnLimparClick(ActionEvent event) {
		txtBuscar.setText("");
		popularDados();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbCategorias.getColumns().addAll(new GenericTable<Categoria>().tableColunas(Categoria.class));
		popularDados();
	}

	private void popularDados() {
		dados.clear();
		tbCategorias.getItems().clear();
		try {
			dados.addAll(categoriaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbCategorias.setItems(dados);
	}

}
