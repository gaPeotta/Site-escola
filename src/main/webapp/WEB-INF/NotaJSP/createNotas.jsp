<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String erro = (String) request.getAttribute("erro");
    String tipoLogado = (String) session.getAttribute("tipoUsuario");
    if (tipoLogado == null) tipoLogado = "";
%>
<html>
<head>
    <title>Nova Nota</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="layout-adm">

    <div class="sidebar">
        <h3><%= tipoLogado.equalsIgnoreCase("adm") ? "Painel ADM" : "Menu" %></h3>
        <a href="${pageContext.request.contextPath}/ServletReadNota" class="active">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>

        <% if (tipoLogado.equalsIgnoreCase("adm")) { %>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
        <% } %>
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 25px;">Nova Nota</h2>

        <div class="div2" style="max-width: 500px;">

            <% if (erro != null) { %>
            <p style="color: #c63b3b; font-weight: bold; margin-bottom: 20px;">⚠ <%= erro %></p>
            <% } %>

            <form method="post" action="${pageContext.request.contextPath}/ServletCreateNota">

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Matrícula do Aluno</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="number" name="matriculaAluno" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Disciplina</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="disciplina" placeholder="Ex: Matemática" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Observação</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="observacao" placeholder="Opcional">
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nota 1</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="number" step="0.01" min="0" max="10" name="nota1" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nota 2</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="number" step="0.01" min="0" max="10" name="nota2" required>
                    </div>
                </div>

                <div style="display:flex; gap:10px; margin-top:10px;">
                    <button type="submit" class="btn-editar">✔ Cadastrar</button>
                    <a href="${pageContext.request.contextPath}/ServletReadNota"
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