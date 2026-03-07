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

    if (buscaCpf  == null) buscaCpf  = "";
    if (orderBy   == null) orderBy   = "id_prematricula";
    if (direction == null) direction = "ASC";

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

<header style="display: flex; justify-content: space-between; align-items: center; padding: 20px 30px; background-color: #f4f5f0; border-bottom: 2px solid #e2e1db;">
    <h1 style="color: #214e3b; margin: 0; font-size: 28px; font-weight: bold;">Central do Administrador</h1>
    
    <div style="display: flex; align-items: center; gap: 12px; color: #1a3c2e; font-size: 18px; font-weight: 500;">
        <img src="${pageContext.request.contextPath}/img/iconePerfil.png" alt="Perfil" style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover;">
        <span><%= nomeUsuarioLogado %></span>
    </div>
</header>

<div class="layout-adm">

    <%-- ===== SIDEBAR ===== --%>
    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletDashboard">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula" class="active">📋 Pré-Matrículas</a>
    </div>

    <%-- ===== CONTEÚDO ===== --%>
    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 20px;">Pré-Matrículas Pendentes</h2>

        <%-- ===== FEEDBACK ===== --%>
        <% if (mensagem != null) { %>
        <p style="color: #2f7d4a; font-weight: bold; margin-bottom: 15px;">✔ <%= mensagem %></p>
        <% } %>
        <% if (erro != null) { %>
        <p style="color: #c63b3b; font-weight: bold; margin-bottom: 15px;">⚠ <%= erro %></p>
        <% } %>

        <div class="div2">

            <%-- ===== FILTROS + BOTÃO NOVO ===== --%>
            <form method="get"
                  action="${pageContext.request.contextPath}/ServletReadPreMatricula"
                  style="display:flex; gap:10px; flex-wrap:wrap; align-items:center; margin-bottom: 20px;">

                <div class="busca-box">
                    <input type="text"
                           name="buscaCpf"
                           placeholder="Pesquisar por CPF..."
                           value="<%= buscaCpf %>">
                </div>

                <select name="orderBy" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="id_prematricula" <%= orderBy.equals("id_prematricula") ? "selected" : "" %>>Ordenar por ID</option>
                    <option value="cpf"             <%= orderBy.equals("cpf") ? "selected" : "" %>>Ordenar por CPF</option>
                </select>

                <select name="direction" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="ASC"  <%= direction.equalsIgnoreCase("ASC") ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= direction.equalsIgnoreCase("DESC") ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" alt="Pesquisar" style="width: 18px; height: 18px; object-fit: contain;">
                    Filtrar
                </button>

                <a href="${pageContext.request.contextPath}/ServletReadPreMatricula" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                    <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" alt="Limpar" style="width: 18px; height: 18px; object-fit: contain;">
                    Limpar
                </a>

                <a href="${pageContext.request.contextPath}/ServletCreatePreMatricula" class="btn-editar" style="margin-left: auto; display: inline-flex; align-items: center; justify-content: center; gap: 8px; background-color: #214e3b; color: white;">
                    <img src="${pageContext.request.contextPath}/img/iconeAdicionar.png" alt="Nova Pré-Matrícula" style="width: 18px; height: 18px; object-fit: contain;">
                    Nova Pré-Matrícula
                </a>

            </form>

            <%-- ===== TABELA ===== --%>
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
                        <td style="display: flex; gap: 8px; justify-content: center;">
                            <a href="${pageContext.request.contextPath}/ServletUpdatePreMatricula?id=<%= pre.getId_prematricula() %>"
                               class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 5px;">
                                <img src="${pageContext.request.contextPath}/img/iconeUpdate.png" alt="Editar" style="width: 16px; height: 16px; object-fit: contain;">
                                Editar
                            </a>

                            <button class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; padding: 5px 8px; background-color: #c63b3b; color: white; border: none; cursor: pointer;"
                                    onclick="if(confirm('Remover CPF <%= pre.getCpf() %> da pré-matrícula?'))
                                             window.location='${pageContext.request.contextPath}/ServletDeletePreMatricula?cpf=<%= pre.getCpf() %>'">
                                <img src="${pageContext.request.contextPath}/img/iconeDelete.png" alt="Excluir" style="width: 16px; height: 16px; object-fit: contain;">
                            </button>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="3" style="text-align:center; padding:20px; color:#888;">
                            Nenhuma pré-matrícula encontrada.
                        </td>
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