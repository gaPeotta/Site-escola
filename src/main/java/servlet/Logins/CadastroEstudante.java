package servlet.Logins;

import Dao.AlunoDAO;
import Dao.PreMatriculaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;

@WebServlet("/cadastroEstudante")
public class CadastroEstudante extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String cpf = (String) request.getSession().getAttribute("cpf");

            // Valida se a sessão ainda contém o CPF
            if (cpf == null || cpf.isEmpty()) {
                response.sendRedirect(request.getContextPath() + "/precadastro");
                return;
            }

            String nome  = request.getParameter("nome");
            String email = request.getParameter("email");
            String senha = request.getParameter("senha");

            Aluno aluno = new Aluno(nome, cpf, email, senha);

            AlunoDAO alunoDAO = new AlunoDAO();
            int matriculaGerada = alunoDAO.create(aluno, true);

            if (matriculaGerada == 0) {
                throw new Exception("Erro ao cadastrar aluno.");
            }

            PreMatriculaDAO preDAO = new PreMatriculaDAO();
            preDAO.deleteByCpf(cpf);

            request.getSession().invalidate();

            // Redireciona para o login após cadastro bem-sucedido
            response.sendRedirect(request.getContextPath() + "/loginAluno.jsp");

        } catch (IllegalArgumentException e) {
            request.setAttribute("erro", e.getMessage());
            request.getRequestDispatcher("/cadastro2.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno do sistema.");
            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
        }
    }
}