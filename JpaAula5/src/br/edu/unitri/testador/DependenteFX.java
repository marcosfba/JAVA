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
import br.edu.unitri.controler.DependenteControler;
import br.edu.unitri.controler.EmpregadoControler;
import br.edu.unitri.model.Dependente;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.TipoDependente;
import br.edu.unitri.model.TipoSexo;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.ConverterUtil;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class DependenteFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private ComboBox<Empregado> cbEmpregado;
	@FXML private TextField txtNome;
	@FXML private TableView<Dependente> tbDependentes;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbNomeDep;
	@FXML private RadioButton rbNomeEmp;
	@FXML private ComboBox<TipoDependente> cbTipoDependente;
	@FXML private ComboBox<TipoSexo> cbSexo;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private DatePicker dtNascimento;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Dependente> dados = FXCollections.observableArrayList();
	private ObservableList<Empregado> dadosEmp = FXCollections.observableArrayList();	
	private DependenteControler dependenteCtr = new DependenteControler();
	private EmpregadoControler empregadoCtr = new EmpregadoControler();
	private Dependente dependente;
	private int Operacao;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbNomeDep.isSelected()) {
			   dados.addAll(dependenteCtr.findAll("select p.* from tbDependente p"," where p.nome like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbNomeEmp.isSelected()) {
				dados.addAll(dependenteCtr.findAll("select p.* from tbDependente p, tbEmpregado e"," where p.idEmpregado = e.codEmpregado and e.nomeEmpregado like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbDependentes.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbNomeDep.isSelected() || rbNomeEmp.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Dependente dependente = new Dependente(txtNome.getText(), cbSexo.getSelectionModel().getSelectedItem(),
					cbTipoDependente.getSelectionModel().getSelectedItem(), 
					ConverterUtil.localDateToDate(dtNascimento.getValue()), cbEmpregado.getSelectionModel().getSelectedItem());
			switch (getOperacao()) {
			case 0:
				try {
					if (dependenteCtr.save(dependente) != null) {
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
					if (dependenteCtr.update(dependente,(int) getDependente().getCodDependente())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome do dependente!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (cbSexo.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o sexo do dependente!").showDialog();
				cbSexo.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (cbTipoDependente.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar o tipo do dependente!").showDialog();
				cbTipoDependente.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (dtNascimento.getValue() == null) {
				new FXDialog(Type.WARNING, "Favor colocar a data de nascimento!").showDialog();
				dtNascimento.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		cbEmpregado.getSelectionModel().select(null);
		cbSexo.getSelectionModel().select(-1);
		cbTipoDependente.getSelectionModel().select(-1);
		dtNascimento.setValue(null);
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = dependenteCtr.delete(getDependente());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Dependente!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbDependentes.getColumns().addAll(new GenericTable<Dependente>().tableColunas(Dependente.class));
		popularDados();
		tbDependentes.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setDependente(tbDependentes.getSelectionModel().getSelectedItem());
					txtNome.setText(tbDependentes.getSelectionModel().getSelectedItem().getNome());
					dtNascimento.setValue(ConverterUtil.utilDateToLocalDate(tbDependentes.getSelectionModel().getSelectedItem().getDtNascimento()));
					cbSexo.getSelectionModel().select(tbDependentes.getSelectionModel().getSelectedItem().getSexo());
					cbTipoDependente.getSelectionModel().select(tbDependentes.getSelectionModel().getSelectedItem().getTipoDependente());
					cbEmpregado.getSelectionModel().select(tbDependentes.getSelectionModel().getSelectedItem().getEmpregado());
				}
			}
		});
	}

	private void popularDados() {
		dados.clear();
		dadosEmp.clear();
		cbSexo.getItems().clear();
		cbTipoDependente.getItems().clear();
		tbDependentes.getItems().clear();
		try {
			dados.addAll(dependenteCtr.findAll());
			dadosEmp.addAll(empregadoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbDependentes.setItems(dados);
		cbSexo.getItems().setAll(TipoSexo.values());
		cbTipoDependente.getItems().setAll(TipoDependente.values());	
		cbEmpregado.setItems(dadosEmp);
	}

	public Dependente getDependente() {
		return dependente;
	}

	public void setDependente(Dependente dependente) {
		this.dependente = dependente;
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

}
