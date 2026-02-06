<%@ page import="Dao.AlunoDAO" %>
<%@ page import="model.Aluno" %><%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 05/02/2026
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
        // Recupera o ID do endereço e busca os dados no banco
        Integer matricula = null;
        try {
            matricula = Integer.valueOf(request.getParameter("matricula"));
        } catch (Exception e) {}
    Aluno aluno = null;
    if (matricula != null) {
        aluno = new AlunoDAO().buscarPorMatricula(matricula);
    }
%>
<html>
<head>
    <title>aluno update</title>
</head>
<body>
<form action="<%= request.getContextPath() %>/ServletUpdateEndereco" method="post" style="max-width: 500px; width: 100%;">
    <input type="hidden" name="matricula" value="<%= aluno.getMatricula() %>"/>

    <label for="nome">nome:</label><br>
    <input type="text" id="nome" name="nome" class="input-redondo"
           value="<%= aluno.getNome() %>" maxlength="100"
           required placeholder="Ex: Lucas" title="Informe o nome do aluno"><br><br>

</form>
</body>
</html>
