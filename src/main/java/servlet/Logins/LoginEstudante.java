package servlet.Logins;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import java.io.IOException;

public class LoginEstudante extends HttpServlet {
    /* Passo a Passo do login
     * 1 - usuario digita o cpf na pagina
     * 2 - procuramos esse cpf no PrematriculaDAO
     * 3 - se estiver la, passa pra proxima pagina. se nao, erro.
     * 4 - na pagina de cadastro, o usuario coloca seus dados, e ao confirmar cadastro, exclui o cpf da tabela prematricula.
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("caminho login professor").forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}