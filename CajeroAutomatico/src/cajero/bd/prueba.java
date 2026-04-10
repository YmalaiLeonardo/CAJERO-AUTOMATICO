/**
 * Clase prueba
 * ------------
 * Esta clase permite verificar la conexión con la base de datos MySQL.
 *
 * <p>Funciones principales:</p>
 * - Conectar con la base de datos mediante {@link ConexionBD}.
 * - Mostrar en consola el estado de la conexión.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
package cajero.bd;
import java.sql.Connection;

public class prueba {
    /**
     * Método principal que ejecuta las pruebas de conexión.
     *
     * @param args argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        Connection c = ConexionBD.conectar();
        if (c != null) {
            System.out.println("¡CONEXIÓN ESTABLECIDA CON ÉXITO!");
        } else {
            System.out.println("LA CONEXIÓN FALLÓ.");
        }
    }   
}
