package cajero.bd;

/**
 *
 * @author ymala
 */
import cajero.modelo.Usuario;
import java.sql.*;

public class UsuarioDAO {
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
        return null; // Si el usuario no existe
    }
    
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
        return null; // Si el usuario no existe
    }


}
