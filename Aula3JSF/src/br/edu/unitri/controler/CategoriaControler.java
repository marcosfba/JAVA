/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class CategoriaControler implements CRUD<Categoria,Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Categoria save(Categoria t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Categoria t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Categoria t) throws SQLException {
		boolean ok = false;
		Categoria Categoria = manager.find(Categoria.class, t.getId());
		if (Categoria != null) {
			ok = true;
			Categoria.setDescricao(t.getDescricao());

			manager.getTransaction().begin();
			manager.persist(Categoria);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Categoria getById(Integer i) throws SQLException {
		return manager.find(Categoria.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> findAll() throws SQLException {
		return  manager.createQuery("from Categoria").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Categoria.class).getResultList();
	}
	

}
