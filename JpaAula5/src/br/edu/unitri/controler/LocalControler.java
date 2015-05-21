/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Local;
import br.edu.unitri.model.Projeto;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
public class LocalControler implements CRUD<Local, Integer> {
	
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Local save(Local t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	private boolean isInconsistencia(Local t) throws SQLException {
		boolean ok = false;
		Projeto projeto = manager.getReference(Projeto.class, t.getIdLocal());
		ok = (projeto == null);
		return ok;
	}

	@Override
	public boolean delete(Local t) throws SQLException {
		if (isInconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean update(Local t, Integer i) throws SQLException {
		boolean ok = false;
		Local local = manager.find(Local.class, (long) i);
		if (local != null) {
			ok = true;
			local.setDepartamentos(t.getDepartamentos());
			local.setDescLocal(t.getDescLocal());
			local.setNomLocal(t.getNomLocal());

			manager.getTransaction().begin();
			manager.persist(local);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Local getById(Integer i) throws SQLException {
		return manager.find(Local.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Local> findAll() throws SQLException {
		return  manager.createQuery("from Local").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Local> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Local.class).getResultList();
	}

}
