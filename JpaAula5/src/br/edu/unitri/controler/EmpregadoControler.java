/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Dependente;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.Gerencia;
import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
public class EmpregadoControler implements CRUD<Empregado, Integer> {
	
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Empregado save(Empregado t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}
	
	private boolean isInconsistencia(Empregado t) throws SQLException {
		boolean ok = false;
		Gerencia gerente = manager.getReference(Gerencia.class,t.getCodEmpregado());
		ProjEmp projEmp = manager.getReference(ProjEmp.class,t.getCodEmpregado());
		Dependente dependente = manager.getReference(Dependente.class, t.getCodEmpregado());
		ok = (gerente == null) && (projEmp == null) && (dependente == null);
		return ok;
	}

	@Override
	public boolean delete(Empregado t) throws SQLException {
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
	public boolean update(Empregado t, Integer i) throws SQLException {
		boolean ok = false;
		Empregado empregado = manager.find(Empregado.class, (long) i);
		if (empregado != null) {
			ok = true;
			empregado.setDepartamento(t.getDepartamento());
			empregado.setDtNasc(t.getDtNasc());
			empregado.setSexo(t.getSexo());
			empregado.setEndEmpregado(t.getEndEmpregado());
			empregado.setNomeEmpregado(t.getNomeEmpregado());
			empregado.setGerente(t.getGerente());

			manager.getTransaction().begin();
			manager.persist(empregado);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Empregado getById(Integer i) throws SQLException {
		return manager.find(Empregado.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empregado> findAll() throws SQLException {
		return  manager.createQuery("from Empregado").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Empregado> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Empregado.class).getResultList();
	}

}
