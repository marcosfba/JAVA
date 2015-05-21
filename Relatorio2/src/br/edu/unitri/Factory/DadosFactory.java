/**
 * 
 */
package br.edu.unitri.Factory;

import java.sql.SQLException;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.dataSource.CategoriaJRDataSource;
import br.edu.unitri.dataSource.ProdutoJRDataSource;
import net.sf.jasperreports.engine.JRDataSource;

/**
 * @author Marcos
 *
 */
public class DadosFactory {
	
	private JRDataSource data;
	
	public JRDataSource getData() {
		return data;
	}

	public void setData(JRDataSource data) {
		this.data = data;
	}

	public JRDataSource createDataSourceCategoria(){
		
		CategoriaControler categoriaCtr = new CategoriaControler();
		
		try {
			this.data = new CategoriaJRDataSource(categoriaCtr.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}

	public JRDataSource createDataSourceProduto(){
		
		ProdutoControler prodCtr = new ProdutoControler();
		
		try {
			this.data = new ProdutoJRDataSource(prodCtr.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}
