/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Produto;
import br.edu.unitri.util.JpaUtil;

/**
 * @author MARCOS FERNANDO
 *
 */
public class CategoriaControler implements CRUD<Categoria, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Categoria save(Categoria t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}
	
	public boolean isInconsistencia(Categoria t) {
		return manager.getReference(Produto.class, t.getCodCategoria()) == null;
	}

	@Override
	public boolean delete(Categoria t) throws SQLException {
		if (isInconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else { 
			return false; }
	}

	@Override
	public boolean update(Categoria t, Integer i) throws SQLException {
		boolean ok = false;
		Categoria categoria = manager.find(Categoria.class, (long) i);
		if (categoria != null) {
			ok = true;
			categoria.setDescCategoria(t.getDescCategoria());
			categoria.setNomeCategoria(t.getNomeCategoria());

			manager.getTransaction().begin();
			manager.persist(categoria);
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
