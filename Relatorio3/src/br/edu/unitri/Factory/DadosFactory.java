/**
 * 
 */
package br.edu.unitri.Factory;

import java.sql.SQLException;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.controler.ClienteControler;
import br.edu.unitri.controler.PedidoControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.dataSource.CategoriaJRDataSource;
import br.edu.unitri.dataSource.ClienteJRDataSource;
import br.edu.unitri.dataSource.ClientesDTOJRDataSource;
import br.edu.unitri.dataSource.PedidoDTOJRDataSource;
import br.edu.unitri.dataSource.ProdutoDTOJRDataSource;
import br.edu.unitri.dataSource.ProdutoJRDataSource;
import br.edu.unitri.model.ClientesDTO;
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

	public JRDataSource createDataSourceCategoria() {

		CategoriaControler categoriaCtr = new CategoriaControler();
		try {
			this.data = new CategoriaJRDataSource(categoriaCtr.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public JRDataSource createDataSourceProduto() {

		ProdutoControler prodCtr = new ProdutoControler();
		try {
			this.data = new ProdutoJRDataSource(prodCtr.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public JRDataSource createDataSourceCliente() {

		ClienteControler clienteCtr = new ClienteControler();
		try {
			this.data = new ClienteJRDataSource(clienteCtr.findAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public JRDataSource createDataSourcePedidoDTO() {

		PedidoControler pedidoCtr = new PedidoControler();
		try {
			this.data = new PedidoDTOJRDataSource(pedidoCtr.findAllPedidoDTO());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public JRDataSource createDataSourcePedidoClientesDTO(ClientesDTO clienteDTO) {

		PedidoControler pedidoCtr = new PedidoControler();
		try {
			this.data = new PedidoDTOJRDataSource(pedidoCtr.findAllPedidoDTO(clienteDTO));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public JRDataSource createDataSourceClientesDTO(ClientesDTO clienteDTO) {

		ClienteControler clienteCtr = new ClienteControler();
		try {
			this.data = new ClientesDTOJRDataSource(clienteCtr.findAllPedidoClienteDTO(clienteDTO));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public JRDataSource createDataSourceProdutoDTO() {

		ProdutoControler produtoCtr = new ProdutoControler();
		try {
			this.data = new ProdutoDTOJRDataSource(produtoCtr.findProdutoDTOAll());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}
}
