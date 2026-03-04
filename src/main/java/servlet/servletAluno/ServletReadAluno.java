package servlet.servletAluno;

import Dao.AlunoDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Aluno;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletReadAluno")
public class ServletReadAluno extends HttpServlet {

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

        // ================= PARÂMETROS DE FILTRO =================
        String busca     = request.getParameter("busca");
        String orderBy   = request.getParameter("orderBy");
        String direction = request.getParameter("direction");

        if (busca != null && busca.isBlank()) busca = null;
        if (orderBy == null || orderBy.isBlank()) orderBy = "matricula";
        if (direction == null || direction.isBlank()) direction = "ASC";

        // ================= FILTRO POR TURMA (só aluno) =================
        String turmaFiltro = null;

        if ("aluno".equalsIgnoreCase(tipo)) {
            AlunoDAO dao = new AlunoDAO();
            Aluno alunoLogado = dao.buscarPorMatricula(idUsuario);

            if (alunoLogado == null) {
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
            }

            turmaFiltro = alunoLogado.getTurma();
        }

        // ================= BUSCA NO BANCO =================
        AlunoDAO dao = new AlunoDAO();
        List<Aluno> lista = dao.read(busca, orderBy, direction, turmaFiltro);

        // ================= ENVIA PARA A VIEW =================
        request.setAttribute("listaAluno", lista);
        request.setAttribute("buscaSelecionada", busca != null ? busca : "");
        request.setAttribute("orderBySelecionado", orderBy);
        request.setAttribute("directionSelecionada", direction);

        // ================= ESCOLHA DA JSP =================
        String view;

        switch (tipo.toLowerCase()) {
            case "adm":
                view = "/WEB-INF/AlunoJSP/readAluno.jsp";
                break;
            case "professor":
            case "aluno":
                view = "/WEB-INF/AlunoJSP/readAlunoProfessor.jsp";
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
        }

        request.getRequestDispatcher(view).forward(request, response);
    }
}