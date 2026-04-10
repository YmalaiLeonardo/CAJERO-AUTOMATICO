
package cajero.servicio;

/**
 * Clase AuthService
 * -----------------
 * Maneja toda la lógica de autenticación dentro del sistema de cajero automático.
 * Se encarga de validar el PIN ingresado por el usuario, controlar intentos fallidos,
 * bloquear cuentas cuando se excede el máximo de intentos y cargar la cuenta asociada
 * al usuario en caso de autenticación exitosa.
 *
 * <p>Funciones principales:</p>
 * - Validar el PIN ingresado utilizando {@link HashUtil}.
 * - Reiniciar o incrementar intentos fallidos según el resultado de la validación.
 * - Bloquear la cuenta del usuario si se excede el máximo de intentos permitidos.
 * - Buscar usuarios en la base de datos mediante {@link UsuarioDAO}.
 * - Cargar la cuenta asociada al usuario mediante {@link CuentaDAO}.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */

import cajero.bd.CuentaDAO;
import cajero.modelo.Usuario;
import cajero.util.HashUtil;
import cajero.bd.UsuarioDAO;
import cajero.modelo.Cuenta;

// Clase que maneja toda la lógica de autenticación
public class AuthService {
    
    // Máximo de intentos permitidos antes de bloquear la cuenta
    private static final int MAX_INTENTOS = 3;
    // Contador de intentos fallidos del usuario actual
    private int intentosFallidos;
    
    private UsuarioDAO usuarioDAO; // <--- Referencia al DAO
    
    /**
     * Constructor que inicializa el servicio de autenticación.
     * Reinicia el contador de intentos fallidos y prepara el acceso a la base de datos.
     */
    public AuthService() {
        this.intentosFallidos = 0;
        this.usuarioDAO = new UsuarioDAO(); // <--- Inicializamos
    }
    
    /**
     * Verifica si el PIN ingresado por el usuario es correcto.
     * 
     * @param pinIngresado PIN ingresado por el usuario.
     * @param usuario objeto {@link Usuario} que contiene los datos del usuario.
     * @return {@code true} si el PIN es correcto y la cuenta no está bloqueada,
     *         {@code false} si el PIN es incorrecto o la cuenta fue bloqueada.
     */
    public boolean validarPin(String pinIngresado, Usuario usuario) {        
        
        boolean pinCorrecto = HashUtil.verificarPin(
            pinIngresado, usuario.getPinHash(), usuario.getPinSalt()
        );

        if (pinCorrecto) {
            // Si el PIN es correcto, reinicia intentos y desbloquea
            usuarioDAO.resetearIntentos(usuario.getId());
            usuarioDAO.actualizarEstadoBloqueo(usuario.getId(), false); 
            usuario.setBloqueado(false); 
            return true;
        } else {
            // Si el PIN es incorrecto, incrementa intentos
            usuarioDAO.incrementarIntentos(usuario.getId()); 
            int intentos = usuarioDAO.obtenerIntentos(usuario.getId()); 

            if (intentos >= MAX_INTENTOS) {
                usuarioDAO.actualizarEstadoBloqueo(usuario.getId(), true); // bloquea
                usuario.setBloqueado(true);
            }
            return false;                       
        }       
    }
    
     /**
     * Busca un usuario en la base de datos a partir de su número de cuenta.
     *
     * @param numeroCuenta número de cuenta del usuario.
     * @return objeto {@link Usuario} si se encuentra en la base de datos,
     *         o {@code null} si no existe.
     */
    public Usuario buscarUsuarioParaLogin(String numeroCuenta) {
        return usuarioDAO.obtenerUsuarioPorCuenta(numeroCuenta);
    }
    
    /**
     * Obtiene la cantidad de intentos restantes antes de que la cuenta sea bloqueada.
     *
     * @return número de intentos restantes.
     */
    public int getIntentosRestantes() {
        return Math.max(0, MAX_INTENTOS - intentosFallidos);
    }
    
    /**
     * Realiza el proceso completo de login:
     * - Busca al usuario en la base de datos.
     * - Valida el PIN ingresado.
     * - Si es correcto, carga la cuenta asociada al usuario.
     *
     * @param numeroCuenta número de cuenta del usuario.
     * @param pinIngresado PIN ingresado por el usuario.
     * @return objeto {@link Usuario} con su cuenta cargada si el login fue exitoso,
     *         o {@code null} si falló la autenticación.
     */
    public Usuario login(String numeroCuenta, String pinIngresado) {
        // 1. Buscamos al usuario en la BD
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCuenta(numeroCuenta);

        if (usuario == null) {
            System.out.println("Usuario no encontrado.");
            return null;
        }

        // 2. Validamos el PIN
        boolean valido = validarPin(pinIngresado, usuario);

        if (valido) {
            // 3. Cargar la cuenta asociada
            CuentaDAO cuentaDAO = new CuentaDAO();
            Cuenta cuenta = cuentaDAO.buscarCuentaPorNumero(usuario.getNumeroCuenta());
            usuario.setCuenta(cuenta);

            return usuario; // ahora el usuario tiene su cuenta
        } else {
            return null; // PIN incorrecto o bloqueado
        }
    }
    
    /**
     * Obtiene la cantidad de intentos fallidos registrados en la base de datos
     * para un usuario específico.
     *
     * @param idUsuario identificador único del usuario.
     * @return número de intentos fallidos registrados.
     */
    public int getIntentosFallidosBD(int idUsuario) {
        return usuarioDAO.obtenerIntentos(idUsuario);
    }
}