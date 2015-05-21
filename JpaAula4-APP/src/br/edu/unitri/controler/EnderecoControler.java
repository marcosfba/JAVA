/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Pessoa;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class EnderecoControler implements CRUD<Endereco,Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Endereco save(Endereco t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Endereco t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	public boolean inconsistencia(Endereco t) {
		Pessoa pessoa = manager.getReference(Pessoa.class, t.getId());
		return pessoa == null;
	}

	@Override
	public boolean update(Endereco t, Integer i) throws SQLException {
		boolean ok = false;
		Endereco endereco = manager.find(Endereco.class, (long) i);
		if (endereco != null) {
			ok = true;
			endereco.setCep(t.getCep());
			endereco.setCidade(t.getCidade());
			endereco.setComplemento(t.getComplemento());
			endereco.setEstado(t.getEstado());
			endereco.setLogradouro(t.getLogradouro());
			endereco.setNumero(t.getNumero());
			endereco.setPais(t.getPais());
			endereco.setTipoEndereco(t.getTipoEndereco());

			manager.getTransaction().begin();
			manager.persist(endereco);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Endereco getById(Integer i) throws SQLException {
		return manager.find(Endereco.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Endereco> findAll() throws SQLException {
		return  manager.createQuery("from Endereco").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Endereco> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Endereco.class).getResultList();
	}

}
