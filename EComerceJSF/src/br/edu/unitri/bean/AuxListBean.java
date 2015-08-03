/**
 * 
 */
package br.edu.unitri.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.unitri.model.Cliente;
import br.edu.unitri.model.Contato;
import br.edu.unitri.model.Endereco;
import br.edu.unitri.model.Operacao;
import br.edu.unitri.model.Telefone;
import br.edu.unitri.model.Usuario;
import br.edu.unitri.util.JpaUtil;

/**
 * @author marcos.fernando
 *
 */
@ManagedBean
@SessionScoped
public class AuxListBean {

	private Collection<Telefone> telefones = new ArrayList<Telefone>();
	private Collection<Endereco> enderecos = new ArrayList<Endereco>();
	private Collection<Contato> contatos = new ArrayList<Contato>();
	private Collection<Operacao> pedidos = new ArrayList<Operacao>();
	private Cliente cliente = new Cliente();
	private Contato contato = new Contato();
	private Endereco endereco = new Endereco();
	private Telefone telefone = new Telefone();
		
	@PostConstruct
	public void init() {	
	}
	
	public void loginUser(Usuario usuario){
		CriteriaBuilder cbCliente = JpaUtil.getManager().getCriteriaBuilder();
		CriteriaQuery<Cliente> query = cbCliente.createQuery(Cliente.class);
		Root<Cliente> root = query.from(Cliente.class);
		Predicate where1 = cbCliente.equal(root.get("usuario"), usuario);
		query.where(where1);
		
		List<Cliente> clientes = JpaUtil.getManager().createQuery(query).getResultList();
		if (clientes.size() > 0) {
			
			for (Cliente clienteLista : clientes) {
				cliente = clienteLista;				
			}
			contatos.clear();			
			for (Contato contato : cliente.getListaContatos()) {
				contatos.add(contato);
				this.contato = contato;
			}
			enderecos.clear();
			for (Endereco endereco : cliente.getListaEnderecos()) {
				enderecos.add(endereco);
				this.endereco = endereco;
			}
			telefones.clear();
			for (Telefone telefone : cliente.getListaTelefones()) {
				telefones.add(telefone);
				this.telefone = telefone;
			}
			CriteriaBuilder cbPedido = JpaUtil.getManager().getCriteriaBuilder();
			CriteriaQuery<Operacao> queryPedido = cbPedido
					.createQuery(Operacao.class);
			Root<Operacao> rootPed = queryPedido.from(Operacao.class);
			Predicate where2 = cbPedido.equal(rootPed.get("pessoa"), cliente);
			queryPedido.where(where2);

			pedidos.clear();
			pedidos = JpaUtil.getManager().createQuery(queryPedido).getResultList();
		}
	}

	public Collection<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(Collection<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Collection<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(Collection<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Collection<Contato> getContatos() {
		return contatos;
	}

	public void setContatos(Collection<Contato> contatos) {
		this.contatos = contatos;
	}

	public Collection<Operacao> getPedidos() {
		return pedidos;
	}

	public void setPedidos(Collection<Operacao> pedidos) {
		this.pedidos = pedidos;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Contato getContato() {
		return contato;
	}

	public void setContato(Contato contato) {
		this.contato = contato;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

}
