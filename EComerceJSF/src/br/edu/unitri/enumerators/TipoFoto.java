/**
 * 
 */
package br.edu.unitri.enumerators;

/**
 * @author marcos.fernando
 *
 */
public enum TipoFoto {
	
	PRINCIPAL("01","Foto principal"), AUXILIARES("02","Fotos Auxiliares");
	
	private final String tipo;
	private final String descricao;

	TipoFoto(String tipo, String descricao) {
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
