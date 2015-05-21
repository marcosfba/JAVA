/**
 * 
 */
package br.edu.unitri.Controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Locacao;
import br.edu.unitri.model.Veiculo;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class VeiculoControler implements CRUD<Veiculo, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Veiculo save(Veiculo t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	public boolean inconsistencia(Veiculo t) {
		Locacao locacao = manager.getReference(Locacao.class, t.getIdVeiculo());
		return locacao == null;
	}

	@Override
	public boolean delete(Veiculo t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else { return false; }
	}

	@Override
	public boolean update(Veiculo t, Integer i) throws SQLException {
        boolean ok = false;		
		Veiculo veiculo = manager.find(Veiculo.class,(long)i);
		if (veiculo != null) {
			ok = true;
			veiculo.setDescricao(t.getDescricao());
			veiculo.setFator(t.getFator());
			veiculo.setCategoria(t.getCategoria());
			veiculo.setModelo(t.getModelo());
			        
			manager.getTransaction().begin();
			manager.persist(veiculo);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Veiculo getById(Integer i) throws SQLException {
		return manager.find(Veiculo.class,(long)i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Veiculo> findAll() throws SQLException {
		return  manager.createQuery("from Veiculo").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Veiculo> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Veiculo.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
