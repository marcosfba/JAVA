/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

/**
 * @author marcos.fernando
 *
 */
public class CategoriaFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtNome;
	@FXML private TableView<Categoria> tbCategorias;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbDescricao;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Categoria> dados = FXCollections.observableArrayList();
	private int Operacao;
	private CategoriaControler categoriaCtr = new CategoriaControler();
	private Categoria categoria;
	
	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(categoriaCtr.findAll("select c.* from tbCategoria c"," where c.nomeCategoria like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbDescricao.isSelected()) {
				dados.addAll(categoriaCtr.findAll("select c.* from tbCategoria c"," where c.descCategoria like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbCategorias.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbDescricao.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Categoria categoria = new Categoria(txtNome.getText(), txtDescricao.getText());
			switch (getOperacao()) {
			case 0:
				try {
					if (categoriaCtr.save(categoria) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				;
				popularDados();
				break;
			case 1:
				try {
					if (categoriaCtr.update(categoria,(int) getCategoria().getCodCategoria())) {
						new FXDialog(Type.INFO,"Registro atualizado com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				popularDados();
				break;
			}
		}
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (txtNome.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome da Categoria!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtDescricao.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a descrição da Categoria!").showDialog();
				txtDescricao.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		txtDescricao.setText("");
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = categoriaCtr.delete(getCategoria());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.WARNING,"Existem relações com outras entidades!").showDialog();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbCategorias.getColumns().addAll(new GenericTable<Categoria>().tableColunas(Categoria.class));	
		popularDados();
		tbCategorias.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);	
					setOperacao(1);
					setCategoria(tbCategorias.getSelectionModel().getSelectedItem());
					txtDescricao.setText(tbCategorias.getSelectionModel().getSelectedItem().getDescCategoria());
					txtNome.setText(tbCategorias.getSelectionModel().getSelectedItem().getNomeCategoria());
				}
			}
		});
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

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
