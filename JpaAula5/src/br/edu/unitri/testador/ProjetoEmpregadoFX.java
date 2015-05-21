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
import br.edu.unitri.DTO.Consultas.ProjetoEmpregadoDTO;
import br.edu.unitri.controler.ConsultasControler;
import br.edu.unitri.controler.EmpregadoControler;
import br.edu.unitri.controler.ProjEmpControler;
import br.edu.unitri.controler.ProjetoControler;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.model.Projeto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

public class ProjetoEmpregadoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private TableView<ProjetoEmpregadoDTO> tbProjetosEmpregados;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeProj;
	@FXML private RadioButton rbNomeEmp;
	@FXML private ComboBox<Projeto> cbProjeto;
	@FXML private Button btnIncluir;
	@FXML private ComboBox<Empregado> cbEmpregados;
	@FXML private ToggleGroup buscarPor;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
 	@FXML private Button btnNovo;
 	@FXML private TextField txtHoras;
 	
	private ObservableList<ProjetoEmpregadoDTO> dados = FXCollections.observableArrayList();
	private ObservableList<Empregado> dadosEmp = FXCollections.observableArrayList();
	private ObservableList<Projeto> dadosProj = FXCollections.observableArrayList();
	
	private ProjEmpControler projEmpCtr = new ProjEmpControler();
	private EmpregadoControler empregadoCtr = new EmpregadoControler();
	private ProjetoControler projetoCtr = new ProjetoControler();
	private ConsultasControler consultaCtr = new ConsultasControler();
	private int Operacao;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			String qry = "select e.nomeempregado as nomeEmpregado, (d2.numDepartamento ||'-'|| d2.descLocal) as nomeDepartamentoEmpregado,"
					+ " p.numProjeto as numProjeto, p.descProjeto as descProjeto, (l.nomLocal || '-'|| l.descLocal) as nomeLocal,"
					+ " (d.numDepartamento || '-' || d.descLocal) as nomeDepartamentoProjeto"
					+ " from Projeto_Emp pe"
					+ " join tbProjeto p on p.idProjeto = pe.projeto_id"
					+ " join tbDepartamento d on d.idDepartamento = p.departamento_id"
					+ " join tbLocal l on l.idLocal = p.local_id"
					+ " join tbEmpregado e on e.codEmpregado = pe.empregado_id"
					+ " join tbDepartamento d2 on d2.idDepartamento = e.departamento_id";
			if (rbNomeProj.isSelected()) {
			   dados.addAll(consultaCtr.findEmpregadoProjeto(qry, " where p.descProjeto like  '%"+ txtBuscar.getText() + "%'"));
			} else if (rbNomeEmp.isSelected()) {
			   dados.addAll(consultaCtr.findEmpregadoProjeto(qry, " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbProjetosEmpregados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNomeProj.isSelected() || rbNomeEmp.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			ProjEmp projEmp = new ProjEmp(cbProjeto.getSelectionModel().getSelectedItem(),
					cbEmpregados.getSelectionModel().getSelectedItem(), Integer.valueOf(txtHoras.getText()));
			switch (getOperacao()) {
			case 0:
				try {
					if (projEmpCtr.save(projEmp) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				;
				popularDados();
				break;
			}
		}
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (cbEmpregados.getSelectionModel().getSelectedItem() == null) {
			new FXDialog(Type.WARNING, "Favor selecionar o empregado!").showDialog();			
			cbEmpregados.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbProjeto.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o projeto!").showDialog();
				cbProjeto.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtHoras.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a quantidade de horas!").showDialog();
				txtHoras.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		cbProjeto.getSelectionModel().select(null);
		cbEmpregados.getSelectionModel().select(null);
		txtHoras.setText("");
		cbProjeto.requestFocus();		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbProjetosEmpregados.getColumns().addAll(new GenericTable<ProjetoEmpregadoDTO>().tableColunas(ProjetoEmpregadoDTO.class));
		popularDados();
	}
	
	private void popularDados() {
		dadosProj.clear();
		dadosEmp.clear();
		dados.clear();
		cbProjeto.getItems().clear();
		cbEmpregados.getItems().clear();
		tbProjetosEmpregados.getItems().clear();
		try {
			dadosProj.addAll(projetoCtr.findAll());
			dadosEmp.addAll(empregadoCtr.findAll());
			dados.addAll(consultaCtr.findEmpregadoProjeto());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbProjeto.setItems(dadosProj);
		cbEmpregados.setItems(dadosEmp);
		tbProjetosEmpregados.setItems(dados);
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

}
