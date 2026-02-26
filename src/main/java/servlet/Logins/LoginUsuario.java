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
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //pega e limpa parâmetros
        String usuario = request.getParameter("usuario");
        String senha = request.getParameter("senha");
        String tipo = request.getParameter("tipo");

        if (usuario != null) usuario = usuario.trim().toLowerCase();
        if (senha != null) senha = senha.trim();

        String erro = null;

        if (tipo == null || tipo.isEmpty()) {
            erro = "Selecione o tipo de usuário.";
        } else {

            try {

                // ================= PROFESSOR =================
                if ("professor".equals(tipo)) {
                    System.out.println("Login professor: " + usuario);

                    ProfessorDAO dao = new ProfessorDAO();
                    Professor prof = dao.buscarPorEmailESenha(usuario, senha);

                    if (prof != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("usuarioLogado", prof);
                        session.setAttribute("tipoUsuario", "professor");
                        session.setAttribute("idUsuario", prof.getIdProfessor());
                        session.setMaxInactiveInterval(30 * 60);

                        response.sendRedirect(request.getContextPath() + "/ServletReadNota");
                        return;

                    } else {
                        System.out.println("Professor NÃO encontrado");
                        erro = "Usuário ou senha incorretos.";
                    }
                }

                // ================= ALUNO =================
                else if ("aluno".equals(tipo)) {

                    AlunoDAO daoAluno = new AlunoDAO();
                    Aluno aluno = daoAluno.buscarPorEmailESenha(usuario, senha);

                    if (aluno != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("usuarioLogado", aluno);
                        session.setAttribute("tipoUsuario", "aluno");
                        session.setAttribute("idUsuario", aluno.getMatricula());
                        session.setMaxInactiveInterval(30 * 60);

                        response.sendRedirect(request.getContextPath() + "/ServletReadNota");
                        return;

                    } else {
                        erro = "Usuário ou senha incorretos.";
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                erro = "Erro inesperado ao processar login.";
            }
        }

        // ================= ERRO =================
        request.setAttribute("erro", erro);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }
}