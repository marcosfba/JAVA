/**
 * 
 */
package br.edu.unitri.Controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.model.Avaria;
import br.edu.unitri.model.Locacao;
import br.edu.unitri.model.Multa;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class LocacaoControler implements CRUD<Locacao, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Locacao save(Locacao t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	public boolean inconsistencia(Locacao t) {
		Avaria avaria = manager.getReference(Avaria.class, t.getIdLocacao());
		Multa multa = manager.getReference(Multa.class, t.getIdLocacao());		
		return ((avaria == null)  && (multa == null));
	}

	@Override
	public boolean delete(Locacao t) throws SQLException {
		if (inconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else { return false ; }
	}

	@Override
	public boolean update(Locacao t, Integer i) throws SQLException {
        boolean ok = false;		
		Locacao locacao = manager.find(Locacao.class,(long)i);
		if (locacao != null) {
			ok = true;
			locacao.setCliente(t.getCliente());
			locacao.setVeiculo(t.getVeiculo());
			locacao.setFuncionarioCad(t.getFuncionarioCad());
			locacao.setFuncionarioRec(t.getFuncionarioRec());
			locacao.setKilometragem(t.getKilometragem());
			locacao.setQtdDias(t.getQtdDias());
			        
			manager.getTransaction().begin();
			manager.persist(locacao);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Locacao getById(Integer i) throws SQLException {
		return manager.find(Locacao.class,(long)i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Locacao> findAll() throws SQLException {
		return  manager.createQuery("from Locacao").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Locacao> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry ,Locacao.class).getResultList();
	}

	public EntityManager getManager() {
		return manager;
	}

	public void setManager(EntityManager manager) {
		this.manager = manager;
	}

}
