/**
 * 
 */
package br.edu.unitri.Controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Veiculo;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class CategoriaControler implements CRUD<Categoria, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Categoria save(Categoria t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Categoria t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}
	
	public boolean inconsistencia(Categoria t) {
		Veiculo veic = manager.getReference(Veiculo.class, t.getIdCategoria());
		return veic == null;
	}

	@Override
	public boolean update(Categoria t, Integer i) throws SQLException {
        boolean ok = false;		
		Categoria categoria = manager.find(Categoria.class,(long)i);
		if (categoria != null) {
			ok = true;
			categoria.setDescricao(t.getDescricao());
			categoria.setPreco(t.getPreco());
			        
			manager.getTransaction().begin();
			manager.persist(categoria);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Categoria getById(Integer i) throws SQLException {
		return manager.find(Categoria.class,(long)i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> findAll() throws SQLException {
		return  manager.createQuery("from Categoria").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Categoria> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Categoria.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
