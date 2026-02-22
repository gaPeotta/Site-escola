<%@ page import="model.Notas" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    Notas nota = (Notas) request.getAttribute("nota");
%>

<html>
<head>
    <title>Atualizar Nota</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="div2">

    <% if (nota != null) { %>

    <form action="<%= request.getContextPath() %>/ServletUpdateNota" method="post">

        <input type="hidden" name="idNotas" value="<%= nota.getIdNotas() %>">

        <label>Matricula do Aluno</label><br>
        <input type="number"
               name="matriculaAluno"
               value="<%= nota.getMatriculaAluno() %>"><br><br>

        <label>Disciplina</label><br>
        <input type="text"
               name="disciplina"
               value="<%= nota.getDisciplina() %>"><br><br>

        <label>Observação</label><br>
        <input type="text"
               name="observacao"
               value="<%= nota.getObservacao() %>"><br><br>

        <label>Nota 1</label><br>
        <input type="number"
               step="0.01"
               min="0"
               max="10"
               name="nota1"
               value="<%= nota.getNota1() %>"><br><br>

        <label>Nota 2</label><br>
        <input type="number"
               step="0.01"
               min="0"
               max="10"
               name="nota2"
               value="<%= nota.getNota2() %>"><br><br>

        <button class="btn-editar">Atualizar</button>
    </form>

    <% } else { %>

    <p style="color: red;">Nota não encontrada ou id inválido.</p>

    <% } %>

</div>

</body>
</html>