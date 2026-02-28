package servlet.servletNota;

import jakarta.servlet.http.HttpServlet;
import Dao.NotaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Administrador;
import model.Notas;
import model.Professor;

import java.io.IOException;

@WebServlet("/ServletUpdateNota")
public class ServletUpdateNota extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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

        // Redireciona se não tiver ninguém logado
        if (profLogado == null && adminLogado == null) {
            response.sendRedirect(request.getContextPath() + "/index.jsp");
            return;
        }

        try {
            int idNota = Integer.parseInt(request.getParameter("idNotas"));

            NotaDAO dao = new NotaDAO();
            Notas notaExistente = dao.read(idNota);

            boolean isAdm = adminLogado != null;
            boolean isDonoDaNota = notaExistente != null && profLogado != null &&
                    profLogado.getIdProfessor().equals(notaExistente.getIdProfessor());

            if (notaExistente != null && (isAdm || isDonoDaNota)) {

                int matricula = Integer.parseInt(request.getParameter("matriculaAluno"));
                String disciplina = request.getParameter("disciplina");
                String observacao = request.getParameter("observacao");

                String n1Param = request.getParameter("nota1");
                String n2Param = request.getParameter("nota2");

                double n1 = (n1Param != null && !n1Param.trim().isEmpty()) ? Double.parseDouble(n1Param) : 0;
                double n2 = (n2Param != null && !n2Param.trim().isEmpty()) ? Double.parseDouble(n2Param) : 0;

                double media = (n1 + n2) / 2.0;
                boolean situacao = media >= 7;

                Notas notaAtualizada = new Notas(
                        idNota,
                        matricula,
                        notaExistente.getIdProfessor(), // preserva o professor original
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

        response.sendRedirect(request.getContextPath() + "/ServletReadNota");
    }
}