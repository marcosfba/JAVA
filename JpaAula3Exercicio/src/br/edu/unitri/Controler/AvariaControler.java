/**
 * 
 */
package br.edu.unitri.Controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Avaria;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class AvariaControler implements CRUD<Avaria, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Avaria save(Avaria t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Avaria t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Avaria t, Integer i) throws SQLException {
        boolean ok = false;		
		Avaria avaria = manager.find(Avaria.class,(long)i);
		if (avaria != null) {
			ok = true;
			avaria.setDescricao(t.getDescricao());
			avaria.setValor(t.getValor());
			avaria.setLocacao(t.getLocacao());
			        
			manager.getTransaction().begin();
			manager.persist(avaria);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Avaria getById(Integer i) throws SQLException {
		return manager.find(Avaria.class,(long)i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Avaria> findAll() throws SQLException {
		return  manager.createQuery("from Avaria").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Avaria> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Avaria.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
