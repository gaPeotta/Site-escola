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

    String tipoUsuarioLogado = (String) session.getAttribute("tipoUsuario");
    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");

    if (nomeUsuarioLogado == null) nomeUsuarioLogado = "Administrador";

    String tituloCentral = "Central do Aluno";
    if ("adm".equalsIgnoreCase(tipoUsuarioLogado)) tituloCentral = "Central do Administrador";
    else if ("professor".equalsIgnoreCase(tipoUsuarioLogado)) tituloCentral = "Central do Professor";
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Alunos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<header class="header">
    <h1><%= tituloCentral %></h1>
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
        <a href="${pageContext.request.contextPath}/ServletReadAluno" class="active">🎓 Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
    </div>

    <div class="conteudo">

        <h2 class="titulo-sessao">Alunos</h2>

        <% if (mensagem != null) { %><p class="msg-sucesso">✔ <%= mensagem %></p><% } %>
        <% if (erro != null) { %><p class="msg-erro">⚠ <%= erro %></p><% } %>

        <div class="div2">

            <form method="get" action="${pageContext.request.contextPath}/ServletReadAluno" class="form-alinhado">

                <div class="busca-box">
                    <input type="text" name="busca" placeholder="Pesquisar por nome..." value="<%= busca %>">
                </div>

                <select name="orderBy" class="select-custom">
                    <option value="matricula" <%= orderBy.equals("matricula") ? "selected" : "" %>>Ordenar por Matrícula</option>
                    <option value="nome"      <%= orderBy.equals("nome")      ? "selected" : "" %>>Ordenar por Nome</option>
                    <option value="turma"     <%= orderBy.equals("turma")     ? "selected" : "" %>>Ordenar por Turma</option>
                    <option value="email"     <%= orderBy.equals("email")     ? "selected" : "" %>>Ordenar por Email</option>
                </select>

                <select name="direction" class="select-custom">
                    <option value="ASC"  <%= direction.equalsIgnoreCase("ASC")  ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= direction.equalsIgnoreCase("DESC") ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" alt="Pesquisar" width="18">
                    Filtrar
                </button>

                <a href="${pageContext.request.contextPath}/ServletReadAluno" class="btn-editar btn-link">
                    <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" alt="Limpar" width="18">
                    Limpar
                </a>

                <a href="${pageContext.request.contextPath}/ServletCreateAluno" class="btn-editar btn-link btn-novo-registro">
                    <img src="${pageContext.request.contextPath}/img/iconeAdicionar.png" alt="Novo Aluno" width="18">
                    Novo Aluno
                </a>

            </form>

            <div class="tabela-responsiva">
                <table>
                    <thead>
                    <tr>
                        <th class="col-foto"></th>
                        <th>Matrícula</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>Senha</th>
                        <th>CPF</th>
                        <th>Turma</th>
                        <th class="col-acoes">Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (!listaAluno.isEmpty()) {
                        for (Aluno aluno : listaAluno) { %>
                    <tr>
                        <td><img src="<%= aluno.getFoto() %>" alt="Foto" class="foto-tabela"></td>
                        <td><%= aluno.getMatricula() %></td>
                        <td><%= aluno.getNome() %></td>
                        <td><%= aluno.getEmail() %></td>
                        <td><%= aluno.getSenha()%></td>
                        <td><%= aluno.getCpf() %></td>
                        <td><%= aluno.getTurma() %></td>
                        
                        <td style="text-align: center; vertical-align: middle;">
                            <div style="display: flex; gap: 8px; justify-content: center; align-items: center;">
                                <a href="${pageContext.request.contextPath}/ServletUpdateAluno?matricula=<%= aluno.getMatricula() %>" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 5px;">
                                    <img src="${pageContext.request.contextPath}/img/iconeUpdate.png" alt="Editar" style="width: 16px; height: 16px; object-fit: contain;">
                                    Editar
                                </a>

                                <a href="${pageContext.request.contextPath}/ServletDeleteAluno?matricula=<%= aluno.getMatricula() %>" 
                                   class="btn-editar" 
                                   style="display: inline-flex; align-items: center; justify-content: center; padding: 5px 8px; background-color: #c63b3b; color: white; border: none;"
                                   onclick="return confirm('Deseja realmente remover o aluno <%= aluno.getNome() %>?');">
                                    <img src="${pageContext.request.contextPath}/img/iconeDelete.png" alt="Excluir" style="width: 16px; height: 16px; object-fit: contain;">
                                </a>
                            </div>
                        </td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="7" style="text-align:center; padding:20px; color:#888;">
                            Nenhum aluno encontrado.
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
