/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

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

	public Long getMaxId() {
		   TypedQuery<Long> q = manager.createQuery("select max(p.id) as valor from PessoaFisica p", Long.class);
		   Long id;
		   if (q.getSingleResult() != null) {
			    id = q.getSingleResult();
		   } else { id = 0L; }
		   return id + 1; 
	}

	@Override
	public boolean delete(PessoaFisica t) throws SQLException {
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
	public boolean update(PessoaFisica t, Integer i) throws SQLException {
		boolean ok = false;
		PessoaFisica pessoafisica = manager.find(PessoaFisica.class, (long) i);
		if (pessoafisica != null) {
			ok = true;
			pessoafisica.setDtNascimento(t.getDtNascimento());
			pessoafisica.setNome(t.getNome());
			pessoafisica.setEmail(t.getEmail());
			pessoafisica.setCpf(t.getCpf());
			pessoafisica.setIdentidade(t.getIdentidade());

			manager.getTransaction().begin();
			manager.persist(pessoafisica);
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
