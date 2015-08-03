/**
 * 
 */
package br.edu.unitri.controler;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.unitri.dto.ProdutoDTO;
import br.edu.unitri.enumerators.TipoFoto;
import br.edu.unitri.interfaces.CRUD;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Foto;
import br.edu.unitri.model.Marca;
import br.edu.unitri.model.Produto;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
public class ProdutoControler implements CRUD<Produto, Integer>, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private EntityManager manager = JpaUtil.getManager();

	@Override
	public Produto save(Produto t) throws SQLException {
		manager.getTransaction().begin();
		manager.persist(t);
		manager.getTransaction().commit();
		return t;
	}

	@Override
	public boolean delete(Produto t) throws SQLException {
		manager.getTransaction().begin();
		manager.remove(t);
		manager.getTransaction().commit();
		return true;
	}

	@Override
	public boolean update(Produto t) throws SQLException {
		boolean ok = false;
		Produto produto = manager.find(Produto.class, t.getId());
		if (produto != null) {
			ok = true;
			// dados referente a produto
			produto.setCategoria(t.getCategoria());
			produto.setCodigo(t.getCodigo());
			produto.setCodBarras(t.getCodBarras());
			produto.setComplemento(t.getComplemento());
			produto.setDescricao(t.getDescricao());
			produto.setDocFabricante(t.getDocFabricante());
			produto.setListaFotos(t.getListaFotos());
			produto.setMarca(t.getMarca());
			produto.setQtdEstoque(t.getQtdEstoque());
			produto.setUnidade(t.getUnidade());
			produto.setVlrCusto(t.getVlrCusto());
			produto.setVlrVenda(t.getVlrVenda());

			manager.getTransaction().begin();
			manager.merge(produto);
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
	public List<Produto> findAll(String qry, String parametros)
			throws SQLException {
		if (!parametros.isEmpty()) {
			qry = qry.concat(parametros);
		}
		return manager.createNativeQuery(qry, Produto.class).getResultList();
	}

	public List<Produto> findByCategoria(Categoria categoria)
			throws SQLException {

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Produto> query = cb.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);
		Predicate where1 = cb.equal(root.get("categoria"), categoria);
		query.where(where1);
		return JpaUtil.getManager().createQuery(query).getResultList();
	}

	public List<Produto> findByMarca(Marca marca) throws SQLException {

		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Produto> query = cb.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);
		Predicate where1 = cb.equal(root.get("marca"), marca);
		query.where(where1);
		return JpaUtil.getManager().createQuery(query).getResultList();
	}

	public List<Foto> findAllFotos(Produto produto) throws SQLException {
		List<Foto> lista = new ArrayList<Foto>();
		Produto prodInt = getById(produto.getId().intValue());
		if (prodInt != null) {
			if (prodInt.getListaFotos().size() > 0) {
				for (Foto fotos : prodInt.getListaFotos()) {					
					Foto fotInt = new Foto(fotos.getNomeImagem(),fotos.getFotoOriginal(),
							               fotos.getFotoTumb(),fotos.getExtensao());
					lista.add(fotInt);
				}
			}
		}
		return lista;
	}
	
	public List<ProdutoDTO> findProdutoFotos() throws SQLException {
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<ProdutoDTO> query = cb.createQuery(ProdutoDTO.class);
		Root<Produto> root = query.from(Produto.class);
		Join<Produto,Foto> joinFoto = root.<Produto,Foto>join("listaFotos");

		Predicate where1 = cb.equal(joinFoto.get("tipoFoto"),TipoFoto.PRINCIPAL);
		query.multiselect(root.get("id").alias("idProduto"),root.get("codigo").alias("codigo"),
				          root.get("codBarras").alias("codBarras"),root.get("descricao").alias("descricao"), 
				          root.get("unidade").alias("unidade"), root.get("complemento").alias("complemento"),
				          root.get("categoria").alias("categoria"), root.get("marca").alias("marca"), 
				          root.get("vlrCusto").alias("vlrCusto"),root.get("vlrVenda").alias("vlrVenda"), 
				          root.get("qtdEstoque").alias("qtdEstoque"), root.get("docFabricante").alias("docFabricante"),
				          joinFoto.get("nomeImagem").alias("nomeImagem"),
				          joinFoto.get("extensao").alias("extensao") , 
				          joinFoto.get("fotoOriginal").alias("fotoOriginal"),
				          joinFoto.get("fotoTumb").alias("fotoTumb"));
		query.where(where1);
		query.distinct(true);
		return JpaUtil.getManager().createQuery(query).getResultList();
	}

}
