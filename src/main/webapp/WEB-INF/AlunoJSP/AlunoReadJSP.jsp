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
    <title>Read aluno</title>
</head>
<body>
<div class="container">
    <div class="actions">
    <form action="<%= request.getContextPath() %>/ServletReadAluno">
        <button type="submit" class="novo" title="Cadastrar nova empresa">+</button>
        <input  type="hidden" name="view" value="create">
    </form>
        <% String mensagem = (String) request.getAttribute("mensagem");
            if (mensagem != null) {
                String cor = mensagem.toLowerCase().contains("sucesso") ? "green" : "red"; %>
        <p style="color: <%= cor %>" title="Mensagem do sistema"><%= mensagem %></p>
        <% } %>
        <div class="tabela-container">
            <table>
                <thead>
                <tr>
                    <th>Matricula</th>
                    <th>Nome</th>
                    <th>CPF</th>
                    <th>Email</th>
                    <th>Senha</th>
                    <th>Turma</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                    <%
                    // Recupera lista de endereços e exibe na tabela
                    List<Endereco> lista = (List<Endereco>) request.getAttribute("listaEnderecos");
                    if (lista != null && !lista.isEmpty()) {
                        for (Endereco endereco : lista) {
                %></tbody>
        </div>
    </div>
</div>
</body>
</html>
