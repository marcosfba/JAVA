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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import br.edu.unitri.Controler.FuncionarioControler;
import br.edu.unitri.Controler.LocacaoControler;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.Funcionario;
import br.edu.unitri.model.Locacao;
import br.edu.unitri.model.Veiculo;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class DevolucaoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private Tab tabCadastro;
	@FXML private ComboBox<Funcionario> cbFuncionario;
	@FXML private TextField txtBuscar;
	@FXML private TextField txtCliente;
	@FXML private RadioButton rbLocacao;
	@FXML private TextField txtVeiculo;
	@FXML private TableView<Locacao> tbLocacoes;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbCliente;
	@FXML private RadioButton rbVeículo;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnFuncionario;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private TextField txtKilometragem;
	@FXML private TextField txtDias;

	private ObservableList<Locacao> dados = FXCollections.observableArrayList();
	private ObservableList<Funcionario> listaFuncionario = FXCollections.observableArrayList();
	private LocacaoControler locacaoCtr = new LocacaoControler();
	private FuncionarioControler funcionarioCtr = new FuncionarioControler();

	private int Operacao;	
	private long idLocacao;
	private Cliente cliente;
	private Veiculo veiculo;
	private Funcionario funcionario;
	
	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbCliente.isSelected()) {
				dados.addAll(locacaoCtr.findAll("select l.* from tbLocacao l, tbCliente c"," where l.cliente_id = c.idCliente and c.nome like  '%"+ txtBuscar.getText() + "%'"));	
			} else if (rbVeículo.isSelected()) {
				dados.addAll(locacaoCtr.findAll("select l.* from tbLocacao l, tbVeiculo c"," where l.veiculo_id = c.idVeiculo and c.descricao like  '%"+ txtBuscar.getText() + "%'"));	
			} else if (rbLocacao.isSelected()) {				
				dados.addAll(locacaoCtr.findAll("select l.* from tbLocacao l"," where l.idLocacao = '"+ txtBuscar.getText() + "'"));	
			}
			txtBuscar.setText("");
			tbLocacoes.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbCliente.isSelected() || rbVeículo.isSelected() || rbLocacao.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnFuncionarioClick(ActionEvent event) {
		Stage telaFuncionario = new Stage();
		try {
			new FormFX<FuncionarioFX>("Funcionario.fxml", telaFuncionario, "Cadastros - Operações com Funcionários", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
		PopularCombos();
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Locacao locacao = new Locacao(veiculo, cliente, 2, cbFuncionario
					.getSelectionModel().getSelectedItem(), funcionario,
					Integer.valueOf(txtDias.getText()),
					Integer.valueOf(txtKilometragem.getText()));
			try {
				if (locacaoCtr.update(locacao, (int) getIdLocacao())) {
					new FXDialog(Type.INFO, "Registro atualizado com sucesso!")
							.showDialog();
				}
			} catch (SQLException e) {
				new FXDialog(Type.ERROR, e.getCause().getMessage())
						.showDialog();
			}
			popularDados();
		}
	}

	private boolean isValidaTela() {
		boolean ok = true;
		if (txtDias.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher a quantidade de dias da Locação!").showDialog();			
			txtDias.requestFocus();
			ok = false;			
		}
		if (ok) {
			if (txtKilometragem.getText().isEmpty()) {
				txtKilometragem.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher a kilometragem do Veículo referente a Locação!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (cbFuncionario.getSelectionModel().getSelectedItem() == null){
				cbFuncionario.requestFocus();
				new FXDialog(Type.WARNING, "Favor escolha o Funcionário da devolução!").showDialog();
				ok = false;			
			}
		}
		return ok;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbLocacoes.getColumns().addAll(new GenericTable<Locacao>().tableColunas(Locacao.class));
		popularDados();
		PopularCombos();
		
		tbLocacoes.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setIdLocacao(tbLocacoes.getSelectionModel().getSelectedItem().getIdLocacao());
					setCliente(tbLocacoes.getSelectionModel().getSelectedItem().getCliente());
					setVeiculo(tbLocacoes.getSelectionModel().getSelectedItem().getVeiculo());
					setFuncionario(tbLocacoes.getSelectionModel().getSelectedItem().getFuncionarioCad());
					txtCliente.setText(tbLocacoes.getSelectionModel().getSelectedItem().getCliente().toString());
					txtVeiculo.setText(tbLocacoes.getSelectionModel().getSelectedItem().getVeiculo().toString());
					cbFuncionario.getSelectionModel().select(tbLocacoes.getSelectionModel().getSelectedItem().getFuncionarioRec());
					txtDias.setText(String.valueOf(tbLocacoes.getSelectionModel().getSelectedItem().getQtdDias()));
					txtKilometragem.setText(String.valueOf(tbLocacoes.getSelectionModel().getSelectedItem().getKilometragem()));
				}
			}
		});
	}

	private void PopularCombos(){
		cbFuncionario.getItems().clear();
		try {
			listaFuncionario.addAll(funcionarioCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao inicializar lista de funcionários ->" +  e.getCause().getMessage()).showDialog();
		}
		cbFuncionario.setItems(listaFuncionario);	
	}

	private void popularDados(){
		dados.clear();
		tbLocacoes.getItems().clear();
		try {
			dados.addAll(locacaoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao buscar dados ->" +  e.getCause().getMessage()).showDialog();
		}
		tbLocacoes.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdLocacao() {
		return idLocacao;
	}

	public void setIdLocacao(long idLocacao) {
		this.idLocacao = idLocacao;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}
}
