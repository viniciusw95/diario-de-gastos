<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Novo cadastro</title>
</head>
<body>
    <!-- TODO: retirar os <br> (usar CSS) -->
    <form method="get" action="cadastro">
        <fieldset>
            <label for="descricao">Descrição: </label>
            <input type="text" placeholder="Ex: Compras final de semana."
            maxlength="60" size="60" name="descricao" id="descricao">
            <br>
            
            
            <br>
            <fieldset>
                <label for="item">Nome do produto ou da conta: </label>
                <input type="text" name="item" id="item">
                <br>
                <label for="categoria">Categoria: </label>
                <input type="text" name="categoria" id="categoria">
                <br>
                <label for="qtd-item">Quantidade: </label>
                <input type="number" name="qtd-item" id="qtd-item">
                <br>
                <label for="preco-unit">Preço unitário: R$ </label>
                <input type="number" name="preco-unit" id="preco-unit">            
                <br>
                <label for="preco-final">Preço total: R$ </label>
                <input type="number" name="preco-final" id="preco-final"
                 value="" disabled>            
                <br>
                <!-- 
                <label for="loja">Loja: </label>
                <input type="text" maxlength="60" size="60" name="loja" id="loja">
                <br>                
                 -->
                <label for="data">Data: </label>
                <input type="date" name="data" id="data">
                <br>
                <label for="pgto">Forma de pagamento:</label>
                <select name="pgto" id="pgto">
                    <optgroup label="À vista"></optgroup>
                    <option>Débito</option>
                    <option>Dinheiro</option>
                    <option>PIX</option>
                </select>
                <label for="valor-vista">Valor pago à vista: R$ </label>
                <input type="number" name="valor-vista" id="valor-vista" value="">
                <br>
                <!-- TODO: adicionar local da loja -->                               
                <button>+1 produto</button>
            
            </fieldset>        
            
            <input type="submit" value="Concluir cadastro"/>
                
        </fieldset>
    </form>
</body>
</html>