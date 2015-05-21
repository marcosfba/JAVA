/**
 * 
 */
package br.edu.unitri.Controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Funcionario;
import br.edu.unitri.model.Locacao;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class FuncionarioControler implements CRUD<Funcionario, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Funcionario save(Funcionario t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	public boolean inconsistencia(Funcionario t) {
		Locacao locacao = manager.getReference(Locacao.class, t.getIdFuncionario());
		return locacao == null;
	}

	@Override
	public boolean delete(Funcionario t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else { 
			return false; 
		}
	}

	@Override
	public boolean update(Funcionario t, Integer i) throws SQLException {
        boolean ok = false;		
		Funcionario funcionario = manager.find(Funcionario.class,(long)i);
		if (funcionario != null) {
			ok = true;
			funcionario.setEndereco(t.getEndereco());
			funcionario.setMatricula(t.getMatricula());
			funcionario.setNome(t.getNome());
			        
			manager.getTransaction().begin();
			manager.persist(funcionario);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Funcionario getById(Integer i) throws SQLException {
		return manager.find(Funcionario.class,(long)i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> findAll() throws SQLException {
		return  manager.createQuery("from Funcionario").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Funcionario> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Funcionario.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
}
