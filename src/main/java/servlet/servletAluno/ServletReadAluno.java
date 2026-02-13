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
@WebServlet(name = "ServletReadAluno", value = "/ServletReadAluno")
public class ServletReadAluno extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            AlunoDAO alunoDAO = new AlunoDAO();
            List<Aluno> lista = alunoDAO.read();

            // Define a lista como atributo da request
            request.setAttribute("listaAluno", lista);
            // Verifica se há uma view específica solicitada
            String view = request.getParameter("view");
            String destino;

            if ("create".equals(view)) {
                destino = "/WEB-INF/AlunoJSP/createAluno.jsp";
            } else if ("update".equals(view)) {
                Integer matricula = Integer.valueOf(request.getParameter("matricula"));
                Aluno aluno = alunoDAO.buscarPorMatricula(matricula);
                request.setAttribute("aluno", aluno);
                destino = "/WEB-INF/AlunoJSP/updateAluno.jsp";
            } else {
                destino = "/WEB-INF/AlunoJSP/readAluno.jsp";
            }

            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
        }
}

