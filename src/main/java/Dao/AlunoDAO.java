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
    public int create(Aluno aluno) throws SQLException {

        String sql = "INSERT INTO aluno (matricula, nome, senha, email, cpf, turma) VALUES (?, ?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, aluno.getMatricula());
            pstmt.setString(2, aluno.getNome());
            pstmt.setString(3, aluno.getSenha());
            pstmt.setString(4, aluno.getEmail());
            pstmt.setString(5, aluno.getCpf());
            pstmt.setString(6, aluno.getTurma());

            int linhas = pstmt.executeUpdate();

            if (linhas == 0) {
                throw new SQLException("Criação do aluno falhou.");
            }

            return aluno.getMatricula();
        }
    }

    /*
     * Busca todos os alunos.
     */
    public List<Aluno> read() throws SQLException {

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
                        rs.getString("turma")
                );

                alunos.add(aluno);
            }
        }

        return alunos;
    }

    /*
     * Busca alunos filtrando por nome e ordenando.
     */
    public List<Aluno> read(String nome, String orderBy, String direction) throws SQLException {

        Conexao conexao = new Conexao();
        List<Aluno> alunos = new LinkedList<>();
        List<Object> parametros = new LinkedList<>();

        StringBuilder sql = new StringBuilder("SELECT * FROM aluno WHERE 1=1 ");

        if (nome != null && !nome.trim().isEmpty()) {
            sql.append(" AND nome ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        // Whitelist ordenação
        String coluna = "matricula";

        if (orderBy != null) {
            switch (orderBy.toLowerCase()) {
                case "nome":
                    coluna = "nome";
                    break;
                case "turma":
                    coluna = "turma";
                    break;
                case "email":
                    coluna = "email";
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

                    Aluno aluno = new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("turma")
                    );

                    alunos.add(aluno);
                }
            }
        }

        return alunos;
    }

    /*
     * Busca aluno pela matrícula.
     */
    public Aluno read(int matricula) throws SQLException {

        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, matricula);

            try (ResultSet rs = pstmt.executeQuery()) {

                if (rs.next()) {
                    return new Aluno(
                            rs.getInt("matricula"),
                            rs.getString("nome"),
                            rs.getString("cpf"),
                            rs.getString("email"),
                            rs.getString("senha"),
                            rs.getString("turma")
                    );
                }
            }
        }

        return null;
    }

    /*
     * Atualiza um aluno.
     */
    public int update(Aluno aluno) throws SQLException {

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
        }
    }

    /*
     * Atualiza aluno via parâmetros diretos.
     */
    public int update(int matricula, String nome, String senha, String email, String cpf, String turma) throws SQLException {

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
        }
    }

    /*
     * Remove aluno pela matrícula.
     */
    public int delete(int matricula) throws SQLException {

        String sql = "DELETE FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, matricula);
            return pstmt.executeUpdate();
        }
    }

    /*
     * Remove aluno pelo nome (cuidado).
     */
    public int delete(String nome) throws SQLException {

        String sql = "DELETE FROM aluno WHERE nome = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, nome);
            return pstmt.executeUpdate();
        }
    }
}
