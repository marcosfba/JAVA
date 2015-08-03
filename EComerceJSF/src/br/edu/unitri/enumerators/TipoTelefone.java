/**
 * 
 */
package br.edu.unitri.enumerators;

/**
 * @author MARCOS FERNANDO
 *
 */
public enum TipoTelefone {
	
	RESIDENCIAL("01","Telefone Residencial"), COMERCIAL("02","Telefone Comercial"), 
	CELULAR("03","Telefone Celular"), CONTATO("04","Telefone Contato"), OUTROS("05","Outros");
	
	private final String tipo;
	private final String descricao;

	TipoTelefone(String tipo, String descricao) {
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

