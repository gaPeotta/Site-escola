    <!DOCTYPE html>
    <html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Login Escolar</title>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Poppgins:wght@300;400;600;700&display=swap" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    </head>
    <body>

        <div class="container">

            <div class="left-side"></div>

            <div class="right-side">

                <div class="form-wrapper">

                    <div class="admin-btn-container">
                        <a href="loginAdm.jsp" class="btn-admin">
                            <i class="fa-regular fa-circle-user"></i> Sou admin</a>
                    </div>

                    <div class="login-card">

                        <div class="tabs">
                            <h2 class="active">Login</h2>
                            <h2><a href="cadastro.jsp" style="text-decoration: none; color: inherit;">Cadastrar</a></h2>
                        </div>

                        <% String erro = (String) request.getAttribute("erro");
                            if (erro != null) {
                                String cor = erro.toLowerCase().contains("incorretos") ? "red" : "green"; %>
                        <p style="color: <%= cor %>"><%= erro %></p>
                        <% } %>

                        <form action="<%=request.getContextPath()%>/loginUsuario" method="post">
                            <div class="input-group">
                                <label>E-mail</label>
                                <input type="email" placeholder="Seunome@email.com" id="usuario" name="usuario">
                            </div>

                            <div class="input-group">
                                <label>Password</label>
                                <input type="password" placeholder="@Password1234" id="senha" name="senha">
                            </div>

                            <div class="role-selector">
                                <label class="radio-box">
                                    <input type="radio" name="tipo" value="professor" required>
                                    <span class="checkmark"></span>
                                    <span class="label-text">Sou professor</span>
                                </label>

                                <label class="radio-box">
                                    <input type="radio" name="tipo" value="aluno">
                                    <span class="checkmark"></span>
                                    <span class="label-text">Sou aluno</span>
                                </label>
                            </div>
                            <button type="submit" class="btn-login">Login</button>

                            <a href="#" class="forgot-link">Esqueceu sua senha?</a>
                        </form>
                    </div>
                </div>
            </div>
        </div>

    </body>
    </html>