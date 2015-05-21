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
import br.edu.unitri.model.PedidoDTO;
import br.edu.unitri.util.JpaUtil;

/**
 * @author MARCOS FERNANDO
 *
 */
public class PedidoControler implements CRUD<Pedido, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Pedido save(Pedido t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}
	
	public boolean isInconsistencia(Pedido t) {
		return manager.getReference(ItensPedido.class, t.getCodPedido()) == null;
	}

	@Override
	public boolean delete(Pedido t) throws SQLException {
		if (isInconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else { 
			return false; }
	}

	@Override
	public boolean update(Pedido t, Integer i) throws SQLException {
		boolean ok = false;
		Pedido pedido = manager.find(Pedido.class, (long) i);
		if (pedido != null) {
			ok = true;
			pedido.setCliente(t.getCliente());
			pedido.setDtPedido(t.getDtPedido());

			manager.getTransaction().begin();
			manager.persist(pedido);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Pedido getById(Integer i) throws SQLException {
		return manager.find(Pedido.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> findAll() throws SQLException {
		return  manager.createQuery("from Pedido").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pedido> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Pedido.class).getResultList();
	}
	
	@SuppressWarnings("rawtypes")
	public List<PedidoDTO> findAllPedidoDTO() throws SQLException {

		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<PedidoDTO> query = cb.createQuery(PedidoDTO.class);
		Root<Pedido> root = query.from(Pedido.class);
		Join<Pedido,Cliente> join = root.join("cliente", JoinType.LEFT);
		query.multiselect(root.get("codPedido").alias("codPedido"), root.get("dtPedido").alias("dtPedido"),
				          join.get("nomeCliente").alias("nomeCliente"),
				          cb.size(root.<Collection>get("itens")).alias("qtdItens")
				);
		query.groupBy(root.get("codPedido"), root.get("cliente"),root.get("dtPedido"));
		query.orderBy(cb.asc(root.get("dtPedido")));
		List<PedidoDTO> listaPedidos = manager.createQuery(query).getResultList();
		return listaPedidos;
	}
	
	@SuppressWarnings("rawtypes")
	public List<PedidoDTO> findAllPedidoDTO(ClientesDTO clienteDTO) throws SQLException {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<PedidoDTO> query = cb.createQuery(PedidoDTO.class);
		Root<Pedido> root = query.from(Pedido.class);
		Join<Pedido,Cliente> join = root.join("cliente", JoinType.LEFT);
		Join<Pedido,ItensPedido> joinPed = root.join("itens", JoinType.LEFT);
		Expression<Double> param  = joinPed.get("VlrItem");
		Expression<Double> param2 = joinPed.get("QtdItem");		
		query.multiselect(root.get("codPedido").alias("codPedido"), root.get("dtPedido").alias("dtPedido"),
				          join.get("nomeCliente").alias("nomeCliente"),join.get("codCliente"),
				          cb.size(root.<Collection>get("itens")).alias("qtdItens"),
				          cb.sum(cb.prod(param,param2)).alias("vlrPedido")				          
				);
		query.where(cb.equal(join.get("codCliente"),clienteDTO.getCodCliente()));
		query.groupBy(root.get("codPedido"), root.get("cliente"),root.get("dtPedido"));
		query.orderBy(cb.asc(root.get("dtPedido")));
		List<PedidoDTO> listaPedidos = manager.createQuery(query).getResultList();
		return listaPedidos;
	}
	
}
