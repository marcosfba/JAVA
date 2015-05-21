/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.Gerencia;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
public class GerenciaControler implements CRUD<Gerencia, Integer> {
	
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Gerencia save(Gerencia t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	private boolean isInconsistencia(Gerencia t) throws SQLException {
		boolean ok = false;
		Empregado empregado = manager.getReference(Empregado.class, t.getEmpregado().getGerente());
		ok = (empregado == null) ;
		return ok;
	}

	@Override
	public boolean delete(Gerencia t) throws SQLException {
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
	public boolean update(Gerencia t, Integer i) throws SQLException {
		boolean ok = false;
		Gerencia gerente = manager.find(Gerencia.class, (long) i);
		if (gerente != null) {
			ok = true;
			gerente.setDepartamento(t.getDepartamento());
			gerente.setDtEmp(t.getDtEmp());
			gerente.setEmpregado(t.getEmpregado());

			manager.getTransaction().begin();
			manager.persist(gerente);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Gerencia getById(Integer i) throws SQLException {
		return manager.find(Gerencia.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gerencia> findAll() throws SQLException {
		return  manager.createQuery("from Gerencia").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Gerencia> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Gerencia.class).getResultList();
	}

}
