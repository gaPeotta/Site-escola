<%@ page import="model.Aluno" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Aluno aluno = (Aluno) request.getAttribute("aluno");
%>

<html>
<head>
    <title>Atualizar Aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="div2">

    <% if (aluno != null) { %>

    <form action="<%= request.getContextPath() %>/ServletUpdateAluno" method="post">

        <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">

        <label>Nome</label><br>
        <input type="text" name="nome" value="<%= aluno.getNome() %>"><br><br>

        <label>CPF</label><br>
        <input type="text" name="cpf" value="<%= aluno.getCpf() %>"><br><br>

        <label>Email</label><br>
        <input type="text" name="email" value="<%= aluno.getEmail() %>"><br><br>

        <label>Senha</label><br>
        <input type="text" name="senha" value="<%= aluno.getSenha() %>"><br><br>

        <label>Turma</label><br>
        <input type="text" name="turma" value="<%= aluno.getTurma() %>"><br><br>

        <label>Situação</label><br>
        <select name="situacao">

            <option value="true"
                    <% if (aluno.getSituacao()) { %> selected <% } %>>
                Passou
            </option>

            <option value="false"
                    <% if (!aluno.getSituacao()) { %> selected <% } %>>
                Reprovou
            </option>

        </select><br><br>

        <button class="btn-editar">Atualizar</button>
    </form>

    <% } else { %>

    <p style="color: red;">Aluno não encontrado ou matrícula inválida.</p>

    <% } %>

</div>

</body>
</html>
