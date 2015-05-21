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
import br.edu.unitri.DTO.Consultas.GerenteEmpregadoDTO;
import br.edu.unitri.controler.ConsultasControler;
import br.edu.unitri.controler.EmpregadoControler;
import br.edu.unitri.controler.GerenciaControler;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.Gerencia;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class GerenteEmpregadoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private ComboBox<Empregado> cbEmpregado;
	@FXML private RadioButton rbGerente;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private TableView<GerenteEmpregadoDTO> tbGerenteEmpregados;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbEmpregado;
	@FXML private ToggleGroup buscarPor;
	@FXML private ComboBox<Gerencia> cbGerente;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Empregado> dadosEmp = FXCollections.observableArrayList();
	private ObservableList<Gerencia> dadosGer = FXCollections.observableArrayList();
	private ObservableList<GerenteEmpregadoDTO> dados = FXCollections.observableArrayList();
	private EmpregadoControler empregadoCtr = new EmpregadoControler();
	private GerenciaControler gerenteCtr = new GerenciaControler();
	private ConsultasControler consultaCtr = new ConsultasControler();

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			String qry = "select e.nomeempregado as nomeEmpregado, e2.nomeempregado as nomeGerente"
					+ " from tbempregado e join tbempregado e2 on e2.codEmpregado = e.gerente_id"
					+ " join tbGerencia g on g.gerente_id = e2.codEmpregado";

			if (rbGerente.isSelected()) {
			   dados.addAll(consultaCtr.findGerenteEmpregado(qry, " where e2.nomeempregado like  '%"+ txtBuscar.getText() + "%'"));
			} else if (rbEmpregado.isSelected()) {
			   dados.addAll(consultaCtr.findGerenteEmpregado(qry, " where e.nomeempregado like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbGerenteEmpregados.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbGerente.isSelected() || rbEmpregado.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Empregado empregado = cbEmpregado.getSelectionModel().getSelectedItem();
			empregado.setGerente(cbGerente.getSelectionModel().getSelectedItem().getEmpregado());
			try {
				if (empregadoCtr.update(empregado, (int) cbEmpregado
						.getSelectionModel().getSelectedItem().getCodEmpregado())) {
					new FXDialog(Type.INFO, "Registro atualizado com sucesso!")
							.showDialog();
				}
			} 
			catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			}
		}
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (cbGerente.getSelectionModel().getSelectedItem() == null) {
			new FXDialog(Type.WARNING, "Favor selecionar o gerente!").showDialog();			
			cbGerente.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbEmpregado.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o empregado!").showDialog();
				cbEmpregado.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		cbGerente.getSelectionModel().select(null);
		cbEmpregado.getSelectionModel().select(null);
		cbGerente.requestFocus();
	}
	
	private void popularDados() {
		cbGerente.getItems().clear();
		cbEmpregado.getItems().clear();
		tbGerenteEmpregados.getItems().clear();
		dadosEmp.clear();
		dadosGer.clear();
		try {
			dadosEmp.addAll(empregadoCtr.findAll());
			dadosGer.addAll(gerenteCtr.findAll());
			dados.addAll(consultaCtr.findGerenteEmpregado());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbGerente.setItems(dadosGer);
		cbEmpregado.setItems(dadosEmp);
		tbGerenteEmpregados.setItems(dados);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbGerenteEmpregados.getColumns().addAll(new GenericTable<GerenteEmpregadoDTO>().tableColunas(GerenteEmpregadoDTO.class));
		popularDados();
	}
}
