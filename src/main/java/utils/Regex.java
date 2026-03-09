package utils;

public class Regex {
    private static String apenasNumeros(String valor){
        return valor.replaceAll("\\D", "");
    }

    public static String formatarCpf(String cpf){
        cpf = apenasNumeros(cpf);
        if (cpf.length() == 11){
            return cpf.substring(0,3) + "." +
                    cpf.substring(3,6) + "." +
                    cpf.substring(6, 9) + "-" +
                    cpf.substring(9, 11);
        } return null;
    }
}
