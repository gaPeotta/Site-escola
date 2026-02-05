package Dao;

import conexao.Conexao;
import model.PreMatricula;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class PreMatriculaDAO {

    /*
     * Cria uma pré-matrícula e retorna o ID gerado.
     */
    public int create(PreMatricula preMatricula){

        String sql = "INSERT INTO pre_matricula (cpf) VALUES (?)";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            pstmt.setString(1, preMatricula.getCpf());

            int linhas = pstmt.executeUpdate();

            if (linhas == 0) {
                throw new SQLException("Criação da pré-matrícula falhou.");
            }

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Busca todas as pré-matrículas.
     */
    public List<PreMatricula> read(){

        String sql = "SELECT * FROM pre_matricula ORDER BY id_prematricula";
        Conexao conexao = new Conexao();
        List<PreMatricula> lista = new LinkedList<>();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                PreMatricula pre = new PreMatricula(
                        rs.getInt("id_prematricula"),
                        rs.getString("cpf")
                );

                lista.add(pre);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }

    /*
     * Busca pré-matrícula com filtro por CPF.
     */
    public List<PreMatricula> read(String cpf){

        Conexao conexao = new Conexao();
        List<PreMatricula> lista = new LinkedList<>();
        List<Object> parametros = new LinkedList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM pre_matricula WHERE 1=1 ");

        if (cpf != null && !cpf.trim().isEmpty()) {
            sql.append(" AND cpf = ?");
            parametros.add(cpf.replaceAll("[^\\d]", ""));
        }

        sql.append(" ORDER BY id_prematricula");

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ) {

            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    PreMatricula pre = new PreMatricula(
                            rs.getInt("id_prematricula"),
                            rs.getString("cpf")
                    );

                    lista.add(pre);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }

    /*
     * Busca pré-matrícula por ID.
     */
    public PreMatricula read(int id){

        String sql = "SELECT * FROM pre_matricula WHERE id_prematricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return new PreMatricula(
                            rs.getInt("id_prematricula"),
                            rs.getString("cpf")
                    );
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    /*
     * Atualiza CPF da pré-matrícula.
     */
    public int update(PreMatricula preMatricula){

        String sql = "UPDATE pre_matricula SET cpf = ? WHERE id_prematricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, preMatricula.getCpf());
            pstmt.setInt(2, preMatricula.getId_prematricula());

            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Remove pré-matrícula pelo ID.
     */
    public int delete(int id){

        String sql = "DELETE FROM pre_matricula WHERE id_prematricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, id);
            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }
}
