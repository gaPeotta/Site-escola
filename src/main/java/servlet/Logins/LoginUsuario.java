package servlet.Logins;

import Dao.AlunoDAO;
import Dao.ProfessorDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Aluno;
import model.Professor;

import java.io.IOException;

@WebServlet("/loginUsuario")
public class LoginUsuario extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String tipo = request.getParameter("tipo");

        boolean login = false;
        String erro = null;

        if (tipo == null) {
            erro = "Selecione o tipo de usuário.";
        } else {

            HttpSession session = request.getSession();

            try {

                if (tipo.equals("professor")) {

                    ProfessorDAO dao = new ProfessorDAO();
                    Professor prof = dao.read(usuario, senha);

                    if (prof != null) {

                        login = true;

                        session.setAttribute("usuarioLogado", prof);
                        session.setAttribute("tipoUsuario", "professor");
                        session.setAttribute("idUsuario", prof.getIdProfessor());

                    } else {
                        erro = "Usuário ou senha incorretos.";
                    }

                } else if (tipo.equals("aluno")) {

                    AlunoDAO dao = new AlunoDAO();
                    Aluno aluno = dao.read(usuario, senha);

                    if (aluno != null) {

                        login = true;

                        session.setAttribute("usuarioLogado", aluno);
                        session.setAttribute("tipoUsuario", "aluno");
                        session.setAttribute("idUsuario", aluno.getMatricula());

                    } else {
                        erro = "Usuário ou senha incorretos.";
                    }
                }

                // tempo da sessão
                session.setMaxInactiveInterval(30 * 60);

            } catch (Exception e) {
                e.printStackTrace();
                erro = "Erro inesperado ao processar login.";
            }
        }

        if (login) {

            System.out.println("Login bem-sucedido para: " + usuario);
            response.sendRedirect(request.getContextPath() + "/telaprincipal");

        } else {

            System.err.println("Falha no login para: " + usuario);

            request.setAttribute("erro", erro);
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
