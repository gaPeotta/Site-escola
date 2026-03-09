<%@ page import="model.Professor" %>
<%@ page import="java.util.List" %>
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
        <a href="${pageContext.request.contextPath}/ServletDashboard">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota" class="active">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <% if (tipoLogado.equalsIgnoreCase("adm")) { %>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
        <% } %>
    </div>

    <div class="conteudo">
        <h2 class="titulo-sessao">Nova Nota</h2>

        <div class="div2 form-container">
            <% if (erro != null) { %><p class="msg-erro">⚠ <%= erro %></p><% } %>

            <form method="post" action="${pageContext.request.contextPath}/ServletCreateNota">

                <div class="field-group">
                    <label class="field-label">Matrícula do Aluno</label>
                    <div class="busca-box input-full">
                        <input type="number" name="matriculaAluno" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Disciplina</label>
                    <select name="disciplina" class="select-custom input-full" required>
                        <option value="" disabled selected>Selecione uma matéria</option>
                        <option value="Matemática">Matemática</option>
                        <option value="Português">Português</option>
                        <option value="História">História</option>
                        <option value="Ciências">Ciências</option>
                        <option value="Informática">Informática</option>
                    </select>
                </div>

                <div class="field-group">
                    <label class="field-label">Observação</label>
                    <div class="busca-box input-full">
                        <input type="text" name="observacao" placeholder="Opcional">
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Nota 1</label>
                    <div class="busca-box input-full">
                        <input type="number" step="0.01" min="0" max="10" name="nota1" required>
                    </div>
                </div>

                <div class="field-group">
                    <label class="field-label">Nota 2</label>
                    <div class="busca-box input-full">
                        <input type="number" step="0.01" min="0" max="10" name="nota2" required>
                    </div>
                </div>

                <div class="btn-group">
                    <button type="submit" class="btn-editar">✔ Cadastrar</button>
                    <a href="${pageContext.request.contextPath}/ServletReadNota" class="btn-excluir btn-link">✖ Cancelar</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>