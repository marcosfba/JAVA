/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class PessoaControler implements CRUD<Pessoa,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Pessoa save(Pessoa t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Pessoa t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Pessoa t) throws SQLException {
		boolean ok = false;
		Pessoa pessoa = manager.find(Pessoa.class, t.getId());
		if (pessoa != null) {
			ok = true;
			//dados referente a pessoa
			pessoa.setNome(t.getNome());
			pessoa.setTipoPessoa(t.getTipoPessoa());
			pessoa.setListaEnderecos(t.getListaEnderecos());
			pessoa.setListaContatos(t.getListaContatos());
			pessoa.setListaTelefones(t.getListaTelefones());
			pessoa.setUsuario(t.getUsuario());
			pessoa.setObservacao(t.getObservacao());

			manager.getTransaction().begin();
			manager.merge(pessoa);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Pessoa getById(Integer i) throws SQLException {
		return manager.find(Pessoa.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> findAll() throws SQLException {
		return  manager.createQuery("from Pessoa").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pessoa> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Pessoa.class).getResultList();
	}
	

}
