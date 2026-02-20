
<%@ page import="java.util.List" %>
<%@ page import="model.Professor" %><%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 04/02/2026
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%
    List<Professor> listaProfessor = (List<Professor>) request.getAttribute("listaProfessor");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Read professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
</head>
<body>
<div class="div2">
    <div style="display: flex; justify-content: flex-end; margin-bottom: 15px;">
        <a href="ServletReadProfessor?view=create" class="btn-criar">Novo Professor</a>
    </div>

    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Nome</th>
            <th>Disciplina</th>
            <th>Usuario</th>
            <th>Senha</th>
            <th>Ações</th>
        </tr>
        </thead>

        <tbody>
        <% for (Professor professor : listaProfessor) { %>
        <tr>
            <td><%= professor.getIdProfessor() %></td>
            <td><%= professor.getNome() %></td>
            <td><%= professor.getDisciplina() %></td>
            <td><%= professor.getUsuario() %></td>
            <td><%= professor.getSenha() %></td>
            <td class="acoes">
                <a class="btn-editar"
                   href="ServletReadProfessor?view=update&id=<%= professor.getIdProfessor() %>">
                    Editar
                </a>
                <a class="btn-excluir"
                   href="ServletDeleteProfessor?id=<%= professor.getIdProfessor() %>"
                   onclick="return confirm('Tem certeza que deseja excluir este professor?')">
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
