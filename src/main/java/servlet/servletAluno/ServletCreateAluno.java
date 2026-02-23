package servlet.servletAluno;

import Dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Aluno;

import java.io.IOException;

@WebServlet("/ServletCreateAluno")
public class ServletCreateAluno extends HttpServlet {

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

        request.getRequestDispatcher("/WEB-INF/AlunoJSP/createAluno.jsp")
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
            String nome  = request.getParameter("nome");
            String cpf   = request.getParameter("cpf");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String turma = request.getParameter("turma");

            Aluno aluno = new Aluno(nome, cpf, senha, email, turma);

            AlunoDAO dao = new AlunoDAO();
            int matricula = dao.create(aluno);

            if (matricula > 0) {
                session.setAttribute("mensagem", "Aluno cadastrado com sucesso!");
            } else {
                throw new Exception("Falha ao cadastrar aluno.");
            }

            response.sendRedirect(request.getContextPath() + "/ServletReadAluno");

        } catch (IllegalArgumentException | NullPointerException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/AlunoJSP/createAluno.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao cadastrar.");
            request.getRequestDispatcher("/WEB-INF/AlunoJSP/createAluno.jsp")
                    .forward(request, response);
        }
    }
}