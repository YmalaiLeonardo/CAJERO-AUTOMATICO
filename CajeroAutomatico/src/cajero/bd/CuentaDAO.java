/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.bd;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author ymala
 */
public class CuentaDAO {
    // MÉTODO 1: Obtener el saldo actual
    public double obtenerSaldo(int idUsuario) {
        String sql = "SELECT saldo FROM cuentas WHERE id_usuario = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                return rs.getDouble("saldo");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener saldo: " + e.getMessage());
        }
        return 0.0;
    }
    
    // MÉTODO 2: Depositar dinero (Sumar)
    public boolean procesarDeposito(int idUsuario, double monto) {
        String sql = "UPDATE cuentas SET saldo = saldo + ? WHERE id_usuario = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDouble(1, monto);
            ps.setInt(2, idUsuario);
            
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al depositar: " + e.getMessage());
            return false;
        }
    }
    
   // MÉTODO 3: Retirar dinero (Restar)
    public boolean procesarRetiro(int idUsuario, double monto) {
        // Solo resta si el saldo es mayor o igual al monto
        String sql = "UPDATE cuentas SET saldo = saldo - ? WHERE id_usuario = ? AND saldo >= ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setDouble(1, monto);
            ps.setInt(2, idUsuario);
            ps.setDouble(3, monto);
            
            int filas = ps.executeUpdate();
            return filas > 0;
        } catch (SQLException e) {
            System.err.println("Error al retirar: " + e.getMessage());
            return false;
        }
    }
    
    
}
