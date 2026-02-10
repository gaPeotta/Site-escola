package servlet.Logins;

import Dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import Dao.PreMatriculaDAO;
import model.Aluno;

import java.io.IOException;

@WebServlet("/cadastroEstudante")
public class CadastroEstudante extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            //  CPF vindo da session
            String cpf = (String) request.getSession().getAttribute("cpf");


            String nome = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");
            String turma = request.getParameter("turma");
            boolean situacao = Boolean.parseBoolean(request.getParameter("situacao"));

            // Criar aluno
            Aluno aluno = new Aluno(nome, cpf, email, senha, turma, situacao);

            // Salvar no banco
            AlunoDAO alunoDAO = new AlunoDAO();
            int matriculaGerada = alunoDAO.create(aluno);

            if (matriculaGerada == 0) {
                throw new Exception("Erro ao cadastrar aluno");
            }

            // Remove prematricula
            PreMatriculaDAO preDAO = new PreMatriculaDAO();
            preDAO.deleteByCpf(cpf);

            // Limpa session
            request.getSession().invalidate();

            response.sendRedirect("login.jsp");

        } catch (IllegalArgumentException e) {

            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("caminho cadastroEstudante")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();
            request.setAttribute("erro", "Erro interno do sistema.");
            request.getRequestDispatcher("erro.jsp")
                    .forward(request, response);
        }
    }
}
