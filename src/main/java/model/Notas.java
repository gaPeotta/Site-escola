package model;

public class Notas {
    private Integer idNotas;
    private Integer matriculaAluno;
    private Integer idProfessor;
    private String discplina;
    private String observcao;
    private double nota1;
    private double nota2;

    public Notas(Integer matriculaAluno, Integer idProfessor, String discplina, String observcao, double nota1, double nota2) {
        this.matriculaAluno = matriculaAluno;
        this.idProfessor = idProfessor;
        this.discplina = discplina;
        this.observcao = observcao;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public Notas(Integer idNotas, Integer matriculaAluno, Integer idProfessor, String discplina, String observcao, double nota1, double nota2) {
        this.idNotas = idNotas;
        this.matriculaAluno = matriculaAluno;
        this.idProfessor = idProfessor;
        this.discplina = discplina;
        this.observcao = observcao;
        this.nota1 = nota1;
        this.nota2 = nota2;
    }

    public Integer getIdNotas() {
        return idNotas;
    }

    public Integer getMatriculaAluno() {
        return matriculaAluno;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public Integer getDisciplina() {
        return discplina;
    }

    public String getObservcao() {
        return observcao;
    }

    public double getNota1() {
        return nota1;
    }

    public double getNota2() {
        return nota2;
    }
}
