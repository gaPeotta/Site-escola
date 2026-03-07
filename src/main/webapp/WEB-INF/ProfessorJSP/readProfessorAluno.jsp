<%@ page import="java.util.List" %>
<%@ page import="model.Professor" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    List<Professor> listaProfessor = (List<Professor>) request.getAttribute("listaProfessor");

    String busca= (String) request.getAttribute("buscaSelecionada");
    String orderBy = (String) request.getAttribute("orderBySelecionado");
    String direction = (String) request.getAttribute("directionSelecionada");

    String mensagem  = (String) session.getAttribute("mensagem");
    String erro = (String) session.getAttribute("erro");

    session.removeAttribute("mensagem");
    session.removeAttribute("erro");

    if (listaProfessor == null) listaProfessor = new java.util.LinkedList<>();
    if (busca == null) busca = "";
    if (orderBy == null) orderBy = "id_professor";
    if (direction == null) direction = "ASC";

    // ===== LÓGICA DO TOPBAR (TÍTULO E NOME DO USUÁRIO) =====
    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");
    if (nomeUsuarioLogado == null) nomeUsuarioLogado = "Aluno";
%>
<html lang="pt-BR">
<head>
    <title>Professores</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
</head>
<body>

<header style="display: flex; justify-content: space-between; align-items: center; padding: 20px 30px; background-color: #f4f5f0; border-bottom: 2px solid #e2e1db;">
    <h1 style="color: #214e3b; margin: 0; font-size: 28px; font-weight: bold;">Central do Aluno</h1>
    
    <div style="display: flex; align-items: center; gap: 12px; color: #1a3c2e; font-size: 18px; font-weight: 500;">
        <img src="${pageContext.request.contextPath}/img/iconePerfil.png" alt="Perfil" style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover;">
        <span><%= nomeUsuarioLogado %></span>
    </div>
</header>

<div class="layout-adm">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="active">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 20px;">Professores</h2>

        <% if (mensagem != null) { %>
        <p style="color: #2f7d4a; font-weight: bold; margin-bottom: 15px;">✔ <%= mensagem %></p>
        <% } %>
        <% if (erro != null) { %>
        <p style="color: #c63b3b; font-weight: bold; margin-bottom: 15px;">⚠ <%= erro %></p>
        <% } %>

        <div class="div2">

            <form method="get"
                  action="${pageContext.request.contextPath}/ServletReadProfessor"
                  style="display:flex; gap:10px; flex-wrap:wrap; align-items:center; margin-bottom: 20px;">

                <div class="busca-box">
                    <input type="text"
                           name="busca"
                           placeholder="Pesquisar por nome..."
                           value="<%= busca %>">
                </div>

                <select name="orderBy" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="id_professor" <%= orderBy.equals("id_professor") ? "selected" : "" %>>Ordenar por ID</option>
                    <option value="nome" <%= orderBy.equals("nome") ? "selected" : "" %>>Ordenar por Nome</option>
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

                <a href="${pageContext.request.contextPath}/ServletReadProfessor" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                    <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" alt="Limpar" style="width: 18px; height: 18px; object-fit: contain;">
                    Limpar
                </a>

            </form>

            <div class="tabela-responsiva">
                <table>
                    <thead>
                    <tr>
                        <th>    </th>
                        <th>ID</th>
                        <th>Nome</th>
                        <th>Disciplina</th>
                        <th>Email</th>
                    </tr>
                    </thead>
                    <tbody>
                    <% if (!listaProfessor.isEmpty()) {
                        for (Professor professor : listaProfessor) { %>
                    <tr>
                        <td>
                            <img src="<%= professor.getFoto() %>" alt="Foto" style="width: 50px; height: 50px; border-radius: 50%; object-fit: cover;">
                        </td>
                        <td><%= professor.getIdProfessor() %></td>
                        <td><%= professor.getNome() %></td>
                        <td><%= professor.getDisciplina() %></td>
                        <td><%= professor.getEmail() %></td>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="4" style="text-align:center; padding:20px; color:#888;">
                            Nenhum professor encontrado.
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