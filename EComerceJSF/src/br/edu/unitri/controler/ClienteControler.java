/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.Usuario;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class ClienteControler implements CRUD<Cliente,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Cliente save(Cliente t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Cliente t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Cliente t) throws SQLException {
		boolean ok = false;
		Cliente cliente = manager.find(Cliente.class, t.getId());
		if (cliente != null) {
			ok = true;
			//dados referente a pessoa
			cliente.setNome(t.getNome());
			cliente.setTipoPessoa(t.getTipoPessoa());
			cliente.setObservacao(t.getObservacao());
			cliente.setUsuario(t.getUsuario());
			//dados referente somente ao cliente			
			cliente.setDtNascimento(t.getDtNascimento());
			cliente.setCpf(t.getCpf());
			cliente.setIdentidade(t.getIdentidade());
			cliente.setTitEleitor(t.getTitEleitor());
			cliente.setCnh(t.getCnh());

			manager.getTransaction().begin();
			manager.merge(cliente);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Cliente getById(Integer i) throws SQLException {
		return manager.find(Cliente.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll() throws SQLException {
		return  manager.createQuery("from Cliente").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Cliente.class).getResultList();
	}
	
	public Cliente getbyUser(Usuario usuario) throws SQLException {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> query = cb.createQuery(Cliente.class);
		Root<Cliente> root = query.from(Cliente.class);
		Predicate where1 = cb.equal(root.get("usuario"),usuario);
		query.where(where1);
		return JpaUtil.getManager().createQuery(query).getSingleResult();
	}
	

}
