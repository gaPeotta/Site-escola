package servlet.servletNota;

import jakarta.servlet.http.HttpServlet;
import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Notas;
import model.Professor;

import java.io.IOException;

@WebServlet("/ServletUpdateNota")
public class ServletUpdateNota extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        Professor profLogado = (Professor) session.getAttribute("usuarioLogado");

        if (profLogado == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }

        try {
            int idNota = Integer.parseInt(request.getParameter("idNotas"));

            NotaDAO dao = new NotaDAO();
            Notas notaExistente = dao.read(idNota);
            request.setAttribute("nota", notaExistente);
            if (notaExistente != null &&
                    profLogado.getIdProfessor().equals(notaExistente.getIdProfessor())) {

                int matricula = Integer.parseInt(request.getParameter("matriculaAluno"));
                String disciplina = request.getParameter("disciplina");
                String observacao = request.getParameter("observacao");

                String n1Param = request.getParameter("nota1");
                String n2Param = request.getParameter("nota2");

                double n1 = (n1Param != null && !n1Param.trim().isEmpty()) ? Double.parseDouble(n1Param) : 0;
                double n2 = (n2Param != null && !n2Param.trim().isEmpty()) ? Double.parseDouble(n2Param) : 0;

                // Calcula média e situação
                double media;
                if (n1 > 0 && n2 > 0) {
                    media = (n1 + n2) / 2;
                } else if (n1 > 0) {
                    media = n1 / 2;
                } else {
                    media = 0;
                }

                boolean situacao = media >= 7;

                Notas notaAtualizada = new Notas(
                        idNota,
                        matricula,
                        profLogado.getIdProfessor(),
                        disciplina,
                        observacao,
                        n1,
                        n2,
                        situacao
                );

                dao.update(notaAtualizada);
                session.setAttribute("mensagem", "Nota atualizada com sucesso!");

            } else {
                session.setAttribute("erro", "Erro: Você não tem permissão para editar esta nota.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("erro", "Erro ao processar atualização.");
        }

        response.sendRedirect(request.getContextPath() + "/ServletReadNotas");
    }
}