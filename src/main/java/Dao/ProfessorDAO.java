package Dao;

import conexao.Conexao;
import model.Administrador;
import model.Professor;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
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

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return lista;
    }


    public List<Professor> read(String nome, String orderBy, String direction){
        Conexao conexao = new Conexao();
        List<Professor> listaProfessor = new LinkedList<>();

        // Usa StringBuilder para construir a query dinamicamente de forma segura
        StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM professor");
        List<Object> parametros = new LinkedList<>(); // Lista para guardar parâmetros do PreparedStatement

        // Adiciona filtro WHERE com placeholder se 'nome' for fornecido
        if (nome != null && !nome.trim().isEmpty()) {
            sqlBuilder.append(" WHERE nome ILIKE ?");
            parametros.add("%" + nome.trim() + "%"); // Adiciona valor à lista de parâmetros
        }

        // Validação (Whitelisting) da coluna de ordenação
        String colunaOrdenacao = "id"; // Default seguro
        if (orderBy != null) {
            String lowerOrderBy = orderBy.trim().toLowerCase();
            if (lowerOrderBy.equals("nome")) {
                colunaOrdenacao = "nome";
            } else if (lowerOrderBy.equals("email")) {
                colunaOrdenacao = "email";
            }
        }

        // Validação da direção da ordenação
        String dir = "ASC";
        if (direction != null && direction.trim().equalsIgnoreCase("DESC")) {
            dir = "DESC";
        }

        // Adiciona ORDER BY seguro (após validação/whitelisting)
        sqlBuilder.append(" ORDER BY ").append(colunaOrdenacao).append(" ").append(dir);
        String sql = sqlBuilder.toString();

        // Usa try-with-resources
        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Define os parâmetros na ordem em que foram adicionados
            for (int i = 0; i < parametros.size(); i++) {
                pstmt.setObject(i + 1, parametros.get(i));
            }

            // Executa a query
            try (ResultSet rset = pstmt.executeQuery()) {
                // Processa os resultados
                while (rset.next()) {
                    Professor prof = new Professor(
                            rset.getInt("idprofessor"),
                            rset.getString("nome"),
                            rset.getString("disciplina"),
                            rset.getString("senha"),
                            rset.getString("usuario")
                    );
                    listaProfessor.add(prof);
                }
            } // rset é fechado automaticamente
        }catch (SQLException sqle){
            sqle.printStackTrace();
        }// pstmt e conn são fechados automaticamente
        // SQLException é propagada
        return listaProfessor;
    }
    public Professor buscarPorEmailESenha(String usuario, String senha){
        String sql = "SELECT * FROM professor WHERE usuario = ? and senha = ?";
        Conexao conexao = new Conexao();
        Professor prof = null;

        // Usa try-with-resources
        try (Connection conn = conexao.conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, usuario);
            pstmt.setString(2, senha);

            try (ResultSet rset = pstmt.executeQuery()) {
                if (rset.next()) {
                    Professor professor = new Professor(
                            rset.getInt("idprofessor"),
                            rset.getString("nome"),
                            rset.getString("disciplina"),
                            rset.getString("senha"),
                            rset.getString("usuario")
                    );
                }
            }
        } catch (SQLException sqle){
            sqle.printStackTrace();
        }
        return prof;
    }
    public Professor buscarPorId(int id) {
        Professor professor = null;
        String sql = "SELECT * FROM professor WHERE id_professor = ?";
        try (Connection conn = new Conexao().conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                professor = new Professor(
                        rs.getInt("idprofessor"),
                        rs.getString("nome"),
                        rs.getString("disciplina"),
                        rs.getString("senha"),
                        rs.getString("usuario")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return professor;
    }



    public int update(Professor professor) {
        String sql = "UPDATE professor SET nome = ?, disciplina = ?, senha = ?, usuario = ? WHERE id_professor = ?";

        try (Connection conn = new Conexao().conectar();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, professor.getNome());
            pstmt.setString(2, professor.getDisciplina());
            pstmt.setString(3, professor.getSenha());
            pstmt.setString(4, professor.getUsuario());
            pstmt.setInt(5, professor.getIdProfessor());

            return pstmt.executeUpdate();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    public int delete(int idProfessor) {
        String sql = "DELETE FROM professor WHERE id_professor = ?";
        Conexao conexao = new Conexao();
        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, idProfessor);
            return pstmt.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return 0;
        }
    }
}
