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
	@FXML private TextField txtNome;
	@FXML private TextField txtDescricao;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbDescricao;
	@FXML private Button btnIncluir;
	@FXML private TableView<Departamento> tbDepartamentos;
	@FXML private RadioButton rbNome;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Departamento> dados = FXCollections.observableArrayList();
	private int Operacao;
	private DepartamentoControler departamentoCtr = new DepartamentoControler();
	private Departamento departamento;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbNome.isSelected()) {
			   dados.addAll(departamentoCtr.findAll("select d.* from Departamento d"," where d.nome like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbDescricao.isSelected()) {
				dados.addAll(departamentoCtr.findAll("select d.* from Departamento d"," where d.descricao like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbDepartamentos.setItems(dados);
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
			Departamento departamento = new Departamento(txtNome.getText(),txtDescricao.getText());
			switch (getOperacao()) {
			case 0:				
				try {
					if (departamentoCtr.save(departamento) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (departamentoCtr.update(departamento, getDepartamento().getId().intValue())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o Descrição do Departamento!").showDialog();
			txtDescricao.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtNome.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o nome do Departamento!").showDialog();			
				txtNome.requestFocus();
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
		txtDescricao.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = departamentoCtr.delete(getDepartamento());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Departamento!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		tbDepartamentos.getColumns().addAll(new GenericTable<Departamento>().tableColunas(Departamento.class));
		try {
			dados.addAll(departamentoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDepartamentos.setItems(dados);		
		tbDepartamentos.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setDepartamento(tbDepartamentos.getSelectionModel().getSelectedItem());
					txtNome.setText(tbDepartamentos.getSelectionModel().getSelectedItem().getNome());
					txtDescricao.setText(tbDepartamentos.getSelectionModel().getSelectedItem().getDescricao());
				}
			}
		});
	}

	private void popularDados(){
		dados.clear();
		tbDepartamentos.getItems().clear();
		try {
			dados.addAll(departamentoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDepartamentos.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}
}
