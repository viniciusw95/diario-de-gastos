package model.tabelas;

import java.sql.ResultSet;
import java.util.ArrayList;

import model.JavaBeans;

public abstract class Tabela extends ArrayList<JavaBeans> {

    private static final long serialVersionUID = 1L;

    @Override 
    public boolean add(JavaBeans e) {
        // TODO Auto-generated method stub
        return super.add(e);
    }
    public abstract boolean add(ResultSet linha);
    @Override
    public String toString() {
        return super.toString();
    }
    
    
}
