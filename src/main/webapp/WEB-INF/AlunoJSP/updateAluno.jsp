<%@ page import="model.Aluno" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Aluno aluno = (Aluno) request.getAttribute("aluno");
    String erro = (String) request.getAttribute("erro");
%>
<html>
<head>
    <title>Editar Aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="layout-adm">

    <div class="sidebar">
        <h3>Painel ADM</h3>
        <a href="${pageContext.request.contextPath}/ServletReadNotas">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno" class="active">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 25px;">Editar Aluno</h2>

        <div class="div2" style="max-width: 500px;">

                <% if (erro != null) { %>
            <p style="color: #c63b3b; font-weight: bold; margin-bottom: 20px;">⚠ <%= erro %></p>
                <% } %>

                <% if (aluno != null) { %>

            <form action="${pageContext.request.contextPath}/ServletUpdateAluno" method="post">

                <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>">

                <div style="margin-bottom: 8px;">
                    <label style="display:block; font-weight:600; color:#888; font-size:13px; margin-bottom:4px;">Matrícula</label>
                    <p style="font-size:15px; color:#214e3b; font-weight:bold;">#<%= aluno.getMatricula() %></p>
                </div>

                <hr style="border:none; border-top:1px solid #e0ddd5; margin: 15px 0;">

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nome</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="nome" value="<%= aluno.getNome() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">CPF</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="cpf" value="<%= aluno.getCpf() %>" maxlength="11" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Email</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="email" name="email" value="<%= aluno.getEmail() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nova Senha</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="password" name="senha" value="<%= aluno.getSenha() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Turma</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="turma" value="<%= aluno.getTurma() %>" required>
                    </div>
                </div>

                <button type="submit" class="btn-acao editar">Salvar Alterações</button>

            </form>

            <% } else { %>
            <p style="color:#c63b3b;">Aluno não encontrado.</p>
            <% } %>

        </div>
    </div>
</div>

</body>
</html>