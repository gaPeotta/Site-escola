<%@ page import="java.util.List" %>
<%@ page import="model.Notas" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Notas> listaNotas = (List<Notas>) request.getAttribute("listaNotas");

    String orderBySelecionado = (String) request.getAttribute("orderBySelecionado");
    String directionSelecionada = (String) request.getAttribute("directionSelecionada");
    String buscaAluno = request.getParameter("buscaAluno");

    String mensagem = (String) session.getAttribute("mensagem");
    String erro = (String) session.getAttribute("erro");
    String tipoLogado = (String) session.getAttribute("tipoUsuario");

    session.removeAttribute("mensagem");
    session.removeAttribute("erro");

    if (orderBySelecionado == null) orderBySelecionado = "id_notas";
    if (directionSelecionada == null) directionSelecionada = "ASC";
    if (buscaAluno == null) buscaAluno = "";
    if (tipoLogado == null) tipoLogado = "";
%>
<html>
<head>
    <title>Notas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>
<div class="div2">

    <% if (mensagem != null) { %>
    <p style="color: green; font-weight: bold;"><%= mensagem %></p>
    <% } %>
    <% if (erro != null) { %>
    <p style="color: red; font-weight: bold;"><%= erro %></p>
    <% } %>

    <!-- FILTROS -->
    <form method="get" action="${pageContext.request.contextPath}/ServletReadNota"
          style="margin-bottom: 15px; display:flex; gap:10px; flex-wrap:wrap;">

        <input type="text"
               name="buscaAluno"
               placeholder="Pesquisar aluno ou disciplina..."
               value="<%= buscaAluno %>">

        <select name="orderBy">
            <option value="id_notas" <%= orderBySelecionado.equals("id_notas") ? "selected" : "" %>>ID</option>
            <option value="disciplina" <%= orderBySelecionado.equals("disciplina") ? "selected" : "" %>>Disciplina</option>
            <option value="nota1" <%= orderBySelecionado.equals("nota1") ? "selected" : "" %>>Nota 1</option>
            <option value="nota2" <%= orderBySelecionado.equals("nota2") ? "selected" : "" %>>Nota 2</option>
        </select>

        <select name="direction">
            <option value="ASC" <%= directionSelecionada.equalsIgnoreCase("ASC") ? "selected" : "" %>>Crescente</option>
            <option value="DESC" <%= directionSelecionada.equalsIgnoreCase("DESC") ? "selected" : "" %>>Decrescente</option>
        </select>

        <button type="submit" class="btn-criar">🔍 Filtrar</button>
        <a href="${pageContext.request.contextPath}/ServletReadNotas" class="btn-criar">🧹 Limpar</a>

        <% if (tipoLogado.equalsIgnoreCase("adm") || tipoLogado.equalsIgnoreCase("professor")) { %>
        <a href="${pageContext.request.contextPath}/ServletReadNota?view=create"
           class="btn-criar" style="margin-left:auto;">➕ Nova Nota</a>
        <% } %>
    </form>

    <!-- TABELA -->
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Aluno</th>
            <th>Professor</th>
            <th>Disciplina</th>
            <th>Observação</th>
            <th>Nota 1</th>
            <th>Nota 2</th>
            <th>Média</th>
            <th>Situação</th>
            <% if (tipoLogado.equalsIgnoreCase("adm") || tipoLogado.equalsIgnoreCase("professor")) { %>
            <th>Ações</th>
            <% } %>
        </tr>
        </thead>
        <tbody>
        <% if (listaNotas != null && !listaNotas.isEmpty()) {
            for (Notas nota : listaNotas) {
                double media = (nota.getNota1() + nota.getNota2()) / 2.0;
        %>
        <tr>
            <td><%= nota.getIdNotas() %></td>
            <td><%= nota.getNomeAluno() %></td>
            <td><%= nota.getNomeProfessor() %></td>
            <td><%= nota.getDisciplina() %></td>
            <td><%= nota.getObservacao() %></td>
            <td><%= nota.getNota1() %></td>
            <td><%= nota.getNota2() %></td>
            <td><%= String.format("%.2f", media) %></td>
            <td style="color: <%= media >= 7 ? "#2f7d4a" : "#c63b3b" %>; font-weight: bold;">
                <%= nota.getSituacao() ? "✔ Aprovado" : "✖ Reprovado" %>
            </td>
            <% if (tipoLogado.equalsIgnoreCase("adm") || tipoLogado.equalsIgnoreCase("professor")) { %>
            <td class="acoes">
                <a href="${pageContext.request.contextPath}/ServletReadNota?view=update&id=<%= nota.getIdNotas() %>"
                   class="btn-editar">✏ Editar</a>
                <a href="${pageContext.request.contextPath}/ServletDeleteNota?id=<%= nota.getIdNotas() %>"
                   class="btn-excluir"
                   onclick="return confirm('Excluir esta nota?')">🗑</a>
            </td>
            <% } %>
        </tr>
        <% } } else { %>
        <tr>
            <td colspan="<%= (tipoLogado.equalsIgnoreCase("adm") || tipoLogado.equalsIgnoreCase("professor")) ? 10 : 9 %>"
                style="text-align:center; padding:20px; color:#888;">
                Nenhuma nota encontrada.
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>
</div>
</body>
</html>
