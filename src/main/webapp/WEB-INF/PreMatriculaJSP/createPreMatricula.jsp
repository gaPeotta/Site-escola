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

  <%-- ===== SIDEBAR ===== --%>
  <div class="sidebar">
    <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
    <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
    <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
    <a href="${pageContext.request.contextPath}/ServletReadPreMatricula" class="active">📋 Pré-Matrículas</a>
  </div>

  <%-- ===== CONTEÚDO ===== --%>
  <div class="conteudo">

    <h2 style="color: #214e3b; margin-bottom: 25px;">Nova Pré-Matrícula</h2>

    <div class="div2" style="max-width: 500px;">

      <% if (erro != null) { %>
      <p style="color: #c63b3b; font-weight: bold; margin-bottom: 20px;">⚠ <%= erro %></p>
      <% } %>

      <form method="post"
            action="${pageContext.request.contextPath}/ServletCreatePreMatricula">

        <div style="margin-bottom: 20px;">
          <label style="display:block; font-weight:600; color:#214e3b; margin-bottom:8px;">
            CPF do Aluno
          </label>
          <div class="busca-box" style="width:100%;">
            <input type="text"
                   name="cpf"
                   placeholder="Somente números (11 dígitos)"
                   maxlength="14"
                   required>
          </div>
        </div>

        <div style="display:flex; gap:10px; margin-top:10px;">
          <button type="submit" class="btn-editar">✔ Cadastrar</button>
          <a href="${pageContext.request.contextPath}/ServletReadPreMatricula"
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