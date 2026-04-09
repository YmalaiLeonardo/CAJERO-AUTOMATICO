/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.bd;

/**
 *
 * @author ymala
 */

import java.sql.*;
import cajero.modelo.Operacion; 

public class OperacionDAO {
    
    public boolean registrarTransferencia(int idCuentaOrigen, double monto, int idCuentaDestino) {
        String sql = "INSERT INTO transacciones (id_cuenta, tipo, monto, fecha, id_cuenta_destino) VALUES (?, ?, ?, NOW(), ?)";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuentaOrigen);
            ps.setString(2, "Transferencia");
            ps.setDouble(3, monto);
            ps.setInt(4, idCuentaDestino);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar transferencia: " + e.getMessage());
            return false;
        }
        
        
    }

    public boolean registrarDeposito(int idCuenta, double monto) {
        String sql = "INSERT INTO transacciones (id_cuenta, tipo, monto, fecha) VALUES (?, ?, ?, NOW())";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);
            ps.setString(2, "DEPOSITO");
            ps.setDouble(3, monto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar depósito: " + e.getMessage());
            return false;
        }
    }

    public boolean registrarRetiro(int idCuenta, double monto) {
        String sql = "INSERT INTO transacciones (id_cuenta, tipo, monto, fecha) VALUES (?, ?, ?, NOW())";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);
            ps.setString(2, "RETIRO");
            ps.setDouble(3, monto);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al registrar retiro: " + e.getMessage());
            return false;
        }
    }
}
