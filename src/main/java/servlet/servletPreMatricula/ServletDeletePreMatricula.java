package servlet.servletPreMatricula;

import Dao.PreMatriculaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/ServletDeletePreMatricula")
public class ServletDeletePreMatricula extends HttpServlet {

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
            String cpf = request.getParameter("cpf");

            if (cpf == null || cpf.isBlank()) {
                session.setAttribute("erro", "CPF não informado.");
                response.sendRedirect(request.getContextPath() + "/ServletReadPreMatricula");
                return;
            }

            PreMatriculaDAO dao = new PreMatriculaDAO();
            int linhas = dao.deleteByCpf(cpf);

            if (linhas > 0) {
                session.setAttribute("mensagem", "Pré-matrícula removida com sucesso!");
            } else {
                session.setAttribute("erro", "CPF não encontrado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro ao remover pré-matrícula.");
        }

        response.sendRedirect(request.getContextPath() + "/ServletReadPreMatricula");
    }
}