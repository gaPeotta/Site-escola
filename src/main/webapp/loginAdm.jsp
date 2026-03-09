<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login - Nexus</title>
    <style>
        /* --- RESET E GERAL --- */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Poppins', sans-serif;
        }

        body, html {
            height: 100%;
            width: 100%;
            background-image: url('${pageContext.request.contextPath}/img/efeito-verde.png');
            background-size: cover;
            background-position: center;
            background-repeat: no-repeat;
            background-attachment: fixed;
        }

        .container {
            display: flex;
            height: 100vh;
            width: 100%;
        }

        /* --- LADO ESQUERDO --- */
        .left-side {
            width: 55%;
            height: 100%;
            /* CONFIGURAÇÃO DA IMAGEM */
            background-image: url('${pageContext.request.contextPath}/img/boas-vindaas.png');
            background-size: cover;
            background-position: left center;
            background-repeat: no-repeat;
        }

        /* --- LADO DIREITO (LOGIN) --- */
        .right-side {
            width: 45%;
            display: flex;
            justify-content: center;
            align-items: center;
            padding-right: 100px;
        }

        /* Container para centralizar verticalmente o conteúdo da direita */
        .form-wrapper {
            width: 100%;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        /* Card Cinza do Formulário */
        .login-card {
            background-color: #E0EBE2;
            padding: 40px 50px;
            border-radius: 40px;
            width: 100%;
            max-width: 420px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.05);
        }

        /* Inputs */
        .input-group {
            margin-bottom: 20px;
        }

        .input-group label {
            display: block;
            color: #666;
            font-size: 0.9rem;
            margin-bottom: 8px;
            font-weight: 500;
        }

        .input-group input {
            width: 100%;
            padding: 15px;
            border-radius: 12px;
            border: 1px solid #9db5aa;
            background-color: #bccbc3;
            color: #333;
            font-size: 1rem;
            outline: none;
            transition: 0.3s;
        }

        .input-group input::placeholder {
            color: #f0f0f0;
        }

        .input-group input:focus {
            background-color: #fff;
            border-color: #1b5e46;
        }

        /* Botão Login Principal */
        .btn-login {
            width: 100%;
            padding: 15px;
            background-color: #198754;
            color: #fff;
            border: none;
            border-radius: 25px;
            font-size: 1.2rem;
            font-weight: 700;
            cursor: pointer;
            transition: 0.3s;
            margin-bottom: 20px;
            margin-top: 10px;
            box-shadow: 0 5px 15px rgba(25, 135, 84, 0.3);
        }

        .btn-login:hover {
            background-color: #146c43;
        }

        .forgot-link {
            display: block;
            text-align: center;
            color: #198754;
            font-weight: 600;
            text-decoration: underline;
            font-size: 0.9rem;
        }

        /* Responsivo para Celular */
        @media (max-width: 900px) {
            .container {
                flex-direction: column;
                height: auto;
            }
            .left-side {
                width: 100%;
                height: 250px;
                background-position: center;
                margin-right: 0;
            }
            .right-side {
                width: 100%;
                padding: 40px 20px;
            }
        }
    </style>
</head>
<body>

<div class="container">

    <div class="left-side"></div>

    <div class="right-side">
        <div class="form-wrapper">

            <div class="login-card">
                <form action="<%=request.getContextPath()%>/Login-Adm" method="post">

                    <div class="input-group">
                        <label>E-mail</label>
                        <input type="email" placeholder="Seunome@email.com" id="usuario" name="usuario" required>
                    </div>

                    <div class="input-group">
                        <label>Password</label>
                        <input type="password" placeholder="Sua senha" id="senha" name="senha" required>
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