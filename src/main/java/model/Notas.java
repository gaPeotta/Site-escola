package model;

public class Notas {

    private Integer idNotas;
    private Integer matriculaAluno;
    private Integer idProfessor;
    private String disciplina;
    private String observacao;
    private double nota1;
    private double nota2;
    private String nomeAluno;
    private String nomeProfessor;
    private boolean situacao;

    // Construtor SEM ID (para inserts)
    public Notas(Integer matriculaAluno, Integer idProfessor, String disciplina,
                 String observacao, double nota1, double nota2, boolean situacao) {

        this.matriculaAluno = matriculaAluno;
        this.idProfessor = idProfessor;
        this.disciplina = disciplina;
        this.observacao = observacao;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.situacao = situacao;
    }

    // Construtor COM ID (para SELECT)
    public Notas(Integer idNotas, Integer matriculaAluno, Integer idProfessor,
                 String disciplina, String observacao, double nota1, double nota2, boolean situacao) {

        this.idNotas = idNotas;
        this.matriculaAluno = matriculaAluno;
        this.idProfessor = idProfessor;
        this.disciplina = disciplina;
        this.observacao = observacao;
        this.nota1 = nota1;
        this.nota2 = nota2;
        this.situacao = situacao;
    }

    // GETTERS
    public Integer getIdNotas() { return idNotas; }
    public Integer getMatriculaAluno() { return matriculaAluno; }
    public Integer getIdProfessor() { return idProfessor; }
    public String getDisciplina() { return disciplina; }
    public String getObservacao() { return observacao; }
    public double getNota1() { return nota1; }
    public double getNota2() { return nota2; }
    public String getNomeAluno() { return nomeAluno; }
    public String getNomeProfessor() { return nomeProfessor; }
    public boolean getSituacao() { return situacao; }

    // SETTERS
    public void setIdNotas(Integer idNotas) { this.idNotas = idNotas; }
    public void setMatriculaAluno(Integer matriculaAluno) { this.matriculaAluno = matriculaAluno; }
    public void setIdProfessor(Integer idProfessor) { this.idProfessor = idProfessor; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public void setNota1(double nota1) { this.nota1 = nota1; }
    public void setNota2(double nota2) { this.nota2 = nota2; }
    public void setNomeAluno(String nomeAluno) { this.nomeAluno = nomeAluno; }
    public void setNomeProfessor(String nomeProfessor) { this.nomeProfessor = nomeProfessor; }
    public void setSituacao(boolean situacao) { this.situacao = situacao; }
}