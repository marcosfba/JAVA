/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
public class ProjEmpControler implements CRUD<ProjEmp, Integer> {
	
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public ProjEmp save(ProjEmp t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(ProjEmp t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(ProjEmp t, Integer i) throws SQLException {
		boolean ok = false;
		ProjEmp projEmp = manager.find(ProjEmp.class, (long) i);
		if (projEmp != null) {
			ok = true;
			projEmp.setEmpregado(t.getEmpregado());
			projEmp.setProjeto(t.getProjeto());
			projEmp.setQuantHoras(t.getQuantHoras());
			
			manager.getTransaction().begin();
			manager.persist(projEmp);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public ProjEmp getById(Integer i) throws SQLException {
		return manager.find(ProjEmp.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjEmp> findAll() throws SQLException {
		return  manager.createQuery("from ProjEmp").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProjEmp> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,ProjEmp.class).getResultList();
	}

}
