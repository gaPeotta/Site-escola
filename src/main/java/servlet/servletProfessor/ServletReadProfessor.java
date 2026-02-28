package servlet.servletProfessor;

import Dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Professor;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletReadProfessor")
public class ServletReadProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ================= VALIDA SESSÃO =================
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String tipo = (String)  session.getAttribute("tipoUsuario");
        Object idUsuario = session.getAttribute("idUsuario");

        if (tipo == null || idUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ================= PARÂMETROS DE FILTRO =================
        String busca = request.getParameter("busca");
        String orderBy = request.getParameter("orderBy");
        String direction = request.getParameter("direction");

        if (busca != null && busca.isBlank()) busca = null;
        if (orderBy == null || orderBy.isBlank()) orderBy = "id_professor";
        if (direction == null || direction.isBlank()) direction = "ASC";

        // ================= BUSCA NO BANCO =================
        ProfessorDAO dao = new ProfessorDAO();
        List<Professor> lista = dao.read(busca, orderBy, direction);

        // ================= ENVIA PARA A VIEW =================
        request.setAttribute("listaProfessor", lista);
        request.setAttribute("buscaSelecionada", busca != null ? busca : "");
        request.setAttribute("orderBySelecionado", orderBy);
        request.setAttribute("directionSelecionada", direction);

        // ================= ESCOLHA DA JSP =================
        String view;

        switch (tipo.toLowerCase()) {

            case "adm":
                view = "/WEB-INF/ProfessorJSP/readProfessor.jsp"; // tabela completa + ações
                break;

            case "professor":
            case "aluno":
                view = "/WEB-INF/ProfessorJSP/readProfessorAluno.jsp"; // só leitura
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
        }

        request.getRequestDispatcher(view).forward(request, response);
    }
}