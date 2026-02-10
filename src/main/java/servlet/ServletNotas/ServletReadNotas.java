package servlet.ServletNotas;

import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import model.Notas;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletReadNotas")
public class ServletReadNotas extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String tipo = (String) session.getAttribute("tipoUsuario");
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        String disciplina = request.getParameter("disciplina");
        String orderBy = request.getParameter("orderBy");
        String direction = request.getParameter("direction");

        Integer matriculaAluno = null;
        Integer idProfessor = null;

        if ("aluno".equals(tipo)) {

            matriculaAluno = idUsuario;

        } else if ("professor".equals(tipo)) {

            idProfessor = idUsuario;

        } else if ("adm".equals(tipo)) {

            // ADM pode ver tudo
            // filtros continuam null

        } else {

            // tipo inválido
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        NotaDAO dao = new NotaDAO();

        List<Notas> listaNotas = dao.read(
                matriculaAluno,
                idProfessor,
                disciplina,
                orderBy,
                direction
        );

        request.setAttribute("listaNotas", listaNotas);

        request.getRequestDispatcher("/WEB-INF/NotasJSP/readNotas.jsp")
                .forward(request, response);
    }
}
