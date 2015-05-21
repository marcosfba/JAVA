/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Dependente;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
public class DependenteControler implements CRUD<Dependente, Integer> {
	
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Dependente save(Dependente t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Dependente t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Dependente t, Integer i) throws SQLException {
		boolean ok = false;
		Dependente dependente = manager.find(Dependente.class, (long) i);
		if (dependente != null) {
			ok = true;
			dependente.setDtNascimento(t.getDtNascimento());
			dependente.setNome(t.getNome());
			dependente.setSexo(t.getSexo());
			dependente.setTipoDependente(t.getTipoDependente());
			dependente.setEmpregado(t.getEmpregado());

			manager.getTransaction().begin();
			manager.persist(dependente);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Dependente getById(Integer i) throws SQLException {
		return manager.find(Dependente.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dependente> findAll() throws SQLException {
		return  manager.createQuery("from Dependente").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Dependente> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Dependente.class).getResultList();
	}

}
