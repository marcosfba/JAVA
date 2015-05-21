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
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.controler.DepartamentoControler;
import br.edu.unitri.controler.EmpregadoControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.TipoSexo;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.ConverterUtil;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class EmpregadoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TextField txtEndereco;
	@FXML private TextField txtNome;
	@FXML private TableView<Empregado> tbEmpregados;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
 	@FXML private RadioButton rbNomeEmp;
 	@FXML private ComboBox<TipoSexo> cbSexo;
	@FXML private ComboBox<Departamento> cbDepartamento;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private DatePicker dtNascimento;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Empregado> dados = FXCollections.observableArrayList();
	private ObservableList<Departamento> dadosDep = FXCollections.observableArrayList();
	private EmpregadoControler empregadoCtr = new EmpregadoControler();
	private DepartamentoControler deptCtr = new DepartamentoControler();
	private Empregado empregado;
	private int Operacao;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbNomeEmp.isSelected()) {
			   dados.addAll(empregadoCtr.findAll("select p.* from tbEmpregado p"," where p.nomeEmpregado like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbNomeDep.isSelected()) {
				dados.addAll(empregadoCtr.findAll("select p.* from tbEmpregado p, tbDepartamento d"," where d.idDepartamento = p.departamento_id and d.numDepartamento like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbEmpregados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNomeEmp.isSelected() || rbNomeDep.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Empregado empregado = new Empregado(txtNome.getText(),txtEndereco.getText(), 
					cbSexo.getSelectionModel().getSelectedItem(), ConverterUtil.localDateToDate(dtNascimento.getValue()),
					cbDepartamento.getSelectionModel().getSelectedItem());
			
			switch (getOperacao()) {
			case 0:
				try {
					if (empregadoCtr.save(empregado) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				popularDados();
				break;
			case 1:
				try {
					if (empregadoCtr.update(empregado,(int) getEmpregado().getCodEmpregado())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome do funcionário!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtEndereco.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o endereço do funcionário!").showDialog();
				txtEndereco.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (cbSexo.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o tipo de sexo!").showDialog();
				cbSexo.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (cbDepartamento.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o departamento do funcionário!").showDialog();
				cbSexo.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (dtNascimento.getValue() == null) {
				new FXDialog(Type.WARNING, "Favor preencher a data de nascimento!").showDialog();
				cbSexo.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		txtEndereco.setText("");
		cbSexo.getSelectionModel().select(-1);
		cbDepartamento.getSelectionModel().select(null);
		dtNascimento.setValue(null);
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = empregadoCtr.delete(getEmpregado());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Empregado!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbEmpregados.getColumns().addAll(new GenericTable<Empregado>().tableColunas(Empregado.class));
		popularDados();
		tbEmpregados.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setEmpregado(tbEmpregados.getSelectionModel().getSelectedItem());
					txtEndereco.setText(tbEmpregados.getSelectionModel().getSelectedItem().getEndEmpregado());
					txtNome.setText(tbEmpregados.getSelectionModel().getSelectedItem().getNomeEmpregado());
					cbDepartamento.getSelectionModel().select(tbEmpregados.getSelectionModel().getSelectedItem().getDepartamento());
					cbSexo.getSelectionModel().select(tbEmpregados.getSelectionModel().getSelectedItem().getSexo());
					dtNascimento.setValue(ConverterUtil.utilDateToLocalDate(tbEmpregados.getSelectionModel().getSelectedItem().getDtNasc()));
				}
			}
		});
	}

	private void popularDados() {
		dados.clear();
		dadosDep.clear();
		cbSexo.getItems().clear();
		cbDepartamento.getItems().clear();
		tbEmpregados.getItems().clear();
		try {
			dados.addAll(empregadoCtr.findAll());
			dadosDep.addAll(deptCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbEmpregados.setItems(dados);
		cbSexo.getItems().setAll(TipoSexo.values());
		cbDepartamento.setItems(dadosDep);
	}

	public Empregado getEmpregado() {
		return empregado;
	}

	public void setEmpregado(Empregado empregado) {
		this.empregado = empregado;
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}
}
