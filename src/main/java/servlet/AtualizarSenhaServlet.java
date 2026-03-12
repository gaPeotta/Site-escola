package servlet;

import Dao.RecuperacaoSenhaDAO;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/atualizarSenha")
public class AtualizarSenhaServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String novaSenha = request.getParameter("novaSenha");
        String confirmarSenha = request.getParameter("confirmarSenha");

        String email = (String) request.getSession().getAttribute("email");

        if(!novaSenha.equals(confirmarSenha)){
            request.setAttribute("erro","As senhas não coincidem");
            request.getRequestDispatcher("novaSenha.jsp")
                    .forward(request,response);
            return;
        }

        RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
        dao.atualizarSenha(email, novaSenha);
        response.sendRedirect("loginAluno.jsp");
    }
}