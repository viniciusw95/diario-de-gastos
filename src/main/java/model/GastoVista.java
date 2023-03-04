package model;

public class GastoVista {
    private Pessoa pessoa;
    
    // TODO: trocar para um objeto da classe Produto (criar esta classe).
    private int idProduto;
	private String nomeGasto;
	private String diaGasto;
	private double valorProduto;
	private int quantidadeProduto;
	
	private int idCompra;
	
	private Loja loja; 
		
	public Pessoa getPessoa() {
        return pessoa;
    }
    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
    public int getIdProduto() {
        return idProduto;
    }
	public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
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
	public double getValorProduto() {
		return valorProduto;
	}
	public void setValorProduto(double valorProduto) {
		this.valorProduto = valorProduto;
	}
    public int getQuantidadeProduto() {
        return quantidadeProduto;
    }
    public void setQuantidadeProduto(int quantidadeProduto) {
        this.quantidadeProduto = quantidadeProduto;
    }       
    public int getIdCompra() {
        return idCompra;
    }
    public void setIdCompra(int idCompra) {
        this.idCompra = idCompra;
    }
    public Loja getLoja() {
        return loja;
    }
    public void setLoja(Loja loja) {
        this.loja = loja;
    }
	
	
	
	
}
