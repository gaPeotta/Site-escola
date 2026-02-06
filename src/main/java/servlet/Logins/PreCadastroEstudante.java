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

        request.getRequestDispatcher("caminho precadastro").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String cpf = request.getParameter("cpf");

        if(cpf == null || cpf.isEmpty()){
            request.setAttribute("erro", "Informe um CPF.");
            request.getRequestDispatcher("caminho precadastro").forward(request, response);
            return;
        }

        cpf = cpf.replaceAll("[^\\d]", "");
        PreMatriculaDAO preDao = new PreMatriculaDAO();

        try {

            PreMatricula preMatricula = preDao.readByCpf(cpf);

            if(preMatricula == null){
                request.setAttribute("erro", "CPF não encontrado");
                request.getRequestDispatcher("caminho precadastro").forward(request, response);
                return;
            }

            request.getSession().setAttribute("cpf", cpf);
            request.getRequestDispatcher("caminho cadastro").forward(request, response);

        } catch (Exception e) {
            request.setAttribute("erro", "Erro interno.");
            request.getRequestDispatcher("erro.jsp").forward(request, response);
        }
    }
}
