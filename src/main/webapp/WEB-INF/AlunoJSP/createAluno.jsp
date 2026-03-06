<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String erro = (String) request.getAttribute("erro");
%>
<html>
<head>
    <title>Novo Aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="layout-adm">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletDashboard">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno" class="active">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 25px;">Novo Aluno</h2>

        <div class="div2" style="max-width: 500px;">

            <% if (erro != null) { %>
            <p style="color: #c63b3b; font-weight: bold; margin-bottom: 20px;">⚠ <%= erro %></p>
            <% } %>

            <form action="${pageContext.request.contextPath}/ServletCreateAluno" method="post">

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nome</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="nome" placeholder="Nome completo" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">CPF</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="cpf" placeholder="000.000.000-00" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Email</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="email" name="email" placeholder="email@exemplo.com" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Senha</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="senha" placeholder="Senha de acesso" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Turma</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="turma" placeholder="Ex: 3A">
                    </div>
                </div>

                <div style="display:flex; gap:10px; margin-top:10px;">
                    <button type="submit" class="btn-editar">✔ Cadastrar</button>
                    <a href="${pageContext.request.contextPath}/ServletReadAluno"
                       class="btn-excluir"
                       style="padding: 8px 15px; border-radius: 6px; text-decoration:none; display:flex; align-items:center;">
                        ✖ Cancelar
                    </a>
                </div>

            </form>

        </div>
    </div>
</div>

</body>
</html>