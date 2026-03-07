package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import conexao.Conexao;

public class DashboardDAO {

    public Map<String, Object> getResumoDashboard(String disciplinaFiltro) {
        Map<String, Object> dados = new HashMap<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Conexao gerenciaConexao = new Conexao();
        boolean temFiltro = disciplinaFiltro != null && !disciplinaFiltro.trim().isEmpty();

        try {
            conn = gerenciaConexao.conectar();

            // 1. TOTAL DE ALUNOS
            if (temFiltro) {
                // Conta apenas os alunos que têm nota na disciplina filtrada
                stmt = conn.prepareStatement("SELECT COUNT(DISTINCT matricula_aluno) AS total FROM nota WHERE UPPER(disciplina) = UPPER(?)");
                stmt.setString(1, disciplinaFiltro);
            } else {
                // Conta todos os alunos da escola
                stmt = conn.prepareStatement("SELECT COUNT(matricula) AS total FROM aluno");
            }
            rs = stmt.executeQuery();
            if (rs.next()) dados.put("totalAlunos", rs.getInt("total"));
            rs.close();
            stmt.close();

            // 2. MÉDIA GERAL DA ESCOLA / DISCIPLINA
            String sqlMedia = "SELECT AVG((nota1 + nota2) / 2.0) AS media_geral FROM nota";
            if (temFiltro) sqlMedia += " WHERE UPPER(disciplina) = UPPER(?)";
            
            stmt = conn.prepareStatement(sqlMedia);
            if (temFiltro) stmt.setString(1, disciplinaFiltro);
            
            rs = stmt.executeQuery();
            if (rs.next()) dados.put("mediaEscola", rs.getDouble("media_geral"));
            rs.close();
            stmt.close();

            // 3. ALUNOS EM RISCO
            String sqlRisco = "SELECT COUNT(DISTINCT matricula_aluno) AS risco FROM nota WHERE ((nota1 + nota2) / 2.0) < 7";
            if (temFiltro) sqlRisco += " AND UPPER(disciplina) = UPPER(?)";
            
            stmt = conn.prepareStatement(sqlRisco);
            if (temFiltro) stmt.setString(1, disciplinaFiltro);
            
            rs = stmt.executeQuery();
            if (rs.next()) dados.put("alunosRisco", rs.getInt("risco"));
            rs.close();
            stmt.close();

            // 4. GRÁFICO DE BARRAS
            String sqlBarras = "SELECT UPPER(disciplina) AS disciplina, AVG((nota1 + nota2) / 2.0) AS media FROM nota";
            if (temFiltro) sqlBarras += " WHERE UPPER(disciplina) = UPPER(?)";
            sqlBarras += " GROUP BY UPPER(disciplina)";
            
            stmt = conn.prepareStatement(sqlBarras);
            if (temFiltro) stmt.setString(1, disciplinaFiltro);
            
            rs = stmt.executeQuery();
            List<String> disciplinas = new ArrayList<>();
            List<Double> medias = new ArrayList<>();
            while (rs.next()) {
                disciplinas.add(rs.getString("disciplina"));
                medias.add(rs.getDouble("media"));
            }
            dados.put("graficoDisciplinas", disciplinas);
            dados.put("graficoMedias", medias);
            rs.close();
            stmt.close();

            // 5. GRÁFICO DE ROSCA (APROVADOS/REPROVADOS)
            String sqlPizza = "SELECT situacao, COUNT(*) AS qtd FROM nota";
            if (temFiltro) sqlPizza += " WHERE UPPER(disciplina) = UPPER(?)";
            sqlPizza += " GROUP BY situacao";
            
            stmt = conn.prepareStatement(sqlPizza);
            if (temFiltro) stmt.setString(1, disciplinaFiltro);
            
            rs = stmt.executeQuery();
            int aprovados = 0;
            int reprovados = 0;
            while (rs.next()) {
                boolean situacao = rs.getBoolean("situacao");
                if (situacao) aprovados = rs.getInt("qtd");
                else reprovados = rs.getInt("qtd");
            }
            dados.put("qtdAprovados", aprovados);
            dados.put("qtdReprovados", reprovados);

        } catch (Exception e) {
            System.out.println("Erro ao buscar dados do Dashboard: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                gerenciaConexao.desconectar(conn); 
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return dados; 
    }
}