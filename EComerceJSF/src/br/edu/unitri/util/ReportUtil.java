/**
 * 
 */
package br.edu.unitri.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 * @author Marcos
 *
 */
@SuppressWarnings("deprecation")
public class ReportUtil {

	public static void geraArquivoRelatorio(String reportFileName,
			JRDataSource dataSource) throws JRException, FileNotFoundException {

		String caminho = UtilBean.getReportResource() + reportFileName
				+ ".jrxml";
		System.out.println(caminho);

		InputStream input = null;
		try {
			input = new FileInputStream(caminho);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		JasperDesign jasperDesign = null;
		JasperReport jasperReport = null;
		JasperPrint jasperPrint = null;

		try {
			jasperDesign = JRXmlLoader.load(input);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
		} catch (Exception e) {
			e.printStackTrace();
		}
		HashMap<String, Object> parametros = new HashMap<String, Object>();
		try {
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					parametros, dataSource);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			createReport(reportFileName, jasperPrint);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void createReport(String arquivo, JasperPrint jasperPrint)
			throws IOException {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.responseComplete();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);

		ServletOutputStream outputStream = null;
		try {
			exporter.exportReport();

			byte[] bytes = baos.toByteArray();

			if (bytes != null && bytes.length > 0) {
				HttpServletResponse response = (HttpServletResponse) facesContext
						.getExternalContext().getResponse();

				response.setContentType("application/pdf");
				response.setHeader("Content-disposition", "inline; filename=\""
						+ arquivo + ".pdf\"");
				response.setContentLength(bytes.length);

				outputStream = response.getOutputStream();
				outputStream.write(bytes, 0, bytes.length);
			}
		} catch (IOException e) {
			throw new IOException(e);
		} catch (JRException e) {
			throw new IOException(e);
		} finally {
			try {
				outputStream.flush();
				outputStream.close();
			} catch (IOException e) {
				throw new IOException(e);
			}
		}
	}

}
