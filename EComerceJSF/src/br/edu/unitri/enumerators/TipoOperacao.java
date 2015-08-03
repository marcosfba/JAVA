/**
 * 
 */
package br.edu.unitri.enumerators;

/**
 * @author MARCOS FERNANDO
 *
 */
public enum TipoOperacao {

	PEDIDO("01","Compras"), VENDAS("02","Vendas");
	
	private final String tipo;
	private final String descricao;

	TipoOperacao(String tipo, String descricao) {
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
