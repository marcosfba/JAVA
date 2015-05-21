/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Empregado;
import br.edu.unitri.model.Local;
import br.edu.unitri.model.ProjEmp;
import br.edu.unitri.model.Projeto;
import br.edu.unitri.util.JpaUtil;

/**
 * @author Marcos
 *
 */
public class DepartamentoControler implements CRUD<Departamento, Integer> {
	
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Departamento save(Departamento t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}
	
	private boolean isInconsistencia(Departamento t) throws SQLException {
		boolean ok = false;
		ProjEmp projEmp = manager.getReference(ProjEmp.class,t.getIdDepartamento());
		Empregado empregado = manager.getReference(Empregado.class, t.getIdDepartamento());
		Projeto projeto = manager.getReference(Projeto.class, t.getIdDepartamento());
		Local local = manager.getReference(Local.class,t.getIdDepartamento());
		ok = (projEmp == null) && (empregado == null) && (projeto == null) && (local == null);
		return ok;
	}

	@Override
	public boolean delete(Departamento t) throws SQLException {
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
	public boolean update(Departamento t, Integer i) throws SQLException {
		boolean ok = false;
		Departamento departamento = manager.find(Departamento.class, (long) i);
		if (departamento != null) {
			ok = true;
			departamento.setDescLocal(t.getDescLocal());
			departamento.setNumDepartamento(t.getNumDepartamento());

			manager.getTransaction().begin();
			manager.persist(departamento);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Departamento getById(Integer i) throws SQLException {
		return manager.find(Departamento.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Departamento> findAll() throws SQLException {
		return  manager.createQuery("from Departamento").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Departamento> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Departamento.class).getResultList();
	}

}
