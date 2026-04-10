/**
 * Clase OperacionDAO
 * ------------------
 * Esta clase implementa el acceso a datos (DAO) para la entidad {@link Operacion}.
 * Se encarga de registrar las operaciones realizadas en el cajero automático
 * dentro de la tabla "transacciones" en la base de datos MySQL.
 *
 * <p>Funciones principales:</p>
 * - Registrar transferencias entre cuentas.
 * - Registrar depósitos en una cuenta.
 * - Registrar retiros de una cuenta.
 *
 * @author Ymalai Leonardo 
 * @author Luis Diaz 
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
package cajero.bd;

import java.sql.*;
import cajero.modelo.Operacion; 

public class OperacionDAO {
    
    /**
     * Registra una transferencia entre dos cuentas en la base de datos.
     *
     * @param idCuentaOrigen identificador de la cuenta origen.
     * @param monto cantidad transferida.
     * @param idCuentaDestino identificador de la cuenta destino.
     * @return {@code true} si la transferencia fue registrada exitosamente,
     *         {@code false} en caso de error.
     */
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
    
    /**
     * Registra un depósito en la cuenta indicada.
     *
     * @param idCuenta identificador de la cuenta donde se realiza el depósito.
     * @param monto cantidad depositada.
     * @return {@code true} si el depósito fue registrado exitosamente,
     *         {@code false} en caso de error.
     */
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

    /**
     * Registra un retiro en la cuenta indicada.
     *
     * @param idCuenta identificador de la cuenta donde se realiza el retiro.
     * @param monto cantidad retirada.
     * @return {@code true} si el retiro fue registrado exitosamente,
     *         {@code false} en caso de error.
     */
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
