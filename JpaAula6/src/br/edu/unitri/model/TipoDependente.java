/**
 * 
 */
package br.edu.unitri.model;

/**
 * @author Marcos
 *
 */
public enum TipoDependente {

	MAE("Mãe"), PAI("Pai"), FILHO("Filho"), FILHA("Filha"), NETO("Neto"), NETA(
			"Neta"), IRMAO("Irmão"), IRMA("Irmã"), CONJUGE("Conjuge"), AVO(
			"Avos");

	public final String opcao;

	TipoDependente(String opcao) {
		this.opcao = opcao;
	}

	public String toString() {
		return opcao;
	}

}
