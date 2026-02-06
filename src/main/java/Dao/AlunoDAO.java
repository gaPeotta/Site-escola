package Dao;
import conexao.Conexao;
import model.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
public class AlunoDAO {

    public boolean create(Aluno aluno) {
    String sql = "INSERT INTO aluno (matricula, nome, senha, email, cpf, turma) VALUES (?, ?, ?, ?, ?, ?)";
    Conexao conexao = new Conexao();
    Connection conn = conexao.conectar();
    try {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, aluno.getMatricula());
        pstmt.setString(2, aluno.getNome());
        pstmt.setString(3, aluno.getSenha());
        pstmt.setString(4, aluno.getEmail());
        pstmt.setString(5, aluno.getCpf());
        pstmt.setString(6, aluno.getTurma());

        return pstmt.executeUpdate() > 0;
    } catch (SQLException sqle) {
        sqle.printStackTrace();
        return false;
    } finally {
        conexao.desconectar(conn);
    }
}
    public List<Aluno> read() {
        ArrayList<Aluno> alunoList = new ArrayList<>();
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM aluno");

            while (rs.next()) {
                Aluno aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getString("senha"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("turma")

                );
                alunoList.add(aluno);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        conexao.desconectar(conn);
        return alunoList;
    }
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
    public Aluno buscarPorMatricula(int matricula) {
        Aluno aluno = null;
        String sql = "SELECT * FROM aluno WHERE matricula = ?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, matricula);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                aluno = new Aluno(
                        rs.getInt("matricula"),
                        rs.getString("nome"),
                        rs.getString("senha"),
                        rs.getString("email"),
                        rs.getString("cpf"),
                        rs.getString("turma")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return aluno;
    }

    public boolean update(Aluno aluno) {
        String sql = "UPDATE aluno SET nome = ?, senha = ?, email = ?, cpf = ?, turma = ? WHERE matricula = ?";
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getSenha());
            pstmt.setString(3, aluno.getEmail());
            pstmt.setString(4, aluno.getCpf());
            pstmt.setString(4, aluno.getTurma());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }
    public int delete(int matricula) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM aluno WHERE matricula = ?");
            pstmt.setInt(1, matricula);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Aluno deletado com sucesso");
                return 1;
            }
            return 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            if (sqle.getMessage().contains("still referenced")) {
                return 0;
            }
            return -1;
        } finally {
            conexao.desconectar(conn);
        }
    }
}
