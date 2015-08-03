package br.edu.unitri.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.model.SelectItem;

import br.edu.unitri.enumerators.TipoContato;
import br.edu.unitri.enumerators.TipoEndereco;
import br.edu.unitri.enumerators.TipoFoto;
import br.edu.unitri.enumerators.TipoOperacao;
import br.edu.unitri.enumerators.TipoPagamento;
import br.edu.unitri.enumerators.TipoStatus;
import br.edu.unitri.enumerators.TipoTelefone;
import br.edu.unitri.enumerators.TipoUsuario;

@ManagedBean
@SessionScoped
public class EnumeratorsBeanUtil {

	private List<SelectItem> listTiposTelefones = getTiposTelefones();
	private List<SelectItem> listTiposUsuarios = getTiposUsuarios();
	private List<SelectItem> listTiposEnderecos = getTiposEnderecos();
	private List<SelectItem> listTiposContatos = getTiposContatos();
	private List<SelectItem> listTiposFotos = getTiposFotos();
	private List<SelectItem> listTiposOperacao = getTiposOperacaos();
	private List<SelectItem> listTiposPagamentos = getTiposPagamentos();
	private List<SelectItem> listTiposStatus = getTiposStatus();

	public EnumeratorsBeanUtil() {
		super();
	}

	public List<SelectItem> getListTiposTelefones() {
		return listTiposTelefones;
	}

	public void setListTiposTelefones(List<SelectItem> listTiposTelefones) {
		this.listTiposTelefones = listTiposTelefones;
	}

	public List<SelectItem> getListTiposUsuarios() {
		return listTiposUsuarios;
	}

	public void setListTiposUsuarios(List<SelectItem> listTiposUsuarios) {
		this.listTiposUsuarios = listTiposUsuarios;
	}

	public List<SelectItem> getListTiposEnderecos() {
		return listTiposEnderecos;
	}

	public void setListTiposEnderecos(List<SelectItem> listTiposEnderecos) {
		this.listTiposEnderecos = listTiposEnderecos;
	}

	public List<SelectItem> getListTiposContatos() {
		return listTiposContatos;
	}

	public void setListTiposContatos(List<SelectItem> listTiposContatos) {
		this.listTiposContatos = listTiposContatos;
	}

	public List<SelectItem> getListTiposFotos() {
		return listTiposFotos;
	}

	public void setListTiposFotos(List<SelectItem> listTiposFotos) {
		this.listTiposFotos = listTiposFotos;
	}

	public List<SelectItem> getListTiposOperacao() {
		return listTiposOperacao;
	}

	public void setListTiposOperacao(List<SelectItem> listTiposOperacao) {
		this.listTiposOperacao = listTiposOperacao;
	}

	public List<SelectItem> getListTiposPagamentos() {
		return listTiposPagamentos;
	}

	public void setListTiposPagamentos(List<SelectItem> listTiposPagamentos) {
		this.listTiposPagamentos = listTiposPagamentos;
	}

	public List<SelectItem> getListTiposStatus() {
		return listTiposStatus;
	}

	public void setListTiposStatus(List<SelectItem> listTiposStatus) {
		this.listTiposStatus = listTiposStatus;
	}

	private List<SelectItem> getTiposTelefones() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoTelefone type : TipoTelefone.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

	private List<SelectItem> getTiposUsuarios() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoUsuario type : TipoUsuario.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

	private List<SelectItem> getTiposEnderecos() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoEndereco type : TipoEndereco.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

	private List<SelectItem> getTiposContatos() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoContato type : TipoContato.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

	private List<SelectItem> getTiposFotos() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoFoto type : TipoFoto.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

	private List<SelectItem> getTiposOperacaos() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoOperacao type : TipoOperacao.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

	private List<SelectItem> getTiposPagamentos() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoPagamento type : TipoPagamento.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

	private List<SelectItem> getTiposStatus() {
		List<SelectItem> items = new ArrayList<SelectItem>();
		for (TipoStatus type : TipoStatus.values()) {
			items.add(new SelectItem(type, type.getDescricao()));
		}
		return items;
	}

}
