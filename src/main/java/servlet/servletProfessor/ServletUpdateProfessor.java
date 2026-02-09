package servlet.servletProfessor;

import Dao.ProfessorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professor;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletUpdateProfessor", value = "/ServletUpdateProfessor")
public class ServletUpdateProfessor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Professor professor = new Professor(
                request.getParameter("nome"),
                request.getParameter("discplina"),
                request.getParameter("usuario"),
                request.getParameter("senha")
        );
        ProfessorDAO dao = new ProfessorDAO();
        int status = dao.update(professor);

        String mensagem;
        switch (status) {
            case 1:
                mensagem = "A atualizaçao de " + professor.getNome() + " foi realizada com sucesso";
                break;
            case 0:
                mensagem = "A atualizaçao de " + professor.getNome() + " falhou, esse cpf ja foi vinculado";
                break;
            case -1:
                mensagem = "A atualizaçao de " + professor.getNome() + " falhou, esse email ja foi vinculado";
                break;
            case -2:
                mensagem = "A atualizaçao falhou: erro desconhecido.";
                break;
            default:
                mensagem = "A atualizaçao falhou: erro interno.";
        }
        request.setAttribute("mensagem", mensagem);
        List<Professor> lista = dao.read();
        request.setAttribute("listaProfessor", lista);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ProfessorJSP/readProfessor.jsp");
        dispatcher.forward(request, response);
    }
    
}
