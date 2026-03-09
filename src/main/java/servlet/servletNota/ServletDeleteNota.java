package servlet.servletNota;

import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Administrador;
import model.Notas;
import model.Professor;
import java.io.IOException;

@WebServlet("/ServletDeleteNota")
public class ServletDeleteNota extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Recupera o usuário logado na sessão
        HttpSession session = request.getSession(false);
        String tipoUsuario = (String) session.getAttribute("tipoUsuario");

        // Pega o usuário logado conforme o tipo
        Professor profLogado = null;
        Administrador adminLogado = null;

        if ("professor".equalsIgnoreCase(tipoUsuario)) {
            profLogado = (Professor) session.getAttribute("usuarioLogado");
        } else if ("adm".equalsIgnoreCase(tipoUsuario)) {
            adminLogado = (Administrador) session.getAttribute("adminLogado");
        }

        // Se ninguém estiver logado, manda pro login
        if (profLogado == null && adminLogado == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            // 2. Pega o ID da nota que deve ser apagada
            int idNotaParaDeletar = Integer.parseInt(request.getParameter("id"));

            NotaDAO dao = new NotaDAO();

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
            }

        } catch (Exception e) {
            e.printStackTrace();
            // Opcional: registrar um erro genérico na sessão caso dê problema no banco
            // request.getSession().setAttribute("erro", "Erro interno ao tentar excluir a nota.");
        }

        // 5. Redireciona de volta para a listagem
        response.sendRedirect("ServletReadNota");
    }
}