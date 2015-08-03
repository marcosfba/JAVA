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
import br.edu.unitri.model.Endereco;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class EnderecoControler implements CRUD<Endereco,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Endereco t) throws SQLException {
		boolean ok = false;
		Endereco endereco = manager.find(Endereco.class, t.getId());
		if (endereco != null) {
			ok = true;
			endereco.setBairro(t.getBairro());
			endereco.setCidade(t.getCidade());
			endereco.setComplemento(t.getComplemento());
			endereco.setEstado(t.getEstado());
			endereco.setLogradouro(t.getLogradouro());
			endereco.setNumero(t.getNumero());
			endereco.setTipoEndereco(t.getTipoEndereco());

			manager.getTransaction().begin();
			manager.merge(endereco);
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
	
	public boolean delete(Collection<Endereco> listaEnderecos) throws SQLException {
		if (listaEnderecos.size() > 0) {
			for (Endereco endereco : listaEnderecos) {
				delete(endereco);
			}
		}
		return true;
	}
}
