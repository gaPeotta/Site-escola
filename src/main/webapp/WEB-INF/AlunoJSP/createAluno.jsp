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

        <h2 class="titulo-sessao">Novo Aluno</h2>

        <div class="div2 form-container">

            <% if (erro != null) { %>
            <p class="msg-erro">⚠ <%= erro %></p>
            <% } %>

            <form action="${pageContext.request.contextPath}/ServletCreateAluno" method="post">

                <div class="field-group">
                    <label class="field-label">Nome</label>
                    <div class="busca-box input-full">
                        <input type="text" name="nome" placeholder="Nome completo" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">CPF</label>
                    <div class="busca-box input-full">
                        <input type="text" name="cpf" placeholder="000.000.000-00" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Email</label>
                    <div class="busca-box input-full">
                        <input type="email" name="email" placeholder="email@exemplo.com" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Senha</label>
                    <div class="busca-box input-full">
                        <input type="text" name="senha" placeholder="Senha de acesso" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Turma</label>
                    <div class="busca-box input-full">
                        <input type="text" name="turma" placeholder="Ex: 3A">
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">
                        Foto <span class="field-label-hint">(opcional — cole o link do
                            <a class="link-label" href="https://institutogerminare-my.sharepoint.com/:f:/g/personal/gabriel_vigna_institutojef_org_br/IgDjfKyDgDFeQbfAVDaBVi1oAXxDmMuRB20ET97ibkT7Cgk?e=PFzUxy" target="_blank">OneDrive</a>)
                        </span>
                    </label>
                    <div class="busca-box input-full">
                        <input type="text" name="foto" placeholder="https://drive.google.com/uc?export=view&id=...">
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn-editar">✔ Cadastrar</button>
                    <a href="${pageContext.request.contextPath}/ServletReadAluno" class="btn-excluir btn-link">
                        ✖ Cancelar
                    </a>
                </div>

            </form>

        </div>
    </div>
</div>

</body>
</html>
