package servlet.Logins;

import Dao.AlunoDAO;
import Dao.ProfessorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Aluno;
import model.Professor;

import java.io.IOException;
import java.util.List;

@WebServlet("/loginUsuario")
public class LoginUsuario extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String tipo = request.getParameter("tipo");

        boolean login = false;
        String erro = null;
        String destino =null;

        if (tipo == null) {
            erro = "Selecione o tipo de usuário.";
        } else {

            HttpSession session = request.getSession();

            try {

                if (tipo.equals("professor")) {

                    ProfessorDAO dao = new ProfessorDAO();
                    Professor prof = dao.buscarPorEmailESenha(usuario, senha);

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
                    Aluno aluno = dao.buscarPorEmailESenha(usuario, senha);

                    if (aluno != null) {
                        login = true;
                        // Login correto!
                        session.setAttribute("usuarioLogado", aluno);
                        session.setAttribute("tipoUsuario", "professor");
                        session.setAttribute("idUsuario", aluno.getMatricula());

                        // Atribui o caminho desejado à variável destino
                        destino = "/WEB-INF/AlunoJSP/readAluno.jsp";

                        // Como a página está no WEB-INF, precisamos buscar a lista antes de ir para lá
                        List<Aluno> lista = dao.read();
                        request.setAttribute("listaAluno", lista);
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

            RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
            dispatcher.forward(request, response);
        } else {

            System.err.println("Falha no login para: " + usuario);

            request.setAttribute("erro", erro);
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}