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

@WebServlet(name = "ServletCreateAluno", value = "/ServletCreateAluno")
public class ServletCreateAluno extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Aluno aluno = new Aluno(
                request.getParameter("nome"),
                request.getParameter("senha"),
                request.getParameter("email"),
                request.getParameter("cpf"),
                request.getParameter("turma")
        );

        AlunoDAO dao = new AlunoDAO();
        String mensagem;

        if (dao.create(aluno) > 0) {
            mensagem = "A criação do aluno foi realizada com sucesso.";
        } else {
            mensagem = "A criação do endereço falhou: erro interno.";
        }

        request.setAttribute("mensagem", mensagem);

        //  Atualiza a lista de endereços para exibir na JSP
        List<Aluno> lista = dao.read();
        request.setAttribute("listaAluno", lista);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/AlunoJSP/readAluno.jsp");
        dispatcher.forward(request, response);
    }
}
