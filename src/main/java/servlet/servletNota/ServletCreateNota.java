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
@WebServlet("/ServletCreateNota")
public class ServletCreateNota extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

    // 1. Pega a sessão para identificar QUEM está lançando a nota
    HttpSession session = request.getSession();
    Professor profLogado = (Professor) session.getAttribute("usuarioLogado");

    // Segurança básica
    if (profLogado == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    try {
        // 2. Captura os dados vindos do formulário (JSP)
        // Note que os nomes dentro do getParameter devem ser iguais aos 'name' do seu HTML
        int matricula = Integer.parseInt(request.getParameter("matriculaAluno"));
        String disciplina = request.getParameter("disciplina");
        String observacao = request.getParameter("observacao");
        double n1 = Double.parseDouble(request.getParameter("nota1"));
        double n2 = Double.parseDouble(request.getParameter("nota2"));

        // 3. Instancia o objeto usando o seu Construtor SEM ID
        // Ordem: matriculaAluno, idProfessor, disciplina, observacao, nota1, nota2
        Notas novaNota = new Notas(
                matricula,
                profLogado.getIdProfessor(), // Aqui injetamos o ID do professor logado
                disciplina,
                observacao,
                n1,
                n2
        );

        // 4. Envia para o DAO salvar no banco
        NotaDAO dao = new NotaDAO();
        dao.create(novaNota);

        // 5. Sucesso! Redireciona para a listagem de notas
        response.sendRedirect("ServletReadNota");

    } catch (Exception e) {
        e.printStackTrace();
        request.setAttribute("erro", "Falha ao cadastrar nota. Verifique os valores inseridos.");
        request.getRequestDispatcher("/WEB-INF/NotaJSP/createNota.jsp").forward(request, response);
    }
}
}
