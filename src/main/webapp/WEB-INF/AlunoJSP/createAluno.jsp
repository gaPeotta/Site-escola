<%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 04/02/2026
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create aluno</title>
</head>
<body>
    <div class="action">
        <form action="<%=request.getContextPath()%>/ServletCreateAluno" method="post">
            <label for="nome">nome:</label><br>
            <input type="text" id="nome" name="nome"
                   placeholder="Nome do aluno" maxlength="100" required
                   title="Informe o nome do aluno"><br><br>

            <label for="senha">nome:</label><br>
            <input type="text" id="senha" name="senha"
                   placeholder="Senha do aluno" maxlength="100" required
                   title="Informe a senha do aluno"><br><br>

            <label for="email">email:</label><br>
            <input type="text" id="email" name="email"
                   placeholder="email do aluno" maxlength="100" required
                   title="Informe o email do aluno"><br><br>

            <label for="cpf">cpf:</label><br>
            <input type="text" id="cpf" name="cpf"
                   placeholder="cpf do aluno" maxlength="100" required
                   title="Informe o cpf do aluno"><br><br>

            <label for="turma">turma:</label><br>
            <input type="text" id="turma" name="turma"
                   placeholder="turma do aluno" maxlength="100" required
                   title="Informe a turma do aluno"><br><br>


            <button type="submit" class="novo" title="Salvar novo aluno">+</button>
        </form>
    </div>
</body>
</html>
