package servlet.servletNota;

import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Notas;

import java.io.IOException;
import java.util.List;

@WebServlet("/ServletReadNota")
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
                matriculaAluno = idUsuario;
                break;

            case "professor":
                idProfessor = idUsuario;
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
        String view=request.getParameter("view");
        String destino;


        switch (tipo.toLowerCase()) {

            case "aluno":
                destino = "/WEB-INF/NotaJSP/readNotasAluno.jsp";
                break;

            case "professor":
            case "adm":
                destino = "/WEB-INF/NotaJSP/readNotasProfessor.jsp";
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/login.jsp");
                return;
        }

        if ("create".equals(view)) {
            destino = "/WEB-INF/NotaJSP/createNotas.jsp";
        } else if ("update".equals(view)) {
            String idParam = request.getParameter("id");
            if (idParam != null) {
                int idNota = Integer.parseInt(idParam);
                Notas nota = dao.read(idNota);
                request.setAttribute("nota", nota); }
            // Caso queira carregar os dados para atualizar futuramente
            destino = "/WEB-INF/NotaJSP/updateNotas.jsp";
        } else {
            destino = "/WEB-INF/NotaJSP/readNotasProfessor.jsp";
        }

        request.getRequestDispatcher(destino)
                .forward(request, response);
    }
}