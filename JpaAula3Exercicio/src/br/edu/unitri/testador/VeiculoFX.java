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
import javafx.stage.Stage;
import br.edu.unitri.Controler.CategoriaControler;
import br.edu.unitri.Controler.VeiculoControler;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Veiculo;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class VeiculoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private Button btnCategoria;
	@FXML private ComboBox<Categoria> cbCategoria;
	@FXML private TextField txtModelo;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtDescricao;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbModelo;
	@FXML private RadioButton rbDescricao;
	@FXML private Button btnIncluir;
	@FXML private TableView<Veiculo> tbVeiculos;
	@FXML private RadioButton rbCategoria;
	@FXML private ToggleGroup buscarPor;
	@FXML private Button btnExcluir;
	@FXML private TextField txtPreco;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Veiculo> dados = FXCollections.observableArrayList();
	private ObservableList<Categoria> listaCategoria = FXCollections.observableArrayList();
	private int Operacao;	
	private VeiculoControler veiculoCtr = new VeiculoControler();
	private CategoriaControler categoriaCtr = new CategoriaControler();
	private long idVeiculo;
	private Veiculo veiculo;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
		if (isValidConsulta()) {
			dados.clear();
			if (rbDescricao.isSelected()) {
				dados.addAll(veiculoCtr.findAll("select v.* from tbVeiculo v"," where v.descricao like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbModelo.isSelected()) {
				dados.addAll(veiculoCtr.findAll("select v.* from tbVeiculo v"," where v.modelo like  '%"+ txtBuscar.getText() + "%'"));	
			} else if (rbCategoria.isSelected()) {				
				dados.addAll(veiculoCtr.findAll("select v.* from tbVeiculo v, tbCategoria c"," where v.categoria_id = c.idCategoria and c.descricao like  '%"+ txtBuscar.getText() + "%'"));	
			}
			txtBuscar.setText("");
			tbVeiculos.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbDescricao.isSelected() || rbModelo.isSelected() || rbCategoria.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnCategoriaClick(ActionEvent event) {
		Stage telaCategoria = new Stage();
		try {
			new FormFX<CategoriaFX>("Categoria.fxml", telaCategoria, "Cadastros - Operações com Categoria", false);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		PopularCombos();
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Veiculo veiculo = new Veiculo(txtDescricao.getText(), txtModelo.getText(), new BigDecimal(txtPreco.getText()),
					cbCategoria.getSelectionModel().getSelectedItem());
			switch (getOperacao()) {
			case 0:				
				try {
					if (veiculoCtr.save(veiculo) != null) {
						new FXDialog(Type.INFO, "Operação realizada com sucesso!").showDialog();
					}
				} catch (SQLException e) {
					new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
				};
				popularDados();
				break;
			case 1:
				try {
					if (veiculoCtr.update(veiculo,(int)getIdVeiculo())) {
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
			new FXDialog(Type.WARNING, "Favor preencher a descrição do Veículo!").showDialog();			
			txtDescricao.requestFocus();
			ok = false;			
		}
		if (ok) {
			if (txtModelo.getText().isEmpty()) {
				txtModelo.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o modelo do Veículo!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (txtPreco.getText().isEmpty()) {
				txtPreco.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher fator preço do Veículo!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (txtPreco.getText().contains(",")) {
				txtPreco.requestFocus();
				new FXDialog(Type.WARNING, "Favor preencher o fator preço com separador de Decimais com '.'!").showDialog();
				ok = false;			
			}
		}
		if (ok) {
			if (cbCategoria.getSelectionModel().getSelectedItem() == null){
				cbCategoria.requestFocus();
				new FXDialog(Type.WARNING, "Favor escolha o tipo de categoria!").showDialog();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtDescricao.setText("");
		txtModelo.setText("");
		txtPreco.setText("");
		PopularCombos();
		txtDescricao.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = veiculoCtr.delete(getVeiculo());
			} catch (SQLException e) {
				new FXDialog(Type.ERROR,"Erro ao tentar excluir veículo ->" + e.getCause().getMessage()).showDialog();
			}
			if (ok) {
				new FXDialog(Type.INFO,"Operação realizada com sucesso!").showDialog();
				popularDados();
				btnNovoClick(event);
			} else {
				new FXDialog(Type.INFO,"Veículo não pode ser excluido. Existem relações com outras entidades!").showDialog();
				popularDados();
			}
		} else {
			new FXDialog(Type.WARNING,"Não foi selecionado nenhum Veículo!").showDialog(); 
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		tbVeiculos.getColumns().addAll(new GenericTable<Veiculo>().tableColunas(Veiculo.class));
		popularDados();
		PopularCombos();
		
		tbVeiculos.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if(event.getClickCount() > 1){
					tabTela.getSelectionModel().select(1);
					setOperacao(1);
					setVeiculo(tbVeiculos.getSelectionModel().getSelectedItem());					
					setIdVeiculo(tbVeiculos.getSelectionModel().getSelectedItem().getIdVeiculo());
					txtDescricao.setText(tbVeiculos.getSelectionModel().getSelectedItem().getDescricao());
					txtModelo.setText(tbVeiculos.getSelectionModel().getSelectedItem().getModelo());
					txtPreco.setText(tbVeiculos.getSelectionModel().getSelectedItem().getFator().toString());
					cbCategoria.getSelectionModel().select(tbVeiculos.getSelectionModel().getSelectedItem().getCategoria());
				}
			}
		});
	}

	private void PopularCombos(){
		cbCategoria.getItems().clear();
		try {
			listaCategoria.addAll(categoriaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao inicializar lista de categorias ->" +  e.getCause().getMessage()).showDialog();
		}
		cbCategoria.setItems(listaCategoria);		
	}

	private void popularDados(){
		dados.clear();
		tbVeiculos.getItems().clear();
		try {
			dados.addAll(veiculoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, "Erro ao buscar dados ->" +  e.getCause().getMessage()).showDialog();
		}
		tbVeiculos.setItems(dados);		
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public long getIdVeiculo() {
		return idVeiculo;
	}

	public void setIdVeiculo(long idVeiculo) {
		this.idVeiculo = idVeiculo;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
}
