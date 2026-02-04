package model;

public class Notas {
    private Integer idNotas;
    private Integer idAluno;
    private Integer idProfessor;
    private Integer idDiscplina;
    private String observcao;
    private boolean nota1;
    private boolean nota2;

    public Notas(Integer idAluno, Integer idProfessor, Integer idDiscplina, String observcao, boolean nota1, boolean nota2) {
        this.idAluno = idAluno;
        this.idProfessor = idProfessor;
        this.idDiscplina = idDiscplina;
        this.observcao = observcao;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public Notas(Integer idNotas, Integer idAluno, Integer idProfessor, Integer idDiscplina, String observcao, boolean nota1, boolean nota2) {
        this.idNotas = idNotas;
        this.idAluno = idAluno;
        this.idProfessor = idProfessor;
        this.idDiscplina = idDiscplina;
        this.observcao = observcao;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public Integer getIdNotas() {
        return idNotas;
    }

    public Integer getIdAluno() {
        return idAluno;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public Integer getIdDiscplina() {
        return idDiscplina;
    }

    public String getObservcao() {
        return observcao;
    }

    public boolean isNota1() {
        return nota1;
    }

    public boolean isNota2() {
        return nota2;
    }
}
