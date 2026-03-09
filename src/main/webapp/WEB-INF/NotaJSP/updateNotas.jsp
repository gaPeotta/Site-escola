<%@ page import="model.Notas" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Notas nota = (Notas) request.getAttribute("nota");
    String erro = (String) request.getAttribute("erro");
    String tipoLogado = (String) session.getAttribute("tipoUsuario");
    if (tipoLogado == null) tipoLogado = "";
%>
<html>
<head>
    <title>Editar Nota</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="layout-adm">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletDashboard">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota" class="active">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <% if (tipoLogado.equalsIgnoreCase("adm")) { %>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
        <% } %>
    </div>

    <div class="conteudo">

        <h2 class="titulo-sessao">Editar Nota</h2>

        <div class="div2 form-container">

            <% if (erro != null) { %>
            <p class="msg-erro">⚠ <%= erro %></p>
            <% } %>

            <% if (nota != null) { %>

            <form action="${pageContext.request.contextPath}/ServletUpdateNota" method="post">

                <input type="hidden" name="idNotas" value="<%= nota.getIdNotas() %>">

                <div class="field-group">
                    <label class="field-label" style="color: #888; font-size: 12px;">ID DO REGISTRO</label>
                    <p style="font-weight: bold; color: #214e3b;">#<%= nota.getIdNotas() %></p>
                </div>

                <div class="field-group">
                    <label class="field-label">Matrícula do Aluno</label>
                    <div class="busca-box input-full">
                        <input type="number" name="matriculaAluno" value="<%= nota.getMatriculaAluno() %>" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">ID do Professor</label>
                    <div class="busca-box input-full">
                        <input type="text" name="id_Professor" value="<%= nota.getIdProfessor() %>" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Disciplina</label>
                    <select name="disciplina" class="select-custom input-full" required>
                        <option value="Matemática" <%= "Matemática".equals(nota.getDisciplina()) ? "selected" : "" %>>Matemática</option>
                        <option value="Português" <%= "Português".equals(nota.getDisciplina()) ? "selected" : "" %>>Português</option>
                        <option value="História" <%= "História".equals(nota.getDisciplina()) ? "selected" : "" %>>História</option>
                        <option value="Ciências" <%= "Ciências".equals(nota.getDisciplina()) ? "selected" : "" %>>Ciências</option>
                        <option value="Informática" <%= "Informática".equals(nota.getDisciplina()) ? "selected" : "" %>>Informática</option>
                    </select>
                </div>

                <div class="field-group">
                    <label class="field-label">Observação</label>
                    <div class="busca-box input-full">
                        <input type="text" name="observacao" value="<%= (nota.getObservacao() != null) ? nota.getObservacao() : "" %>">
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Nota 1</label>
                    <div class="busca-box input-full">
                        <input type="number" step="0.01" min="0" max="10" name="nota1" value="<%= nota.getNota1() %>" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Nota 2</label>
                    <div class="busca-box input-full">
                        <input type="number" step="0.01" min="0" max="10" name="nota2" value="<%= nota.getNota2() %>" required>
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn-editar">✔ Salvar Alterações</button>
                    <a href="${pageContext.request.contextPath}/ServletReadNota" class="btn-excluir btn-link">
                        ✖ Cancelar
                    </a>
                </div>

            </form>

            <% } else { %>
            <div style="text-align: center; padding: 20px;">
                <p class="msg-erro">Nota não encontrada.</p>
                <a href="${pageContext.request.contextPath}/ServletReadNota" class="btn-editar btn-link">← Voltar</a>
            </div>
            <% } %>

        </div>
    </div>
</div>

</body>
</html>