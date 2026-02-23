package servlet.servletAluno;

import Dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Aluno;

import java.io.IOException;

@WebServlet("/ServletUpdateAluno")
public class ServletUpdateAluno extends HttpServlet {

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
            int matricula = Integer.parseInt(request.getParameter("matricula"));

            AlunoDAO dao = new AlunoDAO();
            Aluno aluno = dao.buscarPorMatricula(matricula);

            if (aluno == null) {
                session.setAttribute("erro", "Aluno não encontrado.");
                response.sendRedirect(request.getContextPath() + "/ServletReadAluno");
                return;
            }

            request.setAttribute("aluno", aluno);
            request.getRequestDispatcher("/WEB-INF/AlunoJSP/updateAluno.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro ao carregar aluno.");
            response.sendRedirect(request.getContextPath() + "/ServletReadAluno");
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
            int    matricula = Integer.parseInt(request.getParameter("matricula"));
            String nome      = request.getParameter("nome");
            String cpf       = request.getParameter("cpf");
            String email     = request.getParameter("email");
            String senha     = request.getParameter("senha");
            String turma     = request.getParameter("turma");

            Aluno aluno = new Aluno(matricula, nome, cpf, email, senha, turma);

            AlunoDAO dao = new AlunoDAO();
            int status = dao.update(aluno);

            if (status > 0) {
                session.setAttribute("mensagem", "Aluno " + nome + " atualizado com sucesso!");
            } else {
                session.setAttribute("erro", "Falha ao atualizar aluno.");
            }

            response.sendRedirect(request.getContextPath() + "/ServletReadAluno");

        } catch (IllegalArgumentException | NullPointerException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/AlunoJSP/updateAluno.jsp")
                    .forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro interno ao atualizar.");
            response.sendRedirect(request.getContextPath() + "/ServletReadAluno");
        }
    }
}