<%@ page import="model.Aluno" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Aluno> listaAluno = (List<Aluno>) request.getAttribute("listaAluno");

    String busca = (String) request.getAttribute("buscaSelecionada");
    String orderBy = (String) request.getAttribute("orderBySelecionado");
    String direction = (String) request.getAttribute("directionSelecionada");

    String mensagem = (String) session.getAttribute("mensagem");
    String erro= (String) session.getAttribute("erro");

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
<html lang="pt-BR">
<head>
    <title>Alunos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<header style="display: flex; justify-content: space-between; align-items: center; padding: 20px 30px; background-color: #f4f5f0; border-bottom: 2px solid #e2e1db;">
    <h1 style="color: #214e3b; margin: 0; font-size: 28px; font-weight: bold;"><%= tituloCentral %></h1>
    
    <div style="display: flex; align-items: center; gap: 12px; color: #1a3c2e; font-size: 18px; font-weight: 500;">
        <img src="${pageContext.request.contextPath}/img/iconePerfil.png" alt="Perfil" style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover;">
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
                    <option value="nome" <%= orderBy.equals("nome") ? "selected" : "" %>>Ordenar por Nome</option>
                    <option value="turma" <%= orderBy.equals("turma") ? "selected" : "" %>>Ordenar por Turma</option>
                    <option value="email" <%= orderBy.equals("email") ? "selected" : "" %>>Ordenar por Email</option>
                </select>

                <select name="direction" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="ASC"  <%= direction.equalsIgnoreCase("ASC")  ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= direction.equalsIgnoreCase("DESC") ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" alt="Pesquisar" style="width: 18px; height: 18px; object-fit: contain;">
                    Filtrar
                </button>

                <a href="${pageContext.request.contextPath}/ServletReadAluno" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                    <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" alt="Limpar" style="width: 18px; height: 18px; object-fit: contain;">
                    Limpar
                </a>

                <a href="${pageContext.request.contextPath}/ServletCreateAluno" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px; background-color: #214e3b; color: white;">
                    <img src="${pageContext.request.contextPath}/img/iconeAdicionar.png" alt="Novo Aluno" style="width: 18px; height: 18px; object-fit: contain;">
                    Novo Aluno
                </a>

            </form>

            <div class="tabela-responsiva">
                <table>
                    <thead>
                    <tr>
                        <th>    </th>
                        <th>Matrícula</th>
                        <th>Nome</th>
                        <th>Email</th>
                        <th>CPF</th>
                        <th>Turma</th>
                        <th style="text-align: center;">Ações</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (!listaAluno.isEmpty()) {
                        for (Aluno aluno : listaAluno) { %>
                    <tr>
                        <td>
                            <img src="<%= aluno.getFoto() %>" alt="Foto" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover;">
                        </td>
                        <td><%= aluno.getMatricula() %></td>
                        <td><%= aluno.getNome() %></td>
                        <td><%= aluno.getEmail() %></td>
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