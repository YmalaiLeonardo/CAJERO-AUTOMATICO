/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
        
        ps.setBoolean(1, bloqueado);
        ps.setInt(2, idUsuario);
        ps.executeUpdate();
        
    } catch (SQLException e) {
        System.err.println("Error al actualizar bloqueo: " + e.getMessage());
    }
}
}
