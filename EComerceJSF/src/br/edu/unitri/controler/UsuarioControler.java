/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Usuario;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class UsuarioControler implements CRUD<Usuario,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Usuario save(Usuario t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Usuario t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Usuario t) throws SQLException {
		boolean ok = false;
		Usuario usuario = manager.find(Usuario.class, t.getId());
		if (usuario != null) {
			ok = true;
			usuario.setPassword(t.getPassword());
			usuario.setTipoUsuario(t.getTipoUsuario());
			usuario.setUserName(t.getUserName());
			usuario.setEmail(t.getEmail());

			manager.getTransaction().begin();
			manager.merge(usuario);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Usuario getById(Integer i) throws SQLException {
		return manager.find(Usuario.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAll() throws SQLException {
		return  manager.createQuery("from Usuario").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Usuario.class).getResultList();
	}
	
	public Usuario getUsuario(String nomeUsuario, String senha) {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		Predicate where1 = null, where2 = null;
		where1 = cb.equal(root.get("userName"), nomeUsuario);
		where2 = cb.equal(root.get("password"), senha);
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (where1 != null) {
			predicados.add(where1);
		}
		if (where2 != null) {
			predicados.add(where2);
		}
		query.where(cb.and(predicados.toArray(new Predicate[] {})));
		
		List<Usuario> usuarios = JpaUtil.getManager().createQuery(query).getResultList();
		Usuario user = null;
		if (usuarios.size() > 0) 
		{ user = usuarios.get(0); }
		System.out.println(user);
		return user;
	}

	public Usuario getUsuario(String EmailUsuario) {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Usuario> query = cb.createQuery(Usuario.class);
		Root<Usuario> root = query.from(Usuario.class);
		Predicate where1 = cb.equal(root.get("email"), EmailUsuario);
		query.where(where1);
		return JpaUtil.getManager().createQuery(query).getSingleResult();
	}


}
