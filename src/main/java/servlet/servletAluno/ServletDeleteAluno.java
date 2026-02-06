package servlet.servletAluno;

import Dao.AlunoDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletDeleteAluno", value = "/ServletDeleteAluno")
public class ServletDeleteAluno extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int matricula = Integer.parseInt(request.getParameter("matricula"));

        AlunoDAO dao = new AlunoDAO();
        // Busca o nome da empresa para montar a mensagem
        String aluno = dao.buscarPorMatricula(matricula).getNome();

        // Executa a exclusão via DAO e recebe o status da operação
        int status = dao.delete(matricula);
        String mensagem;
        switch (status) {
            case 1:
                mensagem = "A exclusão de " + aluno + " foi realizada com sucesso.";
                break;
            case 0:
                mensagem = "A exclusão falhou:" + aluno + " está associada a outra tabela. Apague os campos relacionados primeiro.";
                break;
            case -1:
                mensagem = "A exclusão de " + aluno + " falhou: erro interno.";
                break;
            default:
                mensagem = "A exclusão de " + aluno + " falhou: erro desconhecido.";
                break;
        }

        request.setAttribute("mensagem", mensagem);
        List<Aluno> lista = dao.read();

        request.setAttribute("listaAluno", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/alunoJSP/readAluno.jsp");
        dispatcher.forward(request, response);
    }
}
