/**
 * 
 */
package br.edu.unitri.testador;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import br.edu.unitri.controler.AutorControler;
import br.edu.unitri.controler.LivroControler;
import br.edu.unitri.model.Autor;
import br.edu.unitri.model.AutorLivroDTO;
import br.edu.unitri.model.Livro;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class LivrosAutoresFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private Button btnAutor;
	@FXML private Button btnLivro;
	@FXML private RadioButton rbLivro;
	@FXML private Tab tabCadastro;
	@FXML private ComboBox<Autor> cbAutor;
	@FXML private TableView<AutorLivroDTO> tbListaAutorLIvros;
	@FXML private TextField txtBuscar;
	@FXML private ComboBox<Livro> cbLivro;
 	@FXML private Button btnIncluir;
 	@FXML private ToggleGroup buscarPor;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	@FXML private RadioButton rbAutor;
	
	private ObservableList<Autor> listaAutor = FXCollections.observableArrayList();
	private ObservableList<Livro> listaLivro = FXCollections.observableArrayList();
	private ObservableList<AutorLivroDTO> dados = FXCollections.observableArrayList();
	private int Operacao;
	private AutorControler autorCtr = new AutorControler();
	private LivroControler livroCtr = new LivroControler();	
	private Autor autor;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbAutor.isSelected()) {
			   dados.addAll(autorCtr.findLivrosAutores(txtBuscar.getText(), true, false));			
			} else if (rbLivro.isSelected()) {
				dados.addAll(autorCtr.findLivrosAutores(txtBuscar.getText(), false, true));
			}
			tbListaAutorLIvros.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbAutor.isSelected() || rbLivro.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnAutorClick(ActionEvent event) {
		Stage telaAutor = new Stage();
		try {
			new FormFX<AutorFX>("Autor.fxml", telaAutor, "Operações com Autores", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbAutor.getItems().clear();
		listaAutor.clear();
		try {
			listaAutor.addAll(autorCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbAutor.setItems(listaAutor);
	}

	@FXML
	void btnLivroClick(ActionEvent event) {
		Stage telaLivros = new Stage();
		try {
			new FormFX<LivroFX>("Livro.fxml", telaLivros, "Operações com Livros", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbLivro.getItems().clear();
		listaLivro.clear();
		try {
			listaLivro.addAll(livroCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbLivro.setItems(listaLivro);	
		
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			setAutor(cbAutor.getSelectionModel().getSelectedItem());
			Autor autor = cbAutor.getSelectionModel().getSelectedItem();
			autor.getLivros().add(cbLivro.getSelectionModel().getSelectedItem());
			try {
				if (autorCtr.update(autor, getAutor().getId().intValue())) {
					new FXDialog(Type.INFO, "Registro atualizado com sucesso!").showDialog();
				}
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
			popularDados();
			btnNovoClick(event);
		}
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (cbAutor.getSelectionModel().getSelectedItem() == null) {
			new FXDialog(Type.WARNING, "Favor selecionar o Autor!").showDialog();			
			cbAutor.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbLivro.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o Livro!").showDialog();			
				cbLivro.requestFocus();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		cbAutor.getSelectionModel().select(null);
		cbLivro.getSelectionModel().select(null);
		cbAutor.requestFocus();
	}
	
	@FXML
    void cbAutorOnChange(ActionEvent event) {
		if (cbAutor.getSelectionModel().getSelectedItem() != null) {
			listaLivro.clear();
			cbLivro.getItems().clear();
			try {
				listaLivro.addAll(livroCtr.findAll("select l.* from livro l where l.id not in ("
						+ " select la2.liv_id from liv_aut la2, autor a where a.id = la2.aut_id and"
						+ " a.id = '"+ cbAutor.getSelectionModel().getSelectedItem().getId() + "')",""));
	
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
			cbLivro.setItems(listaLivro);
			cbLivro.requestFocus();
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbListaAutorLIvros.getColumns().addAll(new GenericTable<AutorLivroDTO>().tableColunas(AutorLivroDTO.class));
		try {
			dados.addAll(autorCtr.findLivrosAutores("",false, false));
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbListaAutorLIvros.setItems(dados);	
		popularDados();
	}
	
	private void popularDados(){
		cbAutor.getItems().clear();
		cbLivro.getItems().clear();
		listaAutor.clear();
		listaLivro.clear();
		try {
			listaAutor.addAll(autorCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		try {
			listaLivro.addAll(livroCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbAutor.setItems(listaAutor);
		cbLivro.setItems(listaLivro);		
	}


	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Autor getAutor() {
		return autor;
	}

	public void setAutor(Autor autor) {
		this.autor = autor;
	}

}
