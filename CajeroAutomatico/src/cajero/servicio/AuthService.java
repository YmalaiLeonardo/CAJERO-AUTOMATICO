
package cajero.servicio;

/**
 *
 * @author ymala
 */


import cajero.modelo.Usuario;
import cajero.util.HashUtil;
import cajero.bd.UsuarioDAO;

// Clase que maneja toda la lógica de autenticación
public class AuthService {
    
    // Máximo de intentos permitidos antes de bloquear la cuenta
    private static final int MAX_INTENTOS = 3;
    // Contador de intentos fallidos del usuario actual
    private int intentosFallidos;
    
    private UsuarioDAO usuarioDAO; // <--- Referencia al DAO
    
    // Constructor
    public AuthService() {
        this.intentosFallidos = 0;
        this.usuarioDAO = new UsuarioDAO(); // <--- Inicializamos
    }
    
    // Verifica si el PIN ingresado es correcto
    // Retorna true si es correcto, false si no
    public boolean validarPin(String pinIngresado, Usuario usuario) {
        
        // 1. Verificar si ya está bloqueado en el objeto 
        if (usuario.isBloqueado()) {
            return false;
        }
        
        // 2. Verificar el PIN usando tu HashUtil
        boolean pinCorrecto = HashUtil.verificarPin(
            pinIngresado, 
            usuario.getPinHash(), 
            usuario.getPinSalt()
        );
        
        if (pinCorrecto) {
            intentosFallidos = 0;
            return true;
        } else {
            intentosFallidos++;
            
            if (intentosFallidos >= MAX_INTENTOS) {
                // CAMBIO CLAVE: Actualizamos el objeto Y la base de datos
                usuario.setBloqueado(true);
                usuarioDAO.actualizarEstadoBloqueo(usuario.getId(), true); 
                System.out.println("Usuario bloqueado en BD.");
            }
            return false;
        }
    }
    
    // Método extra para el Login inicial
    public Usuario buscarUsuarioParaLogin(String numeroCuenta) {
        return usuarioDAO.obtenerUsuarioPorCuenta(numeroCuenta);
    }
    
    public int getIntentosRestantes() {
        return Math.max(0, MAX_INTENTOS - intentosFallidos);
    }
    
    public Usuario login(String numeroCuenta, String pinIngresado) {
        // 1. Buscamos al usuario en la BD a través del DAO
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCuenta(numeroCuenta);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return null;
        }

        // 2. Usamos el método que ya tenías para validar el PIN
        boolean esValido = validarPin(pinIngresado, usuario);

        if (esValido) {
            return usuario; // Login exitoso
        } else {
            return null; // PIN incorrecto o Bloqueado
        }
    }
}