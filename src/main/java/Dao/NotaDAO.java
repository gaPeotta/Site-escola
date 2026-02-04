package Dao;

import conexao.Conexao;
import model.Notas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {

    public boolean create(Notas notas) {

        String sql = "INSERT INTO notas (matricula_aluno, id_professor, disciplina, observacao, nota1, nota2) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, notas.getMatriculaAluno());
            pstmt.setInt(2, notas.getIdProfessor());
            pstmt.setString(3, notas.getDisciplina());
            pstmt.setString(4, notas.getObservacao());
            pstmt.setDouble(5, notas.getNota1());
            pstmt.setDouble(6, notas.getNota2());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Notas> read() {

        List<Notas> lista = new ArrayList<>();
        String sql = "SELECT * FROM notas";

        try (Connection conn = new Conexao().conectar();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public boolean update(Notas notas) {

        String sql = "UPDATE notas SET matricula_aluno = ?, id_professor = ?, disciplina = ?, observacao = ?, nota1 = ?, nota2 = ? WHERE id_notas = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, notas.getMatriculaAluno());
            pstmt.setInt(2, notas.getIdProfessor());
            pstmt.setString(3, notas.getDisciplina());
            pstmt.setString(4, notas.getObservacao());
            pstmt.setDouble(5, notas.getNota1());
            pstmt.setDouble(6, notas.getNota2());
            pstmt.setInt(7, notas.getIdNotas());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int idNotas) {

        String sql = "DELETE FROM notas WHERE id_notas = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, idNotas);
            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
