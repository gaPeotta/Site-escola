package utils;
import java.util.Random;

public class GeradorCodigo {
    public static String gerarCodigo() {
        Random random = new Random();
        int codigo = 100000 + random.nextInt(900000);
        return String.valueOf(codigo);
    }
}
