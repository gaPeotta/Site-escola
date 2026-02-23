package servlet.servletPreMatricula;

import Dao.PreMatriculaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.PreMatricula;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletReadPreMatricula")
public class ServletReadPreMatricula extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // ================= VALIDA SESSÃO =================
        HttpSession session = request.getSession(false);

        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String tipo = (String) session.getAttribute("tipoUsuario");
        Integer idUsuario = (Integer) session.getAttribute("idUsuario");

        if (tipo == null || idUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ================= CONTROLE DE ACESSO =================
        if (!tipo.equalsIgnoreCase("adm")) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // ================= PARÂMETROS DE FILTRO =================
        String buscaCpf  = request.getParameter("buscaCpf");
        String orderBy   = request.getParameter("orderBy");
        String direction = request.getParameter("direction");

        if (buscaCpf  != null && buscaCpf.isBlank())  buscaCpf  = null;
        if (orderBy   == null || orderBy.isBlank())    orderBy   = "id_prematricula";
        if (direction == null || direction.isBlank())  direction = "ASC";

        // ================= BUSCA NO BANCO =================
        PreMatriculaDAO dao = new PreMatriculaDAO();
        List<PreMatricula> lista = dao.read(buscaCpf, orderBy, direction);

        // ================= ENVIA PARA A VIEW =================
        request.setAttribute("listaPreMatricula", lista);
        request.setAttribute("buscaCpfSelecionada", buscaCpf != null ? buscaCpf : "");
        request.setAttribute("orderBySelecionado",  orderBy);
        request.setAttribute("directionSelecionada", direction);

        request.getRequestDispatcher("/WEB-INF/PreMatriculaJSP/readPreMatricula.jsp")
                .forward(request, response);
    }
}