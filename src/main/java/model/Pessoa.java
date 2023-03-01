package model;

import java.util.ArrayList;

public class Pessoa {
    private int idPessoa;
    private String nomePessoa;
    Renda rendaAtual = new Renda();    
    
    public int getIdPessoa() {
        return idPessoa;
    }
    public void setIdPessoa(int idPessoa) {
        this.idPessoa = idPessoa;
    }
    public String getNomePessoa() {
        return nomePessoa;
    }
    public void setNomePessoa(String nomePessoa) {
        this.nomePessoa = nomePessoa;
    }
    public Renda getRendaAtual() {
        return rendaAtual;
    }
    public void setRendaAtual(Renda rendaAtual) {
        this.rendaAtual = rendaAtual;
    }
    
    
    
    
}
