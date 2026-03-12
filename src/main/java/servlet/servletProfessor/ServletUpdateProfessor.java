package servlet.servletProfessor;

import Dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Professor;

import java.io.IOException;

@WebServlet("/ServletUpdateProfessor")
public class ServletUpdateProfessor extends HttpServlet {

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

        // ================= CARREGA DADOS PARA O FORM =================
        try {
            int id = Integer.parseInt(request.getParameter("id"));

            ProfessorDAO dao = new ProfessorDAO();
            Professor professor = dao.buscarPorId(id);

            if (professor == null) {
                session.setAttribute("erro", "Professor não encontrado.");
                response.sendRedirect(request.getContextPath() + "/ServletReadProfessor");
                return;
            }

            request.setAttribute("professor", professor);
            request.getRequestDispatcher("/WEB-INF/ProfessorJSP/updateProfessor.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro ao carregar professor.");
            response.sendRedirect(request.getContextPath() + "/ServletReadProfessor");
        }
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

        // ================= UPDATE =================
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String nome = request.getParameter("nome");
            String disciplina = request.getParameter("disciplina");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String foto = request.getParameter("foto");

            if (foto != null && foto.isBlank()) foto = null;

            Professor professor = new Professor(id, nome, disciplina, senha, email, foto);

            ProfessorDAO dao = new ProfessorDAO();
            int linhas = dao.update(professor);

            if (linhas > 0) {
                session.setAttribute("mensagem", "Professor atualizado com sucesso!");
            } else {
                session.setAttribute("erro", "Nenhum registro atualizado.");
            }

            response.sendRedirect(request.getContextPath() + "/ServletReadProfessor");

        } catch (IllegalArgumentException | NullPointerException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/ProfessorJSP/updateProfessor.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro interno ao atualizar.");
            response.sendRedirect(request.getContextPath() + "/ServletReadProfessor");
        }
    }
}