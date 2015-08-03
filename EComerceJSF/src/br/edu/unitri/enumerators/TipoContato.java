/**
 * 
 */
package br.edu.unitri.enumerators;

/**
 * @author Marcos
 *
 */
public enum TipoContato {
	
	EMAIL_PRINCIPAL("01","Email Principal"), EMAIL_SECUNDARIO("02","Endereco Secundario"),
	EMAIL_COMERCIAL("03","Email Comercial"), OUTROS("04","Outros");
	
	private final String tipo;
	private final String descricao;

	TipoContato(String tipo, String descricao) {
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
