package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.GastoVista;
import model.Loja;
import model.Pessoa;
import model.Renda;
import model.DAO;
import model.DAOCompraVista;
import model.DAOLoja;
import model.DAOProduto;

/**
 * Servlet implementation class Controller
 */
@WebServlet(urlPatterns = { "/Controller", "/listar", "/cadastrar", "/filtrar" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// Primeiro acesso (a partir de index.html)
	int mesAtual = 1; // TODO: obter do Google (pref) | sistema (fallback)
    int anoAtual = 2023;
    
    // Filtros (primeiro acesso a partir de index.html E fallback)
    final String dataInicial = "2023-01-01";  // TODO: obter ano atual do Google (pref) | sistema (fallback)
    final String dataFinal = "2023-12-31";  // TODO: ...
    
	
	/**
	 * Default constructor.
	 */
	public Controller() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(request.getServletPath());
		
		Pessoa pessoa = new Pessoa();
        pessoa.setIdPessoa(1);
        pessoa.setNomePessoa("Vinícius");
               
        /*
        int mesAtual = 2;
        int anoAtual = 2023;
        DAO dao = new DAO();
        double rendaAtual = dao.getRenda(pessoa.getIdPessoa(), mesAtual, anoAtual);        
        Renda renda = new Renda(mesAtual, anoAtual, rendaAtual);        
        pessoa.setRendaAtual(renda);
		*/
        
        //int mesAtual = 1; // TODO: obter do Sistema/Google (pref).
        //int anoAtual = 2023;
        
		String acao = request.getServletPath();
		
		// filtros: começam com valor padrão (= anoAtual).
		String dataInicial = this.dataInicial;
		String dataFinal = this.dataFinal; 
		
		ArrayList<GastoVista> gastosVista = null;
		if (acao.equals("/listar")) {
		    // Primeiro acesso (a partir de index.html)
		    //mesAtual = 2; // TODO: obter do Sistema/Google (pref).
		    //anoAtual = 2023;
		    
	        DAO dao = new DAO();
	        double rendaAtual = dao.getRenda(pessoa.getIdPessoa(), mesAtual, anoAtual);        
	        Renda renda = new Renda(mesAtual, anoAtual, rendaAtual);
	        pessoa.setRendaAtual(renda);
	        
	        gastosVista = listarGastosAVista(dataInicial, dataFinal);
	        		   
		} else if (acao.equals("/filtrar")) {		    		    
		    
		    String sMesAtual = request.getParameter("mes-atual"); 
		    String sAnoAtual = request.getParameter("ano-atual");
		    
		    if (!sMesAtual.equals("") || !sAnoAtual.equals("")) {
		        // Só ira atualizar o ano se os campos estiver corretamente preenchidos.
		        this.mesAtual = Integer.parseInt(sMesAtual);
		        this.anoAtual = Integer.parseInt(sAnoAtual);
		    }
		    
		    //mesAtual = Integer.parseInt(request.getParameter("mes-atual"));
            //anoAtual = Integer.parseInt(request.getParameter("ano-atual"));                       
            
            dataInicial = request.getParameter("data-inicial");
            dataFinal = request.getParameter("data-final"); 

            if (dataInicial.equals("") || dataFinal.equals("")) {
                // vai usar uma intervalo padrão (início até o fim do ano atual) 
                dataInicial = this.dataInicial;
                dataFinal = this.dataFinal;
            }
 
            DAO dao = new DAO();
            double rendaAtual = dao.getRenda(pessoa.getIdPessoa(), mesAtual, anoAtual);            
            
            Renda renda = new Renda(mesAtual, anoAtual, rendaAtual);
            pessoa.setRendaAtual(renda);
            
            
            if (request.getParameter("filtrar-mes-unico") != null) {
                //dataInicial = String.format("%s-%s-__", anoAtual, mesAtual); 
                //dataFinal = String.format("%s-%s-__", anoAtual, mesAtual);                
                gastosVista = listarGastosAVista(mesAtual, anoAtual);
                
                double totalGasto = 0.0;
                for (int i = 0, qtdGastos = gastosVista.size(); i < qtdGastos; i++) {
                    totalGasto += gastosVista.get(i).getValorGasto();
                }
                
                request.setAttribute("totalGasto", totalGasto);
                
            } else {
                gastosVista = listarGastosAVista(dataInicial, dataFinal);
            }
	
		} else if (acao.equals("/cadastrar")) {
		    System.out.println("Cadastrando compra..:");
		    cadastrarCompraVista(request, response);		  

		    gastosVista = listarGastosAVista(dataInicial, dataFinal);
		}
		
		System.out.println("Mês inicial do filtro: " + dataInicial);
        System.out.println("Ano inicial do filtro: " + dataFinal);
        
		// Voltando para página de listagem.
        //ArrayList<GastoVista> gastosVista = listarGastosAVista(dataInicial, dataFinal);
        request.setAttribute("listaGastosVista", gastosVista);
        request.setAttribute("pessoa", pessoa);
        request.setAttribute("mesAtual", mesAtual);
        request.setAttribute("anoAtual", anoAtual);
        request.setAttribute("dataInicial", dataInicial);
        request.setAttribute("dataFinal", dataFinal);
        
        RequestDispatcher rd = request.getRequestDispatcher("listar.jsp");            
        rd.forward(request, response);
		
		
	}
 
	public ArrayList<GastoVista> listarGastosAVista() {
		DAO dao = new DAO();
		ResultSet resultSet = dao.getGastosVista();
		ArrayList<GastoVista> lista = new ArrayList<GastoVista>();
		try { // TODO: substituir esse trecho por toArrayList(ResultSet);
			while (resultSet.next()) {
				GastoVista gastoVista = new GastoVista();
				Loja loja = new Loja();
				loja.setId(resultSet.getInt("id_loja"));
                loja.setNome(resultSet.getString("nome_loja"));
                
				gastoVista.setIdGasto(resultSet.getInt("id_produto"));
				gastoVista.setNomeGasto(resultSet.getString("nome_produto"));
				gastoVista.setDiaGasto(resultSet.getString("hora"));
				gastoVista.setValorGasto(resultSet.getDouble("valor_unit_produto"));
				gastoVista.setQuantidadeProduto(resultSet.getInt("qtd_produto"));
				gastoVista.setLoja(loja);				
											
				lista.add(gastoVista);
			}
		} catch (SQLException e) {
			System.out.println("Erro em listarGastosAVista():");
			e.printStackTrace();
		}
		return lista;
	}
	
	public ArrayList<GastoVista> listarGastosAVista(String dataInicial, String dataFinal) {
	    DAOCompraVista dao = new DAOCompraVista();
        ResultSet resultSet = dao.getGastosVista(dataInicial, dataFinal);                
        
        ArrayList<GastoVista> gastosFiltrados = toArrayList(resultSet);
        return gastosFiltrados;
	}
	
	public ArrayList<GastoVista> listarGastosAVista(int mesAtual, int anoAtual) {
        DAOCompraVista dao = new DAOCompraVista();
        ResultSet resultSet = dao.getGastosVista(mesAtual, anoAtual);                
        
        ArrayList<GastoVista> gastosFiltrados = toArrayList(resultSet);
        return gastosFiltrados;
    }
	
	public boolean cadastrarCompraVista(HttpServletRequest request, HttpServletResponse response) {
	    
	    DAOProduto daoProduto = new DAOProduto();
	    DAOLoja daoLoja = new DAOLoja();
	    
	    GastoVista compraVista = new GastoVista();
	    Loja loja = new Loja();
	    Pessoa pessoa = new Pessoa();
	    
        pessoa.setIdPessoa(1); // TODO: implementar depois de adicionar login ao site
	    
        compraVista.setPessoa(pessoa);
        
	    int idProduto = daoProduto.cadastrar(request.getParameter("nome-produto"));	
	    compraVista.setIdGasto(idProduto);
	    compraVista.setDiaGasto(request.getParameter("hora-compra"));
	    
	    int idLoja = daoLoja.cadastrar(request.getParameter("nome-loja"));
	    
        loja.setId(idLoja);
        loja.setNome(request.getParameter("nome-loja"));
	    
        compraVista.setLoja(loja);
        
	    compraVista.setNomeGasto(request.getParameter("nome-produto"));	    
	    
	    compraVista.setValorGasto(Double.parseDouble(request.getParameter("valor-produto")));
	    compraVista.setQuantidadeProduto(Integer.parseInt(request.getParameter("quant-produto")));
	    	    
	    System.out.println(pessoa.getIdPessoa());
	    System.out.println(compraVista.getIdGasto());
	    System.out.println(compraVista.getDiaGasto());
	    System.out.println(compraVista.getNomeGasto());
	    System.out.println(compraVista.getValorGasto());
	    System.out.println(compraVista.getQuantidadeProduto());
	    System.out.println(loja.getNome());

	    //String colunas[] = {"a", "b", "c"};
	    //System.out.println(Arrays.toString(colunas).replace("[", "").replace("]", ""));
	    
	    DAOCompraVista daoCompraVista = new DAOCompraVista();
	    
	    String valores[] = {Integer.toString(pessoa.getIdPessoa()), 
	            Integer.toString(compraVista.getIdGasto()),
	            compraVista.getDiaGasto(),
	            Integer.toString(loja.getId()),
	            Double.toString(compraVista.getValorGasto()),
	            Integer.toString(compraVista.getQuantidadeProduto())	            
	            };
	    daoCompraVista.cadastrar(valores);
	    
	    return false;
	}
	
	public ArrayList<GastoVista> toArrayList(ResultSet resultSet) {
	    ArrayList<GastoVista> lista = new ArrayList<GastoVista>();
	    try {
            while (resultSet.next()) {
                GastoVista gastoVista = new GastoVista();
                Loja loja = new Loja();
                loja.setId(resultSet.getInt("id_loja"));
                loja.setNome(resultSet.getString("nome_loja"));
                
                gastoVista.setIdGasto(resultSet.getInt("id_produto"));
                gastoVista.setNomeGasto(resultSet.getString("nome_produto"));
                gastoVista.setDiaGasto(resultSet.getString("hora"));
                gastoVista.setValorGasto(resultSet.getDouble("valor_unit_produto"));
                gastoVista.setQuantidadeProduto(resultSet.getInt("qtd_produto"));
                gastoVista.setLoja(loja);               
                                            
                lista.add(gastoVista);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            System.out.println("Erro em Controller.toArrayList(): ");
            e.printStackTrace();
        }
	    return lista;
	}
	
}
