package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import model.DAO;
import model.ItemDespesa;
import model.JavaBeans;
import model.tabelas.ItensAno;
import model.tabelas.ResumoAnual;
import model.tabelas.Tabela;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/Controller", "/listagem", "/cadastro"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String action = request.getServletPath();
		response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println(action);
		
		if (action.equals("/cadastro")) {
		    
		    DAO conexaoBanco = new DAO();
		    // TODO: fazer integração com a página cadastro.jsp
		    int idUsuario = 1;
		    String descricaoDespesa = "Despesa inserida pelo Eclipse 7.0!";
		    
		    ItemDespesa item1 = new ItemDespesa("Dev de software7.0", "Casa do pai", "2023-04-07",
		            3, 20.50, "DEBITO");
		    ItemDespesa item2 = new ItemDespesa("Dev de software2", "Casa da mamusca", "2023-04-07",
                    3, 20.5, "DEBITO");
		    ArrayList<ItemDespesa> itens = new ArrayList<ItemDespesa>();
		    itens.add(item1);
		    itens.add(item2);
		    
		    /* ------------------- */
		    
		    conexaoBanco.inserirDespesa(idUsuario, descricaoDespesa, itens);
		    
		    System.out.println("funcionou");
		    return;
		}
		
		
		DAO conexaoBanco = new DAO();
		
		ResultSet resumoAnual = conexaoBanco.callQuery("GetTotalItemMes()");
		ResultSet itensAno = conexaoBanco.callQuery("GetNomesItensResumo()");
		
		ResumoAnual resumo = new ResumoAnual();
		this.listagemResumida(resumoAnual, resumo);
		
		ItensAno nomesDespesas = new ItensAno();
		this.listagemResumida(itensAno, nomesDespesas);		
		
		
		String resumoJs = new Gson().toJson(resumo);
		String nomesDespesasJs = new Gson().toJson(nomesDespesas);
		
		request.setAttribute("resumo", resumoJs);
		request.setAttribute("nomesDespesas", nomesDespesasJs);
				
		
		RequestDispatcher rd = request.getRequestDispatcher("listar.jsp");            
        rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	public void listagemResumida(ResultSet resumoAnual, Tabela destino) {
	    try {
	        while (resumoAnual.next()) {                
                destino.add(resumoAnual);
            }
        } catch (SQLException e) {
            System.out.println("Erro em listagemResumida()..:");
            e.printStackTrace();
        }
	}
	
}
