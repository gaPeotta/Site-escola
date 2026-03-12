package servlet;

import Dao.RecuperacaoSenhaDAO;
import utils.ServicoEmail;
import utils.GeradorCodigo;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import utils.ServicoEmail;

import java.io.IOException;

@WebServlet("/esqueciSenha")
public class EsqueciSenhaServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/WEB-INF/AlunoJSP/esqueciSenha.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        try {

            String codigo = GeradorCodigo.gerarCodigo();

            RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
            dao.salvarCodigo(email, codigo);


            ServicoEmail.enviarCodigo(email, codigo);


            request.setAttribute("email", email);


            request.getRequestDispatcher("/digitarCodigo.jsp")
                    .forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();
            request.setAttribute("erro", "Erro ao enviar código");

            request.getRequestDispatcher("/WEB-INF/AlunoJSP/esqueciSenha.jsp")
                    .forward(request, response);
        }
    }
}