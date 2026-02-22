<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cadastrar Nota</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="div2" style="max-width:600px; margin:auto;">

    <h2 style="margin-bottom:20px;">Cadastrar Nova Nota</h2>

    <% String erro = (String) request.getAttribute("erro"); %>
    <% if (erro != null) { %>
    <div style="color:red; margin-bottom:15px;">
        <%= erro %>
    </div>
    <% } %>

    <form method="post"
          action="${pageContext.request.contextPath}/ServletCreateNota"
          style="display:flex; flex-direction:column; gap:12px;">

        <label>Matricula do Aluno</label>
        <input type="number"
               name="matriculaAluno"
               required>

        <label>Disciplina</label>
        <input type="text"
               name="disciplina"
               required>

        <label>Observação</label>
        <textarea name="observacao"
                  rows="3"
                  placeholder="Opcional"></textarea>

        <label>Nota 1</label>
        <input type="number"
               name="nota1"
               step="0.01"
               min="0"
               max="10"
               required>

        <label>Nota 2</label>
        <input type="number"
               name="nota2"
               step="0.01"
               min="0"
               max="10"
               required>

        <div style="display:flex; gap:10px; margin-top:10px;">
            <button type="submit" class="btn-criar">
                💾 Salvar
            </button>

            <a href="${pageContext.request.contextPath}/ServletReadNotas"
               class="btn-criar">
                ↩ Voltar
            </a>
        </div>

    </form>

</div>

</body>
</html>