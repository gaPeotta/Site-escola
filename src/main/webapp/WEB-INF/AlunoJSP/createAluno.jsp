<%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 04/02/2026
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/css/tabelas.css">
<html>
<head>
    <title>Create aluno</title>

</head>
<body>
<div class="container">
    <div class="left-side"></div>

    <div class="right-side">

        <div class="form-wrapper">

            <div class="login-card">

                <div class="tabs">
                    <h2 class="active">Cadastrar Aluno</h2>
                </div>

                <form action="<%=request.getContextPath()%>/ServletCreateAluno" method="post">
                    <div class="input-group">
                        <label for="nome">nome:</label><br>
                        <input type="text" id="nome" name="nome"
                               placeholder="Nome do aluno" maxlength="100" required
                               title="Informe o nome do aluno"><br><br>
                    </div>

                    <div class="input-group">
                        <label for="senha">senha:</label><br>
                        <input type="text" id="senha" name="senha"
                               placeholder="Senha do aluno" maxlength="100" required
                               title="Informe a senha do aluno"><br><br>
                    </div>

                    <div class="input-group">
                        <label for="email">email:</label><br>
                        <input type="text" id="email" name="email"
                               placeholder="email do aluno" maxlength="100" required
                               title="Informe o email do aluno"><br><br>
                    </div>

                    <div class="input-group">
                        <label for="cpf">cpf:</label><br>
                        <input type="text" id="cpf" name="cpf"
                               placeholder="cpf do aluno" maxlength="100" required
                               title="Informe o cpf do aluno"><br><br>
                    </div>

                    <div class="input-group">
                        <label for="turma">turma:</label><br>
                        <input type="text" id="turma" name="turma"
                               placeholder="turma do aluno" maxlength="100" required
                               title="Informe a turma do aluno"><br><br>
                    </div>

                    <div class="input-group">
                        <label for="situacao">situação:</label><br>
                        <select name="situacao" id="situacao">
                            <option value="">Selecione a situação</option>
                            <option value="true">Passou</option>
                            <option value="false">Reprovou</option>
                        </select>
                    </div>

                    <button type="submit" class="btn-login"
                            title="Salvar novo aluno">+
                    </button>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
