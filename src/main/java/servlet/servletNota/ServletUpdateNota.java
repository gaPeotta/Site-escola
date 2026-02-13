package servlet.servletNota;

import jakarta.servlet.http.HttpServlet;
import Dao.NotaDAO;
import jakarta.servlet.ServletException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Notas;
import model.Professor;
import java.io.IOException;

public class ServletUpdateNota extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    HttpSession session = request.getSession();
    Professor profLogado = (Professor) session.getAttribute("usuarioLogado");

    if (profLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    try {
        // 1. Pega o ID da nota que está sendo editada
        int idNota = Integer.parseInt(request.getParameter("idNotas"));

        NotaDAO dao = new NotaDAO();
        // 2. BUSCA A NOTA ORIGINAL NO BANCO (Segurança)
        Notas notaExistente = dao.read(idNota);

        // 3. Verifica se o professor logado é o dono da nota
        if (notaExistente != null && notaExistente.getIdProfessor().equals(profLogado.getIdProfessor())) {

            // 4. Captura os novos dados do formulário
            int matricula = Integer.parseInt(request.getParameter("matriculaAluno"));
            String disciplina = request.getParameter("disciplina");
            String observacao = request.getParameter("observacao");
            double n1 = Double.parseDouble(request.getParameter("nota1"));
            double n2 = Double.parseDouble(request.getParameter("nota2"));

            // 5. Monta o objeto Notas atualizado (Mantendo o ID original e o ID do professor)
            Notas notaAtualizada = new Notas(idNota, matricula, profLogado.getIdProfessor(),
                    disciplina, observacao, n1, n2);

            dao.update(notaAtualizada);
            session.setAttribute("mensagem", "Nota atualizada com sucesso!");
        } else {
            session.setAttribute("erro", "Erro: Você não tem permissão para editar esta nota.");
        }

    } catch (Exception e) {
        e.printStackTrace();
        session.setAttribute("erro", "Erro ao processar atualização.");
    }

        response.sendRedirect("ServletReadNota");
    }
}
