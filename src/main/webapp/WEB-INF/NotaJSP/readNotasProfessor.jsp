<%@ page import="java.util.List" %>
<%@ page import="model.Notas" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Notas> listaNotas = (List<Notas>) request.getAttribute("listaNotas");
    String orderBy = (String) request.getAttribute("orderBySelecionado");
    String direction = (String) request.getAttribute("directionSelecionada");
    String buscaAluno = request.getParameter("buscaAluno");
    String tipoLogado = (String) session.getAttribute("tipoUsuario");

    if (orderBy == null) orderBy = "id_notas";
    if (direction == null) direction = "ASC";
    if (buscaAluno == null) buscaAluno = "";

    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");
    String tituloCentral = "Central do Aluno";
    if ("adm".equalsIgnoreCase(tipoLogado)) tituloCentral = "Central do Administrador";
    else if ("professor".equalsIgnoreCase(tipoLogado)) tituloCentral = "Central do Professor";
%>
<html lang="pt-BR">
<head>
    <title>Notas</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<header class="header">
    <h1><%= tituloCentral %></h1>
    <div class="usuario">
        <img src="${pageContext.request.contextPath}/img/iconePerfil.png" alt="Perfil" class="foto-perfil">
        <span><%= (nomeUsuarioLogado != null) ? nomeUsuarioLogado : "Usuário" %></span>
    </div>
</header>

<div class="layout-adm">
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletDashboard">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota" class="active">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <% if ("adm".equalsIgnoreCase(tipoLogado)) { %>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
        <% } %>
    </div>

    <div class="conteudo">
        <h2 class="titulo-sessao">Notas</h2>

        <div class="div2">
            <form method="get" action="${pageContext.request.contextPath}/ServletReadNota" class="form-alinhado">
                <div class="busca-box">
                    <input type="text" name="buscaAluno" placeholder="Aluno ou disciplina..." value="<%= buscaAluno %>">
                </div>

                <select name="orderBy" class="select-custom">
                    <option value="id_notas" <%= "id_notas".equals(orderBy) ? "selected" : "" %>>ID</option>
                    <option value="disciplina" <%= "disciplina".equals(orderBy) ? "selected" : "" %>>Disciplina</option>
                </select>

                <select name="direction" class="select-custom">
                    <option value="ASC"  <%= "ASC".equalsIgnoreCase(direction)  ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= "DESC".equalsIgnoreCase(direction) ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" width="18"> Filtrar
                </button>

                <% if ("adm".equalsIgnoreCase(tipoLogado) || "professor".equalsIgnoreCase(tipoLogado)) { %>
                <a href="${pageContext.request.contextPath}/ServletReadNota?view=create" class="btn-editar btn-link btn-novo-registro">
                    <img src="${pageContext.request.contextPath}/img/iconeAdicionar.png" width="18"> Nova Nota
                </a>
                <% } %>
            </form>

            <div class="tabela-responsiva">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Matricula</th>
                        <th>Aluno</th>
                        <th>Disciplina</th>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                        <th>Média</th>
                        <th>Situação</th>
                        <% if ("adm".equalsIgnoreCase(tipoLogado) || "professor".equalsIgnoreCase(tipoLogado)) { %>
                        <th style="text-align: center;">Ações</th>
                        <% } %>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (listaNotas != null) {
                        for (Notas nota : listaNotas) {
                            double media = (nota.getNota1() + nota.getNota2()) / 2.0;
                    %>
                    <tr>
                        <td><%= nota.getIdNotas() %></td>
                        <td><%= nota.getMatriculaAluno() %></td>
                        <td><%= nota.getNomeAluno() %></td>
                        <td><%= nota.getDisciplina() %></td>
                        <td><%= nota.getNota1() %></td>
                        <td><%= nota.getNota2() %></td>
                        <td><%= String.format("%.2f", media) %></td>
                        <td class="<%= media >= 7 ? "status-aprovado" : "status-reprovado" %>">
                            <%= nota.getSituacao() ? "✔ Aprovado" : "✖ Reprovado" %>
                        </td>
                        <% if ("adm".equalsIgnoreCase(tipoLogado) || "professor".equalsIgnoreCase(tipoLogado)) { %>
                        <td class="acoes">
                            <a href="ServletReadNota?view=update&id=<%= nota.getIdNotas() %>" class="btn-editar btn-link">✏</a>
                            <button class="btn-excluir" onclick="if(confirm('Excluir nota?')) window.location='ServletDeleteNota?id=<%= nota.getIdNotas() %>'">🗑</button>
                        </td>
                        <% } %>
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