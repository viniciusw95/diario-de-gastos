package model.tabelas;

import java.sql.ResultSet;
import java.sql.SQLException;

import model.JavaBeans;

public class ResumoAnual extends Tabela {

    private static final long serialVersionUID = 1L;

    public boolean add(ResultSet linhaAtual) {
        JavaBeans itemPago;
        try {
            itemPago = new JavaBeans(linhaAtual.getInt("cod_item"),
                    linhaAtual.getString("nome_item"),
                    linhaAtual.getDouble("total_item_mes"),
                    linhaAtual.getInt("mes_despesa"),
                    linhaAtual.getInt("ano_despesa"));
            return super.add(itemPago);
        } catch (SQLException e) {
            System.out.println("Erro em ResumoAnual.add()..:");
            e.printStackTrace();
        }                        
        return false;
    }
}
