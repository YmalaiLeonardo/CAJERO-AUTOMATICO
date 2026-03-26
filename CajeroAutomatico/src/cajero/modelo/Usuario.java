
package cajero.modelo;

/**
 *
 * @author ymala
 */
public class Usuario {
    // Atributos privados que representan los datos del usuario
    private int id;
    private String nombre;
    private String correo;
    private String numeroCuenta;
    private String pinHash;
    private String pinSalt;
    private boolean bloqueado;

    
    // Constructor vacío
    public Usuario() {}
    
    // Constructor con todos los atributos
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

    private Cuenta cuenta; // relación con la cuenta del usuario
    public Cuenta getCuenta() {return cuenta;}
    public void setCuenta(Cuenta cuenta) { this.cuenta = cuenta;}
        
    
}
