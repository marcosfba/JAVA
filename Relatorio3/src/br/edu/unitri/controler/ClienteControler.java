/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.ClientesDTO;
import br.edu.unitri.model.ItensPedido;
import br.edu.unitri.model.Pedido;
import br.edu.unitri.util.JpaUtil;

/**
 * @author MARCOS FERNANDO
 *
 */
public class ClienteControler implements CRUD<Cliente, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Cliente save(Cliente t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}
	
	public boolean isInconsistencia(Cliente t) {
		return manager.getReference(Pedido.class, t.getCodCliente()) == null;
	}

	@Override
	public boolean delete(Cliente t) throws SQLException {
		if (isInconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else { 
			return false; }
	}

	@Override
	public boolean update(Cliente t, Integer i) throws SQLException {
		boolean ok = false;
		Cliente cliente = manager.find(Cliente.class, (long) i);
		if (cliente != null) {
			ok = true;
			cliente.setDescCargo(t.getDescCargo());
			cliente.setDescCep(t.getDescCep());
			cliente.setDescCidade(t.getDescCidade());
			cliente.setDescEndereco(t.getDescEndereco());
			cliente.setDescFax(t.getDescFax());
			cliente.setDescPais(t.getDescPais());
			cliente.setDescTelefone(t.getDescTelefone());
			cliente.setNomeCliente(t.getNomeCliente());

			manager.getTransaction().begin();
			manager.persist(cliente);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Cliente getById(Integer i) throws SQLException {
		return manager.find(Cliente.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() throws SQLException {
		return  manager.createQuery("from Cliente").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Cliente.class).getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	public List<ClientesDTO> findAllClientesDTO() throws SQLException {

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ClientesDTO> query = cb.createQuery(ClientesDTO.class);
		Root<Cliente> root = query.from(Cliente.class);
		query.multiselect(root.get("codCliente").alias("codCliente"), 
						  root.get("nomeCliente").alias("nomeCliente"),
				          cb.size(root.<Collection>get("pedidos")).alias("qtdPedidos")
				);
		query.groupBy(root.get("codCliente"), root.get("nomeCliente"));
		query.orderBy(cb.asc(root.get("codCliente")));
		List<ClientesDTO> listaClientes = manager.createQuery(query).getResultList();
		return listaClientes;
	}
	
	@SuppressWarnings("rawtypes")
	public List<ClientesDTO> findAllPedidoClienteDTO(ClientesDTO clienteDTO) throws SQLException {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ClientesDTO> query = cb.createQuery(ClientesDTO.class);
		
		Root<Pedido> root = query.from(Pedido.class);
		Join<Pedido,Cliente> join = root.join("cliente", JoinType.LEFT);
		Join<Pedido,ItensPedido> joinPed = root.join("itens", JoinType.LEFT);
		
		Expression<Double> param  = joinPed.get("VlrItem");
		Expression<Double> param2 = joinPed.get("QtdItem");
		Expression<Double> param3 = joinPed.get("VlrDesconto");
		Expression<Integer> param4 = cb.function("year", Integer.class, root.get("dtPedido"));
		
		query.multiselect(join.get("codCliente"), join.get("nomeCliente"),
				          cb.size(root.<Collection>get("itens")).alias("qtdItens"),
				          param4, 
				          cb.sum(cb.prod(cb.diff(param,param3),param2)).alias("vlrPedido")				          
				);
		query.where(cb.equal(join.get("codCliente"),clienteDTO.getCodCliente()));
		query.groupBy(root.get("codPedido"), root.get("cliente"),root.get("dtPedido"));
		query.orderBy(cb.asc(root.get("dtPedido")));		
		
		List<ClientesDTO> listaPedidos = manager.createQuery(query).getResultList();
		return listaPedidos;
	}
	

}
