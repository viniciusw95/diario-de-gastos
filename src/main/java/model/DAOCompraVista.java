package model;

import java.sql.ResultSet;
import java.util.Arrays;

public class DAOCompraVista extends DAO {

    private String colunas[] = {"id_pessoa", "id_produto", "hora", "id_loja", 
            "valor_unit_produto", "qtd_produto"};
    
    public DAOCompraVista() {
        super("compra_vista");
    }
    
    public void cadastrar(String valores[]) {
        for (int i = 0, totValores = valores.length; i < totValores; i++) {
            valores[i] = String.format("'%s'", valores[i]);
        }
        
        String valoresSemColchetes = Arrays.toString(valores).replace("[", "");
        valoresSemColchetes = valoresSemColchetes.replace("]", "");
        
        String colunasSemColchetes = Arrays.toString(this.colunas).replace("[", "");        
        colunasSemColchetes = colunasSemColchetes.replace("]", "");                  
               
        String queryInserir = String.format("insert into %s (%s) values (%s)", 
                this.getTabela(), colunasSemColchetes, valoresSemColchetes);
        
        System.out.println(queryInserir);
        
        super.executarUpdate(queryInserir);
        
    }
    
    public ResultSet getGastosVista(String dataInicial, String dataFinal) {
        String query = 
                String.format(
                "select id_compra, nome_pessoa, produto.id_produto, nome_produto, hora, loja.id_loja, nome_loja, valor_unit_produto, qtd_produto \r\n"
                + "from compra_vista \r\n"
                + "inner join pessoa\r\n"
                + "inner join produto\r\n"
                + "inner join loja\r\n"
                + "where pessoa.id_pessoa = compra_vista.id_pessoa\r\n"
                + "and produto.id_produto = compra_vista.id_produto\r\n"
                + "and loja.id_loja = compra_vista.id_loja "
                + "and (hora between '%s 00:00:00' and '%s 23:59:59');", dataInicial, dataFinal);
        ResultSet resultSet = super.executarQuery(query);
        return resultSet;
    }
    
    public ResultSet getGastosVista(int mesAtual, int anoAtual) {
        String query = 
                String.format(
                "select id_compra, nome_pessoa, produto.id_produto, nome_produto, hora, loja.id_loja, nome_loja, valor_unit_produto, qtd_produto \r\n"
                + "from compra_vista \r\n"
                + "inner join pessoa\r\n"
                + "inner join produto\r\n"
                + "inner join loja\r\n"
                + "where pessoa.id_pessoa = compra_vista.id_pessoa\r\n"
                + "and produto.id_produto = compra_vista.id_produto\r\n"
                + "and loja.id_loja = compra_vista.id_loja "
                + "and (hora LIKE '%s-%02d-__ %s');", anoAtual, mesAtual, "%");
        
        System.out.println(query);
        ResultSet resultSet = super.executarQuery(query);
        return resultSet;
    }


}
