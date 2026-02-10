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

@WebServlet(name = "ServletUpdateAluno", value = "/ServletUpdateAluno")
public class ServletUpdateAluno extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Aluno aluno = new Aluno(
                Integer.parseInt(request.getParameter("matricula")),
                request.getParameter("nome"),
                request.getParameter("cpf"),
                request.getParameter("email"),
                request.getParameter("senha"),
                request.getParameter("turma"),
                Boolean.parseBoolean(request.getParameter("situacao"))
        );
        AlunoDAO dao = new AlunoDAO();
        int status = dao.update(aluno);

        String mensagem;
        switch (status) {
            case 1:
                mensagem = "A atualizaçao de " + aluno.getNome() + " foi realizada com sucesso";
                break;
            case 0:
                mensagem = "A atualizaçao de " + aluno.getNome() + " falhou, esse cpf ja foi vinculado";
                break;
            case -1:
                mensagem = "A atualizaçao de " + aluno.getNome() + " falhou, esse email ja foi vinculado";
                break;
            case -2:
                mensagem = "A atualizaçao falhou: erro desconhecido.";
                break;
            default:
                mensagem = "A atualizaçao falhou: erro interno.";
        }
        request.setAttribute("mensagem", mensagem);
        List<Aluno> lista = dao.read();
        request.setAttribute("listaAluno", lista);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AlunoJSP/readAluno.jsp");
        dispatcher.forward(request, response);
    }
}
