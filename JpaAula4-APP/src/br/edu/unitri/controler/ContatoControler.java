/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class ContatoControler implements CRUD<Contato,Integer> {

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
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	public boolean inconsistencia(Contato t) {
		Pessoa pessoa = manager.getReference(Pessoa.class, t.getId());
		return pessoa == null;
	}

	@Override
	public boolean update(Contato t, Integer i) throws SQLException {
		boolean ok = false;
		Contato contato = manager.find(Contato.class, (long) i);
		if (contato != null) {
			ok = true;
			contato.setComplemento(t.getComplemento());
			contato.setTipoContato(t.getTipoContato());
			contato.setDescricao(t.getDescricao());

			manager.getTransaction().begin();
			manager.persist(contato);
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

}
