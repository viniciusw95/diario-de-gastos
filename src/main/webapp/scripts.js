/**
 * qget() author: https://code-boxx.com/
 */

 function qget () {
     
    /* 
    insert into compra_vista 
(id_compra, id_produto, id_pessoa, id_loja, hora, valor_unit_produto, qtd_produto) 
values ('1', '5', '1', '1', '2023-03-01 12:47:00', '3.89', '1');
    
    */ 
    
    // (A) APPEND DATA
    var data = new URLSearchParams();
    data.append("id-compra", ""); // o BD insere sozinho
    data.append("id-produto", ""); // o BD cria/obtém sozinho
    data.append("nome-produto", "Água com gás");
    data.append("id-pessoa", "1"); // TODO: implementar login no site
    data.append("id-loja", ""); // o BD cria/obtém sozinho
    data.append("nome-loja", "Casa do biscoito");
    data.append("hora-compra", "2023-03-04 09:53:00");
    data.append("valor-produto", "3.25");
    data.append("quant-produto", "1");
 
    // (B) URL + REDIRECT
    var url = "http://localhost:8080/diario-de-gastos/cadastrar?" + data.toString();
    location.href = url;
    
}

function imprimir_compras(id_lista) {
    let produto = document.getElementById(id_lista).getElementsByTagName("tr")[0];
    let produtoStr = produto.getElementsByClassName("nome_produto")[0].innerHTML;
    document.write(produtoStr);
    
}
 