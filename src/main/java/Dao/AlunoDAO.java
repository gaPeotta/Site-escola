package Dao;

import conexao.Conexao;
import model.Aluno;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class AlunoDAO {

    /*
     * Cria um aluno e retorna a matrícula.
     */
    public int create(Aluno aluno, boolean semTurma) {

        String sql = "INSERT INTO aluno (nome, senha, email, cpf, turma) VALUES (?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getSenha());
            pstmt.setString(3, aluno.getEmail());
            pstmt.setString(4, aluno.getCpf());
            pstmt.setString(5, "-");

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            System.err.println("Erro SQL: " + e.getMessage());
            System.err.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
            return 0;
        }
    }

    public int create(Aluno aluno) {

        String sql = "INSERT INTO aluno (nome, senha, email, cpf, turma) VALUES (?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getSenha());
            pstmt.setString(3, aluno.getEmail());
            pstmt.setString(4, aluno.getCpf());
            pstmt.setString(5, aluno.getTurma());

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
            return -1;

        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /*
     * Busca todos os alunos.
     */
    public List<Aluno> read() {

        String sql = "SELECT * FROM aluno ORDER BY matricula";
        Conexao conexao = new Conexao();
        List<Aluno> alunos = new LinkedList<>();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()
        ) {
            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("turma"),
                        rs.getString("foto")       // ← adicionado
                );
                alunos.add(aluno);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return alunos;
    }

    /*
     * Busca alunos filtrando por nome e ordenando.
     */
    public List<Aluno> read(String nome, String orderBy, String direction) {

        Conexao conexao = new Conexao();
        List<Aluno> alunos = new LinkedList<>();
        List<Object> parametros = new LinkedList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM aluno WHERE 1=1 ");

        if (nome != null && !nome.trim().isEmpty()) {
            sql.append(" AND nome ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        String coluna = "matricula";
        if (orderBy != null) {
            switch (orderBy.toLowerCase()) {
                case "nome":  coluna = "nome";  break;
                case "turma": coluna = "turma"; break;
                case "email": coluna = "email"; break;
            }
        }

        String dir = "ASC";
        if (direction != null && direction.equalsIgnoreCase("DESC")) dir = "DESC";

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
                    alunos.add(new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("turma"),
                            rs.getString("foto")   // ← adicionado
                    ));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return alunos;
    }

    public Aluno buscarPorMatricula(int matricula) {
        Aluno aluno = null;
        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        try (
                Connection conn = new Conexao().conectar();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("senha"),
                        rs.getString("turma"),
                        rs.getString("foto")       // ← adicionado
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    public List<Aluno> read(String nome, String orderBy, String direction, String turma) {

        Conexao conexao = new Conexao();
        List<Aluno> alunos = new LinkedList<>();
        List<Object> parametros = new LinkedList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM aluno WHERE 1=1");

        if (nome != null && !nome.trim().isEmpty()) {
            sql.append(" AND nome ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        if (turma != null && !turma.trim().isEmpty()) {
            sql.append(" AND turma = ?");
            parametros.add(turma.trim().toUpperCase());
        }

        String coluna = "matricula";
        if (orderBy != null) {
            switch (orderBy.toLowerCase()) {
                case "nome":  coluna = "nome";  break;
                case "turma": coluna = "turma"; break;
                case "email": coluna = "email"; break;
            }
        }

        String dir = "ASC";
        if (direction != null && direction.equalsIgnoreCase("DESC")) dir = "DESC";

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
                    alunos.add(new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("turma"),
                            rs.getString("foto")   // ← adicionado
                    ));
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return alunos;
    }

    /*
     * Busca aluno por email e senha.
     */
    public Aluno buscarPorEmailESenha(String email, String senha) {

        String sql = "SELECT * FROM aluno WHERE email = ? AND senha = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("turma"),
                            rs.getString("foto")   // ← adicionado
                    );
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    /*
     * Atualiza um aluno.
     */
    public int update(Aluno aluno) {

        String sql = "UPDATE aluno SET nome = ?, senha = ?, email = ?, cpf = ?, turma = ? WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getSenha());
            pstmt.setString(3, aluno.getEmail());
            pstmt.setString(4, aluno.getCpf());
            pstmt.setString(5, aluno.getTurma());
            pstmt.setInt(6, aluno.getMatricula());

            return pstmt.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Atualiza aluno via parâmetros diretos.
     */
    public int update(int matricula, String nome, String senha, String email, String cpf, String turma) {

        String sql = "UPDATE aluno SET nome = ?, senha = ?, email = ?, cpf = ?, turma = ? WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            pstmt.setString(3, email);
            pstmt.setString(4, cpf);
            pstmt.setString(5, turma);
            pstmt.setInt(6, matricula);

            return pstmt.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Remove aluno pela matrícula.
     */
    public int delete(int matricula) {

        String sql = "DELETE FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setInt(1, matricula);
            return pstmt.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Remove aluno pelo nome (cuidado).
     */
    public int delete(String nome) {

        String sql = "DELETE FROM aluno WHERE nome = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {
            pstmt.setString(1, nome);
            return pstmt.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }
}