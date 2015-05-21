/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.PessoaJuridica;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class PessoaJuridicaControler implements CRUD<PessoaJuridica, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public PessoaJuridica save(PessoaJuridica t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(PessoaJuridica t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean delete(String qry) throws SQLException {
		manager.getTransaction().begin();
		int i = manager.createQuery(qry).executeUpdate();
		manager.getTransaction().commit();
		return i == 1;
	}

	@Override
	public boolean update(PessoaJuridica t, Integer i) throws SQLException {
		boolean ok = false;
		PessoaJuridica pessoaJuridica = manager.find(PessoaJuridica.class, (long) i);
		if (pessoaJuridica != null) {
			ok = true;
			pessoaJuridica.setDtNascimento(t.getDtNascimento());
			pessoaJuridica.setNome(t.getNome());
			pessoaJuridica.setEmail(t.getEmail());
			pessoaJuridica.setCnpj(t.getCnpj());
			pessoaJuridica.setInscEstadual(t.getInscEstadual());

			manager.getTransaction().begin();
			manager.persist(pessoaJuridica);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public PessoaJuridica getById(Integer i) throws SQLException {
		return manager.find(PessoaJuridica.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaJuridica> findAll() throws SQLException {
		return  manager.createQuery("from PessoaJuridica").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PessoaJuridica> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,PessoaJuridica.class).getResultList();
	}
}
