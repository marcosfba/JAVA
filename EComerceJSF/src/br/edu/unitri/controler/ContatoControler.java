/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Contato;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class ContatoControler implements CRUD<Contato,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Contato save(Contato t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Contato t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Contato t) throws SQLException {
		boolean ok = false;
		Contato contato = manager.find(Contato.class, t.getId());
		if (contato != null) {
			ok = true;
			contato.setEmail(t.getEmail());
			contato.setTipoContato(t.getTipoContato());

			manager.getTransaction().begin();
			manager.merge(contato);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Contato getById(Integer i) throws SQLException {
		return manager.find(Contato.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contato> findAll() throws SQLException {
		return  manager.createQuery("from Contato").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contato> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Contato.class).getResultList();
	}
	
	public boolean delete(Collection<Contato> listaContatos) throws SQLException {
		if (listaContatos.size() > 0) {
			for (Contato contato : listaContatos) {
				delete(contato);
			}
		}
		return true;
	}

}
