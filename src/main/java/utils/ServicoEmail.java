package utils;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class ServicoEmail {
    public static void enviarCodigo(String destino, String codigo) throws Exception {

        final String email = "brenoxgomes0912@gmail.com";
        final String senha = "yrkk nxrn wpes ohgz";

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, senha);
                    }
                });

        Message message = new MimeMessage(session);

        message.setFrom(new InternetAddress(email));
        message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(destino));

        message.setSubject("Recuperação de senha");

        message.setText("Seu código de recuperação é: " + codigo);

        Transport.send(message);
    }
}
