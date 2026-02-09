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

@WebServlet(name = "ServletDeleteProfessor", value = "/ServletDeleteProfessor")
public class ServletDeleteProfessor extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("matricula"));

        ProfessorDAO dao = new ProfessorDAO();
        // Busca o nome da empresa para montar a mensagem
        String professor = dao.buscarPorId(id).getNome();

        // Executa a exclusão via DAO e recebe o status da operação
        int status = dao.delete(id);
        String mensagem;
        switch (status) {
            case 1:
                mensagem = "A exclusão de " + professor + " foi realizada com sucesso.";
                break;
            case 0:
                mensagem = "A exclusão falhou:" + professor + " está associada a outra tabela. Apague os campos relacionados primeiro.";
                break;
            case -1:
                mensagem = "A exclusão de " + professor + " falhou: erro interno.";
                break;
            default:
                mensagem = "A exclusão de " + professor + " falhou: erro desconhecido.";
                break;
        }

        request.setAttribute("mensagem", mensagem);
        List<Professor> lista = dao.read();

        request.setAttribute("listaProfessor", lista);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/professorJSP/readProfessor.jsp");
        dispatcher.forward(request, response);
    }
}
