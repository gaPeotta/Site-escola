package servlet;

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

        // 1. VALIDAÇÃO DE SESSÃO (Segurança)
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("tipoUsuario") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. BUSCA DE DADOS NO BANCO
        DashboardDAO dao = new DashboardDAO();
        Map<String, Object> resumo = dao.getResumoDashboard();
        
        request.setAttribute("resumo", resumo);

        // 3. DIRECIONAMENTO PARA A VIEW
        String destino = "/WEB-INF/DashboardJSP/dashboard.jsp";
        request.getRequestDispatcher(destino).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        doGet(request, response);
    }
}