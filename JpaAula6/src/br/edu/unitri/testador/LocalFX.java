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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.controler.LocalControler;
import br.edu.unitri.model.Local;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class LocalFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbDepartamento;
	@FXML private TextField txtNome;
	@FXML private Tab tabCadastro;
	@FXML private TextArea txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbDescricao;
	@FXML private TableView<Local> tbLocais;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Local> dados = FXCollections.observableArrayList();
	private LocalControler localCtr = new LocalControler();
	private Local local;
	private int Operacao;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbDescricao.isSelected()) {
			   dados.addAll(localCtr.findAll("select p.* from tbLocal p"," where p.nomLocal like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbDepartamento.isSelected()) {
				dados.addAll(localCtr.findAll("select p.* from tbLocal p, tbDepartamento d, Local_Dept ld",
						" where p.idLocal = ld.local_id and d.idDepartamento = ld.departamento_id and"
						+ " d.numDepartamento like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbLocais.setItems(dados);
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
			Local local = new Local(txtNome.getText(),txtDescricao.getText());
			switch (getOperacao()) {
			case 0:
				try {
					if (localCtr.save(local) != null) {
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
					if (localCtr.update(local,(int) getLocal().getIdLocal())) {
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
			new FXDialog(Type.WARNING, "Favor preencher o nome do local!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtDescricao.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a descrição do local!").showDialog();
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
		txtNome.setText("");
		txtNome.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = localCtr.delete(getLocal());
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
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Local!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbLocais.getColumns().addAll(new GenericTable<Local>().tableColunas(Local.class));
		popularDados();
		tbLocais.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setLocal(tbLocais.getSelectionModel().getSelectedItem());
					txtDescricao.setText(tbLocais.getSelectionModel().getSelectedItem().getDescLocal());
					txtNome.setText(tbLocais.getSelectionModel().getSelectedItem().getNomLocal());
				}
			}
		});

	}

	private void popularDados() {
		dados.clear();
		tbLocais.getItems().clear();
		try {
			dados.addAll(localCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbLocais.setItems(dados);
	}

	public Local getLocal() {
		return local;
	}

	public void setLocal(Local local) {
		this.local = local;
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}
	
}
