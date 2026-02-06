package servlet.servletAluno;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Aluno;

import java.io.IOException;
@WebServlet(name = "ServletUpdateAluno", value = "/ServletUpdateAluno")
public class ServletUpdateAluno extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Aluno aluno = new Aluno(
                request.getParameter("matricula"),
                request.getParameter("nome"),
                request.getParameter("senha"),
                request.getParameter("cpf"),
                request.getParameter("turma")
        );
    }
}
