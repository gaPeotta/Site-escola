<%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 04/02/2026
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create aluno</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
</head>
<body>
<div class="div2">
    <form action="<%=request.getContextPath()%>/ServletCreateAluno" method="post">

        <label>Nome</label><br>
        <input type="text" name="nome"><br><br>

        <label>Email</label><br>
        <input type="text" name="email"><br><br>

        <label>Senha</label><br>
        <input type="text" name="senha"><br><br>

        <label>CPF</label><br>
        <input type="text" name="cpf"><br><br>

        <label>Turma</label><br>
        <input type="text" name="turma"><br><br>

        <label>Situação</label><br>
        <select name="situacao">
            <option value="true">Passou</option>
            <option value="false">Reprovou</option>
        </select><br><br>

        <button class="btn-editar">Cadastrar</button>
    </form>
</div>
</body>
</html>
