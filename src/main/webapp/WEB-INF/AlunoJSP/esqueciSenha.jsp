<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="pt-br">

<head>
    <meta charset="UTF-8">
    <title>Recuperar Senha</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/redefinirSenha.css">
</head>

<body>
<div class="mandarEmail">
    <h2>Recuperar Senha</h2>
    <p>Digite seu email para receber o código de recuperação</p>
    <form action="${pageContext.request.contextPath}/recuperarSenha" method="post">
        <div class="input-group">
            <label for="email">Email</label>
            <input
                    type="email"
                    id="email"
                    name="email"
                    placeholder="Digite seu email"
                    required
            >
        </div>
        <button type="submit">
            Enviar Código
        </button>
    </form>

    <div class="voltar-login">
        <a href="${pageContext.request.contextPath}/loginAluno.jsp" class="botaoVolta">
            Voltar para o login
        </a>
    </div>
</div>

</body>
</html>
