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
        <label>Descrição: </label>
        <input type="text" placeholder="Ex: Compras final de semana."
        maxlength="60" size="60">
        <br>
        <label>Loja (opcional): </label>
        <input type="text" maxlength="60" size="60">
        <br>
        <label>Data: </label>
        <input type="date">
        <br>
        <fieldset>
            <label>Nome do produto/conta: </label>
            <input type="text">
            <br>
            <label>Categoria (opcional): </label>
            <input type="text">
            <br>
            <label>Quantidade: </label>
            <input type="number">
            <br>
            <label>Preço: R$ </label>
            <input type="number">            
            <br>
            <label>Preço total: R$ </label>
            <input type="number" value="" disabled>            
            <br>               
            <button>+1 produto/conta</button>
        
        </fieldset>        
        
        
    
    </form>
</body>
</html>