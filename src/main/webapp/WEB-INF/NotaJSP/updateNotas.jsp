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
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <% if (tipoLogado.equalsIgnoreCase("adm")) { %>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
        <% } %>
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 25px;">Editar Nota</h2>

        <div class="div2" style="max-width: 500px;">

            <% if (erro != null) { %>
            <p style="color: #c63b3b; font-weight: bold; margin-bottom: 20px;">⚠ <%= erro %></p>
            <% } %>
            <% if (nota != null) { %>

            <form action="${pageContext.request.contextPath}/ServletUpdateNota" method="post">

                <input type="hidden" name="idNotas" value="<%= nota.getIdNotas() %>">

                <div style="margin-bottom: 8px;">
                    <label style="display:block; font-weight:600; color:#888; font-size:13px; margin-bottom:4px;">ID</label>
                    <p style="font-size:15px; color:#214e3b; font-weight:bold;">#<%= nota.getIdNotas() %></p>
                </div>

                <hr style="border:none; border-top:1px solid #e0ddd5; margin: 15px 0;">

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Matrícula do Aluno</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="number" name="matriculaAluno" value="<%= nota.getMatriculaAluno() %>" required>
                    </div>
                </div>
                
                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Id do Professor</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="id_Professor" value="<%= nota.getIdProfessor() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Disciplina</label>
                    <div style="width:100%;">
                        <select name="disciplina" required style="width:100%; padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b; outline: none;">
                            <option value="Matemática" <%= "Matemática".equals(nota.getDisciplina()) ? "selected" : "" %>>Matemática</option>
                            <option value="Português" <%= "Português".equals(nota.getDisciplina()) ? "selected" : "" %>>Português</option>
                            <option value="História" <%= "História".equals(nota.getDisciplina()) ? "selected" : "" %>>História</option>
                            <option value="Ciências" <%= "Ciências".equals(nota.getDisciplina()) ? "selected" : "" %>>Ciências</option>
                            <option value="Informática" <%= "Informática".equals(nota.getDisciplina()) ? "selected" : "" %>>Informática</option>
                        </select>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Observação</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="text" name="observacao" value="<%= nota.getObservacao() %>">
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nota 1</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="number" step="0.01" min="0" max="10" name="nota1" value="<%= nota.getNota1() %>" required>
                    </div>
                </div>

                <div style="margin-bottom: 20px;">
                    <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">Nota 2</label>
                    <div class="busca-box" style="width:100%;">
                        <input type="number" step="0.01" min="0" max="10" name="nota2" value="<%= nota.getNota2() %>" required>
                    </div>
                </div>

                <div style="display:flex; gap:10px; margin-top:10px;">
                    <button type="submit" class="btn-editar">✔ Salvar</button>
                    <a href="${pageContext.request.contextPath}/ServletReadNota"
                       class="btn-excluir"
                       style="padding: 8px 15px; border-radius: 6px; text-decoration:none; display:flex; align-items:center;">
                        ✖ Cancelar
                    </a>
                </div>

            </form>

            <% } else { %>
            <p style="color: #c63b3b;">Nota não encontrada.</p>
            <a href="${pageContext.request.contextPath}/ServletReadNota" class="btn-editar">← Voltar</a>
            <% } %>

        </div>
    </div>
</div>

</body>
</html>