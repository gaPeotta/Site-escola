package Dao;

import conexao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RecuperacaoSenhaDAO {
    public void salvarCodigo(String email, String codigo) {
        Connection conn = null;
        try {

            Conexao conexao = new Conexao();
            conn = conexao.conectar();

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO recuperacao_senha (email, codigo, expiracao) VALUES (?, ?, NOW() + INTERVAL '10 minutes')"
            );

            ps.setString(1, email);
            ps.setString(2, codigo);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean validarCodigo(String email, String codigo) {
        Connection conn = null;

        try {
            Conexao conexao = new Conexao();
            conn = conexao.conectar();

            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM recuperacao_senha WHERE email = ? AND codigo = ? AND expiracao > NOW()"
            );

            ps.setString(1, email);
            ps.setString(2, codigo);
            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void atualizarSenha(String email, String novaSenha) {

        Connection conn = null;

        try {

            Conexao conexao = new Conexao();
            conn = conexao.conectar();


            PreparedStatement psAluno = conn.prepareStatement(
                    "UPDATE aluno SET senha = ? WHERE email = ?"
            );

            psAluno.setString(1, novaSenha);
            psAluno.setString(2, email);

            int linhasAfetadas = psAluno.executeUpdate();


            if (linhasAfetadas == 0) {

                PreparedStatement psProfessor = conn.prepareStatement(
                        "UPDATE professor SET senha = ? WHERE email = ?"
                );

                psProfessor.setString(1, novaSenha);
                psProfessor.setString(2, email);

                psProfessor.executeUpdate();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}