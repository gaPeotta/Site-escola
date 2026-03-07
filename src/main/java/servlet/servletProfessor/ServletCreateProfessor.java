package servlet.servletProfessor;

import Dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Professor;

import java.io.IOException;

@WebServlet("/ServletCreateProfessor")
public class ServletCreateProfessor extends HttpServlet {

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

        request.getRequestDispatcher("/WEB-INF/ProfessorJSP/createProfessor.jsp")
                .forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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

        // ================= CREATE =================
        try {
            String nome       = request.getParameter("nome");
            String disciplina = request.getParameter("disciplina");
            String email      = request.getParameter("email");
            String senha      = request.getParameter("senha");
            String foto       = request.getParameter("foto"); // opcional

            if (foto != null && foto.isBlank()) foto = null;

            Professor professor = new Professor(nome, disciplina, senha, email);
            professor.setFoto(foto);

            ProfessorDAO dao = new ProfessorDAO();
            boolean sucesso = dao.create(professor);

            if (sucesso) {
                session.setAttribute("mensagem", "Professor cadastrado com sucesso!");
            } else {
                throw new Exception("Falha ao cadastrar professor.");
            }

            response.sendRedirect(request.getContextPath() + "/ServletReadProfessor");

        } catch (IllegalArgumentException | NullPointerException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/ProfessorJSP/createProfessor.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao cadastrar.");
            request.getRequestDispatcher("/WEB-INF/ProfessorJSP/createProfessor.jsp")
                    .forward(request, response);
        }
    }
}