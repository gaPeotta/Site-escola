package model;

public class PreMatricula {
    private int id_prematricula;
    private String cpf;

    public PreMatricula(int id_prematricula, String cpf) {
        this.id_prematricula = id_prematricula;
        this.cpf = cpf;
    }
    public int getId_prematricula() {
        return id_prematricula;
    }
    public void setId_prematricula(int id_prematricula) {
        if (id_prematricula <= 0) { // Exceção: verifica se o ID é negativo ou igual a zero
            throw new IllegalArgumentException("O ID não pode ser zero ou negativo.");
        }
        this.id_prematricula = id_prematricula;
    }
    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        if (cpf == null) {
            throw new NullPointerException("O CPF não pode ser nulo.");
        }

        String cpfLimpo = cpf.replaceAll("[^\\d]", "");
        validateCpf(cpfLimpo);

        this.cpf = cpfLimpo;
    }

    private void validateCpf(String cpfLimpo) {
        if (cpfLimpo.length() != 11) {
            throw new IllegalArgumentException("CPF inválido. Deve conter 11 dígitos.");
        }
    }
}
