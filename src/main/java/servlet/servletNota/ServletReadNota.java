package servlet.servletNota;

import Dao.NotaDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Notas;
import model.Professor;
import java.io.IOException;
import java.util.List;

public class ServletReadNota extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Professor prof = (Professor) session.getAttribute("usuarioLogado");

        // 1. Verifica primeiro se o professor está logado para evitar erro
        if (prof == null) {
            response.sendRedirect("index.jsp");
            return; // Encerra a execução aqui
        }

        // 2. Busca apenas as notas vinculadas ao ID deste professor
        NotaDAO notaDao = new NotaDAO();
        List<Notas> listaFiltrada = notaDao.buscarPorProfessor(prof.getIdProfessor());

        // 3. Define a lista filtrada como atributo para a JSP
        request.setAttribute("listaNotas", listaFiltrada);

        // 4. Decide para onde o usuário vai (View Manager)
        String view = request.getParameter("view");
        String destino;

        if ("create".equals(view)) {
            destino = "/WEB-INF/NotaJSP/createNota.jsp";
        } else if ("update".equals(view)) {
            destino = "/WEB-INF/NotaJSP/updateNota.jsp";
        } else {
            destino = "/WEB-INF/NotaJSP/readNota.jsp";
        }

        // 5. Um ÚNICO forward no final do método
        RequestDispatcher dispatcher = request.getRequestDispatcher(destino);
        dispatcher.forward(request, response);
    }
}