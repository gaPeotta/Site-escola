<%@ page import="Dao.AlunoDAO" %>
<%@ page import="model.Aluno" %><%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 05/02/2026
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
        // Recupera o ID do endereço e busca os dados no banco
        Integer matricula = null;
        try {
            matricula = Integer.valueOf(request.getParameter("matricula"));
        } catch (Exception e) {}
    Aluno aluno = null;
    if (matricula != null) {
        aluno = new AlunoDAO().buscarPorMatricula(matricula);
    }
%>
<html>
<head>
    <title>aluno update</title>
</head>
<body>
<% if (aluno != null) { %>
<form action="<%= request.getContextPath() %>/ServletUpdateAluno" method="post" style="max-width: 500px; width: 100%;">
    <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>"/>

    <label for="nome">nome:</label><br>
    <input type="text" id="nome" name="nome"
           value="<%= aluno.getNome() %>" maxlength="100"
           required placeholder="Ex: Lucas" title="Informe o nome do aluno"><br><br>

    <label for="cpf">cpf:</label><br>
    <input type="text" id="cpf" name="cpf"
    value="<%= aluno.getCpf() %>" maxlength="100"
    required placeholder="Ex: Lucas" title="Informe o cpf do aluno"><br><br>


    <label for="email">email:</label><br>
    <input type="text" id="email" name="email"
    value="<%= aluno.getEmail() %>" maxlength="100"
    required placeholder="Ex: Lucas" title="Informe o email do aluno"><br><br>


    <label for="senha">senha:</label><br>
    <input type="text" id="senha" name="senha"
           value="<%= aluno.getSenha() %>" maxlength="100" title="Informe a senha do aluno"><br><br>

    <label for="turma">turma:</label><br>
    <input type="text" id="turma" name="turma"
           value="<%= aluno.getTurma() %>" maxlength="100"
           required placeholder="Ex: Lucas" title="Informe a turma do aluno"><br><br>

    <label for="situacao">situação:</label><br>
    <select name="situacao" id="situacao">
        <option value="">Selecione a situação</option>
        <option value="true">Passou</option>
        <option value="false">Reprovou</option>
    </select>
    <button type="submit" class="novo" title="Atualizar aluno">+</button>
</form>
<% } else { %>
<p style="color: red;" title="Erro ao carregar aluno">Aluno não encontrado ou matricula inválida.</p>
<% } %>
</body>
</html>
