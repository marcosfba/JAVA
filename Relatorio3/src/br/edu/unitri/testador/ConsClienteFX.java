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

import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.model.Cliente;
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
public class ConsClienteFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbTelefone;
	@FXML private RadioButton rbCidade;
	@FXML private RadioButton rbCep;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtBuscar;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private TableView<Cliente> tbClientes;
	@FXML private Button btnLimpar;
	
	private ObservableList<Cliente> dados = FXCollections.observableArrayList();
	private ClienteControler clienteCtr = new ClienteControler();


	@FXML
	void btnBuscarClick(ActionEvent event) {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
		EntityType<Cliente> typeProd = JpaUtil.getManager().getMetamodel().entity(Cliente.class);
		Root<Cliente> root = query.from(Cliente.class);		
		
		if (rbTelefone.isSelected()) {
			query.where(cb.or(cb.like(root.get(typeProd.getDeclaredSingularAttribute("descTelefone", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		if (rbCidade.isSelected()) {
			query.where(cb.or(cb.like(root.get(typeProd.getDeclaredSingularAttribute("descCidade", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		if (rbCep.isSelected()) {
			query.where(cb.or(cb.like(root.get(typeProd.getDeclaredSingularAttribute("descCep", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		if (rbNome.isSelected()) {
			query.where(cb.or(cb.like(root.get(typeProd.getDeclaredSingularAttribute("nomeCliente", String.class)),
					"%" + txtBuscar.getText() + "%")));
		}
		
		List<Cliente> listaA = JpaUtil.getManager().createQuery(query).getResultList();
		dados.clear();
		tbClientes.getItems().clear();
		dados.addAll(listaA);
		tbClientes.setItems(dados);
	}

	@FXML
	void btnLimparClick(ActionEvent event) {
		txtBuscar.setText("");
		popularDados();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbClientes.getColumns().addAll(new GenericTable<Cliente>().tableColunas(Cliente.class));
		popularDados();

	}

	private void popularDados() {
		dados.clear();
		tbClientes.getItems().clear();
		try {
			dados.addAll(clienteCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbClientes.setItems(dados);
	}

}
