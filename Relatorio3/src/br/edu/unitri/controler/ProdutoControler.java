/**
 * 
 */
package br.edu.unitri.controler;

import java.sql.SQLException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import br.edu.unitri.model.ItensPedido;
import br.edu.unitri.model.Pedido;
import br.edu.unitri.model.Produto;
import br.edu.unitri.model.ProdutoDTO;
import br.edu.unitri.util.JpaUtil;

/**
 * @author MARCOS FERNANDO
 *
 */
public class ProdutoControler implements CRUD<Produto, Integer> {

	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Produto save(Produto t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	public boolean isInconsistencia(Produto t) {
		return manager.getReference(ItensPedido.class, t.getCodProduto()) == null;
	}

	@Override
	public boolean delete(Produto t) throws SQLException {
		if (isInconsistencia(t)) {
			manager.getTransaction().begin();
			manager.remove(t);
			manager.getTransaction().commit();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean update(Produto t, Integer i) throws SQLException {
		boolean ok = false;
		Produto produto = manager.find(Produto.class, (long) i);
		if (produto != null) {
			ok = true;
			produto.setCategoria(t.getCategoria());
			produto.setNomeProduto(t.getNomeProduto());
			produto.setValorProd(t.getValorProd());
			produto.setQuantidade(t.getQuantidade());
			produto.setImagem(t.getImagem());

			manager.getTransaction().begin();
			manager.persist(produto);
			manager.getTransaction().commit();
		}
		return ok;
	}

	@Override
	public Produto getById(Integer i) throws SQLException {
		return manager.find(Produto.class, (long) i);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findAll() throws SQLException {
		return manager.createQuery("from Produto").getResultList();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Produto> findAll(String qry, String parametros) throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry, Produto.class).getResultList();
	}
	
	public List<ProdutoDTO> findProdutoDTOAll() throws SQLException {
		
		CriteriaBuilder cb = manager.getCriteriaBuilder();
		CriteriaQuery<ProdutoDTO> query = cb.createQuery(ProdutoDTO.class);
		
		Root<ItensPedido> root = query.from(ItensPedido.class);
		Join<ItensPedido,Pedido> joinPed = root.join("pedido", JoinType.INNER);
		
		Expression<Double> param  = root.get("VlrItem");
		Expression<Double> param2 = root.get("VlrDesconto");		
		Expression<Double> param3 = root.get("QtdItem");
		Expression<Integer> param4 = cb.function("month", Integer.class, joinPed.get("dtPedido"));
		
		query.multiselect(root.get("produto"),
				          cb.sum(cb.diff(param,param2)),
				          cb.sum(param3), param4);
		query.groupBy(root.get("produto"), param4);
		query.orderBy(cb.asc(root.get("produto")));

        List<ProdutoDTO> listaProdutos = manager.createQuery(query).getResultList();
        return listaProdutos;
	}
	
}
