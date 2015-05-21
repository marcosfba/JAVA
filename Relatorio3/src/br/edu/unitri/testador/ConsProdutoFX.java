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
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Produto;
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
public class ConsProdutoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<Produto> tbProdutos;
	@FXML private RadioButton rbCategoria;
	@FXML private RadioButton rbProduto;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnLimpar;

	private ObservableList<Produto> dados = FXCollections.observableArrayList();
	private ProdutoControler produtoCtr = new ProdutoControler();

	@FXML
	void btnBuscarClick(ActionEvent event) {
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Produto> query = cb.createQuery(Produto.class);
		EntityType<Produto> typeProd = JpaUtil.getManager().getMetamodel().entity(Produto.class);
		EntityType<Categoria> typeCat = JpaUtil.getManager().getMetamodel().entity(Categoria.class);
		Root<Produto> root = query.from(Produto.class);
		Join<Produto,Categoria> join = root.join("categoria", JoinType.INNER);
		
		if (rbProduto.isSelected()) {
			query.where(cb.or(cb.like(root.get(typeProd.getDeclaredSingularAttribute("nomeProduto", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		if (rbCategoria.isSelected()) {
			query.where(cb.or(cb.like(join.get(typeCat.getDeclaredSingularAttribute("descCategoria", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		List<Produto> listaA = JpaUtil.getManager().createQuery(query).getResultList();
		dados.clear();
		tbProdutos.getItems().clear();
		dados.addAll(listaA);
		tbProdutos.setItems(dados);
	}

	@FXML
	void btnLimparClick(ActionEvent event) {
		txtBuscar.setText("");
		popularDados();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbProdutos.getColumns().addAll(new GenericTable<Produto>().tableColunas(Produto.class));
		popularDados();
	}

	private void popularDados() {
		dados.clear();
		tbProdutos.getItems().clear();
		try {
			dados.addAll(produtoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbProdutos.setItems(dados);
	}

}
