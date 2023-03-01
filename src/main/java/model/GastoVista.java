package model;

public class GastoVista {
    private Pessoa pessoa;
    
    // TODO: trocar para um objeto da classe Produto (criar esta classe).
    private int idGasto;
	private String nomeGasto;
	private String diaGasto;
	private double valorGasto;
	private int quantidadeProduto;
	
	private Loja loja; 
		
	public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public int getIdGasto() {
        return idGasto;
    }
	public void setIdGasto(int idGasto) {
        this.idGasto = idGasto;
    }
	public String getNomeGasto() {
		return nomeGasto;
	}
	public void setNomeGasto(String nomeGasto) {
		this.nomeGasto = nomeGasto;
	}
	public String getDiaGasto() {
		return diaGasto;
	}
	public void setDiaGasto(String diaGasto) {
		this.diaGasto = diaGasto;
	}
	public double getValorGasto() {
		return valorGasto;
	}
	public void setValorGasto(double valorGasto) {
		this.valorGasto = valorGasto;
	}
    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }
    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }
    public Loja getLoja() {
        return loja;
    }
    public void setLoja(Loja loja) {
        this.loja = loja;
    }
	
	
	
	
}
