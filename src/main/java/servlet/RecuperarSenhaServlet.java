package servlet;

import Dao.RecuperacaoSenhaDAO;
import jakarta.servlet.annotation.WebServlet;
import utils.ServicoEmail;
import utils.GeradorCodigo;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/recuperarSenha")
public class RecuperarSenhaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String codigo = GeradorCodigo.gerarCodigo();
        RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();
        dao.salvarCodigo(email, codigo);

        try {
            ServicoEmail.enviarCodigo(email, codigo);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("email", email);
        response.sendRedirect(request.getContextPath() + "/digitarCodigo");
    }
}
