package servlet.Logins;

import Dao.PreMatriculaDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.PreMatricula;

import java.io.IOException;

@WebServlet("/precadastro")
public class PreCadastroEstudante extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Exibe a tela de pré-cadastro (coleta de CPF)
        request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cpf = request.getParameter("cpf");

        if (cpf == null || cpf.isEmpty()) {
            request.setAttribute("erro", "Informe um CPF.");
            request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
            return;
        }

        cpf = cpf.replaceAll("[^\\d]", "");
        PreMatriculaDAO preDao = new PreMatriculaDAO();

        try {
            PreMatricula preMatricula = preDao.readByCpf(cpf);

            if (preMatricula == null) {
                request.setAttribute("erro", "CPF não encontrado.");
                request.getRequestDispatcher("/cadastro.jsp").forward(request, response);
                return;
            }

            // CPF válido — salva na sessão e redireciona para cadastro completo
            request.getSession().setAttribute("cpf", cpf);
            request.getRequestDispatcher("/cadastro2.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("erro", "Erro interno.");
            request.getRequestDispatcher("/cadastro2.jsp").forward(request, response);
        }
    }
}