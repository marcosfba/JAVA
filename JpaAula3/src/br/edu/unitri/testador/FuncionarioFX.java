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
import br.edu.unitri.controler.DepartamentoControler;
import br.edu.unitri.controler.FuncionarioControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Funcionario;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class FuncionarioFX implements Initializable {
	
	@FXML private Tab tabConsulta;
    @FXML private RadioButton rbDepartamento;
    @FXML private TableView<Funcionario> tbFuncionarios;
    @FXML private RadioButton rbNome;
    @FXML private ToggleGroup buscarPor;
    @FXML private TextField txtBuscar;
    @FXML private Button btnBuscar;
    @FXML private Button btnDepartamento;
    @FXML private ComboBox<Departamento> cbDepartamento;
    @FXML private Button btnIncluir;
    @FXML private TextField txtNome;
    @FXML private Tab tabCadastro;
    @FXML private Button btnExcluir;
    @FXML private TabPane tabTela;
    @FXML private Button btnNovo;

	private ObservableList<Funcionario> dados = FXCollections.observableArrayList();
	private ObservableList<Departamento> listaDepartamentos = FXCollections.observableArrayList();
	private int Operacao;
	private FuncionarioControler funcionarioCtr = new FuncionarioControler();
	private DepartamentoControler departamentoCtr = new DepartamentoControler();
	private Funcionario funcionario;

    @FXML
    void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(funcionarioCtr.findAll("select f.* from Funcionario f"," where f.nome like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbDepartamento.isSelected()) {
				dados.addAll(funcionarioCtr.findAll("select f.* from Funcionario f, Departamento d"," where d.id = f.departamento_id and d.nome like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbFuncionarios.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
    }

    private boolean isValidConsulta() {
		boolean ok = rbNome.isSelected() || rbDepartamento.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
    void btnDepartamentoClick(ActionEvent event) {
		Stage telaDepartamento = new Stage();
		try {
			new FormFX<DepartamentoFX>("Departamento.fxml", telaDepartamento, "Operações com Departamento", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		popularDados();
    }

    @FXML
    void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Funcionario funcionario = new Funcionario(cbDepartamento.getSelectionModel().getSelectedItem(), txtNome.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (funcionarioCtr.save(funcionario) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (funcionarioCtr.update(funcionario, getFuncionario().getId().intValue())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome do Funcionário!").showDialog();
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbDepartamento.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o Departamento referente ao funcionário!").showDialog();			
				cbDepartamento.requestFocus();
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
		cbDepartamento.getSelectionModel().select(null);
		txtNome.requestFocus();
    }

    @FXML
    void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = funcionarioCtr.delete(getFuncionario());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Funcionário!").showDialog(); 
		}
    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tbFuncionarios.getColumns().addAll(new GenericTable<Funcionario>().tableColunas(Funcionario.class));
		popularDados();
		tbFuncionarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setFuncionario(tbFuncionarios.getSelectionModel().getSelectedItem());
					txtNome.setText(tbFuncionarios.getSelectionModel().getSelectedItem().getNome());
					cbDepartamento.getSelectionModel().select(tbFuncionarios.getSelectionModel().getSelectedItem().getDepartamento());
				}
			}
		});
	}

	private void popularDados(){
		dados.clear();
		tbFuncionarios.getItems().clear();
		try {
			dados.addAll(funcionarioCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbFuncionarios.setItems(dados);		
		
		cbDepartamento.getItems().clear();
		listaDepartamentos.clear();
		try {
			listaDepartamentos.addAll(departamentoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbDepartamento.setItems(listaDepartamentos);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
	

}
