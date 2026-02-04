package model;

public class Administrador {
    private Integer idAdm;
    private String nome;
    private String email;
    private String senha;

    public Administrador(Integer idAdm, String nome, String email, String senha) {
        this.idAdm = idAdm;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Administrador(String nome, String email, String senha) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }

    public Integer getIdAdm() {
        return idAdm;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }
}
