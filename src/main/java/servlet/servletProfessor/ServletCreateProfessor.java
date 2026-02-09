package servlet.servletProfessor;
import Dao.ProfessorDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Professor;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "ServletCreateProfessor", value = "/ServletCreateProfessor")
public class ServletCreateProfessor extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String mensagem;
        ProfessorDAO dao = new ProfessorDAO();

        try {
            // Captura os parâmetros do formulário
            String nome = request.getParameter("nome");
            String disciplina = request.getParameter("disciplina");
            String usuario = request.getParameter("usuario");
            String senha =  request.getParameter("senha");

            // Cria o objeto Professor (o construtor chama os setters com validação)
            Professor professor = new Professor(nome, disciplina, senha, usuario);

            if (dao.create(professor)) {
                mensagem = "O cadastro do professor foi realizado com sucesso!";
            } else {
                mensagem = "Erro ao cadastrar: falha no banco de dados.";
            }

        } catch (Exception e) {
            // Captura erros de validação (Regex, null, etc) definidos no Model
            mensagem = "Erro de validação: " + e.getMessage();
        }

        request.setAttribute("mensagem", mensagem);

        // Atualiza a lista para exibir na tela de consulta
        List<Professor> lista = dao.read();
        request.setAttribute("listaProfessor", lista);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/ProfessorJSP/readProfessor.jsp");
        dispatcher.forward(request, response);
    }
}

