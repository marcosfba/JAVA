/**
 * 
 */
package br.edu.unitri.bean;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;

import net.sf.jasperreports.engine.JRException;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.primefaces.model.chart.PieChartModel;

import br.edu.unitri.controler.CategoriaControler;
import br.edu.unitri.controler.FotoControler;
import br.edu.unitri.controler.MarcaControler;
import br.edu.unitri.controler.ProdutoControler;
import br.edu.unitri.dataSource.DadosFactory;
import br.edu.unitri.dto.GraficoProdutoDTO;
import br.edu.unitri.dto.ProdutoCategoriaDTO;
import br.edu.unitri.dto.ProdutoGeralDTO;
import br.edu.unitri.dto.ProdutoMarcaDTO;
import br.edu.unitri.enumerators.TipoFoto;
import br.edu.unitri.interfaces.Operacoes;
import br.edu.unitri.model.Categoria;
import br.edu.unitri.model.Foto;
import br.edu.unitri.model.Marca;
import br.edu.unitri.model.Produto;
import br.edu.unitri.util.FileUtil;
import br.edu.unitri.util.JpaUtil;
import br.edu.unitri.util.UtilBean;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class ProdutoBean implements Serializable, Operacoes {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Produto produto = new Produto();
	private Produto produtoSel;
	private String tipoGrafico;

	private Foto foto = new Foto();
	private List<Foto> listaFotos = new ArrayList<Foto>();

	private List<Produto> listaProduto = new ArrayList<Produto>();
	private List<Categoria> listaCategorias = new ArrayList<Categoria>();
	private List<Marca> listaMarcas = new ArrayList<Marca>();

	private ProdutoControler produtoControler = new ProdutoControler();
	private MarcaControler marcaControler = new MarcaControler();
	private CategoriaControler categoriaControler = new CategoriaControler();
	private FotoControler fotoControler = new FotoControler();
	private List<GraficoProdutoDTO> listGrafico = new ArrayList<GraficoProdutoDTO>();
	private PieChartModel grafico;

	public ProdutoBean() {
		super();
	}

	@PostConstruct
	public void init() {
		listarTodos();
		grafico = new PieChartModel();
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public Produto getProdutoSel() {
		return produtoSel;
	}

	public void setProdutoSel(Produto produtoSel) {
		this.produtoSel = produtoSel;
	}

	public List<Produto> getListaProduto() {
		return listaProduto;
	}

	public void setListaProduto(List<Produto> listaProduto) {
		this.listaProduto = listaProduto;
	}

	public List<Categoria> getListaCategorias() {
		return listaCategorias;
	}

	public void setListaCategorias(List<Categoria> listaCategorias) {
		this.listaCategorias = listaCategorias;
	}

	public List<Marca> getListaMarcas() {
		return listaMarcas;
	}

	public void setListaMarcas(List<Marca> listaMarcas) {
		this.listaMarcas = listaMarcas;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public List<Foto> getListaFotos() {
		return listaFotos;
	}

	public void setListaFotos(List<Foto> listaFotos) {
		this.listaFotos = listaFotos;
	}

	public String getTipoGrafico() {
		return tipoGrafico;
	}

	public void setTipoGrafico(String tipoGrafico) {
		this.tipoGrafico = tipoGrafico;
	}

	public List<GraficoProdutoDTO> getListGrafico() {
		return listGrafico;
	}

	public void setListGrafico(List<GraficoProdutoDTO> listGrafico) {
		this.listGrafico = listGrafico;
	}

	public PieChartModel getGrafico() {
		return grafico;
	}

	public void setGrafico(PieChartModel grafico) {
		this.grafico = grafico;
	}

	public void listarTodos() {
		try {
			listaProduto = produtoControler.findAll();
			listaCategorias = categoriaControler.findAll();
			listaMarcas = marcaControler.findAll();			
		} catch (SQLException e) {
			FacesContext.getCurrentInstance().addMessage(
					"mesProdutos",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao listar Produtos " + e.getMessage()));
		}
	}

	@Override
	public void salvar() {
		try {
			if (produtoControler.save(getProduto()) != null) {
				for (Foto fotoSel : listaFotos) {
					fotoSel.setProduto(getProduto());
					fotoControler.save(fotoSel);
				}
				limpar();
				FacesContext.getCurrentInstance().addMessage(
						"mesProdutos",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"mesProdutos",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao incluir Produto " + e.getMessage()));
		}
	}

	@Override
	public void excluir() {
		if (getProdutoSel() != null) {
			try {
				produtoControler.delete(getProdutoSel());
				FacesContext.getCurrentInstance().addMessage(
						"mesProdutos",
						new FacesMessage(FacesMessage.SEVERITY_INFO,
								"Informação", "Produto excluido com sucesso "));
				listarTodos();
			} catch (Exception e) {
				FacesContext.getCurrentInstance().addMessage(
						"mesProdutos",
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
								"Erro ao tentar excluir Produto "
										+ e.getMessage()));
			}
		}
	}

	@Override
	public void editar() {

		setProduto(getProdutoSel());
		if (getProduto().getListaFotos().size() > 0) {
			listaFotos.clear();
			for (Foto fotoInt : getProduto().getListaFotos()) {
				listaFotos.add(fotoInt);
			}
		}
	}

	@Override
	public void limpar() {
		produto = new Produto();
		listaFotos.clear();
		listarTodos();
	}

	@Override
	public void alterar() {
		try {
			if (produtoControler.update(getProduto())) {
				for (int i = 0; i < listaFotos.size(); i++) {
					if (listaFotos.get(i).getProduto() != null) {
						fotoControler.update(listaFotos.get(i));
					} else {
						listaFotos.get(i).setProduto(getProduto());
						fotoControler.save(listaFotos.get(i));
					}
				}
				FacesContext.getCurrentInstance().addMessage(
						"mesProdutos",
						new FacesMessage(FacesMessage.SEVERITY_INFO, "INFO",
								"Operação realizada com sucesso"));
				limpar();
			}
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					"mesProdutos",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao editar produto " + e.getMessage()));
		}
	}

	public void doUpload(FileUploadEvent fileUploadEvent) {

		UploadedFile uploadedFile = fileUploadEvent.getFile();

		String fileName = fileUploadEvent.getFile().getFileName();
		File localFile = new File(fileName);
		String[] variaveis = localFile.getName().split("[.]");

		String extensao = variaveis[1];
		String fileSaida = "tumb_" + variaveis[0];
		String fileIn = variaveis[0];
		String nomeImg = variaveis[0];

		// criando o tumb da imagem
		BufferedImage imagem = null;
		try {
			imagem = ImageIO.read(uploadedFile.getInputstream());
		} catch (IOException ex) {
			FacesContext.getCurrentInstance().addMessage(
					"mesFotos",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Erro ao gerar tumb da imagem" + ex.getMessage()));
		}
		int new_w = 125, new_h = 125;
		BufferedImage new_img = new BufferedImage(new_w, new_h,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D g = new_img.createGraphics();

		g.drawImage(imagem, 0, 0, new_w, new_h, null);

		File fileTumb = null;
		try {
			fileTumb = new File(fileSaida);
			ImageIO.write(new_img, extensao, fileTumb);
		} catch (IOException e1) {
			FacesContext.getCurrentInstance().addMessage(
					"mesFotos",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Erro ao gerar tumb da imagem" + e1.getMessage()));
		}

		long fileSizeOriginal = uploadedFile.getSize();
		long fileSizeTumb = 0;
		if (fileTumb != null) {
			fileSizeTumb = fileTumb.length();
		}

		String fileInDestino = UtilBean.getImageResource() + fileIn + "."
				+ extensao;
		String fileTbDestino = UtilBean.getImageResource() + fileSaida + "."
				+ extensao;

		System.out.println(fileInDestino);
		System.out.println(fileTbDestino);

		FileUtil fileUtil = new FileUtil((int) fileSizeOriginal);
		try {
			fileUtil.copiaArquivo(uploadedFile.getInputstream(), fileInDestino);
			fileUtil.copyFile(fileTumb, new File(fileTbDestino));

			boolean ok = false;
			TipoFoto tipo = null;
			if (listaFotos.size() == 0) {
				tipo = TipoFoto.PRINCIPAL;
				ok = true;
			} else {
				tipo = TipoFoto.AUXILIARES;
			}
			Foto fotoUpload = new Foto(tipo, nomeImg, ".".concat(extensao),
					(int) fileSizeOriginal, (int) fileSizeTumb, fileIn,
					fileSaida);
			if (ok) {
				setFoto(fotoUpload);
			}
			listaFotos.add(fotoUpload);

		} catch (IOException e) {
			FacesContext.getCurrentInstance().addMessage(
					"mesFotos",
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"Erro de upload da imagem" + e.getMessage()));
		}
		FacesContext.getCurrentInstance().addMessage(
				"mesFotos",
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Informação",
						"Foto enviada com sucesso"));
	}

	@Override
	public void buscar() {
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Produto> query = cb.createQuery(Produto.class);
		Root<Produto> root = query.from(Produto.class);
		EntityType<Produto> typeProduto = JpaUtil.getManager().getMetamodel()
				.entity(Produto.class);

		Predicate where1 = null, where2 = null, where3 = null, where4 = null, where5 = null;
		if (getProduto().getId() != null) {
			where1 = cb.equal(root.get("id"), getProduto().getId());
		}
		if (getProduto().getCodBarras() != null) {
			where2 = cb.equal(root.get("codBarras"), getProduto()
					.getCodBarras());
		}
		if (getProduto().getDescricao() != null) {
			where3 = cb.like(root.get(typeProduto.getDeclaredSingularAttribute(
					"descricao", String.class)), getProduto().getDescricao());
		}
		if (getProduto().getCategoria() != null) {
			where4 = cb.equal(root.get("categoria"), getProduto()
					.getCategoria());
		}
		if (getProduto().getMarca() != null) {
			where5 = cb.equal(root.get("marca"), getProduto().getMarca());
		}
		List<Predicate> predicados = new ArrayList<Predicate>();
		if (where1 != null) {
			predicados.add(where1);
		}
		if (where2 != null) {
			predicados.add(where2);
		}
		if (where3 != null) {
			predicados.add(where3);
		}
		if (where4 != null) {
			predicados.add(where4);
		}
		if (where5 != null) {
			predicados.add(where5);
		}

		if (predicados.size() > 0) {
			query.where(cb.or(predicados.toArray(new Predicate[] {})));
		}

		try {
			listaProduto.clear();
			listaProduto = JpaUtil.getManager().createQuery(query)
					.getResultList();
		} catch (Exception e) {
			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
							"Erro ao buscar pessoa " + e.getMessage()));
		} finally {
			if (listaProduto.size() == 0) {
				FacesContext
						.getCurrentInstance()
						.addMessage(
								null,
								new FacesMessage(FacesMessage.SEVERITY_WARN,
										"WARNING",
										"Não há produtos a serem exibidos com argumentos passados"));
			}
		}
	}
	
	public void gerarGraficos(){
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<GraficoProdutoDTO> query = cb.createQuery(GraficoProdutoDTO.class);
		Root<Produto> root = query.from(Produto.class);
		if (getTipoGrafico().equals("0")) {
			Join<Produto,Categoria> join = root.join("categoria", JoinType.LEFT);
			Expression<Integer> local = root.get("categoria");
			query.multiselect(cb.count(local), join.get("descricao"));
			query.groupBy(root.get("categoria"));
		} else if (getTipoGrafico().equals("1")) {
			Join<Produto,Marca> join = root.join("marca", JoinType.LEFT);
			Expression<Integer> local = root.get("marca");
			query.multiselect(cb.count(local), join.get("descricao"));
			query.groupBy(root.get("marca"));
		}
		listGrafico = JpaUtil.getManager().createQuery(query).getResultList();
		grafico = new PieChartModel();
		for (GraficoProdutoDTO graficoProdutoDTO : listGrafico) {
			grafico.set(graficoProdutoDTO.getDescricao(), graficoProdutoDTO.getQuantidade());
		}
         
        if (getTipoGrafico().equals("0")){
        	grafico.setTitle("Produtos por categoria");
        } else if (getTipoGrafico().equals("1")) {
        	grafico.setTitle("Produtos por marca");
        }
        grafico.setLegendPosition("e");
        grafico.setFill(false);
        grafico.setShowDataLabels(true);
	}
	
	public void gerarRelatorios(){
		
		CriteriaBuilder cb = JpaUtil.getManager().getCriteriaBuilder();
		
		List<ProdutoCategoriaDTO> listaProdutoCategoriaDTO = new ArrayList<ProdutoCategoriaDTO>();
		List<ProdutoMarcaDTO> listaProdutoMarcaDTO = new ArrayList<ProdutoMarcaDTO>();
		List<ProdutoGeralDTO> listaProdutoGeralDTO = new ArrayList<ProdutoGeralDTO>();
		
		listaProdutoCategoriaDTO.clear();
		listaProdutoMarcaDTO.clear();
		listaProdutoGeralDTO.clear();

		if (getTipoGrafico().equals("0")) {
			//relatório de produtos por categoria
			CriteriaQuery<ProdutoCategoriaDTO> query = cb.createQuery(ProdutoCategoriaDTO.class);
			
			Root<Produto> root = query.from(Produto.class);
			Join<Produto,Categoria> joinCategoria = root.join("categoria", JoinType.LEFT);
			
			query.multiselect(root.get("codigo").alias("codProduto"),root.get("descricao").alias("nomeProduto"),
			                  root.get("vlrVenda").alias("valorProd"),root.get("vlrCusto").alias("valorCusto"),
							  joinCategoria.get("descricao").alias("descCategoria"),
							  joinCategoria.get("complemento").alias("compCategoria"),
							  joinCategoria.get("id").alias("idCategoria"),
							  root.get("unidade").alias("unidade"));
			
			listaProdutoCategoriaDTO = JpaUtil.getManager().createQuery(query).getResultList();	
			
			
		} else if (getTipoGrafico().equals("1")) {
			//relatório de produtos por marca
			CriteriaQuery<ProdutoMarcaDTO> query = cb.createQuery(ProdutoMarcaDTO.class);
			
			Root<Produto> root = query.from(Produto.class);
			Join<Produto,Marca> joinMarca = root.join("marca", JoinType.LEFT);
			
			query.multiselect(root.get("codigo").alias("codProduto"),root.get("descricao").alias("nomeProduto"),
			                  root.get("vlrVenda").alias("valorProd"),root.get("vlrCusto").alias("valorCusto"),
							  joinMarca.get("descricao").alias("descMarca"),
							  joinMarca.get("complemento").alias("compMarca"),
							  joinMarca.get("id").alias("idMarca"),
							  root.get("unidade").alias("unidade"));

			listaProdutoMarcaDTO = JpaUtil.getManager().createQuery(query).getResultList();	
		} else if (getTipoGrafico().equals("2")) {

			CriteriaQuery<ProdutoGeralDTO> query = cb.createQuery(ProdutoGeralDTO.class);
			Root<Produto> root = query.from(Produto.class);
			Join<Produto,Foto> joinFoto = root.<Produto,Foto>join("listaFotos");
			Join<Produto,Categoria> joinCategoria = root.join("categoria", JoinType.LEFT);
			Join<Produto,Marca> joinMarca = root.join("marca", JoinType.LEFT);
			Predicate where = cb.equal(joinFoto.get("tipoFoto"),TipoFoto.PRINCIPAL);
			query.multiselect(root.get("id").alias("idProduto"),root.get("codigo").alias("codInterno"),
	                  root.get("codBarras").alias("codBarras"),root.get("descricao").alias("descProduto"),
	                  root.get("unidade").alias("unidade"), root.get("complemento").alias("complemento"),
					  joinCategoria.get("descricao").alias("nomeCategoria"), joinCategoria.get("complemento").alias("compCategoria"),
					  joinMarca.get("descricao").alias("nomeMarca"), joinMarca.get("complemento").alias("compMarca"),
	                  root.get("vlrCusto").alias("valorCusto"), root.get("vlrVenda").alias("valorVenda"),
					  root.get("docFabricante").alias("docFabricante"),joinFoto.get("fotoOriginal").alias("fotoProduto"));
			
			query.where(where);
			query.distinct(true);

			listaProdutoGeralDTO = JpaUtil.getManager().createQuery(query).getResultList();	
		}
		
		boolean okRelatorio = true;
		
		if (getTipoGrafico().equals("0") && (listaProdutoCategoriaDTO.size() == 0)) {
			okRelatorio = false;
		}
		if (getTipoGrafico().equals("1") && (listaProdutoMarcaDTO.size() == 0)) {
			okRelatorio = false;
		}
		if (getTipoGrafico().equals("2") && (listaProdutoGeralDTO.size() == 0)) {
			okRelatorio = false;
		}
		
		if (!okRelatorio) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_WARN, "WARNING",	
							"Não há produtos a serem exibidos"));

		} 
		else 
		{
			if (getTipoGrafico().equals("0")) {
				Report relatorio = new Report(4);
				try {
					relatorio.getArquivoRelatorio(new DadosFactory().createDataSourceProdutoCategoria(listaProdutoCategoriaDTO));
				} catch (FileNotFoundException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
									"Erro ao gerar relatório de Produtos por Categoria " + e.getMessage()));
				} catch (JRException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
									"Erro ao gerar relatório de Produtos por Categoria " + e.getMessage()));
				}
			}
			if (getTipoGrafico().equals("1")) {
				Report relatorio = new Report(5);
				try {
					relatorio.getArquivoRelatorio(new DadosFactory().createDataSourceProdutoMarca(listaProdutoMarcaDTO));
				} catch (FileNotFoundException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
									"Erro ao gerar relatório de Produtos por Marca " + e.getMessage()));
				} catch (JRException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
									"Erro ao gerar relatório de Produtos por Marca " + e.getMessage()));
				}
			}
			if (getTipoGrafico().equals("2")) {
				Report relatorio = new Report(6);
				try {
					relatorio.getArquivoRelatorio(new DadosFactory().createDataSourceProdutoGeral(listaProdutoGeralDTO));
				} catch (FileNotFoundException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
									"Erro ao gerar relatório de Produtos " + e.getMessage()));
				} catch (JRException e) {
					FacesContext.getCurrentInstance().addMessage(
							null,
							new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro",
									"Erro ao gerar relatório de Produtos " + e.getMessage()));
				}
			}
		}
	}

}
