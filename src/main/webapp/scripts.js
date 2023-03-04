/**
 * qget() author: https://code-boxx.com/
 */

function qget(id_tabela_compras) {
     
    /* 
    insert into compra_vista 
(id_compra, id_produto, id_pessoa, id_loja, hora, valor_unit_produto, qtd_produto) 
values ('1', '5', '1', '1', '2023-03-01 12:47:00', '3.89', '1');
    
    */ 
    
    // (A) APPEND DATA
    /*
    var data = new URLSearchParams();
    
    data.append("id-compra", id_compra); // o BD insere sozinho
    data.append("id-produto", id_produto); // o BD cria/obtém sozinho
    data.append("nome-produto", nome_produto);
    data.append("id-pessoa", id_pessoa); // TODO: implementar login no site
    data.append("id-loja", id_loja); // o BD cria/obtém sozinho
    data.append("nome-loja", nome_loja);
    data.append("hora-compra", hora_compra);
    data.append("valor-produto", valor_produto);
    data.append("quant-produto", quant_produto);
    */
    // (B) URL + REDIRECT
    
    let produtos = document.getElementById(id_tabela_compras).getElementsByTagName("tr");
    let qtd_compras = produtos.length;
    const arrProdutos = [];
    for (let i = 0; i < qtd_compras; i++) {
        let p = produtos[i];
        arrProdutos.push(
            {id_compra: p.querySelector(".id-compra").innerHTML,
             id_produto: p.querySelector(".id-produto").innerHTML,
             nome_produto: p.querySelector(".nome-produto").innerHTML,
             hora_compra: p.querySelector(".hora-compra").innerHTML,
             nome_loja: p.querySelector(".nome-loja").innerHTML,
             valor_produto: p.querySelector(".valor-produto").innerHTML,
             quant_produto: p.querySelector(".quant-produto").innerHTML           
             });
        console.log(arrProdutos[i].id_compra + " | " + arrProdutos[i].id_produto
        + " | " + arrProdutos[i].nome_produto + " | " + arrProdutos[i].hora_compra
        + " | " + arrProdutos[i].nome_loja + " | " + arrProdutos[i].valor_produto
        + " | " + arrProdutos[i].quant_produto);
        //console.log(JSON.stringify(arrProdutos[i]));
        
    }   
    let jsonProdutos = JSON.stringify(arrProdutos);
    let url = "http://localhost:8080/diario-de-gastos/salvar-edicao?" + jsonProdutos;   
    window.location.href = url; 
    
    
}

function imprimir_compras(id_lista) {
    let produto = document.getElementById(id_lista).getElementsByTagName("tr")[0];
    let produtoStr = produto.getElementsByClassName("nome_produto")[0].innerHTML;
    document.write(produtoStr);
    
}


 