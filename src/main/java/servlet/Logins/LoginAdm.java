package servlet.Logins;

import Dao.AdministradorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Administrador;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Login-Adm")
public class LoginAdm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("/loginAdm.jsp").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("usuario");
        String senha = request.getParameter("senha");

        if (email == null || email.isBlank() || senha == null || senha.isBlank()) {
            request.setAttribute("erro", "Preencha todos os campos.");
            request.getRequestDispatcher("/loginAdm.jsp").forward(request, response);
            return;
        }

        try {
            AdministradorDAO dao = new AdministradorDAO();
            Administrador admin = dao.read(email, senha);

            if (admin != null) {
                HttpSession session = request.getSession();
                session.setAttribute("adminLogado",  admin);         // objeto completo
                session.setAttribute("tipoUsuario",  "adm");         //  necessário para controle de acesso
                session.setAttribute("idUsuario",    admin.getId()); //  necessário para controle de acesso
                session.setAttribute("nomeUsuario",  admin.getNome());

                response.sendRedirect(request.getContextPath() + "/ServletDashboard"); //  via servlet
            } else {
                request.setAttribute("erro", "Email ou senha incorretos.");
                request.getRequestDispatcher("/loginAdm.jsp").forward(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno ao processar o login.");
            request.getRequestDispatcher("/loginAdm.jsp").forward(request, response);
        }
    }


}