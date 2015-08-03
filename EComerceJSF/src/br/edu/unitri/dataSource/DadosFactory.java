/**
 * 
 */
package br.edu.unitri.dataSource;

import java.util.List;

import br.edu.unitri.dto.PedidoComplDTO;
import br.edu.unitri.dto.PedidoDTO;
import br.edu.unitri.dto.ProdutoCategoriaDTO;
import br.edu.unitri.dto.ProdutoGeralDTO;
import br.edu.unitri.dto.ProdutoMarcaDTO;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.Fornecedor;
import br.edu.unitri.model.Usuario;
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
	
	public JRDataSource createDataSourceUsuario(List<Usuario> listaUsuarios){
		try {
			this.data = new UsuarioDs(listaUsuarios);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public JRDataSource createDataSourceCliente(List<Cliente> listaClientes){
		try {
			this.data = new ClienteDs(listaClientes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public JRDataSource createDataSourceFornecedor(List<Fornecedor> listaFornecedores){
		try {
			this.data = new FornecedorDs(listaFornecedores);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public JRDataSource createDataSourceOperacoes(List<PedidoDTO> listaOperacoes){
		try {
			this.data = new OperacaoDs(listaOperacoes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public JRDataSource createDataSourceProdutoCategoria(List<ProdutoCategoriaDTO> listacaCategoriaDTOs){
		try {
			this.data = new ProdutoCategoriaDS(listacaCategoriaDTOs) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}
	
	public JRDataSource createDataSourceProdutoMarca(List<ProdutoMarcaDTO> listaMarcaDTOs){
		try {
			this.data = new ProdutoMarcaDS(listaMarcaDTOs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public JRDataSource createDataSourceProdutoGeral(List<ProdutoGeralDTO> listaProdutoGeralDTOs){
		try {
			this.data = new ProdutoGeralDS(listaProdutoGeralDTOs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}

	public JRDataSource createDataSourcePedidoCompleto(List<PedidoComplDTO> listaPedidoCompllDTOs){
		try {
			this.data = new PedidoCompletoDs(listaPedidoCompllDTOs);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this.data;
	}
}
