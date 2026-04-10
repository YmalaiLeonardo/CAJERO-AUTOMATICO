
package cajero.util;

/**
 * Clase HashUtil
 * --------------
 * Clase utilitaria para el manejo seguro del PIN de los usuarios.
 * Contiene métodos para generar salts aleatorios, aplicar hashing con SHA-256
 * y verificar la validez de un PIN ingresado contra el hash almacenado.
 *
 * <p>Funciones principales:</p>
 * - Generar un salt único para cada usuario.
 * - Hashear el PIN combinándolo con el salt mediante SHA-256.
 * - Verificar si un PIN ingresado coincide con el hash guardado.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

// Clase utilitaria para el manejo seguro del PIN
// Contiene los métodos de hashing y generación de salt
public class HashUtil {
    
    /**
     * Genera un salt aleatorio único para cada usuario.
     * El salt evita que dos usuarios con el mismo PIN tengan el mismo hash.
     *
     * @return cadena en Base64 que representa el salt generado.
     */
    public static String generarSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * Hashea el PIN combinándolo con el salt usando el algoritmo SHA-256.
     * Nunca se guarda el PIN real, solo el hash resultante.
     *
     * @param pin PIN ingresado por el usuario.
     * @param salt salt asociado al usuario.
     * @return cadena en Base64 que representa el hash del PIN,
     *         o {@code null} si ocurre un error durante el proceso.
     */
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
    
    /**
     * Verifica si el PIN ingresado coincide con el hash guardado.
     *
     * @param pinIngresado PIN ingresado por el usuario.
     * @param hashGuardado hash almacenado en la base de datos.
     * @param salt salt asociado al usuario.
     * @return {@code true} si el PIN es correcto,
     *         {@code false} si no coincide.
     */
    public static boolean verificarPin(String pinIngresado, String hashGuardado, String salt) {
        
        // Hashear el PIN ingresado con el mismo salt
        String hashIngresado = hashPin(pinIngresado, salt);
        
        // Comparar el hash generado con el guardado
        return hashGuardado.equals(hashIngresado);
    }
}
