package Dao;

import conexao.Conexao;
import model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProfessorDAO {

    // ================= CREATE =================
    public boolean create(Professor professor) {
        String sql = "INSERT INTO professor (nome, disciplina, senha, email) VALUES (?, ?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getDisciplina());
            pstmt.setString(3, professor.getSenha());
            pstmt.setString(4, professor.getEmail());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
    }

    // ================= READ =================
    public List<Professor> read() {
        List<Professor> lista = new ArrayList<>();
        String sql = "SELECT * FROM professor";

        try (Connection conn = new Conexao().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("id_professor"),
                        rs.getString("nome"),
                        rs.getString("disciplina"),
                        rs.getString("senha"),
                        rs.getString("email")
                );

                lista.add(professor);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }

    // ================= READ COM FILTRO =================
    public List<Professor> read(String nome, String orderBy, String direction) {
        Conexao conexao = new Conexao();
        List<Professor> listaProfessor = new LinkedList<>();

        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM professor");
        List<Object> parametros = new LinkedList<>();

        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE nome ILIKE ?");
            parametros.add("%" + nome.trim() + "%");
        }

        String colunaOrdenacao = "id_professor";
        if (orderBy != null) {
            String lowerOrderBy = orderBy.trim().toLowerCase();
            if (lowerOrderBy.equals("nome")) {
                colunaOrdenacao = "nome";
            } else if (lowerOrderBy.equals("email")) {
                colunaOrdenacao = "email";
            }
        }

        String dir = "ASC";
        if ("DESC".equalsIgnoreCase(direction)) {
            dir = "DESC";
        }

        sqlBuilder.append(" ORDER BY ").append(colunaOrdenacao).append(" ").append(dir);
        String sql = sqlBuilder.toString();

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            try (ResultSet rset = pstmt.executeQuery()) {
                while (rset.next()) {
                    Professor prof = new Professor(
                            rset.getInt("id_professor"),
                            rset.getString("nome"),
                            rset.getString("disciplina"),
                            rset.getString("senha"),
                            rset.getString("email")
                    );
                    listaProfessor.add(prof);
                }
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return listaProfessor;
    }

    // ================= LOGIN =================
    public Professor buscarPorEmailESenha(String email, String senha){
        String sql = "SELECT * FROM professor WHERE email = ? AND senha = ?";
        Conexao conexao = new Conexao();
        Professor prof = null;

        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            System.out.println("=== LOGIN PROFESSOR DAO ===");
            System.out.println("Email recebido: [" + email + "]");
            System.out.println("Senha recebida: [" + senha + "]");

            pstmt.setString(1, email);
            pstmt.setString(2, senha);

            try (ResultSet rset = pstmt.executeQuery()) {

                if (rset.next()) {
                    System.out.println("Professor ENCONTRADO no banco!");

                    prof = new Professor(
                            rset.getInt("id_professor"),
                            rset.getString("nome"),
                            rset.getString("disciplina"),
                            rset.getString("senha"),
                            rset.getString("email")
                    );

                } else {
                    System.out.println("Professor NÃO encontrado na query.");
                }
            }

        } catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return prof;
    }
    // ================= BUSCAR POR ID =================
    public Professor buscarPorId(int id) {
        Professor professor = null;
        String sql = "SELECT * FROM professor WHERE id_professor = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                professor = new Professor(
                        rs.getInt("id_professor"),
                        rs.getString("nome"),
                        rs.getString("disciplina"),
                        rs.getString("senha"),
                        rs.getString("email")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professor;
    }

    // ================= UPDATE =================
    public int update(Professor professor) {
        String sql = "UPDATE professor SET nome = ?, disciplina = ?, senha = ?, email = ? WHERE id_professor = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getDisciplina());
            pstmt.setString(3, professor.getSenha());
            pstmt.setString(4, professor.getEmail());
            pstmt.setInt(5, professor.getIdProfessor());

            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    // ================= DELETE =================
    public int delete(int idProfessor) {
        String sql = "DELETE FROM professor WHERE id_professor = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idProfessor);
            return pstmt.executeUpdate();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }
}