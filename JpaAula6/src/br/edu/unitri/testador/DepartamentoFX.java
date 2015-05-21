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
import br.edu.unitri.controler.DepartamentoControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class DepartamentoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbNumero;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private TextField txtNumero;
	@FXML private RadioButton rbDescricao;
	@FXML private Button btnIncluir;
	@FXML private TableView<Departamento> tbDepartamentos;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Departamento> dados = FXCollections.observableArrayList();
	private DepartamentoControler departamentoCtr = new DepartamentoControler();
	private Departamento departamento;
	private int Operacao;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbDescricao.isSelected()) {
			   dados.addAll(departamentoCtr.findAll("select p.* from tbDepartamento p"," where p.descLocal like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbNumero.isSelected()) {
				dados.addAll(departamentoCtr.findAll("select p.* from tbDepartamento p"," where p.numDepartamento like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbDepartamentos.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbDescricao.isSelected() || rbNumero.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Departamento departamento = new Departamento(txtNumero.getText(),txtDescricao.getText());
			switch (getOperacao()) {
			case 0:
				try {
					if (departamentoCtr.save(departamento) != null) {
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
					if (departamentoCtr.update(departamento,(int) getDepartamento().getIdDepartamento())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nº do departamento!").showDialog();			
			txtNumero.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtDescricao.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a descrição do departamento!").showDialog();
				txtDescricao.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtDescricao.setText("");
		txtNumero.setText("");
		txtNumero.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = departamentoCtr.delete(getDepartamento());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Departamento!").showDialog(); 
		}
	}

	private void popularDados() {
		dados.clear();
		tbDepartamentos.getItems().clear();
		try {
			dados.addAll(departamentoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDepartamentos.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDepartamentos.getColumns().addAll(new GenericTable<Departamento>().tableColunas(Departamento.class));
		popularDados();
		tbDepartamentos.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setDepartamento(tbDepartamentos.getSelectionModel().getSelectedItem());
					txtDescricao.setText(tbDepartamentos.getSelectionModel().getSelectedItem().getDescLocal());
					txtNumero.setText(tbDepartamentos.getSelectionModel().getSelectedItem().getNumDepartamento());
				}
			}
		});
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}
}
