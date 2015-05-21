/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Autor;
import br.edu.unitri.model.AutorLivroDTO;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class AutorControler implements CRUD<Autor, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Autor save(Autor t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Autor t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean inconsistencia(Autor t) throws SQLException {
		return  manager.createNativeQuery("select a.* from Autor a, Livro l, "
				+ "Liv_Aut la where a.id = la.Aut_ID and "
				+ "l.id = la.Liv_ID and A.id ='" + t.getId().toString() + "'", Autor.class).getResultList().size() == 0;
	}

	@Override
	public boolean delete(String qry) throws SQLException {
		manager.getTransaction().begin();
		int i = manager.createQuery(qry).executeUpdate();
		manager.getTransaction().commit();
		return i == 1;
	}

	@Override
	public boolean update(Autor t, Integer i) throws SQLException {
		boolean ok = false;
		Autor autor = manager.find(Autor.class, (long) i);
		if (autor != null) {
			ok = true;
			autor.setNome(t.getNome());;
			autor.setEmail(t.getEmail());
			autor.setLivros(t.getLivros());;

			manager.getTransaction().begin();
			manager.persist(autor);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Autor getById(Integer i) throws SQLException {
		return manager.find(Autor.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> findAll() throws SQLException {
		return  manager.createQuery("from Autor").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Autor> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Autor.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}
	
	@SuppressWarnings("unchecked")
	public List<AutorLivroDTO> findLivrosAutores(String param, boolean okAutor, boolean okLivro) throws SQLException{
		List<AutorLivroDTO> livroAut = new ArrayList<AutorLivroDTO>();
		
		String sql = "select l.nome as nomeLivro, a.nome as nomeAutor"
				+ " from livro l, autor a , liv_aut la"
				+ " where l.id = la.Liv_ID and a.id = la.Aut_ID";
		if (okAutor) {
			sql = sql.concat(" and a.nome like '%"+ param + "%'");
		}
		if (okLivro) {
			sql = sql.concat(" and l.nome like '%"+ param + "%'");
		}
		
		List<Object[]> lista =  manager.createNativeQuery(sql).getResultList();
		for (Object[] objects : lista) {			
			livroAut.add( new AutorLivroDTO((String) objects[1], (String) objects[0]));
		}
		return livroAut;
	}

}
