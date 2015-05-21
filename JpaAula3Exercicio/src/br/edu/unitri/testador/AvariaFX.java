/**
 * 
 */
package br.edu.unitri.testador;

import java.math.BigDecimal;
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
import br.edu.unitri.Controler.AvariaControler;
import br.edu.unitri.Controler.LocacaoControler;
import br.edu.unitri.model.Avaria;
import br.edu.unitri.model.Locacao;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class AvariaFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbLocacao;
	@FXML private RadioButton rbDescricao;
	@FXML private TableView<Avaria> tbAvarias;
	@FXML private Button btnIncluir;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtValor;
 	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	@FXML private ComboBox<Locacao> cbLocacao;
	
	private ObservableList<Avaria> dados = FXCollections.observableArrayList();
	private ObservableList<Locacao> listaLocacao = FXCollections.observableArrayList();
	
	private AvariaControler avariaCtr = new AvariaControler();
	private LocacaoControler locacaoCtr = new LocacaoControler();
	
	private int Operacao;	
	private long idAvaria;
	private Avaria avaria;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbDescricao.isSelected()) {
				dados.addAll(avariaCtr.findAll("select l.* from tbAvaria l"," where l.descricao like  '%"+ txtBuscar.getText() + "%'"));	
			} else if (rbLocacao.isSelected()) {
				dados.addAll(avariaCtr.findAll("select l.* from tbAvaria l, tbLocacao c"," where l.idLocacao = c.idLocacao and c.idLocacao = '"+ txtBuscar.getText() + "'"));	
			} 
			txtBuscar.setText("");
			tbAvarias.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbDescricao.isSelected() || rbLocacao.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Avaria avaria = new Avaria(new BigDecimal(txtValor.getText()),txtDescricao.getText(), cbLocacao.getSelectionModel().getSelectedItem());
			switch (getOperacao()) {
			case 0:				
				try {
					if (avariaCtr.save(avaria) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (avariaCtr.update(avaria,(int)getIdAvaria())) {
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
			new FXDialog(Type.WARNING, "Favor preencher a descrição da avaria!").showDialog();			
			txtDescricao.requestFocus();
			ok = false;			
		}
		if (ok) {
			if (txtValor.getText().isEmpty()) {
				txtValor.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o valor da avaria!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (txtValor.getText().contains(",")) {
				txtValor.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o valor da avaria com separador de Decimais com '.'!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (cbLocacao.getSelectionModel().getSelectedItem() == null){
				cbLocacao.requestFocus();
				new FXDialog(Type.WARNING, "Favor escolha o Locação!").showDialog();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtDescricao.setText("");
		txtValor.setText("");
		PopularCombos();
		txtDescricao.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = avariaCtr.delete(getAvaria());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR,"Erro ao tentar excluir avaria ->" + e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhuma Avaria!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tbAvarias.getColumns().addAll(new GenericTable<Avaria>().tableColunas(Avaria.class));
		popularDados();
		PopularCombos();
		
		tbAvarias.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setIdAvaria(tbAvarias.getSelectionModel().getSelectedItem().getIdAvaria());
					setAvaria(tbAvarias.getSelectionModel().getSelectedItem());
					
					cbLocacao.getSelectionModel().select(tbAvarias.getSelectionModel().getSelectedItem().getLocacao());
					txtDescricao.setText(tbAvarias.getSelectionModel().getSelectedItem().getDescricao());
					txtValor.setText(tbAvarias.getSelectionModel().getSelectedItem().getValor().toString());
				}
			}
		});
	}
	
	private void PopularCombos(){
		cbLocacao.getItems().clear();
		try {
			listaLocacao.addAll(locacaoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao inicializar lista de locações ->" +  e.getCause().getMessage()).showDialog();
		}
		cbLocacao.setItems(listaLocacao);		
	}

	private void popularDados(){
		dados.clear();
		tbAvarias.getItems().clear();
		try {
			dados.addAll(avariaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao buscar dados ->" +  e.getCause().getMessage()).showDialog();
		}
		tbAvarias.setItems(dados);		
	}


	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdAvaria() {
		return idAvaria;
	}

	public void setIdAvaria(long idAvaria) {
		this.idAvaria = idAvaria;
	}

	public Avaria getAvaria() {
		return avaria;
	}

	public void setAvaria(Avaria avaria) {
		this.avaria = avaria;
	}
}
