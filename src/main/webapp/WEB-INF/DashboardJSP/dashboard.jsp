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
        <a href="${pageContext.request.contextPath}/ServletDashboard" class="active">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M21 6L15.7071 11.2929C15.3166 11.6834 14.6834 11.6834 14.2929 11.2929L12.7071 9.70711C12.3166 9.31658 11.6834 9.31658 11.2929 9.70711L7 14" stroke="white" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M3 3V17.8C3 18.9201 3 19.4802 3.21799 19.908C3.40973 20.2843 3.71569 20.5903 4.09202 20.782C4.51984 21 5.07989 21 6.2 21H21" stroke="white" stroke-linecap="round"/>
            </svg>
            Dashboard</a>
        <a href="${pageContext.request.contextPath}/ServletReadNota">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M8.5 12.5L15.5 12.5" stroke="#214e3b" stroke-linecap="round"/>
                <path d="M8.5 15.5L12.5 15.5" stroke="#214e3b" stroke-linecap="round"/>
                <path d="M5.5 5.9C5.5 5.05992 5.5 4.63988 5.66349 4.31901C5.8073 4.03677 6.03677 3.8073 6.31901 3.66349C6.63988 3.5 7.05992 3.5 7.9 3.5H12.5059C12.8728 3.5 13.0562 3.5 13.2288 3.54145C13.3819 3.57819 13.5282 3.6388 13.6624 3.72104C13.8138 3.8138 13.9435 3.94352 14.2029 4.20294L17.7971 7.79706C18.0565 8.05648 18.1862 8.1862 18.279 8.33757C18.3612 8.47178 18.4218 8.6181 18.4586 8.77115C18.5 8.94378 18.5 9.12723 18.5 9.49411V18.1C18.5 18.9401 18.5 19.3601 18.3365 19.681C18.1927 19.9632 17.9632 20.1927 17.681 20.3365C17.3601 20.5 16.9401 20.5 16.1 20.5H7.9C7.05992 20.5 6.63988 20.5 6.31901 20.3365C6.03677 20.1927 5.8073 19.9632 5.66349 19.681C5.5 19.3601 5.5 18.9401 5.5 18.1V5.9Z" stroke="#214e3b"/>
                <path d="M12.5 3.5V7.1C12.5 7.94008 12.5 8.36012 12.6635 8.68099C12.8073 8.96323 13.0368 9.1927 13.319 9.33651C13.6399 9.5 14.0599 9.5 14.9 9.5H18.5" stroke="#214e3b"/>
            </svg>
            Notas</a>
        <a href="${pageContext.request.contextPath}/ServletReadProfessor">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <circle cx="12" cy="8" r="2.5" stroke="#214e3b" stroke-linecap="round"/>
                <path d="M13.7679 6.5C13.9657 6.15743 14.2607 5.88121 14.6154 5.70625C14.9702 5.5313 15.3689 5.46548 15.7611 5.51711C16.1532 5.56874 16.5213 5.73551 16.8187 5.99632C17.1161 6.25713 17.3295 6.60028 17.4319 6.98236C17.5342 7.36445 17.521 7.76831 17.3939 8.14288C17.2667 8.51745 17.0313 8.8459 16.7175 9.08671C16.4037 9.32751 16.0255 9.46985 15.6308 9.49572C15.2361 9.52159 14.8426 9.42983 14.5 9.23205" stroke="#214e3b"/>
                <path d="M10.2321 6.5C10.0343 6.15743 9.73935 5.88121 9.38458 5.70625C9.02981 5.5313 8.63113 5.46548 8.23895 5.51711C7.84677 5.56874 7.47871 5.73551 7.18131 5.99632C6.88391 6.25713 6.67053 6.60028 6.56815 6.98236C6.46577 7.36445 6.47899 7.76831 6.60614 8.14288C6.73329 8.51745 6.96866 8.8459 7.28248 9.08671C7.5963 9.32751 7.97448 9.46985 8.36919 9.49572C8.76391 9.52159 9.15743 9.42983 9.5 9.23205" stroke="#214e3b"/>
                <path d="M12 12.5C16.0802 12.5 17.1335 15.8022 17.4054 17.507C17.4924 18.0524 17.0523 18.5 16.5 18.5H7.5C6.94771 18.5 6.50763 18.0524 6.59461 17.507C6.86649 15.8022 7.91976 12.5 12 12.5Z" stroke="#214e3b" stroke-linecap="round"/>
                <path d="M19.2965 15.4162L18.8115 15.5377V15.5377L19.2965 15.4162ZM13.0871 12.5859L12.7179 12.2488L12.0974 12.9283L13.0051 13.0791L13.0871 12.5859ZM17.1813 16.5L16.701 16.639L16.8055 17H17.1813V16.5ZM15.5 12C16.5277 12 17.2495 12.5027 17.7783 13.2069C18.3177 13.9253 18.6344 14.8306 18.8115 15.5377L19.7816 15.2948C19.5904 14.5315 19.2329 13.4787 18.578 12.6065C17.9126 11.7203 16.9202 11 15.5 11V12ZM13.4563 12.923C13.9567 12.375 14.6107 12 15.5 12V11C14.2828 11 13.3736 11.5306 12.7179 12.2488L13.4563 12.923ZM13.0051 13.0791C15.3056 13.4614 16.2789 15.1801 16.701 16.639L17.6616 16.361C17.1905 14.7326 16.019 12.5663 13.1691 12.0927L13.0051 13.0791ZM18.395 16H17.1813V17H18.395V16ZM18.8115 15.5377C18.8653 15.7526 18.7075 16 18.395 16V17C19.2657 17 20.0152 16.2277 19.7816 15.2948L18.8115 15.5377Z" fill="#214e3b"/>
                <path d="M10.9129 12.5859L10.9949 13.0791L11.9026 12.9283L11.2821 12.2488L10.9129 12.5859ZM4.70343 15.4162L5.18845 15.5377L4.70343 15.4162ZM6.81868 16.5V17H7.19453L7.29898 16.639L6.81868 16.5ZM8.49999 12C9.38931 12 10.0433 12.375 10.5436 12.923L11.2821 12.2488C10.6264 11.5306 9.71723 11 8.49999 11V12ZM5.18845 15.5377C5.36554 14.8306 5.68228 13.9253 6.22167 13.2069C6.75048 12.5027 7.47226 12 8.49999 12V11C7.0798 11 6.08743 11.7203 5.42199 12.6065C4.76713 13.4787 4.40955 14.5315 4.21841 15.2948L5.18845 15.5377ZM5.60498 16C5.29247 16 5.13465 15.7526 5.18845 15.5377L4.21841 15.2948C3.98477 16.2277 4.73424 17 5.60498 17V16ZM6.81868 16H5.60498V17H6.81868V16ZM7.29898 16.639C7.72104 15.1801 8.69435 13.4614 10.9949 13.0791L10.8309 12.0927C7.98101 12.5663 6.8095 14.7326 6.33838 16.361L7.29898 16.639Z" fill="#214e3b"/>
            </svg>
            Professores</a>
        <a href="${pageContext.request.contextPath}/ServletReadAluno">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M3.84299 8.12534L11.1877 4.86101C11.7049 4.63118 12.2951 4.63118 12.8123 4.86101L20.157 8.12534C20.4817 8.26962 20.4817 8.73038 20.157 8.87466L12.8123 12.139C12.2951 12.3688 11.7049 12.3688 11.1877 12.139L3.84299 8.87466C3.51835 8.73038 3.51835 8.26962 3.84299 8.12534Z" stroke="#214e3b" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M20.5 8.5V12.5" stroke="#214e3b" stroke-linecap="round" stroke-linejoin="round"/>
                <path d="M6.5 10.5V15.5C6.5 15.5 7 17.5 12 17.5C17 17.5 17.5 15.5 17.5 15.5V10.5" stroke="#214e3b" stroke-linecap="round" stroke-linejoin="round"/>
            </svg>
            Alunos</a>
        <a href="${pageContext.request.contextPath}/ServletReadPreMatricula">
            <svg width="24" height="24" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                <path d="M14.5 18.5004H5.9C5.05992 18.5004 4.63988 18.5004 4.31901 18.3369C4.03677 18.1931 3.8073 17.9636 3.66349 17.6814C3.5 17.3605 3.5 16.9405 3.5 16.1004V6.9C3.5 6.05992 3.5 5.63988 3.66349 5.31901C3.8073 5.03677 4.03677 4.8073 4.31901 4.66349C4.63988 4.5 5.05992 4.5 5.9 4.5H8.47237C8.84808 4.5 9.03594 4.5 9.20646 4.55179C9.35741 4.59763 9.49785 4.6728 9.61972 4.77298C9.75739 4.88614 9.86159 5.04245 10.07 5.35507L10.93 6.64533C11.1384 6.95795 11.2426 7.11426 11.3803 7.22742C11.5022 7.3276 11.6426 7.40277 11.7935 7.44861C11.9641 7.5004 12.1519 7.5004 12.5276 7.5004H16.5004C16.965 7.5004 17.1973 7.5004 17.3879 7.55143C17.9058 7.69008 18.3103 8.09459 18.449 8.61248C18.5 8.80308 18.5 9.03539 18.5 9.5M9.5 13.5H16.5" stroke="#214e3b" stroke-linecap="round"/>
                <path d="M5.95298 11.1411C6.14969 10.551 6.24804 10.2559 6.43047 10.0378C6.59157 9.84512 6.79845 9.69601 7.03216 9.6041C7.2968 9.50002 7.60783 9.50003 8.2299 9.50005L18.1703 9.50031C19.2945 9.50034 19.8566 9.50036 20.2255 9.7357C20.5485 9.94171 20.7804 10.2635 20.8737 10.635C20.9803 11.0595 20.8025 11.5928 20.447 12.6593L19.047 16.8593C18.8503 17.4495 18.752 17.7445 18.5695 17.9627C18.4084 18.1553 18.2016 18.3044 17.9679 18.3963C17.7032 18.5004 17.3922 18.5004 16.7702 18.5004H6.82977C5.70557 18.5004 5.14347 18.5004 4.77449 18.2651C4.4515 18.0591 4.21958 17.7373 4.12628 17.3657C4.01969 16.9413 4.19744 16.408 4.55293 15.3415L5.95298 11.1411Z" stroke="#214e3b"/>
            </svg>
            Pré-Matrículas</a>
    </div>

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