/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Fornecedor;
import br.edu.unitri.util.JpaUtil;


/**
 * @author marcos.fernando
 *
 */
public class FornecedorControler implements CRUD<Fornecedor,Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Fornecedor save(Fornecedor t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}	

	@Override
	public boolean delete(Fornecedor t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Fornecedor t) throws SQLException {
		boolean ok = false;
		Fornecedor fornecedor = manager.find(Fornecedor.class, t.getId());
		if (fornecedor != null) {
			ok = true;
			//dados referente a pessoa
			fornecedor.setNome(t.getNome());
			fornecedor.setTipoPessoa(t.getTipoPessoa());
			fornecedor.setUsuario(t.getUsuario());
			fornecedor.setObservacao(t.getObservacao());
			//dados referente somente ao fornecedor			
			fornecedor.setCnpj(t.getCnpj());
			fornecedor.setInscMunicipal(t.getInscMunicipal());
			fornecedor.setInsEstadual(t.getInsEstadual());

			manager.getTransaction().begin();
			manager.merge(fornecedor);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Fornecedor getById(Integer i) throws SQLException {
		return manager.find(Fornecedor.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fornecedor> findAll() throws SQLException {
		return  manager.createQuery("from Fornecedor").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fornecedor> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Fornecedor.class).getResultList();
	}
	

}
