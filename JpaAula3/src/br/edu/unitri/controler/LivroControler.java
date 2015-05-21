/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Livro;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class LivroControler implements CRUD<Livro, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Livro save(Livro t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Livro t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	public boolean inconsistencia(Livro t) throws SQLException {
		return  manager.createNativeQuery("select l.* from Livro l, Autor a, "
				+ "Liv_Aut la where a.id = la.Aut_ID and "
				+ "l.id = la.Liv_ID and l.id ='" + t.getId().toString() + "'", Livro.class).getResultList().size() == 0;
	}

	@Override
	public boolean delete(String qry) throws SQLException {
		manager.getTransaction().begin();
		int i = manager.createQuery(qry).executeUpdate();
		manager.getTransaction().commit();
		return i == 1;
	}

	@Override
	public boolean update(Livro t, Integer i) throws SQLException {
		boolean ok = false;
		Livro livro = manager.find(Livro.class, (long) i);
		if (livro != null) {
			ok = true;
			livro.setAutores(t.getAutores());
			livro.setEditora(t.getEditora());
			livro.setNome(t.getNome());
			
			manager.getTransaction().begin();
			manager.persist(livro);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Livro getById(Integer i) throws SQLException {
		return manager.find(Livro.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Livro> findAll() throws SQLException {
		return  manager.createQuery("from Livro").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Livro> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Livro.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
