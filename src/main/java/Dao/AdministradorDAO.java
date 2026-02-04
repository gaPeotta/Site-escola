package Dao;

import conexao.Conexao;
import model.Administrador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdministradorDAO {
    public boolean create(Administrador administrador) {
        String sql = "INSERT INTO administrador (id_administrador, nome, senha, email) VALUES (?, ?, ?, ?)";
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, administrador.getIdAdm());
            pstmt.setString(2, administrador.getNome());
            pstmt.setString(3, administrador.getSenha());
            pstmt.setString(4, administrador.getEmail());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public List<Administrador> read() {
        ArrayList<Administrador> admList = new ArrayList<>();
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        ResultSet rs;
        try {
            Statement stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT * FROM administrador");

            while (rs.next()) {
                Administrador administrador = new Administrador(
                        rs.getInt("id_administrador"),
                        rs.getString("nome"),
                        rs.getString("senha"),
                        rs.getString("email")
                );
                admList.add(administrador);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        conexao.desconectar(conn);
        return admList;
    }

    public boolean update(Administrador administrador) {
        String sql = "UPDATE administrador SET nome = ?, senha = ?, email = ? WHERE id_administrador = ?";
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, administrador.getNome());
            pstmt.setString(2, administrador.getSenha());
            pstmt.setString(3, administrador.getEmail());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        } finally {
            conexao.desconectar(conn);
        }
    }

    public int delete(int idAdm) {
        Conexao conexao = new Conexao();
        Connection conn = conexao.conectar();
        try {
            PreparedStatement pstmt = conn.prepareStatement("DELETE FROM alerta WHERE id_administrador = ?");
            pstmt.setInt(1, idAdm);
            if (pstmt.executeUpdate() > 0) {
                System.out.println("Alerta deletado com sucesso");
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
