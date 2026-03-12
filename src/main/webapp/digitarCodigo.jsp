<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <title>Digite o Código</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/redefinirSenha.css">
</head>
<body>
<div class="codigoEmail">
    <h1>Verificação</h1>
    <p>Digite o código enviado ao seu email</p>
    <form action="${pageContext.request.contextPath}/digitarCodigo" method="post">
        <input type="hidden" name="email" value="${sessionScope.email}">
        <input type="text" name="codigo" placeholder="Código" required>
        <button type="submit">Verificar</button>

    </form>

</div>
</body>
</html>