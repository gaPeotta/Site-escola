package servlet.servletPreMatricula;

import Dao.PreMatriculaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.PreMatricula;

import java.io.IOException;

@WebServlet("/ServletUpdatePreMatricula")
public class ServletUpdatePreMatricula extends HttpServlet {

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

            PreMatriculaDAO dao = new PreMatriculaDAO();
            PreMatricula pre = dao.read(id);

            if (pre == null) {
                session.setAttribute("erro", "Pré-matrícula não encontrada.");
                response.sendRedirect(request.getContextPath() + "/ServletReadPreMatricula");
                return;
            }

            request.setAttribute("preMatricula", pre);
            request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/updatePreMatricula.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro ao carregar pré-matrícula.");
            response.sendRedirect(request.getContextPath() + "/ServletReadPreMatricula");
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
            int id   = Integer.parseInt(request.getParameter("id"));
            String cpf = request.getParameter("cpf");

            if (cpf == null || cpf.isBlank()) {
                request.setAttribute("erro", "CPF não informado.");
                request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/updatePreMatricula.jsp")
                        .forward(request, response);
                return;
            }

            PreMatricula pre = new PreMatricula(id, cpf);

            PreMatriculaDAO dao = new PreMatriculaDAO();
            int linhas = dao.update(pre);

            if (linhas > 0) {
                session.setAttribute("mensagem", "Pré-matrícula atualizada com sucesso!");
            } else {
                session.setAttribute("erro", "Nenhum registro atualizado.");
            }

            response.sendRedirect(request.getContextPath() + "/ServletReadPreMatricula");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/updatePreMatricula.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro interno ao atualizar.");
            response.sendRedirect(request.getContextPath() + "/ServletReadPreMatricula");
        }
    }
}