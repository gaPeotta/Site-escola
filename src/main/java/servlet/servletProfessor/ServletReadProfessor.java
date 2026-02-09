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

@WebServlet(name = "ServletReadProfessor", value = "/ServletReadProfessor")
public class ServletReadProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProfessorDAO professorDAO = new ProfessorDAO();
        List<Professor> lista = professorDAO.read();

        // Define a lista como atributo da request
        request.setAttribute("listaProfessor", lista);

        // Lógica de navegação baseada no parâmetro "view"
        String view = request.getParameter("view");
        String destino;

        if ("create".equals(view)) {
            destino = "/WEB-INF/ProfessorJSP/createProfessor.jsp";
        } else if ("update".equals(view)) {
            // Caso queira carregar os dados para atualizar futuramente
            destino = "/WEB-INF/ProfessorJSP/updateProfessor.jsp";
        } else {
            destino = "/WEB-INF/ProfessorJSP/readProfessor.jsp";
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }
}
