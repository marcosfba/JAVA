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
import br.edu.unitri.model.Telefone;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class TelefoneControler implements CRUD<Telefone,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Telefone save(Telefone t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Telefone t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Telefone t) throws SQLException {
		boolean ok = false;
		Telefone telefone = manager.find(Telefone.class, t.getId());
		if (telefone != null) {
			ok = true;
			telefone.setNumeroTelefone(t.getNumeroTelefone());
			telefone.setTipoTelefone(t.getTipoTelefone());

			manager.getTransaction().begin();
			manager.merge(telefone);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Telefone getById(Integer i) throws SQLException {
		return manager.find(Telefone.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Telefone> findAll() throws SQLException {
		return  manager.createQuery("from Telefone").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Telefone> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Telefone.class).getResultList();
	}
	
	public boolean delete(Collection<Telefone> listaTelefones) throws SQLException {
		if (listaTelefones.size() > 0) {
			for (Telefone telefone : listaTelefones) {
				delete(telefone);
			}
		}
		return true;
	}
	

}
