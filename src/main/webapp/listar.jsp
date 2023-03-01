<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, model.GastoVista, model.Pessoa"%>
<%
ArrayList<GastoVista> vista = (ArrayList<GastoVista>) request.getAttribute("listaGastosVista");
Pessoa pessoa = (Pessoa) request.getAttribute("pessoa");
String meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"}; 
int mesAtual = (int) request.getAttribute("mesAtual");
int anoAtual = (int) request.getAttribute("anoAtual");

// filtros
String dataInicial = (String) request.getAttribute("dataInicial");
String dataFinal = (String) request.getAttribute("dataFinal");

%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" type="text/css" href="estilos.css"/>
<title>Listando gastos</title>
</head>
<body>

    <h1>Diário de gastos</h1>
    
    <form action="filtrar" method="get">    
        <fieldset>
            <legend>Filtros..: </legend>        
            <fieldset>  
                <legend>Filtragem mês único:</legend>
            
                <label for="mes-atual">Mês atual: </label>
                <select name="mes-atual" id="mes-atual">
                    
                    <%
                    for (int i = 0, tot = meses.length; i < tot; i++) {             
                                                     
                        if (i+1 == mesAtual) { 
                            %> <option value="<%= i+1 %>" selected> <%= meses[i] %> </option> <%
                        } else {
                            %> <option value="<%= i+1 %>"> <%= meses[i] %> </option> <%
                        }
                                     
                    }            
                    %>                      
                </select>        
                
                <div>
                    <label for="ano-atual">Ano atual: </label>
                    <input type="number" id="ano-atual" name="ano-atual" value="2023"/>
                    
                </div>
                
                <input type="submit" id="filtrar-mes-unico" name="filtrar-mes-unico" value="Filtrar mês único"/>  
                
            </fieldset>  
            
            <span>OU..:</span>
            
            <fieldset>
                <legend>Filtragem intervalo: </legend>
                
                <label for="data-inicial">Data inicial: </label>
                <input type="date" id="data-inicial" name="data-inicial" value="<%= dataInicial%>"/>

                <label for="data-final">Data final: </label>
                <input type="date" id="data-final" name="data-final" value="<%= dataFinal%>"/>            
            
                <input type="submit" id="filtrar-intervalo" name="filtrar-intervalo" value="Filtrar por intervalo"/>  
                
            </fieldset>
                    
        </fieldset>
    </form>
    
    
    <%
    if (request.getAttribute("totalGasto") != null) { %>
        <% double totalGasto = (double) request.getAttribute("totalGasto"); %>
        <p>Renda de <%=pessoa.getNomePessoa() %> em <%= mesAtual %> - <%= anoAtual %>: R$ <%=pessoa.getRendaAtual().getRenda() %></p>
    
        <p>Gastou R$ <%= totalGasto %> em <%= mesAtual %>/<%= anoAtual %></p>
        
        <% double restante = pessoa.getRendaAtual().getRenda() - totalGasto; %>
        
        <% if (restante > 0.0) { %>
            <p>Então, ainda pode gastar <%= restante %></p>
        <%
            
        } else { %>
            <p>Então, não pode gastar mais nada (faltando R$ <%= restante %>)</p>   
        <%    
        }
        %>      

    <%            
    }
    %>
    

    
    <h2>Listagem de gastos</h2>    

    <table>
        <thead>
            <tr>
                <th>Nome do gasto</th>
                <th>Id do gasto</th>
                <th>Dia do gasto</th>
                <th>Loja</th>
                <th>Valor do gasto (R$)</th>
                <th>Quantidade do produto</th>
            </tr>
        </thead>
        <tbody>
            <% for (int i = 0; i < vista.size(); i++) { %>
            <tr><td><%=vista.get(i).getNomeGasto()%></td>
                <td><%=vista.get(i).getIdGasto() %></td>
                <td><%=vista.get(i).getDiaGasto()%></td>
                <td><%=vista.get(i).getLoja().getNome() %></td>
                <td><%=vista.get(i).getValorGasto()%></td>
                <td><%=vista.get(i).getQuantidadeProduto() %></td>
            </tr>
            <%
            }
            %>
        </tbody>

    </table>

    <h2>Adicionar gasto</h2>
    
    <form id="cadastro" action="cadastrar" method="get">
        <label for="nome-pessoa">Nome da pessoa (temp): </label>
        <input type="text" id="nome-pessoa" name="nome-pessoa" value="Vinícius"/>
        
        <label for="nome-produto">Nome do produto: </label>
        <input type="text" id="nome-produto" name="nome-produto" value=""/>
    
        <label for="hora-compra">Hora da compra: </label>
        <input type="datetime-local" id="hora-compra" name="hora-compra" value=""/>
    
        <label for="nome-loja">Nome da loja: </label>
        <input type="text" id="nome-loja" name="nome-loja" value=""/>
    
        <label for="cep-loja">CEP da loja (opcional): </label>
        <input type="text" id="cep-loja" name="cep-loja" value=""/>
    
        <label for="numero-loja">Número de endereço da loja (opcional): </label>
        <input type="text" id="numero-loja" name="numero-loja" value=""/>
        
        <label for="valor-produto">Valor unitário do produto (R$): </label>
        <input type="text" id="valor-produto" name="valor-produto" value=""/>
        
        <label for="quant-produto">Quantidade do produto: </label>
        <input type="number" id="quant-produto" name="quant-produto" value=""/>
        
        
        <input type="submit" value="Registrar compra">
    
    
    </form>
    
    
    

</body>
</html>