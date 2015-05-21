/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.ItensPedido;
import br.edu.unitri.util.JpaUtil;

/**
 * @author MARCOS FERNANDO
 *
 */
public class ItensPedidoControler implements CRUD<ItensPedido, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public ItensPedido save(ItensPedido t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}
	
	@Override
	public boolean delete(ItensPedido t) throws SQLException {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
	}

	@Override
	public boolean update(ItensPedido t, Integer i) throws SQLException {
		boolean ok = false;
		ItensPedido item = manager.find(ItensPedido.class, (long) i);
		if (item != null) {
			ok = true;
			item.setPedido(t.getPedido());
			item.setProduto(t.getProduto());
			item.setQtdItem(t.getQtdItem());
			item.setVlrDesconto(t.getVlrDesconto());
			item.setVlrItem(t.getVlrItem());

			manager.getTransaction().begin();
			manager.persist(item);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public ItensPedido getById(Integer i) throws SQLException {
		return manager.find(ItensPedido.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItensPedido> findAll() throws SQLException {
		return  manager.createQuery("from ItensPedido").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItensPedido> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,ItensPedido.class).getResultList();
	}

}
