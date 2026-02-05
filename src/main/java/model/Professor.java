package model;

import java.util.regex.Pattern;

public class Professor {

    private Integer idProfessor;
    private String nome;
    private String disciplina;
    private String senha;
    private String usuario;

    // ---------------- CONSTRUTORES ----------------

    public Professor(Integer idProfessor, String nome, String disciplina, String senha, String usuario) {
        this.setIdProfessor(idProfessor);
        this.setNome(nome);
        this.setDisciplina(disciplina);
        this.setSenha(senha);
        this.setUsuario(usuario);
    }

    public Professor(String nome, String disciplina, String senha, String usuario) {
        this.setNome(nome);
        this.setDisciplina(disciplina);
        this.setSenha(senha);
        this.setUsuario(usuario);
    }

    // ---------------- GETTERS E SETTERS ----------------

    public Integer getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(Integer idProfessor) {
        if (idProfessor == null) {
            throw new NullPointerException("O ID do professor não pode ser nulo.");
        }

        if (idProfessor <= 0) {
            throw new IllegalArgumentException("O ID do professor deve ser maior que zero.");
        }

        this.idProfessor = idProfessor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {

        if (nome == null) {
            throw new NullPointerException("O nome não pode ser nulo.");
        }

        if (nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode estar em branco.");
        }

        this.nome = nome.trim();
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {

        if (disciplina == null) {
            throw new NullPointerException("A disciplina não pode ser nula.");
        }

        if (disciplina.trim().isEmpty()) {
            throw new IllegalArgumentException("A disciplina não pode estar em branco.");
        }

        this.disciplina = disciplina.trim();
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {

        if (senha == null) {
            throw new NullPointerException("A senha não pode ser nula.");
        }

        if (senha.trim().isEmpty()) {
            throw new IllegalArgumentException("A senha não pode estar em branco.");
        }

        validateSenha(senha);
        this.senha = senha;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {

        if (usuario == null) {
            throw new NullPointerException("O usuário não pode ser nulo.");
        }

        if (usuario.trim().isEmpty()) {
            throw new IllegalArgumentException("O usuário não pode estar em branco.");
        }

        validateUsuario(usuario);
        this.usuario = usuario.trim();
    }

    // ---------------- TO STRING ----------------

    @Override
    public String toString() {
        return String.format(
                "Professor | ID: %-3d | Nome: %-20s | Disciplina: %-20s | Usuário: %-15s | Senha: [PROTEGIDA]",
                this.idProfessor,
                this.nome,
                this.disciplina,
                this.usuario
        );
    }

    // ---------------- REGEX / VALIDAÇÕES ----------------

    private static final Pattern PATTERN_MINUSCULA = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_MAIUSCULA = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGITO = Pattern.compile("\\d");
    private static final Pattern PATTERN_ESPECIAL = Pattern.compile("[^\\sA-Za-z0-9]");

    private static final Pattern PATTERN_USUARIO = Pattern.compile("^[a-zA-Z0-9._-]{4,20}$");

    /*
     * Regras senha:
     * - mínimo 8 caracteres
     * - 1 maiúscula
     * - 1 minúscula
     * - 1 número
     * - 1 especial
     */
    private void validateSenha(String senha) {

        if (senha.length() < 8) {
            throw new IllegalArgumentException("A senha deve ter no mínimo 8 caracteres.");
        }

        if (!PATTERN_MINUSCULA.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve conter ao menos uma letra minúscula.");
        }

        if (!PATTERN_MAIUSCULA.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve conter ao menos uma letra maiúscula.");
        }

        if (!PATTERN_DIGITO.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve conter ao menos um número.");
        }

        if (!PATTERN_ESPECIAL.matcher(senha).find()) {
            throw new IllegalArgumentException("A senha deve conter ao menos um caractere especial.");
        }
    }

    /*
     * Regras usuário:
     * - 4 a 20 caracteres
     * - letras, números, ponto, underline ou hífen
     */
    private void validateUsuario(String usuario) {

        if (!PATTERN_USUARIO.matcher(usuario).matches()) {
            throw new IllegalArgumentException(
                    "Usuário inválido. Use entre 4 e 20 caracteres com letras, números, ponto, underline ou hífen."
            );
        }
    }
}
