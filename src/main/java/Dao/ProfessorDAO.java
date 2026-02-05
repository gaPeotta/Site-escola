package Dao;

import conexao.Conexao;
import model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {

    public boolean create(Professor professor) {
        String sql = "INSERT INTO professor (nome, disciplina, senha, usuario) VALUES (?, ?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getDisciplina());
            pstmt.setString(3, professor.getSenha());
            pstmt.setString(4, professor.getUsuario());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
                        rs.getString("usuario")
                );

                lista.add(professor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean update(Professor professor) {
        String sql = "UPDATE professor SET nome = ?, disciplina = ?, senha = ?, usuario = ? WHERE id_professor = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getDisciplina());
            pstmt.setString(3, professor.getSenha());
            pstmt.setString(4, professor.getUsuario());
            pstmt.setInt(5, professor.getIdProfessor());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idProfessor) {
        String sql = "DELETE FROM professor WHERE id_professor = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idProfessor);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
