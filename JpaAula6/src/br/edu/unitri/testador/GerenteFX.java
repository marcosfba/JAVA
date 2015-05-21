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
import br.edu.unitri.controler.GerenciaControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.Gerencia;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.ConverterUtil;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class GerenteFX implements Initializable {
	
	@FXML private Tab tabConsulta;
	@FXML private ComboBox<Empregado> cbEmpregado;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private DatePicker dtInicio;
	@FXML private RadioButton rbNomeDep;
	@FXML private TableView<Gerencia> tbGerenteDepartamentos;
	@FXML private RadioButton rbNomeEmp;
	@FXML private ComboBox<Departamento> cbDepartamento;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Gerencia> dados = FXCollections.observableArrayList();
	private ObservableList<Empregado> dadosEmp = FXCollections.observableArrayList();
	private ObservableList<Departamento> dadosDep = FXCollections.observableArrayList();
	
	private GerenciaControler gerenciaCtr = new GerenciaControler();
	private EmpregadoControler empregadoCtr = new EmpregadoControler();
	private DepartamentoControler departamentoCtr = new DepartamentoControler();
	
	private Gerencia gerente;
	private int Operacao;


	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbNomeEmp.isSelected()) {
			   dados.addAll(gerenciaCtr.findAll("select g.* from tbGerencia g join tbEmpregado e on e.codEmpregado = g.gerente_id",
					        " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'"));
			} else if (rbNomeDep.isSelected()) {
				dados.addAll(gerenciaCtr.findAll("select g.* from tbGerencia g join tbDepartamento d on d.idDepartamento = g.departamento_id",
						" where d.numDepartamento like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbGerenteDepartamentos.setItems(dados);
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
			Gerencia gerencia = new Gerencia(cbEmpregado.getSelectionModel().getSelectedItem(),
					 cbDepartamento.getSelectionModel().getSelectedItem(),ConverterUtil.localDateToDate(dtInicio.getValue()));
			switch (getOperacao()) {
			case 0:
				try {
					if (gerenciaCtr.save(gerencia) != null) {
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
					if (gerenciaCtr.update(gerencia, (int) getGerente().getEmpregado().getCodEmpregado())) {
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
		if (cbEmpregado.getSelectionModel().getSelectedItem() == null) {
			new FXDialog(Type.WARNING, "Favor selecionar o empregado!").showDialog();			
			cbEmpregado.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbDepartamento.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o departamento!").showDialog();
				cbDepartamento.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (dtInicio.getValue() == null) {
				new FXDialog(Type.WARNING, "Favor colocar a data de inicio!").showDialog();
				dtInicio.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		cbEmpregado.getSelectionModel().select(null);
		cbDepartamento.getSelectionModel().select(null);
		dtInicio.setValue(null);
		cbEmpregado.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = gerenciaCtr.delete(getGerente());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Gerente!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbGerenteDepartamentos.getColumns().addAll(new GenericTable<Gerencia>().tableColunas(Gerencia.class));
		popularDados();
		tbGerenteDepartamentos.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setGerente(tbGerenteDepartamentos.getSelectionModel().getSelectedItem());
					cbEmpregado.getSelectionModel().select(tbGerenteDepartamentos.getSelectionModel().getSelectedItem().getEmpregado());
					cbDepartamento.getSelectionModel().select(tbGerenteDepartamentos.getSelectionModel().getSelectedItem().getDepartamento());
					dtInicio.setValue(ConverterUtil.utilDateToLocalDate(tbGerenteDepartamentos.getSelectionModel().getSelectedItem().getDtEmp()));
				}
			}
		});
	}
	
	private void popularDados() {
		dados.clear();
		dadosDep.clear();
		dadosEmp.clear();
		cbDepartamento.getItems().clear();
		cbEmpregado.getItems().clear();
		tbGerenteDepartamentos.getItems().clear();
		try {
			dados.addAll(gerenciaCtr.findAll());
			dadosDep.addAll(departamentoCtr.findAll());
			dadosEmp.addAll(empregadoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbGerenteDepartamentos.setItems(dados);
		cbDepartamento.setItems(dadosDep);
		cbEmpregado.setItems(dadosEmp);
	}


	public Gerencia getGerente() {
		return gerente;
	}

	public void setGerente(Gerencia gerente) {
		this.gerente = gerente;
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}
}
