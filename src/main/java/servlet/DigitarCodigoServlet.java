package servlet;

import Dao.RecuperacaoSenhaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;


@WebServlet("/digitarCodigo")
public class DigitarCodigoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/digitarCodigo.jsp")
                .forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String codigo = request.getParameter("codigo");

        RecuperacaoSenhaDAO dao = new RecuperacaoSenhaDAO();

        boolean valido = dao.validarCodigo(email, codigo);

        if (valido) {
            request.getRequestDispatcher("/WEB-INF/novaSenha.jsp")
                    .forward(request, response);


        } else {
            request.setAttribute("erro", "Código inválido ou expirado");
            request.getRequestDispatcher("/digitarCodigo.jsp").forward(request, response);

        }
    }
}