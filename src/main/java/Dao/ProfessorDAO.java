package Dao;

import conexao.Conexao;
import model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfessorDAO {
    public boolean create(Professor professor) {
        String sql = "INSERT INTO professor (id_professor, nome, discplina, senha, usuario) VALUES (?, ?, ?, ?, ?)";
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, professor.getIdProfessor());
            pstmt.setString(2, professor.getNome());
            pstmt.setString(3, professor.getDisciplina());
            pstmt.setString(4, professor.getSenha());
            pstmt.setString(5, professor.getUsuario());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }
    public List<Professor> read() {
        ArrayList<Professor> professorList = new ArrayList<>();
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM professor");

            while (rs.next()) {
                Professor professor = new Professor(
                        rs.getInt("id_professor"),
                        rs.getString("nome"),
                        rs.getString("discplina"),
                        rs.getString("senha"),
                        rs.getString("usuario")
                );
                professorList.add(professor);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        conexao.desconectar(conn);
        return professorList;
    }
    public boolean update(Professor professor) {
        String sql = "UPDATE professor SET nome = ?, discplina = ?, senha = ?, cpf = ?, usuario = ? WHERE id_professor = ?";
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getDisciplina());
            pstmt.setString(3, professor.getSenha());
            pstmt.setString(4, professor.getUsuario());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }
    public int delete(int id_professor) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM professor WHERE id_professor = ?");
            pstmt.setInt(1, id_professor);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Professor deletado com sucesso");
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
