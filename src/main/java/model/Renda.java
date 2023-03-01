package model;

public class Renda {
    private int mes;
    private int ano;
    private double renda;
    
    public Renda() {
        
    }
    
    public Renda(int mes, int ano, double renda) {
        this.mes = mes;
        this.ano = ano;
        this.renda = renda;
    }    
    public int getMes() {
        return mes;
    }
    public int getAno() {
        return ano;
    }
    public double getRenda() {
        return renda;
    }
    
}
