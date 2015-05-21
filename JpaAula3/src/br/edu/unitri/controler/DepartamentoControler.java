/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Departamento;
import br.edu.unitri.model.Funcionario;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class DepartamentoControler implements CRUD<Departamento,Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Departamento save(Departamento t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Departamento t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	public boolean inconsistencia(Departamento t) {
		Funcionario funcionario = manager.getReference(Funcionario.class, t.getId());
		return funcionario == null;
	}

	@Override
	public boolean delete(String qry) throws SQLException {
		manager.getTransaction().begin();
		int i = manager.createQuery(qry).executeUpdate();
		manager.getTransaction().commit();
		return i == 1;
	}

	@Override
	public boolean update(Departamento t, Integer i) throws SQLException {
		boolean ok = false;
		Departamento departamento = manager.find(Departamento.class, (long) i);
		if (departamento != null) {
			ok = true;
			departamento.setDescricao(t.getDescricao());
			departamento.setFuncionarios(t.getFuncionarios());
			departamento.setNome(t.getNome());

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

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}	

}
