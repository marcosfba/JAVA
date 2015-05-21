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
import br.edu.unitri.DTO.Consultas.DepartamentoLocalDTO;
import br.edu.unitri.controler.ConsultasControler;
import br.edu.unitri.controler.DepartamentoControler;
import br.edu.unitri.controler.LocalControler;
import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Local;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class LocalDepartamentosFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbDepartamento;
	@FXML private Tab tabCadastro;
	@FXML private ComboBox<Local> cbLocal;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbDescricao;
	@FXML private TableView<DepartamentoLocalDTO> tbLocaisDep;
	@FXML private ComboBox<Departamento> cbDepartamento;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<DepartamentoLocalDTO> dados = FXCollections.observableArrayList();
	private ObservableList<Departamento> dadosDep = FXCollections.observableArrayList();
	private ObservableList<Local> dadosLoc = FXCollections.observableArrayList();
	private DepartamentoControler departamentoCtr = new DepartamentoControler();
	private LocalControler localCtr = new LocalControler();
	private ConsultasControler consultaCtr = new ConsultasControler();
	
	private Local local;
	private Departamento departamento;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			String qry = "select d.numdepartamento || '-' || d.desclocal as nomedepartamento,"
					    + " l.nomlocal ||'-'|| l.descLocal as nomeLocal"
			 	        + " from local_dept ld join tbDepartamento d on d.idDepartamento = ld.departamento_id"
					    + " join tbLocal l on l.idLocal = ld.local_id";
			if (rbDescricao.isSelected()) {
			   dados.addAll(consultaCtr.findDepartamentoLocal(qry, " where (l.nomlocal like  '%"+ txtBuscar.getText() + "%') or"
					            							  	 + " (l.descLocal like  '%"+ txtBuscar.getText() + "%')"));
			} else if (rbDepartamento.isSelected()) {
			   dados.addAll(consultaCtr.findDepartamentoLocal(qry, " where (d.numdepartamento like  '%"+ txtBuscar.getText() + "%') or"
					        						             + " (d.desclocal like  '%"+ txtBuscar.getText() + "%')"));
			}
			tbLocaisDep.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbDescricao.isSelected() || rbDepartamento.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			setLocal(cbLocal.getSelectionModel().getSelectedItem());
			boolean ok = false;
			for (Departamento depExiste : local.getDepartamentos()) {
				if (depExiste.equals(cbDepartamento.getSelectionModel().getSelectedItem())){
					ok = true;
				}
			}
			if (!ok) {
				local.getDepartamentos().add(cbDepartamento.getSelectionModel().getSelectedItem());
				Local locInt = null;
				try {
					locInt = localCtr.save(getLocal());
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				}
				if (locInt != null) {
					new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					popularDados();
					btnNovoClick(event);
				}
			} else {
				new FXDialog(Type.INFO, "Departameno selecionado não pode ser inserido neste local!").showDialog();
				cbLocal.requestFocus();			
			}
		}
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (cbLocal.getSelectionModel().getSelectedItem() == null) {
			new FXDialog(Type.WARNING, "Favor selecione o local!").showDialog();			
			cbLocal.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbDepartamento.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecione o departamento!").showDialog();
				cbDepartamento.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		cbLocal.getSelectionModel().select(null);
		cbDepartamento.getSelectionModel().select(null);
		cbLocal.requestFocus();		
	}
	
	private void popularDados() {
		cbDepartamento.getItems().clear();
		cbLocal.getItems().clear();
		dados.clear();
		dadosDep.clear();
		dadosLoc.clear();
		try {
			dados.addAll(consultaCtr.findDepartamentoLocal());
			dadosDep.addAll(departamentoCtr.findAll());
			dadosLoc.addAll(localCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		cbDepartamento.setItems(dadosDep);
		cbLocal.setItems(dadosLoc);
		tbLocaisDep.setItems(dados);
	}
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbLocaisDep.getColumns().addAll(new GenericTable<DepartamentoLocalDTO>().tableColunas(DepartamentoLocalDTO.class));
		popularDados();		
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

}
