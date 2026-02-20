<%@ page import="model.Aluno" %>
<%@ page import="model.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Professor professor = (Professor) request.getAttribute("professor");
%>

<html>
<head>
    <title>Atualizar Professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="div2">

    <% if (professor != null) { %>

    <form action="<%= request.getContextPath() %>/ServletUpdateProfessor" method="post">

        <input type="hidden" name="id" value="<%= professor.getIdProfessor() %>">

        <label>Nome</label><br>
        <input type="text" name="nome" value="<%= professor.getNome() %>"><br><br>

        <label>Disciplina</label><br>
        <input type="text" name="disciplina" value="<%= professor.getDisciplina() %>"><br><br>

        <label>Email</label><br>
        <input type="text" name="usuario" value="<%= professor.getUsuario() %>"><br><br>

        <label>Senha</label><br>
        <input type="text" name="senha" value="<%= professor.getSenha() %>"><br><br>


        <button class="btn-editar">Atualizar</button>
    </form>

    <% } else { %>

    <p style="color: red;">Professor não encontrado ou id inválido.</p>

    <% } %>

</div>

</body>
</html>
