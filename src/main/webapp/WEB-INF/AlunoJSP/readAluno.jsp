<%@ page import="model.Aluno" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: lucascosta-ieg
  Date: 04/02/2026
  Time: 14:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<link rel="stylesheet" href="/css/tabelas.css">
<html>
<head>
    <title>Read aluno</title>
</head>
<body>
<div class="container">
    <div class="actions">
        <!-- Botão para cadastrar novo aluno -->
        <form action="<%= request.getContextPath() %>/ServletReadAluno" method="get">
            <button type="submit" class="novo" title="Cadastrar novo aluno">+</button>
            <input  type="hidden" name="view" value="create">
        </form>
    </div>
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
                    <th>Situação</th>
                    <th>Update</th>
                    <th>Delete</th>
                </tr>
                </thead>
                <tbody>
                    <%
                    // Recupera lista de endereços e exibe na tabela
                    List<Aluno> lista = (List<Aluno>) request.getAttribute("listaAluno");
                    if (lista != null && !lista.isEmpty()) {
                        for (Aluno aluno : lista) {
                %>
                    <tr>
                        <td title="Matricula do aluno:"><%= aluno.getMatricula() %>
                        <td title="Nome do aluno:"><%= aluno.getNome() %>
                        <td title="CPF do aluno:"><%= aluno.getCpf() %>
                        <td title="Email do aluno:"><%= aluno.getEmail() %>
                        <td title="Senha do aluno:"><%= aluno.getSenha() %>
                        <td title="Turma do aluno:"><%= aluno.getTurma() %>
                        <td title="Situação do aluno:"><%= aluno.getSituacao() %>
                        </td>
                        <td class="acoes">
                            <a href="<%= request.getContextPath() %>/ServletReadAluno?view=update&matricula=<%= aluno.getMatricula() %>" title="Editar aluno">editar aluno
                            </a>
                        </td>
                        <td class="acoes">
                            <a href="<%= request.getContextPath() %>/ServletDeleteAluno?matricula=<%= aluno.getMatricula() %>"
                               onclick="return confirm('Tem certeza que deseja excluir este aluno?');"
                               title="Excluir aluno">exluir aluno
                            </a>
                        </td>
                    </tr>
                    <%     }
                    } else { %>
                    <tr>
                        <td colspan="9" title="Nenhum aluno foi encontrado">Nenhum sluno encontrado.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
</div>
</body>
</html>
