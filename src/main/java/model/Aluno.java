package model;

public class Aluno {
    private Integer matricula;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String turma;

    public Aluno(Integer matricula, String nome, String cpf, String email, String senha, String turma) {
        this.matricula = matricula;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.turma = turma;
    }

    public Aluno(String nome, String cpf, String email, String senha, String turma) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.senha = senha;
        this.turma = turma;
    }

    public Integer getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public String getTurma() {
        return turma;
    }
}
