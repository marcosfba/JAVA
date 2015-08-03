package br.edu.unitri.enumerators;

/**
 * @author MARCOS FERNANDO
 *
 */
public enum TipoPagamento {

	DINHEIRO("01","Pagamento a Vista"), BOLETO("02","Boleto Bancario"), CARTAODEBITO("03","Cartao de Debito"), 
	CARTAOCREDITO("04","Cartao Credito"), DEPOSITO("05","Deposito Bancario"), OUTROS("06","Outras");
	
	private final String tipo;
	private final String descricao;

	TipoPagamento(String tipo, String descricao) {
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
