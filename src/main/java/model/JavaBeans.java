package model;

public class JavaBeans {
    private int idItem;
    private String nomeItem = null;
    private double total;
    private int mes;
    private int ano;
    
    public JavaBeans(int idItem, String nomeItem, double total, int mes, int ano) {
        super();
        this.idItem = idItem;
        this.nomeItem = nomeItem;
        this.total = total;
        this.mes = mes;
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "JavaBeans [idItem=" + idItem + ", nomeItem=" + nomeItem + ", total=" + total + ", mes=" + mes + ", ano="
                + ano + "]";
    }

    

    
    
    
    
    
}
