<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, model.JavaBeans"%>    
<% String resumo = (String) request.getAttribute("gson"); %>    
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
            <th>Jan</th>
            <th>Fev</th>
            <th>Mar</th>
            <th>Abr</th>
            <th>Mai</th>
            <th>Jun</th>
            <th>Jul</th>
            <th>Ago</th>
            <th>Set</th>
            <th>Out</th>
            <th>Nov</th>
            <th>Dez</th>            
        </tr>
        <tbody id="corpo-resumo">
            <tr></tr>
        </tbody> 
        
    
    </table>
    
    <script>
        let texto = '<%= resumo %>';
        const itens = JSON.parse(texto);
        
        let total = itens.length; 
        let corpo = document.getElementById("corpo-resumo");
        let i = 0;
        let item_anterior = "";
        while (i < total) {
        	let item = document.getElementById("coditem-" + itens[i].idItem);
        	
        	if (item === null) {
        		item = document.createElement("tr");
        		item.id = "coditem-" + itens[i].idItem;
                let item_nome = document.createElement("td");
                item_nome.innerHTML = itens[i].nomeItem;
                item.appendChild(item_nome);
                corpo.appendChild(item);
                // adiciona as células 'Mês' desse item
                for (let j = 1; j <= 12; j++) {
                    let valor = document.createElement("td");
                    valor.className = "mes-" + j;
                    item.appendChild(valor);                  
                }
        	}
        	let valor = item.querySelector(".mes-" + itens[i].mes);
            
            valor.innerHTML = itens[i].total;

        	i++;
        }
        
        
    </script>
    
</body>
</html>