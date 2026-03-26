/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author ymala
 */
public class ConexionBD {
    // Datos de tu base de datos local
    private static final String DATABASE = "cajeroautomatico_db";
    private static final String URL = "jdbc:mysql://localhost:3306/" + DATABASE + "?useSSL=false";
    private static final String USER = "root"; 
    private static final String PASSWORD = "123456789"; 

    public static Connection conectar() {
        Connection conexion = null;
        try {
            // Cargar el driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Intentar la conexión
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: No se encontró el Driver de MySQL.");
        } catch (SQLException e) {
            System.out.println("Error: No se pudo conectar a la base de datos: " + e.getMessage());
        }
        return conexion;
    }
}
