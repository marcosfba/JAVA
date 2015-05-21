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
import br.edu.unitri.controler.LivroControler;
import br.edu.unitri.model.Livro;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class LivroFX implements Initializable {

	@FXML private Tab tabConsulta;
    @FXML private TextField txtEditora;
    @FXML private TextField txtNome;
    @FXML private Tab tabCadastro;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Livro> tbLivros;
    @FXML private Button btnIncluir;
    @FXML private RadioButton rbEditora;
    @FXML private RadioButton rbNome;
    @FXML private ToggleGroup buscarPor;
    @FXML private Button btnExcluir;
    @FXML private TabPane tabTela;
    @FXML private Button btnBuscar;
    @FXML private Button btnNovo;
    @FXML private RadioButton rbAutor;
    
	private ObservableList<Livro> dados = FXCollections.observableArrayList();
	private int Operacao;
	private LivroControler livroCtr = new LivroControler();
	private Livro livro;

    @FXML
    void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(livroCtr.findAll("select l.* from Livro l"," where l.nome like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbEditora.isSelected()) {
				dados.addAll(livroCtr.findAll("select l.* from Livro l"," where l.editora like  '%"+ txtBuscar.getText() + "%'"));
			} else if (rbAutor.isSelected()) {
				dados.addAll(livroCtr.findAll("select l.* from Livro l, Autor a, Liv_Aut la "," where a.id = la.Aut_ID and l.id = la.Liv_ID and l.nome like '%"+ txtBuscar.getText() + "%'"));
			}
			tbLivros.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
    }

    private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbEditora.isSelected() || rbAutor.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
    void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Livro livro = new Livro(txtNome.getText(), txtEditora.getText(), null);
			switch (getOperacao()) {
			case 0:				
				try {
					if (livroCtr.save(livro) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (livroCtr.update(livro, getLivro().getId().intValue())) {
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
		if (txtNome.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome do Livro!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtEditora.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o nome da Editora do livro!").showDialog();
				txtEditora.requestFocus();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
    void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		txtEditora.setText("");
		txtNome.requestFocus();
    }

    @FXML
    void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = livroCtr.delete(getLivro());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.WARNING,"Existem relações com outras entidades!").showDialog();
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Livro!").showDialog(); 
		}
    }	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
    	tbLivros.getColumns().addAll(new GenericTable<Livro>().tableColunas(Livro.class));
		popularDados();
		tbLivros.setItems(dados);		
		tbLivros.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setLivro(tbLivros.getSelectionModel().getSelectedItem());
					txtEditora.setText(tbLivros.getSelectionModel().getSelectedItem().getEditora());
					txtNome.setText(tbLivros.getSelectionModel().getSelectedItem().getNome());
				}
			}
		});
	}

	private void popularDados(){
		dados.clear();
		tbLivros.getItems().clear();
		try {
			dados.addAll(livroCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbLivros.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

}
