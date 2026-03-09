<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    // ===== LÓGICA DO TOPBAR (TÍTULO E NOME DO USUÁRIO) =====
    String tipoUsuarioLogado = (String) session.getAttribute("tipoUsuario");
    String nomeUsuarioLogado = (String) session.getAttribute("nomeUsuario");

    if (nomeUsuarioLogado == null) nomeUsuarioLogado = "Administrador";

    String tituloCentral = "Central do Administrador";
    if ("professor".equalsIgnoreCase(tipoUsuarioLogado)) tituloCentral = "Central do Professor";
    else if ("aluno".equalsIgnoreCase(tipoUsuarioLogado)) tituloCentral = "Central do Aluno";

    // Pega a disciplina selecionada do Servlet para manter o select atualizado
    String disciplinaFiltro = (String) request.getAttribute("disciplinaSelecionada");
    if (disciplinaFiltro == null) disciplinaFiltro = "";

    // ===== LÓGICA DO DASHBOARD (DADOS DO BANCO) =====
    Map<String, Object> resumo = (Map<String, Object>) request.getAttribute("resumo");
    
    // Variáveis com valores padrão caso o banco esteja vazio
    int totalAlunos = 0;
    double mediaEscola = 0.0;
    int alunosRisco = 0;
    int qtdAprovados = 0;
    int qtdReprovados = 0;
    
    // Strings que vão virar Arrays no JavaScript
    StringBuilder labelsDisciplinas = new StringBuilder("[]");
    StringBuilder dataMedias = new StringBuilder("[]");
    String tipoLogado = (String) session.getAttribute("tipoUsuario");

    if (resumo != null) {
        // Extraindo os cards
        if (resumo.get("totalAlunos") != null) totalAlunos = (Integer) resumo.get("totalAlunos");
        if (resumo.get("mediaEscola") != null) mediaEscola = (Double) resumo.get("mediaEscola");
        if (resumo.get("alunosRisco") != null) alunosRisco = (Integer) resumo.get("alunosRisco");
        
        // Extraindo dados do Gráfico de Rosca
        if (resumo.get("qtdAprovados") != null) qtdAprovados = (Integer) resumo.get("qtdAprovados");
        if (resumo.get("qtdReprovados") != null) qtdReprovados = (Integer) resumo.get("qtdReprovados");
        
        // Extraindo e formatando dados do Gráfico de Barras para o JS
        List<String> disciplinas = (List<String>) resumo.get("graficoDisciplinas");
        List<Double> medias = (List<Double>) resumo.get("graficoMedias");
        
        if (disciplinas != null && !disciplinas.isEmpty()) {
            labelsDisciplinas = new StringBuilder("[");
            dataMedias = new StringBuilder("[");
            for (int i = 0; i < disciplinas.size(); i++) {
                labelsDisciplinas.append("'").append(disciplinas.get(i)).append("'");
                dataMedias.append(String.format(java.util.Locale.US, "%.2f", medias.get(i))); // Locale US garante o ponto no decimal
                if (i < disciplinas.size() - 1) {
                    labelsDisciplinas.append(", ");
                    dataMedias.append(", ");
                }
            }
            labelsDisciplinas.append("]");
            dataMedias.append("]");
        }
    }
