package br.edu.unitri.model;

public enum TipoSexo {

	MASCULINO("M"), FEMININO("F"), INDEFINIDO("I");

	public final String opcao;

	TipoSexo(String opcao) {
		this.opcao = opcao;
	}

	public String toString() {
		return opcao;
	}
}
