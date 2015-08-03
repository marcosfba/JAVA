/**
 * 
 */
package br.edu.unitri.enumerators;

/**
 * @author MARCOS FERNANDO
 *
 */
public enum TipoEndereco {

	RESIDENCIAL("01","Endereco Residencial"), COMERCIAL("02","Endereco Comercial"), 
	CONTATO("03","Endereco de Contatro"), OUTROS("04","Outros");
	
	private final String tipo;
	private final String descricao;

	TipoEndereco(String tipo, String descricao) {
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
