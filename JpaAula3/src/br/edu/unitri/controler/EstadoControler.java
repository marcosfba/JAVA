package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Estado;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class EstadoControler implements CRUD<Estado,Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Estado save(Estado t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Estado t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean delete(String qry) throws SQLException {
		manager.getTransaction().begin();
		int i = manager.createQuery(qry).executeUpdate();
		manager.getTransaction().commit();
		return i == 1;
	}

	@Override
	public boolean update(Estado t, Integer i) throws SQLException {
		boolean ok = false;
		Estado estado = manager.find(Estado.class, (long) i);
		if (estado != null) {
			ok = true;
			estado.setGovernador(t.getGovernador());
			estado.setNomeEstado(t.getNomeEstado());
			estado.setSigla(t.getSigla());

			manager.getTransaction().begin();
			manager.persist(estado);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Estado getById(Integer i) throws SQLException {
		return manager.find(Estado.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Estado> findAll() throws SQLException {
		return  manager.createQuery("from Estado").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Estado> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Estado.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	

}
