package model;

import java.sql.ResultSet;
import java.sql.SQLException;

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

    public JavaBeans(int idItem, String nomeItem, int ano) {
        super();
        this.idItem = idItem;
        this.nomeItem = nomeItem;
        this.ano = ano;
    }
    
    public void lerDados(ResultSet dados) {};

    protected void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }
    protected void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return "JavaBeans [idItem=" + idItem + ", nomeItem=" + nomeItem + ", total=" + total + ", mes=" + mes + ", ano="
                + ano + "]";
    }

    
}
