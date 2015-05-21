/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Projeto;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
public class ProjetoControler implements CRUD<Projeto, Integer> {
	
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Projeto save(Projeto t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Projeto t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Projeto t, Integer i) throws SQLException {
		boolean ok = false;
		Projeto projeto = manager.find(Projeto.class, (long) i);
		if (projeto != null) {
			ok = true;
			projeto.setDepartamento(t.getDepartamento());
			projeto.setDescProjeto(t.getDescProjeto());
			projeto.setLocal(t.getLocal());
			projeto.setNumProjeto(t.getNumProjeto());
			
			manager.getTransaction().begin();
			manager.persist(projeto);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Projeto getById(Integer i) throws SQLException {
		return manager.find(Projeto.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projeto> findAll() throws SQLException {
		return  manager.createQuery("from Projeto").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Projeto> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Projeto.class).getResultList();
	}

}
