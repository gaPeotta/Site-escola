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

    public Map<String, Object> getResumoDashboard() {
        Map<String, Object> dados = new HashMap<>();
        
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        Conexao gerenciaConexao = new Conexao();

        try {
            conn = gerenciaConexao.conectar();

            // 1. TOTAL DE ALUNOS
            stmt = conn.prepareStatement("SELECT COUNT(matricula) AS total FROM aluno");
            rs = stmt.executeQuery();
            if (rs.next()) dados.put("totalAlunos", rs.getInt("total"));
            rs.close();
            stmt.close();

            // 2. MÉDIA GERAL DA ESCOLA
            stmt = conn.prepareStatement("SELECT AVG((nota1 + nota2) / 2.0) AS media_geral FROM notas");
            rs = stmt.executeQuery();
            if (rs.next()) dados.put("mediaEscola", rs.getDouble("media_geral"));
            rs.close();
            stmt.close();

            // 3. ALUNOS COM MEDIA MENOR Q 7
            stmt = conn.prepareStatement("SELECT COUNT(DISTINCT matricula_aluno) AS risco FROM notas WHERE ((nota1 + nota2) / 2.0) < 7");
            rs = stmt.executeQuery();
            if (rs.next()) dados.put("alunosRisco", rs.getInt("risco"));
            rs.close();
            stmt.close();

            // 4. MEDIA POR DISCIPLINA
            stmt = conn.prepareStatement("SELECT disciplina, AVG((nota1 + nota2) / 2.0) AS media FROM notas GROUP BY disciplina");
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

            // 5. DADOS PARA O GRÁFICO DE ROSCA (Aprovações vs Reprovações)
            stmt = conn.prepareStatement("SELECT situacao, COUNT(*) AS qtd FROM notas GROUP BY situacao");
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