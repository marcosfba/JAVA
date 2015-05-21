/**
 * 
 */
package br.edu.unitri.Controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Multa;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class MultaControler implements CRUD<Multa, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Multa save(Multa t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Multa t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Multa t, Integer i) throws SQLException {
        boolean ok = false;		
		Multa multa = manager.find(Multa.class,(long)i);
		if (multa != null) {
			ok = true;
			multa.setDescricao(t.getDescricao());
			multa.setLocacao(t.getLocacao());
			multa.setValor(t.getValor());
			        
			manager.getTransaction().begin();
			manager.persist(multa);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Multa getById(Integer i) throws SQLException {
		return manager.find(Multa.class,(long)i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Multa> findAll() throws SQLException {
		return  manager.createQuery("from Multa").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Multa> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Multa.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
