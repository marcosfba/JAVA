package br.edu.unitri.bean;

import java.io.FileNotFoundException;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import br.edu.unitri.util.ReportUtil;

public class Report implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int relatorio;	
	
	public Report() {
		super();
	}

	public Report(int relatorio) {
		super();
		this.relatorio = relatorio;
	}

	public void getArquivoRelatorio(JRDataSource ds) throws JRException, FileNotFoundException {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			switch (this.relatorio) {
			case 0:
				//relatório de usuarios
				ReportUtil.geraArquivoRelatorio("relUsuarios",ds);
				break;
			case 1:
				//relatório de clientes
				ReportUtil.geraArquivoRelatorio("relClientes",ds);
				break;
			case 2:
				//relatório de fornecedores
				ReportUtil.geraArquivoRelatorio("relFornecedores",ds);
				break;
			case 3:
				//relatório de pedidos
				ReportUtil.geraArquivoRelatorio("relOperacoes",ds);
				break;
			case 4:
				//relatório de produtos por categoria
				ReportUtil.geraArquivoRelatorio("relProdutosCat",ds);
				break;
			case 5:
				//relatório de produtos por marca
				ReportUtil.geraArquivoRelatorio("relProdutosMar",ds);
				break;
			case 6:
				//relatório de produtos geral
				ReportUtil.geraArquivoRelatorio("relProdutos",ds);
				break;
			case 7:
				//relatório de pedido completo
				ReportUtil.geraArquivoRelatorio("relCompletoPed",ds);
				break;
			}
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(e.getMessage()));			
		}		
	}
	

	public int getRelatorio() {
		return relatorio;
	}

	public void setRelatorio(int relatorio) {
		this.relatorio = relatorio;
	}
}
