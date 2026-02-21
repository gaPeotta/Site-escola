package servlet.servletNota;

import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Notas;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletReadNotas")
public class ServletReadNota extends HttpServlet {

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
        String buscaAluno = request.getParameter("buscaAluno");// -> busca feita pelo aluno
        String buscaProfessor = request.getParameter("buscaProfessor"); // -> busca feita pelo professor
        String disciplina = request.getParameter("disciplina");
        String orderBy = request.getParameter("orderBy");
        String direction = request.getParameter("direction");

        // 🔧 limpa strings vazias → null
        if (disciplina != null && disciplina.isBlank()) disciplina = null;
        if (orderBy == null || orderBy.isBlank()) orderBy = "id_notas";
        if (direction == null || direction.isBlank()) direction = "ASC";

        Integer matriculaAluno = null;
        Integer idProfessor = null;

        // ================= CONTROLE DE ACESSO =================
        switch (tipo.toLowerCase()) {

            case "aluno":
                matriculaAluno = idUsuario; // 🔒 aluno só vê as próprias
                break;

            case "professor":
                idProfessor = idUsuario; // 🔒 professor só vê as dele
                break;

            case "adm":
                // admin vê tudo
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
        }

        // ================= BUSCA NO BANCO =================
        NotaDAO dao = new NotaDAO();

        List<Notas> listaNotas = dao.read(
                matriculaAluno,
                idProfessor,
                disciplina,
                buscaProfessor,
                buscaAluno,
                orderBy,
                direction
        );

        // ================= ENVIA PARA A VIEW =================
        request.setAttribute("listaNotas", listaNotas);

        // (opcional mas MUITO útil para manter filtros na tela)
        request.setAttribute("disciplinaSelecionada", disciplina);
        request.setAttribute("orderBySelecionado", orderBy);
        request.setAttribute("directionSelecionada", direction);

        // ================= ESCOLHA DA JSP =================
        String view;

        switch (tipo.toLowerCase()) {

            case "aluno":
                view = "/WEB-INF/NotaJSP/readNotasAluno.jsp";
                break;

            case "professor":
            case "adm":
                view = "/WEB-INF/NotaJSP/readNotasProfessor.jsp";
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
        }

        request.getRequestDispatcher(view)
                .forward(request, response);
    }
}