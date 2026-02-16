<%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 16/02/2026
  Time: 17:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="login-card">

    <form action="<%=request.getContextPath()%>/Login-Adm" method="post">
        <div class="input-group">
            <label>E-mail</label>
            <input type="email" placeholder="Seunome@email.com" id="usuario" name="usuario">
        </div>

        <div class="input-group">
            <label>Password</label>
            <input type="password" placeholder="@Password1234" id="senha" name="senha">
        </div>

        <button type="submit" class="btn-login">Login</button>

        <a href="#" class="forgot-link">Esqueceu sua senha?</a>
    </form>
</div>
</body>
</html>
