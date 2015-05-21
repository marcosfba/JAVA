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
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.controler.DepartamentoControler;
import br.edu.unitri.controler.LocalControler;
import br.edu.unitri.controler.ProjetoControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Local;
import br.edu.unitri.model.Projeto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ProjetoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbDepartamento;
	@FXML private RadioButton rbNumero;
	@FXML private Tab tabCadastro;
 	@FXML private TextField txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private TextField txtNumero;
	@FXML private TableView<Projeto> tbProjetos;
	@FXML private ComboBox<Departamento> cbDepartamento;
	@FXML private ComboBox<Local> cbLocal;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Projeto> dados = FXCollections.observableArrayList();
	private ObservableList<Departamento> dadosDep = FXCollections.observableArrayList();
	private ObservableList<Local> dadosLoc = FXCollections.observableArrayList();
	private ProjetoControler projetoCtr = new ProjetoControler();
	private DepartamentoControler deparatamntoCtr = new DepartamentoControler();
	private LocalControler localCtr = new LocalControler();
	
	private Projeto projeto;
	private int Operacao;


	@FXML
	void btnBuscarClick(ActionEvent event) {

	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Projeto projeto = new Projeto(txtNumero.getText(),txtDescricao.getText(), 
					cbLocal.getSelectionModel().getSelectedItem(), cbDepartamento.getSelectionModel().getSelectedItem());
			switch (getOperacao()) {
			case 0:
				try {
					if (projetoCtr.save(projeto) != null) {
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
					if (projetoCtr.update(projeto,(int) getProjeto().getIdProjeto())) {
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
		if (txtNumero.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o número do projeto!").showDialog();			
			txtNumero.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbLocal.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o local do projeto!").showDialog();
				cbLocal.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtDescricao.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a descrição do projeto!").showDialog();
				txtDescricao.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNumero.setText("");
		txtDescricao.setText("");
		cbDepartamento.getSelectionModel().select(null);
		cbLocal.getSelectionModel().select(null);
		txtNumero.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = projetoCtr.delete(getProjeto());
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
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Dependente!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbProjetos.getColumns().addAll(new GenericTable<Projeto>().tableColunas(Projeto.class));
		popularDados();
		tbProjetos.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setProjeto(tbProjetos.getSelectionModel().getSelectedItem());
					txtNumero.setText(tbProjetos.getSelectionModel().getSelectedItem().getNumProjeto());
					txtDescricao.setText(tbProjetos.getSelectionModel().getSelectedItem().getDescProjeto());
					cbDepartamento.getSelectionModel().select(tbProjetos.getSelectionModel().getSelectedItem().getDepartamento());
					cbLocal.getSelectionModel().select(tbProjetos.getSelectionModel().getSelectedItem().getLocal());
				}
			}
		});
	}

	private void popularDados() {
		dados.clear();
		dadosDep.clear();
		dadosLoc.clear();
		cbDepartamento.getItems().clear();
		cbLocal.getItems().clear();
		tbProjetos.getItems().clear();
		try {
			dados.addAll(projetoCtr.findAll());
			dadosDep.addAll(deparatamntoCtr.findAll());
			dadosLoc.addAll(localCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbProjetos.setItems(dados);
		cbDepartamento.setItems(dadosDep);
		cbLocal.setItems(dadosLoc);
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

}
