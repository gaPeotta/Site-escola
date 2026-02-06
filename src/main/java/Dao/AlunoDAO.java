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
    public int create(Aluno aluno){

        String sql = "INSERT INTO aluno (nome, senha, email, cpf, turma, situacao) VALUES (?, ?, ?, ?, ?, ?)";

        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ){

            pstmt.setString(1, aluno.getNome());
            pstmt.setString(2, aluno.getSenha());
            pstmt.setString(3, aluno.getEmail());
            pstmt.setString(4, aluno.getCpf());
            pstmt.setString(5, aluno.getTurma());
            pstmt.setBoolean(6, true);

            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();

            if(rs.next()){
                return rs.getInt(1);
            }

            return 0;

        } catch (SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    /*
     * Busca todos os alunos.
     */
    public List<Aluno> read(){

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
                        rs.getBoolean("situacao")
                );

                alunos.add(aluno);
            }
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return alunos;
    }

    /*
     * Busca alunos filtrando por nome e ordenando.
     * Busca pela situacao.
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
        } else if (direction != null && direction.equalsIgnoreCase("SIT")) {
            dir = "situacao DESC";

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
                            rs.getString("turma"),
                            rs.getBoolean("situacao")
                    );

                    alunos.add(aluno);
                }
            }
        }catch (SQLException sqle) {
            sqle.printStackTrace();
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

    /*
     * Busca aluno pela matrícula.
     */
    public Aluno read(int matricula) {

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
                            rs.getString("turma"),
                            rs.getBoolean("situacao")
                    );
                }
            }
        }catch (SQLException sqle) {
            sqle.printStackTrace();
        }

        return null;
    }

    /*
     * Atualiza um aluno.
     */
    public int update(Aluno aluno){

        String sql = "UPDATE aluno SET nome = ?, senha = ?, email = ?, cpf = ?, turma = ?, situacao = ? WHERE matricula = ?";
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
            pstmt.setBoolean(6, aluno.getSituacao());
            pstmt.setInt(7, aluno.getMatricula());

            return pstmt.executeUpdate();
        }catch (SQLException sqle) {
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Atualiza aluno via parâmetros diretos.
     */
    public int update(int matricula, String nome, String senha, String email, String cpf, String turma, boolean situacao){

        String sql = "UPDATE aluno SET nome = ?, senha = ?, email = ?, cpf = ?, turma = ?, situacao = ? WHERE matricula = ?";
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
            pstmt.setBoolean(6, situacao);
            pstmt.setInt(7, matricula);

            return pstmt.executeUpdate();

        }catch (SQLException sqle){
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Remove aluno pela matrícula.
     */
    public int delete(int matricula)  {

        String sql = "DELETE FROM aluno WHERE matricula = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setInt(1, matricula);
            return pstmt.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return 0;
        }
    }

    /*
     * Remove aluno pelo nome (cuidado).
     */
    public int delete(String nome){

        String sql = "DELETE FROM aluno WHERE nome = ?";
        Conexao conexao = new Conexao();

        try (
                Connection conn = conexao.conectar();
                PreparedStatement pstmt = conn.prepareStatement(sql)
        ) {

            pstmt.setString(1, nome);
            return pstmt.executeUpdate();
        }catch (SQLException sqle){
            sqle.printStackTrace();
            return 0;
        }
    }
}
