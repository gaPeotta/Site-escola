package servlet.servletNota;

import jakarta.servlet.http.HttpServlet;
import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Administrador;
import model.Notas;
import model.Professor;
import java.io.IOException;

@WebServlet("/ServletCreateNota")
public class ServletCreateNota extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        Professor profLogado = null;
        Administrador adminLogado = null;

        if (tipoUsuario.equalsIgnoreCase("professor")) {
            profLogado = (Professor) session.getAttribute("usuarioLogado");
        } else if (tipoUsuario.equalsIgnoreCase("adm")) {
            adminLogado = (Administrador) session.getAttribute("adminLogado");
        }

        if (profLogado == null && adminLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int matricula = Integer.parseInt(request.getParameter("matriculaAluno"));
            String disciplina = request.getParameter("disciplina");
            String observacao = request.getParameter("observacao");

            String n1Param = request.getParameter("nota1");
            String n2Param = request.getParameter("nota2");

            double n1 = (n1Param != null && !n1Param.trim().isEmpty()) ? Double.parseDouble(n1Param) : 0;
            Double n2 = (n2Param == null || n2Param.trim().isEmpty() || n2Param.equals("-"))
                    ? null
                    : Double.parseDouble(n2Param);
            Double media = (n2 == null) ? null : (n1 + n2) / 2;
            Boolean situacao = (n2 == null) ? null : (media >= 7);
            // Determina o idProfessor conforme quem está logado
            Integer idProfessor = null;

            if (adminLogado != null) {
                idProfessor = 6;
            } else if (profLogado != null) {
                idProfessor = profLogado.getIdProfessor();
            }
            Notas novaNota = new Notas(
                    matricula,
                    idProfessor,
                    disciplina,
                    observacao,
                    n1,
                    n2,
                    situacao
            );

            NotaDAO dao = new NotaDAO();
            dao.create(novaNota);


            response.sendRedirect("ServletReadNota");

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Falha ao cadastrar nota. Verifique os valores inseridos.");
            request.getRequestDispatcher("/WEB-INF/NotaJSP/readNotasAluno.jsp") // ajuste pro nome real do JSP
                    .forward(request, response);
        }
    }
}