package servlet.Logins;

import Dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Professor;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/Login-professor")
public class LoginProfessor extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.getRequestDispatcher("caminho login professor").forward(request, response);

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        Professor prof = null;
        boolean login = false;
        String erro = null;

        ProfessorDAO dao = new ProfessorDAO();
        try{

            prof = dao.read(usuario, senha);
            if(prof != null){
                login = true;
                HttpSession session = request.getSession();
                session.setAttribute("adminLogado", prof);
                session.setMaxInactiveInterval(30 * 60);
            }else {
                System.out.println("usuario ou senha incorretos");
            }
        }catch (Exception e){
            e.printStackTrace();
            erro = "Erro inesperado ao processar o login: " + e.getMessage();
        }

        if(login){
            System.out.println("Login bem-sucedido para: " + usuario);
            response.sendRedirect(request.getContextPath() + "/telaadm");
        }
        else{
            System.err.println("Falha no login para: " + usuario + ". Erro: " + erro);
            request.setAttribute("erro", erro);
            request.getRequestDispatcher("volta pra tela de login").forward(request, response);
        }

    }



}
