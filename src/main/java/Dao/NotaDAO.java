package Dao;

import conexao.Conexao;
import model.Aluno;
import model.Notas;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaDAO {public boolean create(Notas notas) {
    String sql = "INSERT INTO aluno (id_notas, matricula_alunos, disciplina, id_professor, obeservacao, nota1, nota2) VALUES (?, ?, ?, ?, ?,?,?)";
    Conexao conexao = new Conexao();
    Connection conn = conexao.conectar();
    try {
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, notas.getIdNotas());
        pstmt.setInt(2, notas.getMatriculaAluno());
        pstmt.setString(3, notas.getDisciplina());
        pstmt.setInt(4, notas.getIdProfessor());
        pstmt.setString(5, notas.getObservcao());
        pstmt.setDouble(6, notas.getNota1());
        pstmt.setDouble(7, notas.getNota2());

        return pstmt.executeUpdate() > 0;
    } catch (SQLException sqle) {
        sqle.printStackTrace();
        return false;
    } finally {
        conexao.desconectar(conn);
    }
}
    public List<Notas> read() {
        ArrayList<Notas> notasList = new ArrayList<>();
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM notas");

            while (rs.next()) {
                Notas notas = new Notas(
                        rs.getInt("id_notas"),
                        rs.getInt("id_professor"),
                        rs.getInt("matricula_aluno"),
                        rs.getString("discplina"),
                        rs.getString("observacao"),
                        rs.getDouble("nota1"),
                        rs.getDouble("nota2")

                );
                notasList.add(notas);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        conexao.desconectar(conn);
        return notasList;
    }
    public boolean update(Notas notas) {
        String sql = "UPDATE aluno SET id_professor = ?, matricula_aluno = ?, discplina = ?, observacao = ?, nota1 = ?,nota2 = ? WHERE id_notas = ?";
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, notas.getIdProfessor());
            pstmt.setInt(2, notas.getMatriculaAluno());
            pstmt.setString(3, notas.getDisciplina());
            pstmt.setString(4, notas.getObservcao());
            pstmt.setDouble(5, notas.getNota1());
            pstmt.setDouble(6, notas.getNota2());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }
    public int delete(int idNotas) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM notas WHERE id_notas = ?");
            pstmt.setInt(1, idNotas);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Notas deletado com sucesso");
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
