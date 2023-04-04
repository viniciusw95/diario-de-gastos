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
import model.JavaBeans;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = {"/Controller", "/listagem"})
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
		
		DAO conexaoBanco = new DAO();
		ResultSet resumoAnual = conexaoBanco.getTotalItemMes();
		
		ArrayList<JavaBeans> resumo = this.listagemResumida(resumoAnual);
		
		String gson = new Gson().toJson(resumo);
		
		request.setAttribute("gson", gson);
		
		
		
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

	public ArrayList<JavaBeans> listagemResumida(ResultSet resumoAnual) {
	    try {
	        ArrayList<JavaBeans> resumo = new ArrayList<JavaBeans>();
	        JavaBeans itemPago;
	        while (resumoAnual.next()) {                
                itemPago = new JavaBeans(resumoAnual.getInt("cod_item"),
                        resumoAnual.getString("nome_item"),
                        resumoAnual.getDouble("total_item_mes"),
                        resumoAnual.getInt("mes_despesa"),
                        resumoAnual.getInt("ano_despesa"));
                resumo.add(itemPago);                
            }
	        return resumo;
        } catch (SQLException e) {
            System.out.println("Erro em listagemResumida()");
            e.printStackTrace();
        }
	    return null;
	}
	
}
