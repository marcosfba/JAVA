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
import javafx.stage.Stage;
import br.edu.unitri.controler.EstadoControler;
import br.edu.unitri.controler.GovernadorControler;
import br.edu.unitri.model.Estado;
import br.edu.unitri.model.Governador;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class EstadoFX implements Initializable {
	
	@FXML private Tab tabConsulta;
    @FXML private TextField txtNome;
    @FXML private Tab tabCadastro;
    @FXML private TextField txtBuscar;
    @FXML private TableView<Estado> tbEstados;
    @FXML private Button btnGovernador;
    @FXML private RadioButton rbSigla;
    @FXML private ComboBox<Governador> cbGovernador;
    @FXML private Button btnIncluir;
    @FXML private RadioButton rbNome;
    @FXML private ToggleGroup buscarPor;
    @FXML private Button btnExcluir;
    @FXML private TabPane tabTela;
    @FXML private Button btnBuscar;
    @FXML private Button btnNovo;
    @FXML private TextField txtSigla;

	private ObservableList<Estado> dados = FXCollections.observableArrayList();
	private ObservableList<Governador> listaGovernador = FXCollections.observableArrayList();
	private int Operacao;
	private EstadoControler estadoCtr = new EstadoControler();
	private GovernadorControler governadorCtr = new GovernadorControler();
	private Estado estado;

	@FXML
    void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(estadoCtr.findAll("select d.* from Estado d"," where d.nomeEstado like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbSigla.isSelected()) {
				dados.addAll(estadoCtr.findAll("select d.* from Estado d"," where d.sigla like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbEstados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
    }

    private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbSigla.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
    void btnGovernadorClick(ActionEvent event) {
		Stage telaGovernador = new Stage();
		try {
			new FormFX<GovernadorFX>("Governador.fxml", telaGovernador, "Operações com Governador", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		popularDados();
    }

    @FXML
    void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Estado estado = new Estado(cbGovernador.getSelectionModel().getSelectedItem(), txtSigla.getText(), txtNome.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (estadoCtr.save(estado) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (estadoCtr.update(estado, getEstado().getId().intValue())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome do Estado!").showDialog();
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtSigla.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a sigla do Estado!").showDialog();			
				txtSigla.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (cbGovernador.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o Governador referente ao estado!").showDialog();			
				cbGovernador.requestFocus();
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
		txtSigla.setText("");
		cbGovernador.getSelectionModel().select(null);
		txtSigla.requestFocus();
    }

    @FXML
    void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = estadoCtr.delete(getEstado());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Estado!").showDialog(); 
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tbEstados.getColumns().addAll(new GenericTable<Estado>().tableColunas(Estado.class));
		popularDados();
		tbEstados.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setEstado(tbEstados.getSelectionModel().getSelectedItem());
					txtNome.setText(tbEstados.getSelectionModel().getSelectedItem().getNomeEstado());
					txtSigla.setText(tbEstados.getSelectionModel().getSelectedItem().getSigla());
					cbGovernador.getSelectionModel().select(tbEstados.getSelectionModel().getSelectedItem().getGovernador());
				}
			}
		});
	}

	private void popularDados(){
		dados.clear();
		tbEstados.getItems().clear();
		try {
			dados.addAll(estadoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbEstados.setItems(dados);		
		
		cbGovernador.getItems().clear();
		listaGovernador.clear();
		try {
			listaGovernador.addAll(governadorCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbGovernador.setItems(listaGovernador);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Estado getEstado() {
		return estado;
	}

	public void setEstado(Estado estado) {
		this.estado = estado;
	}
	

}
