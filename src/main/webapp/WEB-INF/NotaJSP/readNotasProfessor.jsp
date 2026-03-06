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

    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");
    if (nomeUsuarioLogado == null) nomeUsuarioLogado = "Administrador";

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

<header style="display: flex; justify-content: space-between; align-items: center; padding: 20px 30px; background-color: #f4f5f0; border-bottom: 2px solid #e2e1db;">
    <h1 style="color: #214e3b; margin: 0; font-size: 28px; font-weight: bold;"><%= tituloCentral %></h1>
    
    <div style="display: flex; align-items: center; gap: 12px; color: #1a3c2e; font-size: 18px; font-weight: 500;">
        <img src="${pageContext.request.contextPath}/img/iconePerfil.png" alt="Perfil" style="width: 40px; height: 40px; border-radius: 50%; object-fit: cover;">
        <span><%= nomeUsuarioLogado %></span>
    </div>
</header>

<div class="layout-adm">

    <div class="sidebar">
        <a href="${pageContext.request.contextPath}/ServletReadNota" class="active">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <% if (tipoLogado.equalsIgnoreCase("adm")) { %>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
        <% } %>    
    </div>

    <div class="conteudo">

        <h2 style="color: #214e3b; margin-bottom: 20px;">Notas</h2>

        <% if (mensagem != null) { %>
        <p style="color: #2f7d4a; font-weight: bold; margin-bottom: 15px;">✔ <%= mensagem %></p>
        <% } %>
        <% if (erro != null) { %>
        <p style="color: #c63b3b; font-weight: bold; margin-bottom: 15px;">⚠ <%= erro %></p>
        <% } %>

        <div class="div2">

            <form method="get" action="${pageContext.request.contextPath}/ServletReadNota"
                  style="display:flex; gap:10px; flex-wrap:wrap; align-items:center; margin-bottom: 20px;">

                <div class="busca-box">
                    <input type="text"
                           name="buscaAluno"
                           placeholder="Pesquisar aluno ou disciplina..."
                           value="<%= buscaAluno %>">
                </div>

                <select name="orderBy" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="id_notas"   <%= orderBySelecionado.equals("id_notas")   ? "selected" : "" %>>Ordenar por ID</option>
                    <option value="disciplina" <%= orderBySelecionado.equals("disciplina") ? "selected" : "" %>>Ordenar por Disciplina</option>
                    <option value="nota1"      <%= orderBySelecionado.equals("nota1")      ? "selected" : "" %>>Ordenar por Nota 1</option>
                    <option value="nota2"      <%= orderBySelecionado.equals("nota2")      ? "selected" : "" %>>Ordenar por Nota 2</option>
                </select>

                <select name="direction" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b;">
                    <option value="ASC"  <%= directionSelecionada.equalsIgnoreCase("ASC")  ? "selected" : "" %>>Crescente</option>
                    <option value="DESC" <%= directionSelecionada.equalsIgnoreCase("DESC") ? "selected" : "" %>>Decrescente</option>
                </select>

                <button type="submit" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                    <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" alt="Pesquisar" style="width: 18px; height: 18px; object-fit: contain;">
                    Filtrar
                </button>

                <a href="${pageContext.request.contextPath}/ServletReadNota" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                    <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" alt="Limpar" style="width: 18px; height: 18px; object-fit: contain;">
                    Limpar
                </a>

                <% if (tipoLogado.equalsIgnoreCase("adm") || tipoLogado.equalsIgnoreCase("professor")) { %>
                <a href="${pageContext.request.contextPath}/ServletReadNota?view=create"
                   class="btn-editar" style="margin-left: auto; display: inline-flex; align-items: center; justify-content: center; gap: 8px; background-color: #214e3b; color: white;">
                    <img src="${pageContext.request.contextPath}/img/iconeAdicionar.png" alt="Nova Nota" style="width: 18px; height: 18px; object-fit: contain;">
                    Nova Nota
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
                        <th>Professor</th>
                        <th>Disciplina</th>
                        <th>Observação</th>
                        <th>Nota 1</th>
                        <th>Nota 2</th>
                        <th>Média</th>
                        <th>Situação</th>
                        <% if (tipoLogado.equalsIgnoreCase("adm") || tipoLogado.equalsIgnoreCase("professor")) { %>
                        <th style="text-align: center;">Ações</th>
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
                        <td><%= nota.getMatriculaAluno() %></td>
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
                        <td style="display: flex; gap: 8px; justify-content: center;">
                            <a href="${pageContext.request.contextPath}/ServletReadNota?view=update&id=<%= nota.getIdNotas() %>"
                               class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 5px;">
                                <img src="${pageContext.request.contextPath}/img/iconeUpdate.png" alt="Editar" style="width: 16px; height: 16px; object-fit: contain;">
                                Editar
                            </a>
                            <button class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; padding: 5px 8px; background-color: #c63b3b; color: white; border: none; cursor: pointer;"
                                    onclick="if(confirm('Excluir esta nota?'))
                                             window.location='${pageContext.request.contextPath}/ServletDeleteNota?id=<%= nota.getIdNotas() %>'">
                                <img src="${pageContext.request.contextPath}/img/iconeDelete.png" alt="Excluir" style="width: 16px; height: 16px; object-fit: contain;">
                            </button>
                        </td>
                        <% } %>
                    </tr>
                    <% } } else { %>
                    <tr>
                        <td colspan="<%= (tipoLogado.equalsIgnoreCase("adm") || tipoLogado.equalsIgnoreCase("professor")) ? 11 : 10 %>"
                            style="text-align:center; padding:20px; color:#888;">
                            Nenhuma nota encontrada.
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