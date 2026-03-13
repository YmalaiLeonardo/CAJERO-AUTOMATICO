/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.util;

/**
 *
 * @author ymala
 */
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

// Clase utilitaria para el manejo seguro del PIN
// Contiene los métodos de hashing y generación de salt
public class HashUtil {
    
    // Genera un salt aleatorio único para cada usuario
    // El salt evita que dos usuarios con el mismo PIN tengan el mismo hash
    public static String generarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    // Hashea el PIN combinándolo con el salt usando SHA-256
    // Nunca se guarda el PIN real, solo este hash
    public static String hashPin(String pin, String salt) {
        try {
            // Combinar el PIN con el salt
            String pinConSalt = pin + salt;
            
            // Aplicar el algoritmo SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(pinConSalt.getBytes());
            
            // Convertir el resultado a texto legible
            return Base64.getEncoder().encodeToString(hashBytes);
            
        } catch (Exception e) {
            // Si ocurre un error retorna null
            return null;
        }
    }
    
    // Verifica si el PIN ingresado coincide con el hash guardado
    // Retorna true si el PIN es correcto, false si no
    public static boolean verificarPin(String pinIngresado, String hashGuardado, String salt) {
        
        // Hashear el PIN ingresado con el mismo salt
        String hashIngresado = hashPin(pinIngresado, salt);
        
        // Comparar el hash generado con el guardado
        return hashGuardado.equals(hashIngresado);
    }
}
