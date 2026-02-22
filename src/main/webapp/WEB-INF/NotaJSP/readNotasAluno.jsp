<%@ page import="java.util.List" %>
<%@ page import="model.Notas" %>
<%
    List<Notas> listaNotas = (List<Notas>) request.getAttribute("listaNotas");

    String disciplinaSelecionada = (String) request.getAttribute("disciplinaSelecionada");
    String orderBySelecionado = (String) request.getAttribute("orderBySelecionado");
    String directionSelecionada = (String) request.getAttribute("directionSelecionada");
    String buscaAlunoSelecionada = (String) request.getParameter("buscaAluno");

    if (disciplinaSelecionada == null) disciplinaSelecionada = "";
    if (orderBySelecionado == null) orderBySelecionado = "id_notas";
    if (directionSelecionada == null) directionSelecionada = "ASC";
    if (buscaAlunoSelecionada == null) buscaAlunoSelecionada = "";
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Minhas Notas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
</head>

<body>
<div class="div2">

    <h2 style="margin-bottom: 15px;">Minhas Notas</h2>

    <form method="get"
          action="${pageContext.request.contextPath}/ServletReadNotas"
          style="margin-bottom: 15px; display:flex; gap:10px; flex-wrap:wrap;">

        <input type="text"
               name="buscaAluno"
               placeholder="Pesquisar professor ou disciplina..."
               value="<%= buscaAlunoSelecionada %>">

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

        <a href="${pageContext.request.contextPath}/ServletReadNotas" class="btn-criar">
            🧹 Limpar
        </a>
    </form>

    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Aluno</th>
            <th>Professor</th>
            <th>Disciplina</th>
            <th>Observação</th>
            <th>Nota 1</th>
            <th>Nota 2</th>
            <th>Média</th>
            <th>Situação</th>
        </tr>
        </thead>

        <tbody>
        <% if (listaNotas != null && !listaNotas.isEmpty()) { %>
        <% for (Notas nota : listaNotas) {
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
            <td><%= nota.getSituacao() ? "Aprovado" : "Reprovado" %></td>
        </tr>
        <% } %>
        <% } else { %>
        <tr>
            <td colspan="9" style="text-align:center; padding:20px;">
                Nenhuma nota encontrada.
            </td>
        </tr>
        <% } %>
        </tbody>
    </table>

</div>
</body>
</html>