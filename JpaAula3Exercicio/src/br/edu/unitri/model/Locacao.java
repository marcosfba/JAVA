/**
 * 
 */
package br.edu.unitri.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * @author marcos.fernando
 *
 */
@Entity
@Table(name = "tbLocacao")
public class Locacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long idLocacao;

	@OneToOne
	@JoinColumn(name = "veiculo_id", referencedColumnName="idVeiculo")
	@Colunas(nome = "Descrição Veículo", size = 175)
	private Veiculo veiculo;

	@OneToOne
	@JoinColumn(name = "cliente_id", referencedColumnName="idCliente")
	@Colunas(nome = "Cliente", size = 175)
	private Cliente cliente;

	@OneToOne 
	@JoinColumn(name = "idFuncionario_cad", referencedColumnName="idFuncionario")
	@Colunas(nome = "Funcionário Empréstimo", size = 175)
	private Funcionario funcionarioCad;

	@OneToOne
	@JoinColumn(name = "idFuncionario_rec", referencedColumnName="idFuncionario")
	@Colunas(nome = "Funcionário Devolução", size = 75)
	private Funcionario funcionarioRec;

	@Colunas(nome = "Nº de Dias", size = 75)
	private int qtdDias;
	@Colunas(nome = "Kilometragem", size = 75)
	private int kilometragem;

	public Locacao() {
		super();
	}
	
	public Locacao(Veiculo veiculo, Cliente cliente, int tipoFunc,
			Funcionario funcionario, Funcionario funionarioOld, int qtdDias, int kilometragem) {
		super();
		this.veiculo = veiculo;
		this.cliente = cliente;
		if (tipoFunc == 1) {
			this.funcionarioCad = funcionario;
		} else if (tipoFunc == 2) {
			this.funcionarioCad = funionarioOld;
			this.funcionarioRec = funcionario;
		}
		this.qtdDias = qtdDias;
		this.kilometragem = kilometragem;
	}

	public long getIdLocacao() {
		return idLocacao;
	}

	public void setIdLocacao(long idLocacao) {
		this.idLocacao = idLocacao;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionarioCad() {
		return funcionarioCad;
	}

	public void setFuncionarioCad(Funcionario funcionarioCad) {
		this.funcionarioCad = funcionarioCad;
	}

	public Funcionario getFuncionarioRec() {
		return funcionarioRec;
	}

	public void setFuncionarioRec(Funcionario funcionarioRec) {
		this.funcionarioRec = funcionarioRec;
	}

	public int getQtdDias() {
		return qtdDias;
	}

	public void setQtdDias(int qtdDias) {
		this.qtdDias = qtdDias;
	}

	public int getKilometragem() {
		return kilometragem;
	}

	public void setKilometragem(int kilometragem) {
		this.kilometragem = kilometragem;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idLocacao ^ (idLocacao >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Locacao other = (Locacao) obj;
		if (idLocacao != other.idLocacao)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Locacao [idLocacao=" + idLocacao + ", veiculo=" + veiculo
				+ ", cliente=" + cliente + ", funcionarioCad=" + funcionarioCad
				+ ", funcionarioRec=" + funcionarioRec + ", qtdDias=" + qtdDias
				+ ", kilometragem=" + kilometragem + "]";
	}

}
