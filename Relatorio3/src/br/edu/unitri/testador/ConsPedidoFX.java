/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.controler.PedidoControler;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.Pedido;
import br.edu.unitri.model.Produto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.ConverterUtil;
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
public class ConsPedidoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbCodigo;
	@FXML private TableView<Pedido> tbPedidos;
	@FXML private RadioButton rbData;
	@FXML private RadioButton rbCliente;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnLimpar;
	
	private ObservableList<Pedido> dados = FXCollections.observableArrayList();
	private PedidoControler pedidoCtr = new PedidoControler();

	
	@FXML
	void btnBuscarClick(ActionEvent event) {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Pedido> query = cb.createQuery(Pedido.class);
		EntityType<Pedido> typeProd = JpaUtil.getManager().getMetamodel().entity(Pedido.class);
		EntityType<Cliente> typeCat = JpaUtil.getManager().getMetamodel().entity(Cliente.class);
		Root<Pedido> root = query.from(Pedido.class);		
		Join<Produto,Cliente> join = root.join("cliente", JoinType.INNER);
		
		if (rbData.isSelected()) {
			query.where(cb.equal(root.get(typeProd.getDeclaredSingularAttribute("dtPedido",Date.class)),
					 ConverterUtil.stringToDateSql(txtBuscar.getText()))); 
		}
		if (rbCliente.isSelected()) {
			query.where(cb.or(cb.like(join.get(typeCat.getDeclaredSingularAttribute("nomeCliente", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		if (rbCodigo.isSelected()) {
			query.where(cb.equal(root.get(typeProd.getDeclaredSingularAttribute("codPedido",Long.class)),txtBuscar.getText()));
		}
		
		tbPedidos.getItems().clear();
		dados.clear();
		List<Pedido> listaA = JpaUtil.getManager().createQuery(query).getResultList();
		dados.addAll(listaA);
		tbPedidos.setItems(dados);
	}

	@FXML
	void btnLimparClick(ActionEvent event) {
		txtBuscar.setText("");
		popularDados();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbPedidos.getColumns().addAll(new GenericTable<Pedido>().tableColunas(Pedido.class));
		popularDados();
	}

	private void popularDados() {
		dados.clear();
		tbPedidos.getItems().clear();
		try {
			dados.addAll(pedidoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbPedidos.setItems(dados);
	}

}
