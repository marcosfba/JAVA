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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import br.edu.unitri.Factory.DadosFactory;
import br.edu.unitri.testador.FXDialog.Type;
import br.edu.unitri.util.Path;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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
public class RelClienteFX implements Initializable {

	@FXML private RadioButton rbHTML;
	@FXML private RadioButton rbPDF;
	@FXML private ToggleGroup buscarPor;
	@FXML private RadioButton rbXLS;
	@FXML private RadioButton rbVisualizar;
	@FXML private Button btnGerarRelatorio;

	@SuppressWarnings({ "unchecked", "rawtypes"})
	@FXML
	void btnGerarRelatorioClick(ActionEvent event) {
		InputStream input = null;
		try {
			input = new FileInputStream(Path.PATH_REPORTS + "relClientes.jrxml");
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
			jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new DadosFactory().createDataSourceCliente());
		} catch (Exception e) {
			new FXDialog(Type.ERROR, e.getMessage()).showDialog();
		}
		
		boolean ok = false;
		if (rbVisualizar.isSelected()) {
		   JasperViewer.viewReport(jasperPrint, false);
		   ok = true;
		}
		String filename = "";
		if (rbPDF.isSelected()) {
			filename = Path.PATH_Raiz + "relClientes.pdf";
			try {
				JasperExportManager.exportReportToPdfFile(jasperPrint,filename);
			} catch (Exception e) {
				new FXDialog(Type.ERROR,"Erro ao gerar arquivo PDF: " + e.getMessage()).showDialog();
			}
		}
		else if (rbHTML.isSelected()) {
			filename = Path.PATH_Raiz + "relClientes.html";
			try {
				JasperExportManager.exportReportToHtmlFile(jasperPrint,filename);
			} catch (Exception e) {
				new FXDialog(Type.ERROR,"Erro ao gerar arquivo HTML: " + e.getMessage()).showDialog();
			}
		}
		else if (rbXLS.isSelected()) {
			filename = Path.PATH_Raiz + "relClientes.xls";
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
		rbVisualizar.setSelected(true);
	}

}
