package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAO {
    private String driver = "com.mysql.jdbc.Driver";
    private String username = "root";
    private String password = "mysql-vinicius-951";
    private String connectionURL = String.format("jdbc:mysql://localhost:3306/"
            + "despesas?useTimezone=true?serverTimezone=UTC",
            this.username, this.password);
    public DAO() {
        // TODO Auto-generated constructor stub
    }
    
    private Connection getConnection() {
        Connection conn = null;
        
        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(this.connectionURL, this.username,
                    this.password);
            System.out.println("Conectado ao banco com sucesso.");
        } catch (Exception e){
            System.out.println("Erro em getConnection():");
            System.out.println(e.toString());
        } 
        return conn;        
    }
  
    public ResultSet callQuery(String storedProcedureName) {
        Connection conn = this.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("call " + storedProcedureName);
            ResultSet lista = ps.executeQuery();
            return lista;
        } catch (SQLException e) {
            System.out.println("Erro em " + storedProcedureName + "..:");
            e.printStackTrace();
        }
        return null;
    }
    
    public void inserirDespesa(int idUsuario, String descricaoDespesa, ArrayList<ItemDespesa> itens) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = this.getConnection();
            conn.setAutoCommit(false);
            
            // Inserir despesa
            String sqlQuery = "select max(cod_despesa) + 1 as novo_cod_despesa from despesa;";
            pstmt = conn.prepareStatement(sqlQuery);
            
            // Obtendo código da nova despesa 
            rs = pstmt.executeQuery();
            rs.next();
            int codNovaDespesa = rs.getInt("novo_cod_despesa");
            if (codNovaDespesa <= 0) {
                throw new Exception("Não obtive um código para nova despesa.");
            }
            System.out.println("Código da nova despesa: " + codNovaDespesa);
            
            /* Inserindo novo registro de despesa */
            String sqlInsert = "call INSERT_DESPESA(?, ?, ?); ";
            
            pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setInt(1, codNovaDespesa);
            pstmt.setInt(2, idUsuario);
            pstmt.setString(3, descricaoDespesa);
            
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new Exception("Experado inserir 1 registro para despesa, "
                        + "mas foram inseridas: (" + rowsInserted + ")");
            }
            System.out.println("Inserido 1 registro de despesa no banco.");
                      
            // TODO: usar while para todos os itens.
            ItemDespesa item = itens.get(0);
            // Inserindo item no banco, caso ainda não exista:
            sqlInsert = "call INSERT_ITEM(?); ";
            pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setString(1, item.getNomeItem());
            
            int cont = pstmt.executeUpdate();
            if (cont >= 2) {
                throw new Exception("Algum erro ocorreu: mais de uma linha "
                        + "inserida em INSERT_ITEM().");
            }
            
            // Inserindo loja no banco, caso ainda não exista:
            sqlInsert = "call INSERT_LOJA(?); ";
            pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setString(1, item.getNomeLoja());
            
            cont = pstmt.executeUpdate();
            if (cont >= 2) {
                throw new Exception("Algum erro ocorreu: mais de uma linha "
                        + "inserida em INSERT_LOJA().");
            }
            // Obtendo código da loja no banco:
            sqlQuery = "select cod_loja\r\n"
                    + "from loja\r\n"
                    + "where nome_loja = ?;";
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, item.getNomeLoja());
            rs = pstmt.executeQuery();
            rs.next();
            int codLoja = rs.getInt("cod_loja");
            if (codLoja == 0) {
                throw new Exception("Erro ao obter código da loja (obteve-se cod_loja = 0)");
            }
            System.out.println("Código da loja obtido: " + codLoja);
            
            // Obtendo código do item no banco:
            sqlQuery = "select cod_item\r\n"
                    + "from item\r\n"
                    + "where nome_item = ?;";
            pstmt = conn.prepareStatement(sqlQuery);
            pstmt.setString(1, item.getNomeItem());
            rs = pstmt.executeQuery();
            rs.next();
            int codItem = rs.getInt("cod_item");
            if (codItem == 0) {
                throw new Exception("Erro ao obter código da loja (obteve-se cod_item = 0)");
            }
            System.out.println("Código do item obtido: " + codItem);
            
            /* Inserindo item da despesa! */
            // Obtendo novo código para item da despesa:
            sqlQuery = "select max(id_item_despesa) + 1 as novo_id_item_despesa from item_despesa";
            pstmt = conn.prepareStatement(sqlQuery);
            rs = pstmt.executeQuery();
            rs.next();
            int codNovoItemDespesa = rs.getInt("novo_id_item_despesa");
            if (codNovoItemDespesa == 0) {
                throw new Exception("Erro ao gerar novo código para item da "
                        + "despesa (obteve-se novo_id_item_despesa = 0)");
            }
            
            // Inserindo novo item da despesa novo banco:
            sqlInsert = "call INSERT_ITEM_DESPESA(?, ?, ?, ?, ?, ?, ?);";
            pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setInt(1, codNovoItemDespesa);
            pstmt.setInt(2, codItem);
            pstmt.setInt(3, codNovaDespesa);
            pstmt.setString(4, item.getDataDespesa());
            pstmt.setInt(5, codLoja);
            pstmt.setInt(6, item.getQtd());
            pstmt.setDouble(7, item.getValorUnit());
            
            rowsInserted = pstmt.executeUpdate();
            if (rowsInserted != 1) {
                throw new Exception("Experado inserir 1 registro para item_despesa, "
                        + "mas foram inseridas: (" + rowsInserted + ")");
            }
            
            System.out.println("Transação executada com sucesso até o fim. Commitando alterações.");
            
            conn.commit();
            System.out.println("Sucesso no commit!");
            
        } catch (SQLException e) {            
            try {
                if (conn != null) {
                    System.out.println("Revertendo alterações (rollback):");
                    conn.rollback();
                }
            } catch (SQLException e1) {
                System.out.println(e1.getMessage());
            }
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.toString());
            
        }         
        
        
        return;
        
    }
    
}
