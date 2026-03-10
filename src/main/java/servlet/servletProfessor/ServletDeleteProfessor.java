package servlet.servletProfessor;

import Dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/ServletDeleteProfessor")
public class ServletDeleteProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ================= VALIDA SESSÃO =================
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String tipo = (String) session.getAttribute("tipoUsuario");

        if (tipo == null || !tipo.equalsIgnoreCase("adm")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ================= DELETE =================
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            ProfessorDAO dao = new ProfessorDAO();
            String nomeProfessor = dao.buscarPorId(id).getNome();
            int status = dao.delete(id);

            switch (status) {
                case 1:
                    session.setAttribute("mensagem", "Professor " + nomeProfessor + " removido com sucesso!");
                    break;
                case 0:
                    session.setAttribute("erro", nomeProfessor + "Erro ao remover professor.");
                    break;
                default:
                    session.setAttribute("erro", "Erro ao remover professor.");
                    break;
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro interno ao remover professor.");
        }

        response.sendRedirect(request.getContextPath() + "/ServletReadProfessor");
    }
}