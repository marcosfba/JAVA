/**
 * 
 */
package br.edu.unitri.Factory;

import java.sql.SQLException;

import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.dataSource.ClienteJRDataSource;
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

	public JRDataSource createDataSourceCliente(){
		
		ClienteControler clienteCtr = new ClienteControler();
		
		try {
			this.data = new ClienteJRDataSource(clienteCtr.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.data;
	}

	public JRDataSource createDataSourceProduto(){
		
		ProdutoControler prodCtr = new ProdutoControler();
		
		try {
			this.data = new ProdutoJRDataSource(prodCtr.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this.data;
	}
}
