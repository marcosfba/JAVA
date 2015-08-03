package br.edu.unitri.enumerators;

/**
 * @author MARCOS FERNANDO
 *
 */
public enum TipoUsuario {

	ADMINISTRADOR("0", "Administracao do Site"), USERSITE("1", "Usuario logado"), USER(
			"2", "Usuario browser");

	private final String tipo;
	private final String descricao;

	TipoUsuario(String tipo, String descricao) {
		this.tipo = tipo;
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public String getDescricao() {
		return descricao;
	}
	
	@Override
    public String toString() {
        return  this.getDescricao();
    }

}
