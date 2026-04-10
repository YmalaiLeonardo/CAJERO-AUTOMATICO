/**
 * Clase UsuarioDAO
 * ----------------
 * Esta clase implementa el acceso a datos (DAO) para la entidad {@link Usuario}.
 * Se encarga de realizar operaciones sobre la tabla "usuarios" en la base de datos MySQL,
 * permitiendo gestionar información relacionada con cuentas, PIN, intentos fallidos y estado de bloqueo.
 *
 * <p>Funciones principales:</p>
 * - Obtener un usuario por número de cuenta.
 * - Actualizar el estado de bloqueo de un usuario.
 * - Incrementar y resetear intentos fallidos de inicio de sesión.
 * - Consultar la cantidad de intentos fallidos.
 * - Obtener un usuario por su identificador único.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
package cajero.bd;

import cajero.modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {
    /**
     * Obtiene un usuario a partir de su número de cuenta.
     *
     * @param numCuenta número de cuenta del usuario.
     * @return un objeto {@link Usuario} con los datos encontrados,
     *         o {@code null} si no existe el usuario.
     */
    public Usuario obtenerUsuarioPorCuenta(String numCuenta) {
        String sql = "SELECT * FROM usuarios WHERE numero_cuenta = ?";
        
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setString(1, numCuenta);
            ResultSet rs = ps.executeQuery();
            
            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setNumeroCuenta(rs.getString("numero_cuenta"));
                user.setPinHash(rs.getString("pin_hash"));
                user.setPinSalt(rs.getString("pin_salt"));
                user.setBloqueado(rs.getBoolean("bloqueado"));
                user.setCorreo(rs.getString("correo"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error en DAO: " + e.getMessage());
        }
        return null; 
    }
    
    /**
     * Actualiza el estado de bloqueo de un usuario.
     *
     * @param idUsuario identificador único del usuario.
     * @param bloqueado valor booleano que indica si el usuario debe estar bloqueado.
     */
    public void actualizarEstadoBloqueo(int idUsuario, boolean bloqueado) {
        String sql = "UPDATE usuarios SET bloqueado = ? WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, bloqueado ? 1 : 0);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error al actualizar estado de bloqueo: " + e.getMessage());
        }
    }
    
    /**
     * Incrementa en uno el contador de intentos fallidos de inicio de sesión.
     *
     * @param idUsuario identificador único del usuario.
     */
    public void incrementarIntentos(int idUsuario) {
        String sql = "UPDATE usuarios SET intentos_fallidos = intentos_fallidos + 1 WHERE id = ?";
        try (Connection con = ConexionBD.conectar();   // usa el mismo método que en los otros
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al incrementar intentos: " + e.getMessage());
        }
    }
    
    /**
     * Resetea el contador de intentos fallidos de inicio de sesión a cero.
     *
     * @param idUsuario identificador único del usuario.
     */
    public void resetearIntentos(int idUsuario) {
        String sql = "UPDATE usuarios SET intentos_fallidos = 0 WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ps.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error al resetear intentos: " + e.getMessage());
        }
    }

    /**
     * Obtiene la cantidad de intentos fallidos de inicio de sesión de un usuario.
     *
     * @param idUsuario identificador único del usuario.
     * @return número de intentos fallidos registrados.
     */
    public int obtenerIntentos(int idUsuario) {
        String sql = "SELECT intentos_fallidos FROM usuarios WHERE id = ?";
        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("intentos_fallidos");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener intentos: " + e.getMessage());
        }
        return 0;
    }
    
    /**
     * Obtiene un usuario a partir de su identificador único.
     *
     * @param idUsuario identificador único del usuario.
     * @return un objeto {@link Usuario} con los datos encontrados,
     *         o {@code null} si no existe el usuario.
     */
    public Usuario obtenerUsuarioPorId(int idUsuario) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario user = new Usuario();
                user.setId(rs.getInt("id"));
                user.setNombre(rs.getString("nombre"));
                user.setNumeroCuenta(rs.getString("numero_cuenta"));
                user.setPinHash(rs.getString("pin_hash"));
                user.setPinSalt(rs.getString("pin_salt"));
                user.setBloqueado(rs.getBoolean("bloqueado"));
                user.setCorreo(rs.getString("correo"));
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error en DAO: " + e.getMessage());
        }
        return null;
    }

}
