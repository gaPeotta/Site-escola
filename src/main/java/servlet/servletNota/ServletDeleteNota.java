package servlet.servletNota;
import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Notas;
import java.io.IOException;

@WebServlet("/ServletDeleteNota")
public class ServletDeleteNota extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Valida sessão
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        String tipoUsuario = (String) session.getAttribute("tipoUsuario");
        Integer idUsuario  = (Integer) session.getAttribute("idUsuario");

        if (tipoUsuario == null || idUsuario == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        // 2. Só professor e adm podem deletar
        boolean isProfessor = "professor".equalsIgnoreCase(tipoUsuario);
        boolean isAdm       = "adm".equalsIgnoreCase(tipoUsuario);

        if (!isProfessor && !isAdm) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int idNotaParaDeletar = Integer.parseInt(request.getParameter("id"));

            NotaDAO dao = new NotaDAO();
            Notas notaExistente = dao.read(idNotaParaDeletar);

            if (notaExistente == null) {
                session.setAttribute("erro", "Nota não encontrada.");
                response.sendRedirect(request.getContextPath() + "/ServletReadNota");
                return;
            }

            // 3. Admin deleta qualquer nota; professor só deleta as suas
            boolean podeDeletar = isAdm ||
                    (isProfessor && notaExistente.getIdProfessor().equals(idUsuario));

            if (podeDeletar) {
                dao.delete(idNotaParaDeletar);
                session.setAttribute("mensagem", "Nota excluída com sucesso!");
            } else {
                session.setAttribute("erro", "Acesso negado: você não tem permissão para excluir esta nota.");
            }

        } catch (NumberFormatException e) {
            session.setAttribute("erro", "ID de nota inválido.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro ao excluir a nota.");
        }

        response.sendRedirect(request.getContextPath() + "/ServletReadNota");
    }
}