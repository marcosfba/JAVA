/**
 * 
 */
package br.edu.unitri.testador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

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
import br.edu.unitri.Controler.CategoriaControler;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class CategoriaFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbPreco;
	@FXML private TableView<Categoria> tbCategorias;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbDescricao;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TextField txtPreco;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Categoria> dados = FXCollections.observableArrayList();
	private int Operacao;	
	private CategoriaControler categoriaCtr = new CategoriaControler();
	private long idCategoria;
	private Categoria categoria;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbDescricao.isSelected()) {
			   dados.addAll(categoriaCtr.findAll("select c.* from tbCategoria c"," where c.descricao like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbPreco.isSelected()) {
				dados.addAll(categoriaCtr.findAll("select c.* from tbCategoria c"," where c.preco = '"+ txtBuscar.getText() + "'"));	
			}
			txtBuscar.setText("");
			tbCategorias.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbDescricao.isSelected() || rbPreco.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Categoria categoria = new Categoria(new BigDecimal(txtPreco.getText()), txtDescricao.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (categoriaCtr.save(categoria) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (categoriaCtr.update(categoria,(int)getIdCategoria())) {
						new FXDialog(Type.INFO, "Registro atualizado com sucesso!").showDialog();
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
		if (txtDescricao.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher a descrição da Categoria!").showDialog();			
			txtDescricao.requestFocus();
			ok = false;			
		}
		if (ok) {
			if (txtPreco.getText().isEmpty()) {
				txtPreco.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o preço!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (txtPreco.getText().contains(",")) {
				txtPreco.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o preço com separador de Decimais com '.'!").showDialog();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtDescricao.setText("");
		txtPreco.setText("");
		txtDescricao.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = categoriaCtr.delete(getCategoria());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR,"Erro ao tentar excluir categoria ->" + e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.INFO,"Categoria não pode ser excluida. Existem relações com outras entidades!").showDialog();
				popularDados();
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhuma Categoria!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tbCategorias.getColumns().addAll(new GenericTable<Categoria>().tableColunas(Categoria.class));
		try {
			dados.addAll(categoriaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro durante a inicialização ->" +  e.getCause().getMessage()).showDialog();
		}
		tbCategorias.setItems(dados);		
		
		tbCategorias.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setCategoria(tbCategorias.getSelectionModel().getSelectedItem());					
					setIdCategoria(tbCategorias.getSelectionModel().getSelectedItem().getIdCategoria());
					txtDescricao.setText(tbCategorias.getSelectionModel().getSelectedItem().getDescricao());
					txtPreco.setText(tbCategorias.getSelectionModel().getSelectedItem().getPreco().toString());
				}
			}
		});
	}

	private void popularDados(){
		dados.clear();
		tbCategorias.getItems().clear();
		try {
			dados.addAll(categoriaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao buscar dados ->" +  e.getCause().getMessage()).showDialog();
		}
		tbCategorias.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(long idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
}
