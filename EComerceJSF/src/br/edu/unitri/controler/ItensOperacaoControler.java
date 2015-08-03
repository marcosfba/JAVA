/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.ItensOperacao;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ItensOperacaoControler implements CRUD<ItensOperacao, Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public ItensOperacao save(ItensOperacao t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(ItensOperacao t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(ItensOperacao t) throws SQLException {
		boolean ok = false;
		ItensOperacao itensOperacao = manager.find(ItensOperacao.class, t.getId());
		if (itensOperacao != null) {
			ok = true;
			
			itensOperacao.setOperacao(t.getOperacao());
			itensOperacao.setProduto(t.getProduto());
			itensOperacao.setQuantidade(t.getQuantidade());
			itensOperacao.setVlrdesc(t.getVlrdesc());
			itensOperacao.setVlrItem(t.getVlrItem());

			manager.getTransaction().begin();
			manager.merge(itensOperacao);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public ItensOperacao getById(Integer i) throws SQLException {
		return manager.find(ItensOperacao.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItensOperacao> findAll() throws SQLException {
		return manager.createQuery("from ItensOperacao").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ItensOperacao> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry, ItensOperacao.class).getResultList();
	}

}
