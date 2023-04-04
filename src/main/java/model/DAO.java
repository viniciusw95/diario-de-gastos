package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {
    private String driver = "com.mysql.jdbc.Driver";
    private String username = "root";
    private String password = "mysql-vinicius-951";
    private String connectionURL = String.format("jdbc:mysql://localhost:3306/"
            + "despesas?useTimezone=true?serverTimezone=UTC",
            this.username, this.password);
    private final String storedProcedures[] = {"GetTotalItemMes"};
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
    
    public ResultSet getTotalItemMes() {
        
        Connection conn = this.getConnection();
        PreparedStatement ps;
        try {
            ps = conn.prepareStatement("call GetTotalItemMes()");
            ResultSet lista = ps.executeQuery();
            return lista;
        } catch (SQLException e) {
            System.out.println("Erro em getTotalItemMes()..:");
            e.printStackTrace();
        }
        return null;
    }
    
}
