package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Estado;
import br.edu.unitri.model.Governador;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class GovernadorControler implements CRUD<Governador,Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Governador save(Governador t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Governador t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	public boolean inconsistencia(Governador t) {
		Estado estado = manager.getReference(Estado.class, t.getId());
		return estado == null;
	}

	@Override
	public boolean delete(String qry) throws SQLException {
		manager.getTransaction().begin();
		int i = manager.createQuery(qry).executeUpdate();
		manager.getTransaction().commit();
		return i == 1;
	}

	@Override
	public boolean update(Governador t, Integer i) throws SQLException {
		boolean ok = false;
		Governador governador = manager.find(Governador.class, (long) i);
		if (governador != null) {
			ok = true;
			governador.setNome(t.getNome());
			governador.setUf(t.getUf());

			manager.getTransaction().begin();
			manager.persist(governador);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Governador getById(Integer i) throws SQLException {
		return manager.find(Governador.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Governador> findAll() throws SQLException {
		return  manager.createQuery("from Governador").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Governador> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Governador.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	

}
