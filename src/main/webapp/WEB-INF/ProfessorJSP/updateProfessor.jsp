<%@ page import="model.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Professor professor = (Professor) request.getAttribute("professor");
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Editar Professor</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="layout-adm">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletDashboard">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="active">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">

        <h2 class="titulo-sessao">Editar Professor</h2>

        <div class="div2 form-container">

            <% if (erro != null) { %>
            <p class="msg-erro">⚠ <%= erro %></p>
            <% } %>

            <% if (professor != null) { %>

            <form action="${pageContext.request.contextPath}/ServletUpdateProfessor" method="post">

                <input type="hidden" name="id" value="<%= professor.getIdProfessor() %>">

                <div class="field-group">
                    <label class="field-label field-label-hint">ID DO REGISTRO</label>
                    <p class="field-id">#<%= professor.getIdProfessor() %></p>
                </div>

                <div class="field-group">
                    <label class="field-label">Nome</label>
                    <div class="busca-box input-full">
                        <input type="text" name="nome" value="<%= professor.getNome() %>" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Disciplina</label>
                    <div class="busca-box input-full">
                        <input type="text" name="disciplina" value="<%= professor.getDisciplina() %>" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Email</label>
                    <div class="busca-box input-full">
                        <input type="email" name="email" value="<%= professor.getEmail() %>" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Senha</label>
                    <div class="busca-box input-full">
                        <input type="password" name="senha" value="<%= professor.getSenha() %>" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Link da Foto</label>
                    <div class="busca-box input-full">
                        <input type="text" name="foto" value="<%= (professor.getFoto() != null) ? professor.getFoto() : "" %>" placeholder="URL da imagem">
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn-editar">✔ Salvar Alterações</button>
                    <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="btn-excluir btn-link">
                        ✖ Cancelar
                    </a>
                </div>

            </form>

            <% } else { %>
            <div class="not-found">
                <p class="msg-erro">Professor não encontrado ou ID inválido.</p>
                <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="btn-editar btn-link">← Voltar para a lista</a>
            </div>
            <% } %>

        </div>
    </div>
</div>

</body>
</html>
