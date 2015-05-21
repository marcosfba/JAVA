/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.PessoaFisica;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class PessoaFisicaControler implements CRUD<PessoaFisica, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public PessoaFisica save(PessoaFisica t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(PessoaFisica t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean inconsistencia(PessoaFisica t) {
		Endereco endereco = manager.getReference(Endereco.class, t.getId());
		Contato contato = manager.getReference(Contato.class, t.getId());
		return  ((endereco == null) && (contato == null));
	}

	@Override
	public boolean update(PessoaFisica t, Integer i) throws SQLException {
		boolean ok = false;
		PessoaFisica pessoaFisica = manager.find(PessoaFisica.class, (long) i);
		if (pessoaFisica != null) {
			ok = true;
			pessoaFisica.setDtNascimento(t.getDtNascimento());
			pessoaFisica.setNome(t.getNome());
			pessoaFisica.setEmail(t.getEmail());
			pessoaFisica.setCpf(t.getCpf());
			pessoaFisica.setIdentidade(t.getIdentidade());
			pessoaFisica.setEnderecos(t.getEnderecos());
			pessoaFisica.setContatos(t.getContatos());
			
			manager.getTransaction().begin();
			manager.persist(pessoaFisica);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public PessoaFisica getById(Integer i) throws SQLException {
		return manager.find(PessoaFisica.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaFisica> findAll() throws SQLException {
		return  manager.createQuery("from PessoaFisica").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaFisica> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,PessoaFisica.class).getResultList();
	}

}
