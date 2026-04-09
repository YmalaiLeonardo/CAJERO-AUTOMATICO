
package cajero.bd;

/**
 *
 * @author ymala
 */
import cajero.util.HashUtil;
import java.sql.Connection;
public class prueba {
    public static void main(String[] args) {
        Connection c = ConexionBD.conectar();
        if (c != null) {
            System.out.println("¡CONEXIÓN ESTABLECIDA CON ÉXITO!");
        } else {
            System.out.println("LA CONEXIÓN FALLÓ.");
        }
        
        String pin = "5698"; 
        String salt = HashUtil.generarSalt();
        String hash = HashUtil.hashPin(pin, salt);

        System.out.println("Copia esto en pin_salt: " + salt);
        System.out.println("Copia esto en pin_hash: " + hash);
    }   
    
}
