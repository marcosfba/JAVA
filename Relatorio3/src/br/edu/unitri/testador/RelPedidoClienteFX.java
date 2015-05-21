/**
 * 
 */
package br.edu.unitri.testador;

import java.awt.Desktop;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import br.edu.unitri.Factory.DadosFactory;
import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.ClientesDTO;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.GenericTable;
import br.edu.unitri.util.JpaUtil;
import br.edu.unitri.util.Path;
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
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.view.JasperViewer;

/**
 * @author marcos.fernando
 *
 */
public class RelPedidoClienteFX implements Initializable {

	@FXML private ToggleGroup tipoRelatorio;
	@FXML private Tab tabRelatorio;
	@FXML private TextField txtNome;
	@FXML private TextField txtTelefone;
	@FXML private ToggleGroup gerarPor;
	@FXML private TextField txtProfissao;
	@FXML private TableView<ClientesDTO> tbPedidos;
	@FXML private RadioButton rbHtml;
	@FXML private RadioButton rbRelatorioSint;
	@FXML private RadioButton rbPDF;
	@FXML private TextField txtCidade;
	@FXML private RadioButton rbRelatorioDet;
	@FXML private RadioButton rbXls;
	@FXML private RadioButton rbVisualizar;
	@FXML private TabPane tabTela;
	@FXML private Button btnBuscar;
	@FXML private Button btnLimpar;
    
	private ObservableList<ClientesDTO> dadosCli = FXCollections.observableArrayList();
	private ClienteControler clienteCtr = new ClienteControler();
	private ClientesDTO clienteDTO;

	@SuppressWarnings("rawtypes")
	@FXML
	void btnBuscarClick(ActionEvent event) {

		CriteriaBuilder cb =  JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ClientesDTO> query = cb.createQuery(ClientesDTO.class);
		
		Root<Cliente> root = query.from(Cliente.class);
		EntityType<Cliente> typeCli = JpaUtil.getManager().getMetamodel().entity(Cliente.class);
		
		Predicate condicao1 = null, condicao2 = null, condicao3 = null, condicao4 = null; 
		
		if (!txtNome.getText().isEmpty()) {
			condicao1 = cb.like(root.get(typeCli.getDeclaredSingularAttribute("nomeCliente", String.class)),
					"%" + txtNome.getText() + "%");
		}
		if (!txtCidade.getText().isEmpty()) {
			condicao2 = cb.like(root.get(typeCli.getDeclaredSingularAttribute("descCidade", String.class)),
					"%" + txtCidade.getText() + "%");
		}
		if (!txtTelefone.getText().isEmpty()) {
			condicao3 = cb.like(root.get(typeCli.getDeclaredSingularAttribute("descTelefone", String.class)),
					"%" + txtTelefone.getText() + "%");
		}
		if (!txtProfissao.getText().isEmpty()) {
			condicao4 = cb.like(root.get(typeCli.getDeclaredSingularAttribute("descCargo", String.class)),
					"%" + txtProfissao.getText() + "%");
		}
		
		List<Predicate> predicados = new ArrayList<Predicate>(); 
		if (condicao1 != null) { predicados.add(condicao1) ; }
		if (condicao2 != null) { predicados.add(condicao2) ; }
		if (condicao3 != null) { predicados.add(condicao3) ; }
		if (condicao4 != null) { predicados.add(condicao4) ; }
		
		query.multiselect(root.get("codCliente").alias("codCliente"), 
				  root.get("nomeCliente").alias("nomeCliente"),
		          cb.size(root.<Collection>get("pedidos")).alias("qtdPedidos")
		);
		query.where(cb.and(predicados.toArray(new Predicate[]{})));
		query.groupBy(root.get("codCliente"), root.get("nomeCliente"));
		query.orderBy(cb.asc(root.get("codCliente")));
		query.distinct(true);
		
		List<ClientesDTO> listaClientes =  JpaUtil.getManager().createQuery(query).getResultList();
		dadosCli.clear();
		tbPedidos.getItems().clear();
		dadosCli.addAll(listaClientes);
		tbPedidos.setItems(dadosCli);
	}

