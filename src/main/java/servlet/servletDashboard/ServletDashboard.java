package servlet.servletDashboard;

import Dao.DashboardDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

@WebServlet("/ServletDashboard")
public class ServletDashboard extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // 1. Segurança 
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("tipoUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        if ("aluno".equals(tipoUsuario)) {
            response.sendRedirect(request.getContextPath() + "/ServletReadNota");
            return;
        }

        // 2. CAPTURA O FILTRO DA TELA
        String disciplinaFiltro = request.getParameter("disciplinaFiltro");

        // 3. BUSCA DE DADOS COM O FILTRO
        DashboardDAO dao = new DashboardDAO();
        Map<String, Object> resumo = dao.getResumoDashboard(disciplinaFiltro);
        
        request.setAttribute("resumo", resumo);
        
        request.setAttribute("disciplinaSelecionada", disciplinaFiltro != null ? disciplinaFiltro : "");

        // 4. DIRECIONAMENTO
        String destino = "/WEB-INF/DashboardJSP/dashboard.jsp";
        request.getRequestDispatcher(destino).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}