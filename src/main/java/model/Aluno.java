package model;

import java.util.regex.Pattern;

public class Aluno {

    private Integer matricula;
    private String nome;
    private String cpf;
    private String email;
    private String senha;
    private String turma;
    private String foto;
    // =========================
    // Construtores
    // =========================

    public Aluno(Integer matricula, String nome, String cpf, String email, String senha, String turma, String foto  ) {
        this.setMatricula(matricula);
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTurma(turma);
        this.setFoto(foto);
    }

    public Aluno(String nome, String cpf, String email, String senha, String turma) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setTurma(turma);
        this.setFoto(null);
    }

    public Aluno(String nome, String cpf, String email, String senha) {
        this.setNome(nome);
        this.setCpf(cpf);
        this.setEmail(email);
        this.setSenha(senha);
        this.setFoto(null);
    }


    // =========================
    // Getters e Setters
    // =========================

    public Integer getMatricula() {
        return matricula;
    }

    public void setMatricula(Integer matricula) {
        if (matricula == null) {
            throw new NullPointerException("A matrícula não pode ser nula.");
        }
        if (matricula <= 0) {
            throw new IllegalArgumentException("A matrícula deve ser maior que zero.");
        }
        this.matricula = matricula;
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

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null) {
            throw new NullPointerException("O CPF não pode ser nulo.");
        }

        String cpfLimpo = cpf.replaceAll("[^\\d]", "");

        this.cpf = cpfLimpo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null) {
            throw new NullPointerException("O email não pode ser nulo.");
        }
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("O email não pode estar em branco.");
        }

        validateEmail(email);
        this.email = email.trim().toLowerCase();
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
    public String getTurma() {
        return turma;
    }

    public void setTurma(String turma) {
        if (turma == null) {
            throw new NullPointerException("A turma não pode ser nula.");
        }
        if (turma.trim().isEmpty()) {
            throw new IllegalArgumentException("A turma não pode estar em branco.");  // ← aqui
        }
        this.turma = turma.trim().toUpperCase();
    }
    public String getFoto() {
        return foto;
    }
    public void setFoto(String foto) {
        if (foto == null) {
            foto ="https://institutogerminare-my.sharepoint.com/:i:/g/personal/gabriel_vigna_institutojef_org_br/IQDRaCkMoUtmRrCxATPR58YyAWL6lezDhNN0gvuKZCtPmF0?download=1";
        }
        this.foto = foto;
    }

    // =========================
    // toString protegido
    // =========================

    @Override
    public String toString() {
        return String.format(
                "Aluno | Matrícula: %-5s | Nome: %-20s | CPF: %-11s | Email: %-25s | Senha:[PROTEGIDA] | Turma: %-10s | Foto: %s",
                this.matricula != null ? this.matricula : "N/A",
                this.nome,
                this.cpf,
                this.email,
                this.turma != null ? this.turma : "-",
                this.foto != null ? this.foto : "sem foto"
        );
    }

    private static final Pattern PATTERN_MINUSCULA = Pattern.compile("[a-z]");
    private static final Pattern PATTERN_MAIUSCULA = Pattern.compile("[A-Z]");
    private static final Pattern PATTERN_DIGITO = Pattern.compile("\\d");
    private static final Pattern PATTERN_ESPECIAL = Pattern.compile("[^\\sA-Za-z0-9]");

    private static final Pattern PATTERN_EMAIL =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

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

    private void validateEmail(String email) {
        if (!PATTERN_EMAIL.matcher(email).matches()) {
            throw new IllegalArgumentException("Email inválido.");
        }
    }

}