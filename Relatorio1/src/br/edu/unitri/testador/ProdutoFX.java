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
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.model.Produto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;

/**
 * @author marcos.fernando
 *
 */
public class ProdutoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private RadioButton rbCodBarras;
	@FXML private TextField txtProduto;
	@FXML private TableView<Produto> tbProdutos;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private RadioButton rbDescricao;
	@FXML private Button btnIncluir;
	@FXML private TextField txtCodBarras;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtValor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;
	
	private ObservableList<Produto> dados = FXCollections.observableArrayList();
	private int Operacao;
	private ProdutoControler produtoCtr = new ProdutoControler();
	private Produto produto;

	
	@FXML 
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbDescricao.isSelected()) {
			   dados.addAll(produtoCtr.findAll("select p.* from tbProduto p"," where p.nomeProduto like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbCodBarras.isSelected()) {
				dados.addAll(produtoCtr.findAll("select p.* from tbProduto p"," where p.codBarras like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbProdutos.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}

	private boolean isValidConsulta() {
		boolean ok = rbDescricao.isSelected() || rbCodBarras.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}

	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Produto produto = new Produto(txtProduto.getText(),txtCodBarras.getText(), new BigDecimal(txtValor.getText()));
			switch (getOperacao()) {
			case 0:
				try {
					if (produtoCtr.save(produto) != null) {
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
					if (produtoCtr.update(produto,(int) getProduto().getCodProduto())) {
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
		if (txtProduto.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome do Produto!").showDialog();			
			txtProduto.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtCodBarras.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o código de barras do produto!").showDialog();
				txtCodBarras.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtValor.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher o valor do produto!").showDialog();
				txtValor.requestFocus();
				ok = false;			
			}
		}
		if (ok){
			if (txtValor.getText().contains(",")) {
				new FXDialog(Type.WARNING, "Favor preencher o Separador de Decimais do valor do produto com '.'").showDialog();
				txtValor.requestFocus();
				ok = false;			
			}
		}
		return ok;		 
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtProduto.setText("");
		txtCodBarras.setText("");
		txtValor.setText("");
		txtProduto.requestFocus();
	}

	@FXML
	void btnExcluirClick(ActionEvent event) {
		boolean ok = new FXDialog(Type.CONFIRM,
				"Tem certeza que deseja excluir este registro?").showDialog();
		if ((ok) && (getOperacao() == 1)) {
			try {
				ok = produtoCtr.delete(getProduto());
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
		}
	}
	
	private void popularDados() {
		dados.clear();
		tbProdutos.getItems().clear();
		try {
			dados.addAll(produtoCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbProdutos.setItems(dados);
	}


	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbProdutos.getColumns().addAll(new GenericTable<Produto>().tableColunas(Produto.class));
		popularDados();
		tbProdutos.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					tabTela.getSelectionModel().select(1);	
					setOperacao(1);
					setProduto(tbProdutos.getSelectionModel().getSelectedItem());
					txtCodBarras.setText(tbProdutos.getSelectionModel().getSelectedItem().getCodBarras());
					txtProduto.setText(tbProdutos.getSelectionModel().getSelectedItem().getNomeProduto());
					txtValor.setText(tbProdutos.getSelectionModel().getSelectedItem().getValorProd().toString());					
				}
			}
		});
	}

	public int getOperacao() {
		return Operacao;
	}

	public void setOperacao(int operacao) {
		Operacao = operacao;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}	

}
