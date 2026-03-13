/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.servicio;

/**
 *
 * @author ymala
 */
import cajero.modelo.Usuario;
import cajero.util.HashUtil;

//import cajeroautomatico.modelo.Cuenta;


// Clase que maneja toda la lógica de autenticación
public class AuthService {
    
    // Máximo de intentos permitidos antes de bloquear la cuenta
    private static final int MAX_INTENTOS = 3;
    
    // Contador de intentos fallidos del usuario actual
    private int intentosFallidos;
    
    // Constructor
    public AuthService() {
        this.intentosFallidos = 0;
    }
    
    // Verifica si el PIN ingresado es correcto
    // Retorna true si es correcto, false si no
    public boolean validarPin(String pinIngresado, Usuario usuario) {
        
        // Verificar si la cuenta está bloqueada
        if (usuario.isBloqueado()) {
            return false;
        }
        
        // Verificar el PIN usando HashUtil
        boolean pinCorrecto = HashUtil.verificarPin(
            pinIngresado, 
            usuario.getPinHash(), 
            usuario.getPinSalt()
        );
        
        if (pinCorrecto) {
            // PIN correcto, reiniciar intentos fallidos
            intentosFallidos = 0;
            return true;
        } else {
            // PIN incorrecto, incrementar intentos fallidos
            intentosFallidos++;
            
            // Verificar si se alcanzó el máximo de intentos
            if (intentosFallidos >= MAX_INTENTOS) {
                usuario.setBloqueado(true);
            }
            return false;
        }
    }
    
    // Retorna los intentos fallidos restantes
    public int getIntentosRestantes() {
        return MAX_INTENTOS - intentosFallidos;
    }
    
    // Verifica si la cuenta está bloqueada
    public boolean estaBloqueado(Usuario usuario) {
        return usuario.isBloqueado();
    }
}