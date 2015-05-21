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
import br.edu.unitri.controler.GovernadorControler;
import br.edu.unitri.model.Governador;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class GovernadorFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtNome;
	@FXML private TableView<Governador> tbGovernadores;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbUF;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtUF;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Governador> dados = FXCollections.observableArrayList();
	private int Operacao;
	private GovernadorControler governadorCtr = new GovernadorControler();
	private Governador governador;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(governadorCtr.findAll("select g.* from Governador g"," where g.nome like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbUF.isSelected()) {
				dados.addAll(governadorCtr.findAll("select g.* from Governador g"," where g.uf = '"+ txtBuscar.getText() + "'"));
			}
			tbGovernadores.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbUF.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Governador governador = new Governador(txtNome.getText(), txtUF.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (governadorCtr.save(governador) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (governadorCtr.update(governador, getGovernador().getId().intValue())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome do Governador!").showDialog();
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtUF.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a sigla do Estado referente ao Governador!").showDialog();			
				txtUF.requestFocus();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		popularDados();
		txtNome.setText("");
		txtUF.setText("");
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = governadorCtr.delete(getGovernador());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Governador!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tbGovernadores.getColumns().addAll(new GenericTable<Governador>().tableColunas(Governador.class));
		popularDados();
		tbGovernadores.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setGovernador(tbGovernadores.getSelectionModel().getSelectedItem());
					txtNome.setText(tbGovernadores.getSelectionModel().getSelectedItem().getNome());
					txtUF.setText(tbGovernadores.getSelectionModel().getSelectedItem().getUf());
				}
			}
		});
	}

	private void popularDados(){
		dados.clear();
		tbGovernadores.getItems().clear();
		try {
			dados.addAll(governadorCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbGovernadores.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Governador getGovernador() {
		return governador;
	}

	public void setGovernador(Governador governador) {
		this.governador = governador;
	}
	
	
}