	@FXML
	void btnLimparClick(ActionEvent event) {
		txtCidade.setText("");
		txtNome.setText("");
		txtProfissao.setText("");
		txtTelefone.setText("");
		popularDados();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@FXML
	void btnGerarRelatorioClick(ActionEvent event) {
		
		boolean okSintetico = rbRelatorioSint.isSelected();
		boolean okAnalitico = rbRelatorioDet.isSelected();
			
		InputStream input = null;
		try {
			if (okSintetico) {
				input = new FileInputStream(Path.PATH_REPORTS + "relPedidosClienteSint.jrxml");
			}
			if (okAnalitico) {
				input = new FileInputStream(Path.PATH_REPORTS + "relPedidosCliente.jrxml");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Map parameters = new HashMap();
		JasperDesign jasperDesign = null;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;
		
		try {
			jasperDesign = JRXmlLoader.load(input);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getMessage()).showDialog();
		}
		try {
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getMessage()).showDialog();
		}
		try {
			if (okSintetico) {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new DadosFactory().createDataSourceClientesDTO(getClienteDTO()));
			}
			if (okAnalitico) {
				jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new DadosFactory().createDataSourcePedidoClientesDTO(getClienteDTO()));
			}
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getMessage()).showDialog();
		}
		
		boolean ok = false;
		String fileReport = "";
		if (okSintetico) {
			fileReport = "relPedidosClienteSint";
		}
		if (okAnalitico) {
			fileReport = "relPedidosCliente";
		}
		
		if (rbVisualizar.isSelected()) {
		   JasperViewer.viewReport(jasperPrint, false);
		   ok = true;
		}
		String filename = fileReport;
		if (rbPDF.isSelected()) {
			filename = Path.PATH_Raiz + ".pdf";
			try {
				JasperExportManager.exportReportToPdfFile(jasperPrint,filename);
			} catch (Exception e) {
				new FXDialog(Type.ERROR,"Erro ao gerar arquivo PDF: " + e.getMessage()).showDialog();
			}
		}
		else if (rbHtml.isSelected()) {
			filename = Path.PATH_Raiz + ".html";
			try {
				JasperExportManager.exportReportToHtmlFile(jasperPrint,filename);
			} catch (Exception e) {
				new FXDialog(Type.ERROR,"Erro ao gerar arquivo HTML: " + e.getMessage()).showDialog();
			}
		}
		else if (rbXls.isSelected()) {
			filename = Path.PATH_Raiz + ".xls";
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(filename));		
			SimpleXlsReportConfiguration configuration = new SimpleXlsReportConfiguration();
			configuration.setOnePagePerSheet(true);
			configuration.setDetectCellType(true);
			configuration.setCollapseRowSpan(false);
			exporter.setConfiguration(configuration);
			try {
				exporter.exportReport();
			} catch (Exception e) {
				new FXDialog(Type.ERROR, e.getMessage()).showDialog();
			}
		}
		
		if (!ok) {
			new FXDialog(Type.WARNING,"Relatório gerado com sucesso no diretorio: " + filename).showDialog();
			try {
				Desktop.getDesktop().open(new File(filename));
			} catch (IOException e) {
				new FXDialog(Type.ERROR, e.getMessage()).showDialog();
			}
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tbPedidos.getColumns().addAll(new GenericTable<ClientesDTO>().tableColunas(ClientesDTO.class));
		popularDados();
		rbVisualizar.setSelected(true);
		tbPedidos.setOnMouseClicked(new EventHandler<MouseEvent>() {			
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() == 1) {
					setClienteDTO(tbPedidos.getSelectionModel().getSelectedItem());
				}
			}
		});
	}
	
	private void popularDados() {
		dadosCli.clear();
		tbPedidos.getItems().clear();
		try {
			dadosCli.addAll(clienteCtr.findAllClientesDTO());
		} catch (SQLException e) {
			new FXDialog(Type.ERROR, e.getCause().getMessage()).showDialog();
		}
		tbPedidos.setItems(dadosCli);
	}

	public ClientesDTO getClienteDTO() {
		return clienteDTO;
	}

	public void setClienteDTO(ClientesDTO clienteDTO) {
		this.clienteDTO = clienteDTO;
	}

}
