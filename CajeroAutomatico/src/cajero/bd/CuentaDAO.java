/**
 * Clase CuentaDAO
 * ----------------
 * Esta clase implementa el acceso a datos (DAO) para la entidad {@link Cuenta}.
 * Se encarga de realizar operaciones sobre la tabla "cuentas" en la base de datos MySQL,
 * utilizando consultas SQL a través de JDBC.
 *
 * <p>Funciones principales:</p>
 * - Consultar el saldo actual de un usuario.
 * - Procesar depósitos y retiros con validaciones de saldo.
 * - Buscar cuentas por número de cuenta.
 * - Actualizar el saldo de una cuenta específica.
 *
 * @author Ymalai Leonardo 
 * @author Luis Diaz 
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
package cajero.bd;
import cajero.modelo.Cuenta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CuentaDAO {
    /**
     * Obtiene el saldo actual de un usuario.
     *
     * @param idUsuario identificador único del usuario.
     * @return el saldo actual de la cuenta, o 0.0 si ocurre un error.
     */
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
    
    /**
     * Procesa un depósito en la cuenta de un usuario.
     * Suma el monto indicado al saldo actual.
     *
     * @param idUsuario identificador único del usuario.
     * @param monto cantidad a depositar.
     * @return {@code true} si el depósito fue exitoso, {@code false} en caso contrario.
     */
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
    
    /**
     * Procesa un retiro en la cuenta de un usuario.
     * Solo permite retirar si el saldo es mayor o igual al monto solicitado.
     *
     * @param idUsuario identificador único del usuario.
     * @param monto cantidad a retirar.
     * @return {@code true} si el retiro fue exitoso, {@code false} en caso contrario.
     */
    public boolean procesarRetiro(int idUsuario, double monto) {
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
    
    /**
     * Busca una cuenta en la base de datos por su número de cuenta.
     *
     * @param numeroCuenta número de cuenta a buscar.
     * @return un objeto {@link Cuenta} con los datos encontrados,
     *         o {@code null} si no existe la cuenta.
     */
    public Cuenta buscarCuentaPorNumero(String numeroCuenta) {
        String sql = "SELECT id, id_usuario, saldo, numeroCuenta FROM cuentas WHERE numeroCuenta = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, numeroCuenta);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Cuenta(
                    rs.getInt("id"),
                    rs.getInt("id_usuario"),
                    rs.getDouble("saldo"),
                    rs.getString("numeroCuenta")
                );
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cuenta: " + e.getMessage());
        }
        return null;
    }
    
    /**
     * Actualiza el saldo de una cuenta específica.
     *
     * @param idCuenta identificador único de la cuenta.
     * @param nuevoSaldo nuevo valor de saldo a establecer.
     * @return {@code true} si la actualización fue exitosa, {@code false} en caso contrario.
     */
    public boolean actualizarSaldo(int idCuenta, double nuevoSaldo) {
        String sql = "UPDATE cuentas SET saldo = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, nuevoSaldo);
            ps.setInt(2, idCuenta);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar saldo: " + e.getMessage());
            return false;
        }
    }
   
}
