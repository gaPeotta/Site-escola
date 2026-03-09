<%@ page import="java.util.List" %>
<%@ page import="model.PreMatricula" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<PreMatricula> lista = (List<PreMatricula>) request.getAttribute("listaPreMatricula");
    String buscaCpf  = (String) request.getAttribute("buscaCpfSelecionada");
    String orderBy   = (String) request.getAttribute("orderBySelecionado");
    String direction = (String) request.getAttribute("directionSelecionada");
    String mensagem  = (String) session.getAttribute("mensagem");
    String erro      = (String) session.getAttribute("erro");

    session.removeAttribute("mensagem");
    session.removeAttribute("erro");

    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");
    if (nomeUsuarioLogado == null) nomeUsuarioLogado = "Administrador";
%>
<html lang="pt-BR">
<head>
    <title>Pré-Matrículas</title>
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
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula" class="active">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">
        <h2 class="titulo-sessao">Pré-Matrículas Pendentes</h2>

        <% if (mensagem != null) { %><p class="msg-sucesso">✔ <%= mensagem %></p><% } %>
        <% if (erro != null) { %><p class="msg-erro">⚠ <%= erro %></p><% } %>

        <div class="div2">
            <form method="get" action="${pageContext.request.contextPath}/ServletReadPreMatricula" class="form-alinhado">
                <div class="busca-box">
                    <input type="text" name="buscaCpf" placeholder="Pesquisar por CPF..." value="<%= (buscaCpf != null) ? buscaCpf : "" %>">
                </div>

                <select name="orderBy" class="select-custom">
                    <option value="id_prematricula" <%= "id_prematricula".equals(orderBy) ? "selected" : "" %>>ID</option>
                    <option value="cpf" <%= "cpf".equals(orderBy) ? "selected" : "" %>>CPF</option>
                </select>

                <select name="direction" class="select-custom">
                    <option value="ASC"  <%= "ASC".equalsIgnoreCase(direction) ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= "DESC".equalsIgnoreCase(direction) ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" width="18"> Filtrar
                </button>

                <a href="${pageContext.request.contextPath}/ServletReadPreMatricula" class="btn-editar btn-link">
                    <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" width="18"> Limpar
                </a>

                <a href="${pageContext.request.contextPath}/ServletCreatePreMatricula" class="btn-editar btn-link btn-novo-registro">
                    <img src="${pageContext.request.contextPath}/img/iconeAdicionar.png" width="18"> Nova Pré-Matrícula
                </a>
            </form>

            <div class="tabela-responsiva">
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>CPF</th>
                        <th style="text-align: center;">Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (lista != null && !lista.isEmpty()) {
                        for (PreMatricula pre : lista) { %>
                    <tr>
                        <td><%= pre.getId_prematricula() %></td>
                        <td><%= pre.getCpf() %></td>
                        <td class="acoes">
                            <a href="${pageContext.request.contextPath}/ServletUpdatePreMatricula?id=<%= pre.getId_prematricula() %>" class="btn-editar btn-link">
                                <img src="${pageContext.request.contextPath}/img/iconeUpdate.png" width="16"> Editar
                            </a>
                            <button class="btn-excluir"
                                    onclick="if(confirm('Remover CPF <%= pre.getCpf() %>?')) window.location='${pageContext.request.contextPath}/ServletDeletePreMatricula?cpf=<%= pre.getCpf() %>'">
                                <img src="${pageContext.request.contextPath}/img/iconeDelete.png" width="16">
                            </button>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="3" style="text-align:center; padding:20px; color:#888;">Nenhuma pré-matrícula encontrada.</td>
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