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

@WebServlet("Login-Adm")
public class LoginAdm extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("caminho login adm").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String email = request.getParameter("email");
        String senha = request.getParameter("senha");
        Administrador admin = null;
        boolean login = false;
        String erro = null;

        AdministradorDAO dao = new AdministradorDAO();
        try{
            admin = dao.read(email, senha);
            if(admin != null){
                login = true;
                HttpSession session = request.getSession();
                session.setAttribute("adminLogado", admin);
                session.setMaxInactiveInterval(30 * 60);
            }else {
                System.out.println("Email ou senha incorretos");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            erro = "Erro de banco de dados. Tente novamente mais tarde.";
        }catch (Exception e){
            e.printStackTrace();
            erro = "Erro inesperado ao processar o login: " + e.getMessage();
        }

        if(login){
            System.out.println("Login bem-sucedido para: " + email);
            response.sendRedirect(request.getContextPath() + "/telaadm");
        }
        else{
            System.err.println("Falha no login para: " + email + ". Erro: " + erro);
            request.setAttribute("erro", erro);
            request.getRequestDispatcher("volta pra tela de login").forward(request, response);
        }

    }



}
