/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class PessoaControler implements CRUD<Pessoa, Integer> {

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
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	public boolean inconsistencia(Pessoa t) {
		Endereco endereco = manager.getReference(Endereco.class, t.getId());
		Contato contato = manager.getReference(Contato.class, t.getId());
		return  ((endereco == null) && (contato == null));
	}

	@Override
	public boolean update(Pessoa t, Integer i) throws SQLException {
		boolean ok = false;
		Pessoa pessoa = manager.find(Pessoa.class, (long) i);
		if (pessoa != null) {
			ok = true;
			pessoa.setDtNascimento(t.getDtNascimento());
			pessoa.setNome(t.getNome());
			pessoa.setEmail(t.getEmail());
			pessoa.setEnderecos(t.getEnderecos());
			pessoa.setContatos(t.getContatos());
			
			manager.getTransaction().begin();
			manager.persist(pessoa);
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
