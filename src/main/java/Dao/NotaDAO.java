package Dao;

import conexao.Conexao;
import model.Notas;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class NotaDAO {

    /*
     * Cria uma nota e retorna o ID.
     */
    public int create(Notas notas){

        String sql = "INSERT INTO notas (matricula_aluno, id_professor, disciplina, observacao, nota1, nota2) VALUES (?, ?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {

            pstmt.setInt(1, notas.getMatriculaAluno());
            pstmt.setInt(2, notas.getIdProfessor());
            pstmt.setString(3, notas.getDisciplina());
            pstmt.setString(4, notas.getObservacao());
            pstmt.setDouble(5, notas.getNota1());
            pstmt.setDouble(6, notas.getNota2());

            int linhas = pstmt.executeUpdate();

            if (linhas == 0) {
                throw new SQLException("Criação da nota falhou.");
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
     * Busca todas as notas.
     */
    public List<Notas> read(){

        String sql = "SELECT * FROM notas ORDER BY id_notas";
        Conexao conexao = new Conexao();
        List<Notas> lista = new LinkedList<>();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {

                Notas notas = new Notas(
                        rs.getInt("id_notas"),
                        rs.getInt("matricula_aluno"),
                        rs.getInt("id_professor"),
                        rs.getString("disciplina"),
                        rs.getString("observacao"),
                        rs.getDouble("nota1"),
                        rs.getDouble("nota2")
                );

                lista.add(notas);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }

    /*
     * Busca notas com filtros dinâmicos.
     */
    public List<Notas> read(Integer matriculaAluno, Integer idProfessor, String disciplina, String orderBy, String direction) {

        Conexao conexao = new Conexao();
        List<Notas> lista = new LinkedList<>();
        List<Object> parametros = new LinkedList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM notas WHERE 1=1 ");
//      e se quiser ver todas as notas de todos os alunos? -> matriculaAluno = null
        if (matriculaAluno != null) {
            sql.append(" AND matricula_aluno = ?");
            parametros.add(matriculaAluno);
        }
//      e se quiser ver todas as notas de todos os professores? -> idProfessor = null
        if (idProfessor != null) {
            sql.append(" AND id_professor = ?");
            parametros.add(idProfessor);
        }
//      e se quiser ver todas as notas de todas disciplinas? -> disciplina = null
        if (disciplina != null && !disciplina.trim().isEmpty()) {
            sql.append(" AND disciplina ILIKE ?");
            parametros.add("%" + disciplina.trim() + "%");
        }

        // whitelist ordenação
        String coluna = "id_notas";

        if (orderBy != null) {
            switch (orderBy.toLowerCase()) {
                case "disciplina":
                    coluna = "disciplina";
                    break;
                case "nota1":
                    coluna = "nota1";
                    break;
                case "nota2":
                    coluna = "nota2";
                    break;
                case "matricula":
                    coluna = "matricula_aluno";
                    break;
            }
        }

        String dir = "ASC";
        if (direction != null && direction.equalsIgnoreCase("DESC")) {
            dir = "DESC";
        }

        sql.append(" ORDER BY ").append(coluna).append(" ").append(dir);

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql.toString())
        ) {

            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rs = pstmt.executeQuery()) {

                while (rs.next()) {

                    Notas notas = new Notas(
                            rs.getInt("id_notas"),
                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            rs.getDouble("nota2")
                    );

                    lista.add(notas);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }

    /*
     * Busca nota pelo ID.
     */
    public Notas read(int idNotas) {

        String sql = "SELECT * FROM notas WHERE id_notas = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, idNotas);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return new Notas(
                            rs.getInt("id_notas"),
                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            rs.getDouble("nota2")
                    );
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }
    public List<Notas> buscarPorProfessor(int idProfessor) {

                        String sql = "SELECT * FROM notas WHERE id_professor = ? ORDER BY id_notas";
                        Conexao conexao = new Conexao();
                        List<Notas> notas = new LinkedList<>();

                        try (
                                Connection conn = conexao.conectar();
                                PreparedStatement pstmt = conn.prepareStatement(sql)
                        ) {

                            pstmt.setInt(1, idProfessor);

                            try (ResultSet rs = pstmt.executeQuery()) {

                                if (rs.next()) {
                                    Notas nota = new Notas(
                                            rs.getInt("id_notas"),
                                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            rs.getDouble("nota2")
                    );
                    notas.add(nota);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return notas;
    }

    public List<Notas> buscarPorId(int idNota) {

        String sql = "SELECT * FROM notas WHERE id_nota = ? ORDER BY id_notas";
        Conexao conexao = new Conexao();
        List<Notas> notas = new LinkedList<>();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, idNota);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    Notas nota = new Notas(
                            rs.getInt("id_notas"),
                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            rs.getDouble("nota2")
                    );
                    notas.add(nota);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return notas;
    }

    /*
     * Atualiza nota.
     */
    public int update(Notas notas){

        String sql = "UPDATE notas SET matricula_aluno = ?, id_professor = ?, disciplina = ?, observacao = ?, nota1 = ?, nota2 = ? WHERE id_notas = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, notas.getMatriculaAluno());
            pstmt.setInt(2, notas.getIdProfessor());
            pstmt.setString(3, notas.getDisciplina());
            pstmt.setString(4, notas.getObservacao());
            pstmt.setDouble(5, notas.getNota1());
            pstmt.setDouble(6, notas.getNota2());
            pstmt.setInt(7, notas.getIdNotas());

            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Atualiza nota via parâmetros diretos.
     */
    public int update(int idNotas, Integer matriculaAluno, Integer idProfessor, String disciplina,
                      String observacao, double nota1, double nota2){

        String sql = "UPDATE notas SET matricula_aluno = ?, id_professor = ?, disciplina = ?, observacao = ?, nota1 = ?, nota2 = ? WHERE id_notas = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, matriculaAluno);
            pstmt.setInt(2, idProfessor);
            pstmt.setString(3, disciplina);
            pstmt.setString(4, observacao);
            pstmt.setDouble(5, nota1);
            pstmt.setDouble(6, nota2);
            pstmt.setInt(7, idNotas);

            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Remove nota pelo ID.
     */
    public int delete(int idNotas){

        String sql = "DELETE FROM notas WHERE id_notas = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, idNotas);
            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Remove notas pela matrícula do aluno.
     */
    public int deleteByMatricula(int matriculaAluno){

        String sql = "DELETE FROM notas WHERE matricula_aluno = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, matriculaAluno);
            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }
}