%>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Dashboard - Nexus</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bases.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tabelas.css">
    
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
    <style>
        .card-dash {
            background: white; 
            padding: 20px; 
            border-radius: 15px; 
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
            transition: transform 0.2s;
        }
        .card-dash:hover {
            transform: translateY(-5px);
        }
        
        .chart-container {
            position: relative; 
            height: 300px; 
            width: 100%;
            min-width: 0;
        }
    </style>
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
        <a href="${pageContext.request.contextPath}/ServletDashboard" class="active">📊 Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota">📝 Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">🧑‍🏫 Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">🎓 Alunos</a>
        <% if (tipoLogado.equalsIgnoreCase("adm")) { %>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">📋 Pré-Matrículas</a>
        <% } %>    </div>

    <div class="conteudo">
        
        <h2 style="color: #214e3b; margin-bottom: 25px;">Resumo Acadêmico</h2>

        <form method="GET" action="${pageContext.request.contextPath}/ServletDashboard" style="display: flex; gap: 10px; margin-bottom: 25px; align-items: center; flex-wrap: wrap;">
            
            <select name="disciplinaFiltro" style="padding: 10px 15px; border-radius: 50px; border: 1px solid #dcdad4; background-color: #edece6; font-size: 14px; color: #214e3b; outline: none; min-width: 250px;">
                <option value="">Todas as Disciplinas</option>
                <option value="Matemática" <%= "Matemática".equals(disciplinaFiltro) ? "selected" : "" %>>Matemática</option>
                <option value="Português" <%= "Português".equals(disciplinaFiltro) ? "selected" : "" %>>Português</option>
                <option value="História" <%= "História".equals(disciplinaFiltro) ? "selected" : "" %>>História</option>
                <option value="Ciências" <%= "Ciências".equals(disciplinaFiltro) ? "selected" : "" %>>Ciências</option>
                <option value="Informática" <%= "Informática".equals(disciplinaFiltro) ? "selected" : "" %>>Informática</option>
            </select>

            <button type="submit" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px;">
                <img src="${pageContext.request.contextPath}/img/iconePesquisa.png" alt="Filtrar" style="width: 18px; height: 18px; object-fit: contain;">
                Filtrar
            </button>
            
            <% if (!disciplinaFiltro.isEmpty()) { %>
            <a href="${pageContext.request.contextPath}/ServletDashboard" class="btn-editar" style="display: inline-flex; align-items: center; justify-content: center; gap: 8px; background-color: #c63b3b; color: white; border: none;">
                <img src="${pageContext.request.contextPath}/img/iconeLimpar.png" alt="Limpar" style="width: 18px; height: 18px; object-fit: contain; filter: brightness(0) invert(1);">
                Limpar
            </a>
            <% } %>
        </form>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(220px, 1fr)); gap: 20px; margin-bottom: 30px;">
            <div class="card-dash" style="border-left: 5px solid #214e3b;">
                <p style="color: #888; margin: 0; font-size: 14px;">Total de Alunos</p>
                <h3 style="margin: 10px 0 0; font-size: 28px; color: #214e3b;"><%= totalAlunos %></h3>
            </div>
            <div class="card-dash" style="border-left: 5px solid #2f7d4a;">
                <p style="color: #888; margin: 0; font-size: 14px;">Média <%= disciplinaFiltro.isEmpty() ? "da Escola" : "em " + disciplinaFiltro %></p>
                <h3 style="margin: 10px 0 0; font-size: 28px; color: #2f7d4a;"><%= String.format("%.1f", mediaEscola) %></h3>
            </div>
            <div class="card-dash" style="border-left: 5px solid #c63b3b;">
                <p style="color: #888; margin: 0; font-size: 14px;">Alunos em Risco</p>
                <h3 style="margin: 10px 0 0; font-size: 28px; color: #c63b3b;"><%= alunosRisco %></h3>
            </div>
        </div>

        <div style="display: grid; grid-template-columns: repeat(auto-fit, minmax(350px, 1fr)); gap: 25px; min-width: 0;">
            
            <div style="background: white; padding: 25px; border-radius: 15px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); min-width: 0;">
                <h4 style="margin: 0 0 20px; color: #214e3b; font-size: 18px;">Média por Disciplina</h4>
                <div class="chart-container">
                    <canvas id="graficoMedia"></canvas>
                </div>
            </div>

            <div style="background: white; padding: 25px; border-radius: 15px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); min-width: 0;">
                <h4 style="margin: 0 0 20px; color: #214e3b; font-size: 18px;">Status de Aprovação</h4>
                <div class="chart-container">
                    <canvas id="graficoStatus"></canvas>
                </div>
            </div>

        </div>

    </div>
</div>

<script>
    const globalOptions = {
        responsive: true,
        maintainAspectRatio: false, 
        plugins: {
            legend: {
                position: 'bottom', 
                labels: { padding: 20, font: { size: 12 } }
            }
        }
    };

    const ctxBar = document.getElementById('graficoMedia').getContext('2d');
    new Chart(ctxBar, {
        type: 'bar',
        data: {
            labels: <%= labelsDisciplinas.toString() %>, 
            datasets: [{
                label: 'Média Atual',
                data: <%= dataMedias.toString() %>, 
                backgroundColor: '#214e3b',
                borderRadius: 10,
                barThickness: 30
            }]
        },
        options: {
            ...globalOptions,
            scales: {
                y: { beginAtZero: true, max: 10, ticks: { stepSize: 2 } },
                x: { grid: { display: false } }
            }
        }
    });

    const ctxPie = document.getElementById('graficoStatus').getContext('2d');
    new Chart(ctxPie, {
        type: 'doughnut',
        data: {
            labels: ['Aprovados', 'Reprovados'],
            datasets: [{
                data: [<%= qtdAprovados %>, <%= qtdReprovados %>], 
                backgroundColor: ['#2f7d4a', '#c63b3b'],
                hoverOffset: 10,
                borderWidth: 0,
                cutout: '70%' 
            }]
        },
        options: globalOptions
    });
</script>

</body>
</html>