<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Escolar</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="css/cadastro.css">
</head>
<body>

<div class="container">
    <div class="left-side"></div>
    <div class="right-side">
        <div class="form-wrapper">
            <div class="admin-btn-container">
                <button class="btn-admin">
                    <i class="fa-regular fa-circle-user"></i> Sou admin
                </button>
            </div>

            <div class="login-card">
                <div class="tabs">
                    <h2><a href="index.jsp" style="text-decoration: none; color: inherit;">Login</a></h2>
                    <h2 class="active">Cadastrar</h2>
                </div>

                <p class="description-text">
                    Preencha seu CPF, assim poderemos identificar a sua matrícula...
                </p>

                <% String erro = (String) request.getAttribute("erro"); %>
                <% if (erro != null) { %>
                <p style="color: red;"><%= erro %></p>
                <% } %>

                <form action="${pageContext.request.contextPath}/precadastro" method="post">
                    <div class="input-group">
                        <label>CPF</label>
                        <input type="text" name="cpf" placeholder="000.000.000-00" maxlength="14" required>
                    </div>
                    <button type="submit" class="btn-login">Cadastrar</button>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>