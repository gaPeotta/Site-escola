package Dao;

import conexao.Conexao;
import model.Notas;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class NotaDAO {

    public int create(Notas notas){

        String sql = "INSERT INTO nota (matricula_aluno, id_professor, disciplina, observacao, nota1, nota2, situacao) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            pstmt.setObject(6, notas.getNota2());
            pstmt.setObject(7, notas.getSituacao());

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

    public List<Notas> read(){

        String sql = "SELECT * FROM nota ORDER BY id_notas";
        Conexao conexao = new Conexao();
        List<Notas> lista = new LinkedList<>();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {

            while (rs.next()) {
                Double nota2 = rs.getObject("nota2") != null ? rs.getDouble("nota2") : null;
                Boolean situacao = rs.getObject("situacao") != null ? rs.getBoolean("situacao") : null;

                Notas notas = new Notas(
                        rs.getInt("id_notas"),
                        rs.getInt("matricula_aluno"),
                        rs.getInt("id_professor"),
                        rs.getString("disciplina"),
                        rs.getString("observacao"),
                        rs.getDouble("nota1"),
                        nota2,
                        situacao
                );
                lista.add(notas);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }

    public List<Notas> read(Integer matriculaAluno, Integer idProfessor, String disciplina,
                            String buscaProfessor, String buscaAluno,
                            String orderBy, String direction) {

        Conexao conexao = new Conexao();
        List<Notas> lista = new LinkedList<>();
        List<Object> parametros = new LinkedList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT n.*, a.nome AS nome_aluno, p.nome AS nome_professor " +
                        "FROM nota n " +
                        "JOIN aluno a ON a.matricula = n.matricula_aluno " +
                        "JOIN professor p ON p.id_professor = n.id_professor " +
                        "WHERE 1=1 "
        );

        if (matriculaAluno != null) {
            sql.append(" AND n.matricula_aluno = ?");
            parametros.add(matriculaAluno);
        }

        if (idProfessor != null) {
            sql.append(" AND n.id_professor = ?");
            parametros.add(idProfessor);
        }

        if (disciplina != null && !disciplina.trim().isEmpty()) {
            sql.append(" AND n.disciplina ILIKE ?");
            parametros.add("%" + disciplina.trim() + "%");
        }

        if (buscaAluno != null && !buscaAluno.trim().isEmpty()) {
            sql.append(" AND (a.nome ILIKE ? OR n.disciplina ILIKE ?)");
            parametros.add("%" + buscaAluno.trim() + "%");
            parametros.add("%" + buscaAluno.trim() + "%");
        }

        if (buscaProfessor != null && !buscaProfessor.trim().isEmpty()) {
            sql.append(" AND p.nome ILIKE ?");
            parametros.add("%" + buscaProfessor.trim() + "%");
        }

        String coluna = "n.id_notas";
        if (orderBy != null) {
            switch (orderBy.toLowerCase()) {
                case "disciplina": coluna = "n.disciplina";      break;
                case "nota1":      coluna = "n.nota1";           break;
                case "nota2":      coluna = "n.nota2";           break;
                case "matricula":  coluna = "n.matricula_aluno"; break;
            }
        }

        String dir = "DESC".equalsIgnoreCase(direction) ? "DESC" : "ASC";
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
                    Double nota2 = rs.getObject("nota2") != null ? rs.getDouble("nota2") : null;
                    Boolean situacao = rs.getObject("situacao") != null ? rs.getBoolean("situacao") : null;

                    Notas notas = new Notas(
                            rs.getInt("id_notas"),
                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            nota2,
                            situacao
                    );
                    notas.setNomeAluno(rs.getString("nome_aluno"));
                    notas.setNomeProfessor(rs.getString("nome_professor"));
                    lista.add(notas);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }

    public List<Notas> buscarPorProfessor(int idProfessor) {

        String sql = "SELECT * FROM nota WHERE id_professor = ? ORDER BY id_notas";
        Conexao conexao = new Conexao();
        List<Notas> notas = new LinkedList<>();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, idProfessor);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Double nota2 = rs.getObject("nota2") != null ? rs.getDouble("nota2") : null;
                    Boolean situacao = rs.getObject("situacao") != null ? rs.getBoolean("situacao") : null;

                    Notas nota = new Notas(
                            rs.getInt("id_notas"),
                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            nota2,
                            situacao
                    );
                    notas.add(nota);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return notas;
    }

    public Notas read(int idNotas) {

        String sql = "SELECT n.*, a.nome AS nome_aluno, p.nome AS nome_professor " +
                "FROM nota n " +
                "JOIN aluno a ON a.matricula = n.matricula_aluno " +
                "JOIN professor p ON p.id_professor = n.id_professor " +
                "WHERE n.id_notas = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, idNotas);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Double nota2 = rs.getObject("nota2") != null ? rs.getDouble("nota2") : null;
                    Boolean situacao = rs.getObject("situacao") != null ? rs.getBoolean("situacao") : null;

                    Notas nota = new Notas(
                            rs.getInt("id_notas"),
                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            nota2,
                            situacao
                    );
                    nota.setNomeAluno(rs.getString("nome_aluno"));
                    nota.setNomeProfessor(rs.getString("nome_professor"));
                    return nota;
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    public List<Notas> buscarPorId(int idNota) {

        String sql = "SELECT * FROM nota WHERE id_notas = ? ORDER BY id_notas";
        Conexao conexao = new Conexao();
        List<Notas> notas = new LinkedList<>();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, idNota);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Double nota2 = rs.getObject("nota2") != null ? rs.getDouble("nota2") : null;
                    Boolean situacao = rs.getObject("situacao") != null ? rs.getBoolean("situacao") : null;

                    Notas nota = new Notas(
                            rs.getInt("id_notas"),
                            rs.getInt("matricula_aluno"),
                            rs.getInt("id_professor"),
                            rs.getString("disciplina"),
                            rs.getString("observacao"),
                            rs.getDouble("nota1"),
                            nota2,
                            situacao
                    );
                    notas.add(nota);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return notas;
    }

    public int update(Notas notas){

        String sql = "UPDATE nota SET matricula_aluno = ?, id_professor = ?, disciplina = ?, observacao = ?, nota1 = ?, nota2 = ?, situacao = ? WHERE id_notas = ?";
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
            pstmt.setObject(6, notas.getNota2());
            pstmt.setObject(7, notas.getSituacao());
            pstmt.setInt(8, notas.getIdNotas());

            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    public int update(int idNotas, Integer matriculaAluno, Integer idProfessor, String disciplina,
                      String observacao, double nota1, Double nota2, Boolean situacao){

        String sql = "UPDATE nota SET matricula_aluno = ?, id_professor = ?, disciplina = ?, observacao = ?, nota1 = ?, nota2 = ?, situacao = ? WHERE id_notas = ?";
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
            pstmt.setObject(6, nota2);
            pstmt.setObject(7, situacao);
            pstmt.setInt(8, idNotas);

            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    public int delete(int idNotas){

        String sql = "DELETE FROM nota WHERE id_notas = ?";
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

    public int deleteByMatricula(int matriculaAluno){

        String sql = "DELETE FROM nota WHERE matricula_aluno = ?";
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