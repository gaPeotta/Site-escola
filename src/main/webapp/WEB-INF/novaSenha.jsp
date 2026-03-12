<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Redefinir Senha</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/redefinirSenha.css">
</head>
<body>

<div class="novaSenha">
    <h1>Nova senha</h1>

    <%
        String erro = (String) request.getAttribute("erro");
        if(erro != null){
    %>
    <p class="erro"><%=erro%></p>
    <%
        }
    %>

    <form action="${pageContext.request.contextPath}/atualizarSenha" method="post">
        <label>Nova senha</label>
        <input type="password" name="novaSenha" required>
        <label>Confirmar senha</label>
        <input type="password" name="confirmarSenha" required>
        <button type="submit" >Atualizar senha</button>
    </form>

</div>

</body>
</html>