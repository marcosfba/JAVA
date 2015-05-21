/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Fornecedor;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class FornecedorControler implements CRUD<Fornecedor,Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Fornecedor save(Fornecedor t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Integer i) throws SQLException {
        boolean ok = false;		
		Fornecedor fornecedor = manager.find(Fornecedor.class,(long)i);
		if (fornecedor != null) {
			ok = true;
			manager.getTransaction().begin();
			manager.remove(fornecedor);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public boolean delete(String qry) throws SQLException {
		manager.getTransaction().begin();
		int i = manager.createQuery(qry).executeUpdate();
		manager.getTransaction().commit();
		return i == 1;
	}

	@Override
	public boolean update(Fornecedor t, Integer i) throws SQLException {
        boolean ok = false;		
		Fornecedor fornecedor = manager.find(Fornecedor.class,(long)i);
		if (fornecedor != null) {
			ok = true;
			fornecedor.setCnpj(t.getCnpj());
			fornecedor.setEndereco(t.getEndereco());
			fornecedor.setNome(t.getNome());
			        
			manager.getTransaction().begin();
			manager.persist(fornecedor);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Fornecedor getById(Integer i) throws SQLException {
		return manager.find(Fornecedor.class,(long)i);
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

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
