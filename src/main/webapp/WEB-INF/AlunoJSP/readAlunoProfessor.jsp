<%@ page import="model.Aluno" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Aluno> listaAluno = (List<Aluno>) request.getAttribute("listaAluno");

    String busca = (String) request.getAttribute("buscaSelecionada");
    String orderBy = (String) request.getAttribute("orderBySelecionado");
    String direction = (String) request.getAttribute("directionSelecionada");

    String mensagem = (String) session.getAttribute("mensagem");
    String erro = (String) session.getAttribute("erro");

    session.removeAttribute("mensagem");
    session.removeAttribute("erro");

    if (listaAluno == null) listaAluno = new java.util.LinkedList<>();
    if (busca == null) busca = "";
    if (orderBy == null) orderBy = "matricula";
    if (direction == null) direction = "ASC";
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
        <h3>Menu</h3>
        <a href="${pageContext.request.contextPath}/ServletReadNotas">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno" class="active">🎓 Alunos</a>
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

            <form method="get"
                  action="${pageContext.request.contextPath}/ServletReadAluno"
                  style="display:flex; gap:10px; flex-wrap:wrap; align-items:center; margin-bottom: 20px;">

                <div class="busca-box">
                    <input type="text"
                           name="busca"
                           placeholder="Pesquisar por nome..."
                           value="<%= busca %>">
                </div>

                <select name="orderBy" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="matricula" <%= orderBy.equals("matricula") ? "selected" : "" %>>Ordenar por Matrícula</option>
                    <option value="nome"      <%= orderBy.equals("nome") ? "selected" : "" %>>Ordenar por Nome</option>
                    <option value="turma"     <%= orderBy.equals("turma") ? "selected" : "" %>>Ordenar por Turma</option>
                    <option value="email"     <%= orderBy.equals("email") ? "selected" : "" %>>Ordenar por Email</option>
                </select>

                <select name="direction" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="ASC"  <%= direction.equalsIgnoreCase("ASC") ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= direction.equalsIgnoreCase("DESC") ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar">🔍 Filtrar</button>

                <a href="${pageContext.request.contextPath}/ServletReadAluno"
                   class="btn-editar">🧹 Limpar</a>

            </form>

            <table>
                <thead>
                <tr>
                    <th>Matrícula</th>
                    <th>Nome</th>
                    <th>Email</th>
                    <th>Turma</th>
                </tr>
                </thead>
                <tbody>
                <% if (!listaAluno.isEmpty()) {
                    for (Aluno aluno : listaAluno) { %>
                <tr>
                    <td><%= aluno.getMatricula() %></td>
                    <td><%= aluno.getNome() %></td>
                    <td><%= aluno.getEmail() %></td>
                    <td><%= aluno.getTurma() %></td>
                </tr>
                <% } } else { %>
                <tr>
                    <td colspan="4" style="text-align:center; padding:20px; color:#888;">
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