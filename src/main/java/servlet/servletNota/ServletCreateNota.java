package servlet.servletNota;

import jakarta.servlet.http.HttpServlet;
import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Notas;
import model.Professor;
import java.io.IOException;
@WebServlet("/ServletCreateNota")
public class ServletCreateNota extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        Professor profLogado = (Professor) session.getAttribute("usuarioLogado");

        if (profLogado == null) {
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
            double n2 = (n2Param != null && !n2Param.trim().isEmpty()) ? Double.parseDouble(n2Param) : 0;

            // Calcula média e situação
            double media = (n1 + n2) / 2.0;
            boolean situacao = media >= 7;

            Notas novaNota = new Notas(
                    matricula,
                    profLogado.getIdProfessor(),
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
            request.getRequestDispatcher("/WEB-INF/NotaJSP/createNota.jsp").forward(request, response);
        }
    }
}

