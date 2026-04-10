
package cajero.servicio;

/**
 * Clase CorreoService
 * -------------------
 * Maneja el envío de comprobantes de operaciones bancarias por correo electrónico.
 * Genera mensajes en formato HTML con los detalles de la operación y utiliza
 * el protocolo SMTP para enviar correos mediante JavaMail.
 *
 * <p>Funciones principales:</p>
 * - Configurar los parámetros del servidor de correo (host, puerto, correo origen).
 * - Generar el contenido HTML del comprobante con los datos de la operación.
 * - Enviar el comprobante al correo electrónico del usuario.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
import cajero.bd.UsuarioDAO;
import cajero.modelo.Operacion;
import cajero.modelo.Transferencia;
import cajero.modelo.Usuario;
import jakarta.mail.Session;
import jakarta.mail.Message;
import jakarta.mail.Transport;
import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class CorreoService {
    // Datos del servidor de correo
    private String host;
    private int puerto;
    private String correoOrigen;
    
    /**
     * Constructor que inicializa los parámetros del servidor de correo.
     * Por defecto se configura para usar Gmail con puerto 587.
     */
    public CorreoService() {
        this.host = "smtp.gmail.com";
        this.puerto = 587;
        this.correoOrigen = "bancodigital00@gmail.com";
    }
    
    /**
     * Genera el mensaje HTML del comprobante con los datos de la operación.
     *
     * @param usuario objeto {@link Usuario} que representa al titular de la cuenta.
     * @param operacion objeto {@link Operacion} que contiene los detalles de la operación.
     * @return cadena en formato HTML con el comprobante de la operación.
     */
    public String generarMensaje(Usuario usuario, Operacion operacion) {
        
        // Formatear la fecha de la operación
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = formato.format(new Date());

        // Construir el mensaje del comprobante en HTML
        String mensaje = "<html>"
            + "<head>"
            + "<style>"
            + "body { font-family: 'Georgia', Arial, sans-serif; margin: 20px; color: #333; font-size:16px; }"
            + "h2 { text-align: center; font-size:20px; }"
            + "h3 { color: #1A5276; text-align: center; font-size:30px; }"
            + "table { width: 100%; border-collapse: collapse; margin-top: 15px; font-size:16px; }"
            + "td, th { padding: 10px; border: 1px solid #ccc; }"
            + "th { background-color: #D6EAF8; text-align: left; }"
            + "</style>"
            + "</head>"
            + "<body>"
            + "<h3><b>Banco Digital</b></h3>"
            + "<h2><b>Comprobante de operación</b></h2>"
            + "<p>Estimado/a <b>" + usuario.getNombre() + "</b>,</p>"
            + "<p>Le informamos que su operación se realizó exitosamente.</p>"
            + "<h2>Detalles de la operación:</h3>";
           
           
           // Construir tabla según tipo de operación                               
            if (operacion.getTipo().equalsIgnoreCase("TRANSFERENCIA")) {
                Transferencia transferencia = (Transferencia) operacion;
                
                // Obtener el usuario titular de la cuenta destino
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                Usuario titular = usuarioDAO.obtenerUsuarioPorId(
                    transferencia.getCuentaDestino().getIdUsuario()
                );
                mensaje += "<table>"
                    + "<tr><th>Tipo de operación</th><td>" + operacion.getTipo().toUpperCase() + "</td></tr>"
                    + "<tr><th>Monto</th><td>$" + String.format("%.2f", transferencia.getMonto()) + "</td></tr>"
                    + "<tr><th>Cuenta destino</th><td>" + transferencia.getCuentaDestino().getNumeroCuenta() + "</td></tr>"
                    + "<tr><th>Titular</th><td>" + titular.getNombre() + "</td></tr>"
                    + "<tr><th>Fecha y hora</th><td>" + fechaFormateada + "</td></tr>"
                    + "</table>";
            } else {
                mensaje += "<table>"
                    + "<tr><th>Tipo de operación</th><td>" + operacion.getTipo().toUpperCase() + "</td></tr>"
                    + "<tr><th>Monto</th><td>$" + String.format("%.2f", operacion.getMonto()) + "</td></tr>"
                    + "<tr><th>Cuenta</th><td>" + usuario.getNumeroCuenta() + "</td></tr>"
                    + "<tr><th>Fecha y hora</th><td>" + fechaFormateada + "</td></tr>"                   
                    + "</table>";
            }
            
            mensaje += "<p style=\"text-align:center; margin-top:20px;\"><b>Gracias por usar Banco Digital</b>.</p>"
        + "</body></html>";
        
        return mensaje;
    }
    
    /**
     * Envía el comprobante de la operación al correo electrónico del usuario.
     * Utiliza JavaMail para establecer la conexión SMTP y enviar el mensaje.
     *
     * @param usuario objeto {@link Usuario} que recibirá el comprobante.
     * @param operacion objeto {@link Operacion} que contiene los detalles de la operación.
     * @return {@code true} si el correo fue enviado exitosamente,
     *         {@code false} si ocurrió algún error durante el envío.
     */
    public boolean enviarComprobante(Usuario usuario, Operacion operacion) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");

            final String correoOrigen = "bancodigital00@gmail.com";
            final String password = "uleg wxdm mkel dgmx"; 

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(correoOrigen, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(correoOrigen));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(usuario.getCorreo()));
            message.setSubject("Comprobante de operación - Banco Digital");
            
            String cuerpo = generarMensaje(usuario, operacion);                     
            message.setContent(cuerpo, "text/html; charset=UTF-8");        
            
            Transport.send(message);

            System.out.println("Correo enviado a: " + usuario.getCorreo());
            return true;

        } catch (Exception e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
            return false;
        }
    }
}
