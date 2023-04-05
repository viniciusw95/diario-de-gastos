<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, model.JavaBeans"%>    
<% String resumo = (String) request.getAttribute("resumo"); %>
<% String nomesDespesas = (String) request.getAttribute("nomesDespesas"); %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Início</title>
</head>
<body>
    Listagem
    
    <table>
        <tr>
            <th>Listagem</th>
            <th>- Resumo anual</th>
        </tr>
        <tr>
            <th>Item / Mês </th>
            <th id="mes-1">Jan</th>
            <th id="mes-2">Fev</th>
            <th id="mes-3">Mar</th>
            <th id="mes-4">Abr</th>
            <th id="mes-5">Mai</th>
            <th id="mes-6">Jun</th>
            <th id="mes-7">Jul</th>
            <th id="mes-8">Ago</th>
            <th id="mes-9">Set</th>
            <th id="mes-10">Out</th>
            <th id="mes-11">Nov</th>
            <th id="mes-12">Dez</th>            
        </tr>
        <tbody id="corpo-resumo">
        </tbody> 
        
    
    </table>
    
    <script>
        let texto = '<%= resumo %>';
        let nomes = '<%= nomesDespesas %>';
        const itens = JSON.parse(texto);
        const nomesDespesas = JSON.parse(nomes);
        
        let total = itens.length; 
        let totalNomes = nomesDespesas.length;
        
        let corpo = document.getElementById("corpo-resumo");
        let i = 0;
        let j = 0;
        // Adiciona nomes dos itens à tabela
        while (i < totalNomes) {
        	
        	let linha = document.createElement("tr");
        	corpo.appendChild(linha);
        	
        	let nomeItem = document.createElement("th");
        	nomeItem.id = nomesDespesas[i].idItem;
        	nomeItem.innerHTML = nomesDespesas[i].nomeItem;
        	linha.append(nomeItem);
        	
        	for (let j = 1; j <= 12; j++) {
                let dado = document.createElement("td");
                dado.headers = "item-" + nomeItem.id + " mes-" + j;
                linha.appendChild(dado);                  
            }
        	
            i++;
        }           
        
        /* Adiciona valor de cada item à tabela */
        j = 0;
        while (j < total) {
        	corpo.querySelector('td[headers="item-' + itens[j].idItem 
        		+ ' mes-' + itens[j].mes + '"]').innerHTML = itens[j].total;
        	j++;
        }            
        
    </script>
    
</body>
</html>