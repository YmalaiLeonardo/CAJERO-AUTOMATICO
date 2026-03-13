/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.servicio;

/**
 *
 * @author ymala
 */
import cajero.modelo.Usuario;
import cajero.modelo.Operacion;
import java.text.SimpleDateFormat;
import java.util.Date;

// Clase que maneja el envío de comprobantes por correo
// Por ahora simulada, después se conectará con JavaMail
public class CorreoService {
    
    // Datos del servidor de correo
    // Estos se configurarán cuando se implemente JavaMail
    private String host;
    private int puerto;
    private String correoOrigen;
    
    // Constructor
    public CorreoService() {
        this.host = "smtp.gmail.com";
        this.puerto = 587;
        this.correoOrigen = "cajero@bancodigital.com";
    }
    
    // Genera el mensaje del comprobante con los datos de la operación
    public String generarMensaje(Usuario usuario, Operacion operacion) {
        
        // Formatear la fecha de la operación
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String fechaFormateada = formato.format(new Date());
        
        // Construir el mensaje del comprobante
        String mensaje = "Estimado/a " + usuario.getNombre() + ",\n\n"
            + "Le informamos que su operación se realizó exitosamente.\n\n"
            + "DETALLES DE LA OPERACIÓN:\n"
            + "Tipo: " + operacion.getTipo().toUpperCase() + "\n"
            + "Monto: $" + String.format("%.2f", operacion.getMonto()) + "\n"
            + "Fecha: " + fechaFormateada + "\n"
            + "Cuenta: " + usuario.getNumeroCuenta() + "\n\n"
            + "Gracias por usar Banco Digital.\n";
        
        return mensaje;
    }
    
    // Envía el comprobante al correo del usuario
    // Por ahora simulado, imprime en consola
    // Después se reemplazará por JavaMail
    public boolean enviarComprobante(Usuario usuario, Operacion operacion) {
        try {
            // Generar el mensaje
            String mensaje = generarMensaje(usuario, operacion);
            
            // Por ahora solo simulamos el envío imprimiendo en consola
            System.out.println("=== COMPROBANTE ENVIADO ===");
            System.out.println("Para: " + usuario.getCorreo());
            System.out.println(mensaje);
            System.out.println("==========================");
            
            return true; // Envío simulado exitoso
            
        } catch (Exception e) {
            System.out.println("Error al enviar correo: " + e.getMessage());
            return false;
        }
    }
}
