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

@WebServlet("/ServletCreatePreMatricula")
public class ServletCreatePreMatricula extends HttpServlet {

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

        // apenas abre o formulário
        request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/createPreMatricula.jsp")
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
            String cpf = request.getParameter("cpf");

            if (cpf == null || cpf.isBlank()) {
                request.setAttribute("erro", "CPF não informado.");
                request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/createPreMatricula.jsp")
                        .forward(request, response);
                return;
            }

            // PreMatricula(0, cpf) → ID 0 pois será gerado pelo banco
            PreMatricula pre = new PreMatricula(0, cpf);

            PreMatriculaDAO dao = new PreMatriculaDAO();

            // verifica se CPF já existe
            if (dao.readByCpf(cpf) != null) {
                request.setAttribute("erro", "Este CPF já está cadastrado na pré-matrícula.");
                request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/createPreMatricula.jsp")
                        .forward(request, response);
                return;
            }

            int idGerado = dao.create(pre);

            if (idGerado == 0) {
                throw new Exception("Falha ao inserir pré-matrícula.");
            }

            session.setAttribute("mensagem", "Pré-matrícula cadastrada com sucesso!");
            response.sendRedirect(request.getContextPath() + "/ServletReadPreMatricula");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/createPreMatricula.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao cadastrar.");
            request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/createPreMatricula.jsp")
                    .forward(request, response);
        }
    }
}