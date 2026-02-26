<%@ page import="model.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Professor professor = (Professor) request.getAttribute("professor");
    String erro = (String) request.getAttribute("erro");
%>
<html>
<head>
    <title>Editar Professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="layout-adm">

    <div class="sidebar">
        <h3>Painel ADM</h3>
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="active">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 25px;">Editar Professor</h2>

        <div class="div2" style="max-width: 500px;">

                <% if (erro != null) { %>
            <p style="color: #c63b3b; font-weight: bold; margin-bottom: 20px;">⚠ <%= erro %></p>
                <% } %>

                <% if (professor != null) { %>

            <form action="${pageContext.request.contextPath}/ServletUpdateProfessor" method="post">

                <input type="hidden" name="id" value="<%= professor.getIdProfessor() %>">

                <div style="margin-bottom: 8px;">
                    <label style="display:block; font-weight:600; color:#888; font-size:13px; margin-bottom:4px;">ID</label>
                    <p style="font-size:15px; color:#214e3b; font-weight:bold;">#<%= professor.getIdProfessor() %></p>
                </div>

                <hr style="border:none; border-top:1px solid #e0ddd5; margin: 15px 0;">

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nome</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="nome" value="<%= professor.getNome() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Disciplina</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="disciplina" value="<%= professor.getDisciplina() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Email</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="email" name="email" value="<%= professor.getEmail() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nova Senha</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="password" name="senha" placeholder="Digite a nova senha" required>
                    </div>
                </div>

                <div style="display:flex; gap:10px; margin-t