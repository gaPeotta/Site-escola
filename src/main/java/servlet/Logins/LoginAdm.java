package servlet.Logins;

import Dao.AdministradorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Administrador;

import java.io.IOException;

@WebServlet("/loginAdm")
public class LoginAdm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("loginAdm.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        boolean login = false;
        String erro = null;

        HttpSession session = request.getSession();

        try {

            AdministradorDAO dao = new AdministradorDAO();
            Administrador admin = dao.read(email, senha);

            if (admin != null) {

                login = true;

                // PADRÃO IGUAL AOS OUTROS LOGINS
                session.setAttribute("usuarioLogado", admin);
                session.setAttribute("tipoUsuario", "adm");
                session.setAttribute("idUsuario", admin.getId());

                session.setMaxInactiveInterval(30 * 60);

            } else {
                erro = "Email ou senha incorretos.";
            }

        } catch (Exception e) {
            e.printStackTrace();
            erro = "Erro inesperado ao processar login.";
        }

        if (login) {

            System.out.println("Login ADM bem-sucedido para: " + email);
            response.sendRedirect(request.getContextPath() + "/telaadm");

        } else {

            System.err.println("Falha no login ADM para: " + email);

            request.setAttribute("erro", erro);
            request.getRequestDispatcher("loginAdm.jsp").forward(request, response);
        }
    }
}