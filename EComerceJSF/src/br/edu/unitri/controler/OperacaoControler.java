/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;

import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Operacao;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class OperacaoControler implements CRUD<Operacao, Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Operacao save(Operacao t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Operacao t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Operacao t) throws SQLException {
		boolean ok = false;
		Operacao operacao = manager.find(Operacao.class, t.getId());
		if (operacao != null) {
			ok = true;
			
			operacao.setPessoa(t.getPessoa());
			operacao.setDtOperacao(t.getDtOperacao());
			operacao.setTipoOperacao(t.getTipoOperacao());
			operacao.setTipoPagamento(t.getTipoPagamento());
			operacao.setTipoStatus(t.getTipoStatus());
			operacao.setVlrPedido(t.getVlrPedido());

			manager.getTransaction().begin();
			manager.merge(operacao);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Operacao getById(Integer i) throws SQLException {
		return manager.find(Operacao.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operacao> findAll() throws SQLException {
		return manager.createQuery("from Operacao").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Operacao> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry, Operacao.class).getResultList();
	}

}
