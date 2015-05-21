/**
 * 
 */
package br.edu.unitri.testador;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Produto;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
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

/**
 * @author marcos.fernando
 *
 */
public class ProdutoFX implements Initializable {

	@FXML private Tab tabConsulta;
	@FXML private ComboBox<Categoria> cbCategoria;
	@FXML private TextField txtImagem;
	@FXML private TableView<Produto> tbProdutos;
	@FXML private TextField txtNome;
	@FXML private Tab tabCadastro;
	@FXML private TextField txtBuscar;
	@FXML private TextField txtQtd;
	@FXML private Button btnIncluir;
	@FXML private RadioButton rbCategoria;
	@FXML private RadioButton rbProduto;
	@FXML private ToggleGroup buscarPor;
	@FXML private TextField txtValor;
	@FXML private Button btnExcluir;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnNovo;

	private ObservableList<Produto> dados = FXCollections.observableArrayList();
	private ObservableList<Categoria> dadosCat = FXCollections.observableArrayList();
	private int Operacao;
	private ProdutoControler produtoCtr = new ProdutoControler();
	private CategoriaControler categoriaCtr = new CategoriaControler();
	private Produto produto;

	@FXML
	void btnBuscarClick(ActionEvent event) throws SQLException {
	   	if (isValidConsulta()) {
			dados.clear();
			if (rbProduto.isSelected()) {
			   dados.addAll(produtoCtr.findAll("select p.* from tbProduto p"," where p.nomeProduto like  '%"+ txtBuscar.getText() + "%'"));			
			} else if (rbCategoria.isSelected()) {
				dados.addAll(produtoCtr.findAll("select p.* from tbProduto p join tbCategoria c on c.codCategoria = p.idCategoria"," where c.descCategoria like  '%"+ txtBuscar.getText() + "%'"));
			}
			tbProdutos.setItems(dados);
		} else {
			new FXDialog(Type.WARNING, "Escolha pelo menos uma das opções para consulta!").showDialog();
			txtBuscar.requestFocus();
		}
	}
	
	private boolean isValidConsulta() {
		boolean ok = rbProduto.isSelected() || rbCategoria.isSelected();
		if (ok) {
			ok = !txtBuscar.getText().isEmpty();
		}
		return ok;
	}


	@FXML
	void btnIncluirClick(ActionEvent event) {
		if (isValidaTela()) {
			Produto produto = new Produto(txtNome.getText(),new BigDecimal(txtValor.getText()),Integer.valueOf(txtQtd.getText()), cbCategoria.getSelectionModel().getSelectedItem());
			produto.setImagem(txtImagem.getText());
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
		if (txtNome.getText().isEmpty()) {
			new FXDialog(Type.WARNING, "Favor preencher o nome do Produto!").showDialog();			
			txtNome.requestFocus();
			ok = false;			
		}
		if (ok){
			if (txtQtd.getText().isEmpty()) {
				new FXDialog(Type.WARNING, "Favor preencher a quantidade do produto!").showDialog();
				txtQtd.requestFocus();
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
			if (cbCategoria.getSelectionModel().getSelectedItem() == null) {
				new FXDialog(Type.WARNING, "Favor selecionar a categoria referente ao produto!").showDialog();
				cbCategoria.requestFocus();
				ok = false;			
			}
		}
		return ok;
	}

	@FXML
	void btnNovoClick(ActionEvent event) {
		setOperacao(0);
		txtNome.setText("");
		txtQtd.setText("");
		txtValor.setText("");
		cbCategoria.getSelectionModel().select(null);
		txtImagem.setText("");
		txtNome.requestFocus();
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
					SetValoresComponentes(getProduto());
				}
			}
		});
	}
	
	private void SetValoresComponentes(Produto produto) {
		txtNome.setText(produto.getNomeProduto());
		txtQtd.setText(String.valueOf(produto.getQuantidade()));
		txtValor.setText(String.valueOf(produto.getValorProd()));
		cbCategoria.getSelectionModel().select(produto.getCategoria());
		txtImagem.setText(produto.getImagem());
	}

	private void popularDados() {
		dados.clear();
		dadosCat.clear();
		cbCategoria.getItems().clear();
		tbProdutos.getItems().clear();
		try {
			dados.addAll(produtoCtr.findAll());
			dadosCat.addAll(categoriaCtr.findAll());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbProdutos.setItems(dados);
		cbCategoria.setItems(dadosCat);
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
