package servlet.servletNota;
import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Notas;
import model.Professor;
import java.io.IOException;

public class ServletDeleteNota extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recupera o professor logado na sessão
        HttpSession session = request.getSession();
        Professor profLogado = (Professor) session.getAttribute("usuarioLogado");

        if (profLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 2. Pega o ID da nota que deve ser apagada
            int idNotaParaDeletar = Integer.parseInt(request.getParameter("id"));

            NotaDAO dao = new NotaDAO();

            // 3. BUSCA A NOTA ANTES DE DELETAR (Segurança)
            // Precisamos verificar se o idProfessor da nota é o mesmo do profLogado
            Notas notaExistente = dao.read(idNotaParaDeletar);

            if (notaExistente != null && notaExistente.getIdProfessor().equals(profLogado.getIdProfessor())) {
                // 4. Se for o dono da nota, deleta
                dao.delete(idNotaParaDeletar);
                request.getSession().setAttribute("mensagem", "Nota excluída com sucesso!");
            } else {
                // Se tentar deletar nota de outro professor
                request.getSession().setAttribute("erro", "Acesso negado: Você não tem permissão para excluir esta nota.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 5. Redireciona de volta para a listagem filtrada
        response.sendRedirect("ServletReadNota");
    }
}
