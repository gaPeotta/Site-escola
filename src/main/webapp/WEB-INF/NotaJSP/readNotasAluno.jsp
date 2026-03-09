<%@ page import="java.util.List" %>
<%@ page import="model.Notas" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Notas> listaNotas = (List<Notas>) request.getAttribute("listaNotas");
    String orderBy = (String) request.getAttribute("orderBySelecionado");
    String direction = (String) request.getAttribute("directionSelecionada");
    String buscaAluno = request.getParameter("buscaAluno");

    if (orderBy == null) orderBy = "id_notas";
    if (direction == null) direction = "ASC";
    if (buscaAluno == null) buscaAluno = "";

    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");
    if (nomeUsuarioLogado == null) nomeUsuarioLogado = "Aluno";
%>
<html lang="pt-BR">
<head>
    <title>Minhas Notas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<header class="header">
    <h1>Central do Aluno</h1>
    <div class="usuario">
        <img src="${pageContext.request.contextPath}/img/iconePerfil.png" alt="Perfil" class="foto-perfil">
        <span><%= nomeUsuarioLogado %></span>
    </div>
</header>

<div class="layout-adm">
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletReadNota" class="active">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
    </div>

    <div class="conteudo">
        <h2 class="titulo-sessao">Minhas Notas</h2>

        <div class="div2">
            <form method="get" action="${pageContext.request.contextPath}/ServletReadNotas" class="form-alinhado">
                <div class="busca-box">
                    <input type="text" name="buscaAluno" placeholder="Professor ou disciplina..." value="<%= buscaAluno %>">
                </div>

                <select name="orderBy" class="select-custom">
                    <option value="id_notas" <%= "id_notas".equals(orderBy)? "selected" : "" %>>ID</option>
                    <option value="disciplina" <%= "disciplina".equals(orderBy) ? "selected" : "" %>>Disciplina</option>
                </select>

                <select name="direction" class="select-custom">
                    <option value="ASC" <%= "ASC".equalsIgnoreCase(direction) ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= "DESC".equalsIgnoreCase(direction) ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" width="18"> Filtrar
                </button>
            </form>

            <div class="tabela-responsiva">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Professor</th>
                        <th>Disciplina</th>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                        <th>Média</th>
                        <th>Situação</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listaNotas != null) {
                        for (Notas nota : listaNotas) {
                            double media = (nota.getNota1() + nota.getNota2()) / 2.0;
                    %>
                    <tr>
                        <td><%= nota.getIdNotas() %></td>
                        <td><%= nota.getNomeProfessor() %></td>
                        <td><%= nota.getDisciplina() %></td>
                        <td><%= nota.getNota1() %></td>
                        <td><%= nota.getNota2() %></td>
                        <td><%= String.format("%.2f", media) %></td>
                        <td class="<%= media >= 7 ? "status-aprovado" : "status-reprovado" %>">
                            <%= nota.getSituacao() ? "✔ Aprovado" : "✖ Reprovado" %>
                        </td>
                    </tr>
                    <% } } %>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>