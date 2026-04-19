/**
 * Clase ConexionBD
 * ----------------
 * Esta clase se encarga de gestionar la conexión entre la aplicación de escritorio
 * del cajero automático y la base de datos MySQL. 
 * 
 * Proporciona un método estático para establecer la conexión utilizando JDBC,
 * cargando el driver correspondiente y validando credenciales de acceso.
 * 
 * Características principales:
 * - Define los parámetros de conexión (URL, usuario y contraseña).
 * - Carga el driver de MySQL (com.mysql.cj.jdbc.Driver).
 * - Devuelve un objeto {@link Connection} listo para ejecutar consultas SQL.
 * - Maneja excepciones comunes como:
 *   - {@link ClassNotFoundException} si el driver JDBC no está disponible.
 *   - {@link SQLException} si ocurre un error al intentar conectarse.
 * 
 * Notas:
 * - Se recomienda cerrar la conexión después de usarla para liberar recursos.
 * - Los parámetros de usuario y contraseña deben configurarse según la instalación local de MySQL.
 * 
 * @author Ymalai Leonardo 
 * @author Luis Diaz 
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
package cajero.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    // Datos de tu base de datos local
    private static final String DATABASE = "cajeroautomatico_db";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "root"; 
    private static final String PASSWORD = "123456789"; 

    /**
     * Establece la conexión con la base de datos MySQL.
     *
     * @return un objeto {@link Connection} si la conexión fue exitosa,
     *         o {@code null} si ocurrió un error.
     */
    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver JDBC de MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Intentar la conexión con los parámetros definidos
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el Driver de MySQL.");
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}
