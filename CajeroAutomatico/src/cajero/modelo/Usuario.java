
package cajero.modelo;

/**
 * Clase Usuario
 * -------------
 * Representa a un usuario dentro del sistema de cajero automático.
 * Contiene atributos relacionados con la identidad, credenciales y estado de bloqueo,
 * además de la relación con la cuenta bancaria asociada.
 *
 * <p>Funciones principales:</p>
 * - Almacenar información personal del usuario (id, nombre, correo).
 * - Gestionar credenciales seguras mediante hash y salt del PIN.
 * - Controlar el estado de bloqueo del usuario.
 * - Relacionar al usuario con su cuenta bancaria correspondiente.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
public class Usuario {
    
    // Atributos privados que representan los datos del usuario
    private int id;                // Identificador único del usuario
    private String nombre;         // Nombre completo del usuario
    private String correo;         // Correo electrónico del usuario
    private String numeroCuenta;   // Número de cuenta asociado
    private String pinHash;        // Hash del PIN para autenticación segura
    private String pinSalt;        // Salt utilizado en el hash del PIN
    private boolean bloqueado;     // Estado de bloqueo del usuario

    private Cuenta cuenta;         // Relación con la cuenta del usuario
    
    /**
     * Constructor vacío.
     * Permite crear un objeto Usuario sin inicializar atributos.
     */
    public Usuario() {}
    
     /**
     * Constructor con todos los atributos del usuario.
     *
     * @param id identificador único del usuario.
     * @param nombre nombre completo del usuario.
     * @param correo correo electrónico del usuario.
     * @param numeroCuenta número de cuenta asociado.
     * @param pinHash hash del PIN para autenticación.
     * @param pinSalt salt utilizado en el hash del PIN.
     * @param bloqueado estado de bloqueo del usuario.
     */
    public Usuario(int id, String nombre, String correo, String numeroCuenta, 
                   String pinHash, String pinSalt, boolean bloqueado) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.numeroCuenta = numeroCuenta;
        this.pinHash = pinHash;
        this.pinSalt = pinSalt;
        this.bloqueado = bloqueado;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getNumeroCuenta() { return numeroCuenta; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    
    public String getPinHash() { return pinHash; }
    public void setPinHash(String pinHash) { this.pinHash = pinHash; }
    
    public String getPinSalt() { return pinSalt; }
    public void setPinSalt(String pinSalt) { this.pinSalt = pinSalt; }
    
    public boolean isBloqueado() { return bloqueado; }
    public void setBloqueado(boolean bloqueado) { this.bloqueado = bloqueado; }

    public Cuenta getCuenta() {return cuenta;}
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta;}
            
}
