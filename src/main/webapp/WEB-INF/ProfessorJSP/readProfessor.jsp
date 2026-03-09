<%@ page import="java.util.List" %>
<%@ page import="model.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Professor> listaProfessor = (List<Professor>) request.getAttribute("listaProfessor");

    String busca = (String) request.getAttribute("buscaSelecionada");
    String orderBy = (String) request.getAttribute("orderBySelecionado");
    String direction = (String) request.getAttribute("directionSelecionada");

    String mensagem = (String) session.getAttribute("mensagem");
    String erro = (String) session.getAttribute("erro");

    session.removeAttribute("mensagem");
    session.removeAttribute("erro");

    if (listaProfessor == null) listaProfessor = new java.util.LinkedList<>();

    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");
    if (nomeUsuarioLogado == null) nomeUsuarioLogado = "Administrador";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Professores - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<header class="header">
    <h1>Central do Administrador</h1>
    <div class="usuario">
        <img src="${pageContext.request.contextPath}/img/iconePerfil.png" alt="Perfil" class="foto-perfil">
        <span><%= nomeUsuarioLogado %></span>
    </div>
</header>

<div class="layout-adm">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletDashboard">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="active">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">

        <h2 class="titulo-sessao">Professores</h2>

        <% if (mensagem != null) { %><p class="msg-sucesso">✔ <%= mensagem %></p><% } %>
        <% if (erro != null) { %><p class="msg-erro">⚠ <%= erro %></p><% } %>

        <div class="div2">

            <form method="get" action="${pageContext.request.contextPath}/ServletReadProfessor" class="form-alinhado">

                <div class="busca-box">
                    <input type="text" name="busca" placeholder="Pesquisar por nome..." value="<%= (busca != null) ? busca : "" %>">
                </div>

                <select name="orderBy" class="select-custom">
                    <option value="id_professor" <%= "id_professor".equals(orderBy) ? "selected" : "" %>>Ordenar por ID</option>
                    <option value="nome"         <%= "nome".equals(orderBy)         ? "selected" : "" %>>Ordenar por Nome</option>
                    <option value="email"        <%= "email".equals(orderBy)        ? "selected" : "" %>>Ordenar por Email</option>
                </select>

                <select name="direction" class="select-custom">
                    <option value="ASC"  <%= "ASC".equalsIgnoreCase(direction)  ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= "DESC".equalsIgnoreCase(direction) ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" alt="Pesquisar" width="18">
                    Filtrar
                </button>

                <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="btn-editar btn-link">
                    <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" alt="Limpar" width="18">
                    Limpar
                </a>

                <a href="${pageContext.request.contextPath}/ServletCreateProfessor" class="btn-editar btn-link btn-novo-registro">
                    <img src="${pageContext.request.contextPath}/img/iconeAdicionar.png" alt="Novo" width="18">
                    Novo Professor
                </a>

            </form>

            <div class="tabela-responsiva">
                <table>
                    <thead>
                    <tr>
                        <th class="col-foto">Foto</th>
                        <th class="col-id">ID</th>
                        <th>Nome</th>
                        <th>Disciplina</th>
                        <th>Email</th>
                        <th>Senha</th>
                        <th class="col-acoes">Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (!listaProfessor.isEmpty()) {
                        for (Professor professor : listaProfessor) { %>
                    <tr>
                        <td><img src="<%= professor.getFoto() %>" alt="Foto" class="foto-tabela"></td>
                        <td><%= professor.getIdProfessor() %></td>
                        <td><%= professor.getNome() %></td>
                        <td><%= professor.getDisciplina() %></td>
                        <td><%= professor.getEmail() %></td>
                        <td><%= professor.getSenha() %></td>
                        <td class="acoes">
                            <a href="${pageContext.request.contextPath}/ServletUpdateProfessor?id=<%= professor.getIdProfessor() %>" class="btn-editar btn-link">
                                <img src="${pageContext.request.contextPath}/img/iconeUpdate.png" alt="Editar" width="16">
                                Editar
                            </a>
                            <button class="btn-excluir btn-icone"
                                    onclick="if(confirm('Remover professor <%= professor.getNome() %>?')) window.location='${pageContext.request.contextPath}/ServletDeleteProfessor?id=<%= professor.getIdProfessor() %>'">
                                <img src="${pageContext.request.contextPath}/img/iconeDelete.png" alt="Excluir" width="16">
                            </button>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="7" class="td-vazio">Nenhum professor encontrado.</td>
                    </tr>
                    <% } %>
                    </tbody>
                </table>
            </div>

        </div>
    </div>
</div>

</body>
</html>
