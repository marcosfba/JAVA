/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Marca;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class MarcaControler implements CRUD<Marca,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Marca save(Marca t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Marca t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Marca t) throws SQLException {
		boolean ok = false;
		Marca marca = manager.find(Marca.class, t.getId());
		if (marca != null) {
			ok = true;
			marca.setDescricao(t.getDescricao());
			marca.setComplemento(t.getComplemento());

			manager.getTransaction().begin();
			manager.merge(marca);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Marca getById(Integer i) throws SQLException {
		return manager.find(Marca.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> findAll() throws SQLException {
		return  manager.createQuery("from Marca").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Marca> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Marca.class).getResultList();
	}
	

}
