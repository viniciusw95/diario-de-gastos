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
			+ "gastos?useTimezone=true?serverTimezone=UTC",
			this.username, this.password);
		
	// Atributos para o método DAO.getId(String ...);
	private String tabela;
	private String colunaPK;
	private String colunaNome;
	
	public DAO() {
        // TODO Auto-generated constructor stub
    }
	
	public String getTabela() {
        return tabela;
    }
	
	public DAO(String tabela, String colunaPK, String colunaNome) {
        super();
        this.tabela = tabela;
        this.colunaPK = colunaPK;
        this.colunaNome = colunaNome;
    }

	public DAO(String tabela) {
        super();
        this.tabela = tabela;
    }
	
    public Connection getConnection() {
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
	
	public ResultSet getGastosVista() {
		//String query = "select * from compra_vista;";
		String query = "select nome_pessoa, produto.id_produto, nome_produto, hora, loja.id_loja, nome_loja, valor_unit_produto, qtd_produto \r\n"
		        + "from compra_vista \r\n"
		        + "inner join pessoa\r\n"
		        + "inner join produto\r\n"
		        + "inner join loja\r\n"
		        + "where pessoa.id_pessoa = compra_vista.id_pessoa\r\n"
		        + "and produto.id_produto = compra_vista.id_produto\r\n"
		        + "and loja.id_loja = compra_vista.id_loja;";
		
		Connection conn = this.getConnection();
		ResultSet resultSet = null;
		try {
			PreparedStatement pst = conn.prepareStatement(query);
			resultSet = pst.executeQuery();
			
		} catch (SQLException e) {
			System.out.println("Erro em getGastosVista():");
			e.printStackTrace();
		}
		return resultSet;
	}	

	
	protected ResultSet executarQuery(String query) {
	    
	    //String query = String.format("select id_produto from produto where nome_produto = '%s';", nomeUnico);
        Connection conn = this.getConnection();
        
        ResultSet resultSet = null;
        try {
            PreparedStatement pst = conn.prepareStatement(query);
            resultSet = pst.executeQuery();
                        
        } catch (Exception e) {
            System.out.println("Erro em DAO.executarQuery():");
            e.printStackTrace();
        }
        return resultSet;
	}
	
	protected int executarUpdate(String query) {
        try {
            Connection conn = this.getConnection();
            PreparedStatement pst = conn.prepareStatement(query);
            int count = pst.executeUpdate();
            System.out.print  ("Método DAO.executarUpdate() teve sucesso: ");
            System.out.println(String.format("%s linhas afetadas", count));
            return count;
        } catch (Exception e) {
            System.out.println("Erro em DAO.executarUpdate(): ");
            System.out.println(e.toString());
        }
        return -1;
	}
	
	public int cadastrar(String nomeRegistro) {
	    
	    //String nomesColunas = this.colunas.toString();
	    //String nomesColunas = Arrays.toString(this.colunas).replace("[", "").replace("]", "");
	    
	    String queryInsert = String.format("insert into %s (%s) values ('%s');",
                this.tabela, this.colunaNome, nomeRegistro);
	    
	    try {
            int idRegistro = this.getId(nomeRegistro);
            boolean registroJaExiste = (idRegistro != -1);
            
            if (registroJaExiste) {
                System.out.println(String.format("Registro %s já existe na tabela %s", nomeRegistro, this.tabela));
                return idRegistro;
            }               
            Connection conn = this.getConnection();
            PreparedStatement pst = conn.prepareStatement(queryInsert);
            int count = pst.executeUpdate();
            // Precisa inserir apenas 1 linha, caso contrário, algum erro aconteceu
            if (count != 1) {
                throw new Exception(String.format("Erro. Inseriu %s de %s", count, nomeRegistro));
            } else {
                System.out.println("Sucesso na inserção de " + nomeRegistro);
                idRegistro = this.getId(nomeRegistro);
                return idRegistro;
            }
            
        } catch (SQLException e) {
            System.out.println("Erro em DAO.cadastrar():");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro em DAO.cadastrar():");
            e.printStackTrace();
        }
        return -1;	   
	}
	
	public int getId(String nomeRegistro) {
        String queryBusca = String.format("select %s from %s where %s = '%s';", 
                this.colunaPK, this.tabela, this.colunaNome, nomeRegistro);
        
        try {
            ResultSet resultSet = this.executarQuery(queryBusca);
            if (this.existeRegistro(resultSet)) {
                int idRegistro = resultSet.getInt(this.colunaPK);
                return idRegistro;
            }
        } catch (Exception e) {
            System.out.println("Erro em DAO.getId(String, ...): ");
            System.out.println(e.toString());
        }
        return -1;
    }
	
	// Informa se existe pelo menos 1 registro neste resultSet
    private boolean existeRegistro(ResultSet resultSet) throws SQLException {
        boolean resposta = resultSet.next();
        return resposta;
    }
	
    public double getRenda(int idPessoa, int mes, int ano) {
        String query = String.format("select * from renda_pessoa "
                + "inner join pessoa "
                + "where pessoa.id_pessoa = '%s' and renda_pessoa.id_pessoa = '%s' "
                + "and mes = '%s' and ano = '%s';", idPessoa, idPessoa, mes, ano);
        ResultSet resultSet = this.executarQuery(query);
        
        double renda = 0.0;
        try {
            if (resultSet.next()) {
                renda = resultSet.getDouble("renda");
            }
        } catch (SQLException e) {
            System.out.println("Erro em DAO.getRenda(): ");
            e.printStackTrace();
        }
        return renda;
                 
    }
    
}
