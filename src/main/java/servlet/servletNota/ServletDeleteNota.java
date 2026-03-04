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
<<<<<<< HEAD

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Valida sessão
=======
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recupera o usuário logado na sessão
>>>>>>> 3df044f37efcc55f789c28835e0ded5f1aa7c7ee
        HttpSession session = request.getSession(false);
        if (session == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

<<<<<<< HEAD
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
=======
        // Se ninguém estiver logado, manda pro login
        if (profLogado == null && adminLogado == null) {
            response.sendRedirect("login.jsp");
>>>>>>> 3df044f37efcc55f789c28835e0ded5f1aa7c7ee
            return;
        }

        try {
            int idNotaParaDeletar = Integer.parseInt(request.getParameter("id"));

            NotaDAO dao = new NotaDAO();
<<<<<<< HEAD
            Notas notaExistente = dao.read(idNotaParaDeletar);

            if (notaExistente == null) {
                session.setAttribute("erro", "Nota não encontrada.");
                response.sendRedirect(request.getContextPath() + "/ServletReadNota");
                return;
=======

            // 3. BUSCA A NOTA ANTES DE DELETAR (Segurança)
            Notas notaExistente = dao.read(idNotaParaDeletar);

            if (notaExistente != null) {
                boolean temPermissao = false;

                // Se for administrador, tem permissão total
                if (adminLogado != null) {
                    temPermissao = true;
                }
                // Se for professor, verifica se o ID bate com o dono da nota
                else if (profLogado != null && notaExistente.getIdProfessor().equals(profLogado.getIdProfessor())) {
                    temPermissao = true;
                }

                // 4. Executa a deleção caso tenha permissão
                if (temPermissao) {
                    dao.delete(idNotaParaDeletar);
                    request.getSession().setAttribute("mensagem", "Nota excluída com sucesso!");
                } else {
                    request.getSession().setAttribute("erro", "Acesso negado: Você não tem permissão para excluir esta nota.");
                }
            } else {
                request.getSession().setAttribute("erro", "Erro: A nota que você tentou excluir não existe.");
>>>>>>> 3df044f37efcc55f789c28835e0ded5f1aa7c7ee
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
<<<<<<< HEAD
            session.setAttribute("erro", "Erro ao excluir a nota.");
        }

        response.sendRedirect(request.getContextPath() + "/ServletReadNota");
=======
            // Opcional: registrar um erro genérico na sessão caso dê problema no banco
            // request.getSession().setAttribute("erro", "Erro interno ao tentar excluir a nota.");
        }

        // 5. Redireciona de volta para a listagem
        response.sendRedirect("ServletReadNota");
>>>>>>> 3df044f37efcc55f789c28835e0ded5f1aa7c7ee
    }
}