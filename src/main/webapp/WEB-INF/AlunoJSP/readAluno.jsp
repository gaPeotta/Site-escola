<%@ page import="model.Aluno" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Aluno> listaAluno = (List<Aluno>) request.getAttribute("listaAluno");

    String mensagem = (String) session.getAttribute("mensagem");
    String erro = (String) session.getAttribute("erro");

    session.removeAttribute("mensagem");
    session.removeAttribute("erro");

    if (listaAluno == null) listaAluno = new java.util.LinkedList<>();
%>
<html>
<head>
    <title>Alunos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<div class="layout-adm">

    <div class="sidebar">
        <h3>Painel ADM</h3>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno" class="active">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 20px;">Alunos</h2>

        <% if (mensagem != null) { %>
        <p style="color: #2f7d4a; font-weight: bold; margin-bottom: 15px;">✔ <%= mensagem %></p>
        <% } %>
        <% if (erro != null) { %>
        <p style="color: #c63b3b; font-weight: bold; margin-bottom: 15px;">⚠ <%= erro %></p>
        <% } %>

        <div class="div2">

            <div style="display:flex; justify-content:flex-end; margin-bottom: 20px;">
                <a href="${pageContext.request.contextPath}/ServletCreateAluno"
                   class="btn-editar">➕ Novo Aluno</a>
            </div>

            <table>
                <thead>
                <tr>
                    <th>Matrícula</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>CPF</th>
                    <th>Turma</th>
                    <th>Ações</th>
                </tr>
                </thead>
                <tbody>
                <% if (!listaAluno.isEmpty()) {
                    for (Aluno aluno : listaAluno) { %>
                <tr>
                    <td><%= aluno.getMatricula() %></td>
                    <td><%= aluno.getNome() %></td>
                    <td><%= aluno.getEmail() %></td>
                    <td><%= aluno.getCpf() %></td>
                    <td><%= aluno.getTurma() %></td>
                    <td class="acoes">
                        <a href="${pageContext.request.contextPath}/ServletUpdateAluno?matricula=<%= aluno.getMatricula() %>"
                           class="btn-editar">✏ Editar</a>
                        <button class="btn-excluir"
                                onclick="if(confirm('Remover aluno <%= aluno.getNome() %>?'))
                                        window.location='${pageContext.request.contextPath}/ServletDeleteAluno?matricula=<%= aluno.getMatricula() %>'">
                            🗑
                        </button>
                    </td>
                </tr>
                <% } } else { %>
                <tr>
                    <td colspan="6" style="text-align:center; padding:20px; color:#888;">
                        Nenhum aluno encontrado.
                    </td>
                </tr>
                <% } %>
                </tbody>
            </table>

        </div>
    </div>
</div>

</body>
</html>