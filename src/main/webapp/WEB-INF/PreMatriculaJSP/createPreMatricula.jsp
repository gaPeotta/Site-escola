<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String erro = (String) request.getAttribute("erro");
%>
<html>
<head>
  <title>Nova Pré-Matrícula</title>
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
    <a href="${pageContext.request.contextPath}/ServletReadPreMatricula" class="active">📋 Pré-Matrículas</a>
  </div>

  <div class="conteudo">
    <h2 class="titulo-sessao">Nova Pré-Matrícula</h2>

    <div class="div2 form-container">

      <% if (erro != null) { %>
      <p class="msg-erro">⚠ <%= erro %></p>
      <% } %>

      <form method="post" action="${pageContext.request.contextPath}/ServletCreatePreMatricula">

        <div class="field-group">
          <label class="field-label">CPF do Aluno</label>
          <div class="busca-box input-full">
            <input type="text" name="cpf" placeholder="Somente números (11 dígitos)" maxlength="14" required>
          </div>
        </div>

        <div class="btn-group">
          <button type="submit" class="btn-editar">✔ Cadastrar</button>
          <a href="${pageContext.request.contextPath}/ServletReadPreMatricula" class="btn-excluir btn-link">
            ✖ Cancelar
          </a>
        </div>

      </form>
    </div>
  </div>
</div>

</body>
</html>