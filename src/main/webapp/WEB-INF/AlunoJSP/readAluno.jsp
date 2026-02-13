<%@ page import="model.Aluno" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 04/02/2026
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%
    List<Aluno> listaAluno = (List<Aluno>) request.getAttribute("listaAluno");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Read aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
</head>
<body>
<div class="div2">
    <div style="display: flex; justify-content: flex-end; margin-bottom: 15px;">
        <a href="ServletReadAluno?view=create" class="btn-criar">Novo Aluno</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>Matrícula</th>
            <th>Nome</th>
            <th>Email</th>
            <th>Turma</th>
            <th>Ações</th>
        </tr>
        </thead>

        <tbody>
        <% for (Aluno aluno : listaAluno) { %>
        <tr>
            <td><%= aluno.getMatricula() %></td>
            <td><%= aluno.getNome() %></td>
            <td><%= aluno.getEmail() %></td>
            <td><%= aluno.getTurma() %></td>
            <td class="acoes">
                <a class="btn-editar"
                   href="ServletReadAluno?view=update&matricula=<%= aluno.getMatricula() %>">
                    Editar
                </a>
                <a class="btn-excluir"
                   href="ServletDeleteAluno?matricula=<%= aluno.getMatricula() %>"
                   onclick="return confirm('Tem certeza que deseja excluir este aluno?')">
                    Excluir
                </a>
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
