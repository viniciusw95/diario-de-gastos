package model;

public class ItemDespesa extends JavaBeans {
    private String nomeLoja;
    private String dataDespesa;
    private int qtd;
    private double valorUnit;
    private String formaPgto;
    public ItemDespesa(String nomeItem, String nomeLoja, String dataDespesa, int qtd, double valorUnit, String formaPgto) {
        super();
        this.setNomeItem(nomeItem);
        this.nomeLoja = nomeLoja;
        this.dataDespesa = dataDespesa;
        this.qtd = qtd;
        this.valorUnit = valorUnit;
        this.formaPgto = formaPgto;
    }
    public String getNomeLoja() {
        return nomeLoja;
    }
    public String getDataDespesa() {
        return dataDespesa;
    }
    public int getQtd() {
        return qtd;
    }
    public double getValorUnit() {
        return valorUnit;
    }
    public String getFormaPgto() {
        return formaPgto;
    }
    
    
    
}
