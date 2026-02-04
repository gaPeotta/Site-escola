package model;

public class Professor {
    private Integer idProfessor;
    private String nome;
    private String disciplina;
    private String senha;
    private String usuario;

    public Professor(Integer idProfessor ,String nome,String disciplina,String senha,String usuario ){
    this.idProfessor = idProfessor;
    this.nome = nome;
    this.disciplina = disciplina;
    this.senha = senha;
    this.usuario = usuario;
    }

    public Professor(String nome, String disciplina, String senha, String usuario) {
        this.nome = nome;
        this.disciplina = disciplina;
        this.senha = senha;
        this.usuario = usuario;
    }

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public String getNome() {
        return nome;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public String getSenha() {
        return senha;
    }

    public String getUsuario() {
        return usuario;
    }
}
