package br.edu.unitri.enumerators;

public enum TipoStatus {

	ABERTO("01","Aberto"), PROCESSADO("02","Processado"), 
	ENVIADO("03","Enviado"), ENTREGUE("04","Entregue"), 
	CANCELADO("05","Cancelado");
	
	private final String tipo;
	private final String descricao;

	TipoStatus(String tipo, String descricao) {
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
