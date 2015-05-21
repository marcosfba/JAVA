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
import br.edu.unitri.Controler.ClienteControler;
import br.edu.unitri.Controler.FuncionarioControler;
import br.edu.unitri.Controler.LocacaoControler;
import br.edu.unitri.Controler.VeiculoControler;
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
public class EmprestimoFX implements Initializable {
	
	@FXML private Tab tabConsulta;
	@FXML private ComboBox<Veiculo> cbVeiculo;
	@FXML private Tab tabCadastro;
	@FXML private ComboBox<Funcionario> cbFuncionario;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbLocacao;
	@FXML private TableView<Locacao> tbLocacoes;
	@FXML private ComboBox<Cliente> cbCliente;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbCliente;
	@FXML private Button btnCliente;
	@FXML private RadioButton rbVeículo;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnVeiculo;
	@FXML private Button btnFuncionario;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	@FXML private TextField txtKilometragem;
	@FXML private TextField txtDias;

	private ObservableList<Locacao> dados = FXCollections.observableArrayList();
	
	private ObservableList<Veiculo> listaVeiculo = FXCollections.observableArrayList();
	private ObservableList<Cliente> listaCliente = FXCollections.observableArrayList();
	private ObservableList<Funcionario> listaFuncionario = FXCollections.observableArrayList();
	
	private VeiculoControler veiculoCtr = new VeiculoControler();
	private FuncionarioControler funcionarioCtr = new FuncionarioControler();
	private ClienteControler clienteCtr = new ClienteControler();
	private LocacaoControler locacaoCtr = new LocacaoControler();
	
	private int Operacao;	
	private long idLocacao;
	private Locacao locacao;

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
	void btnClienteClick(ActionEvent event) {
		Stage telaCliente = new Stage();
		try {
			new FormFX<ClienteFX>("Cliente.fxml", telaCliente, "Cadastros - Operações com Clientes", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
		PopularCombos();
	}

	@FXML
	void btnVeiculoClick(ActionEvent event) {
		Stage telaVeiculo = new Stage();
		try {
			new FormFX<VeiculoFX>("Veiculo.fxml", telaVeiculo, "Cadastros - Operações com Veículos", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
			e.printStackTrace();
		}
		PopularCombos();
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
			Locacao locacao = new Locacao(cbVeiculo.getSelectionModel().getSelectedItem(),
					                      cbCliente.getSelectionModel().getSelectedItem(),
					                      1, cbFuncionario.getSelectionModel().getSelectedItem(), null,
					                      Integer.valueOf(txtDias.getText()), Integer.valueOf(txtKilometragem.getText()));
			switch (getOperacao()) {
			case 0:				
				try {
					if (locacaoCtr.save(locacao) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (locacaoCtr.update(locacao,(int)getIdLocacao())) {
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
			if (cbCliente.getSelectionModel().getSelectedItem() == null){
				cbCliente.requestFocus();
				new FXDialog(Type.WARNING, "Favor escolha o Cliente!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (cbVeiculo.getSelectionModel().getSelectedItem() == null){
				cbVeiculo.requestFocus();
				new FXDialog(Type.WARNING, "Favor escolha o Veículo!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (cbFuncionario.getSelectionModel().getSelectedItem() == null){
				cbFuncionario.requestFocus();
				new FXDialog(Type.WARNING, "Favor escolha o Funcionário!").showDialog();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtDias.setText("");
		txtKilometragem.setText("");
		PopularCombos();
		cbCliente.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = locacaoCtr.delete(getLocacao());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR,"Erro ao tentar excluir locação ->" + e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.INFO,"Locação não pode ser excluida. Existem relações com outras entidades!").showDialog();
				popularDados();
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhuma Locação!").showDialog(); 
		}
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
					setOperacao(1);
					setLocacao(tbLocacoes.getSelectionModel().getSelectedItem());
					setIdLocacao(tbLocacoes.getSelectionModel().getSelectedItem().getIdLocacao());
					cbCliente.getSelectionModel().select(tbLocacoes.getSelectionModel().getSelectedItem().getCliente());
					cbFuncionario.getSelectionModel().select(tbLocacoes.getSelectionModel().getSelectedItem().getFuncionarioCad());
					cbVeiculo.getSelectionModel().select(tbLocacoes.getSelectionModel().getSelectedItem().getVeiculo());
					txtDias.setText(String.valueOf(tbLocacoes.getSelectionModel().getSelectedItem().getQtdDias()));
					txtKilometragem.setText(String.valueOf(tbLocacoes.getSelectionModel().getSelectedItem().getKilometragem()));
				}
			}
		});
	}

	private void PopularCombos(){
		cbCliente.getItems().clear();
		try {
			listaCliente.addAll(clienteCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao inicializar lista de clientes ->" +  e.getCause().getMessage()).showDialog();
		}
		cbFuncionario.getItems().clear();
		try {
			listaFuncionario.addAll(funcionarioCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao inicializar lista de funcionários ->" +  e.getCause().getMessage()).showDialog();
		}
		cbVeiculo.getItems().clear();
		try {
			listaVeiculo.addAll(veiculoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao inicializar lista de veículos ->" +  e.getCause().getMessage()).showDialog();
		}
		
		cbCliente.setItems(listaCliente);		
		cbFuncionario.setItems(listaFuncionario);	
		cbVeiculo.setItems(listaVeiculo);
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

	public Locacao getLocacao() {
		return locacao;
	}

	public void setLocacao(Locacao locacao) {
		this.locacao = locacao;
	}
	
}
