
package cajero.modelo;

/**
 * Clase Cuenta
 * ------------
 * Representa una cuenta bancaria dentro del sistema de cajero automático.
 * Contiene atributos básicos como identificador, usuario asociado, saldo y número de cuenta,
 * además de métodos para realizar operaciones de depósito y retiro.
 *
 * <p>Funciones principales:</p>
 * - Almacenar información de la cuenta (id, idUsuario, saldo, número de cuenta).
 * - Permitir depósitos que incrementan el saldo.
 * - Permitir retiros con validación de saldo suficiente.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
public class Cuenta {
    // Atributos privados que representan los datos de la cuenta
    private int id;              // Identificador único de la cuenta
    private int idUsuario;       // Identificador del usuario propietario
    private double saldo;        // Saldo actual de la cuenta
    private String numeroCuenta; // Número de cuenta
    
    /**
     * Constructor con todos los atributos de la cuenta.
     *
     * @param id identificador único de la cuenta.
     * @param idUsuario identificador del usuario propietario.
     * @param saldo saldo inicial de la cuenta.
     * @param numeroCuenta número de cuenta asignado.
     */
    public Cuenta(int id, int idUsuario, double saldo, String numeroCuenta) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
    }
    
    /** @return identificador único de la cuenta. */
    public int getId() { return id; }
    /** @param id nuevo identificador único de la cuenta. */
    public void setId(int id) { this.id = id; }
    
    /** @return identificador del usuario propietario. */
    public int getIdUsuario() { return idUsuario; }
    /** @param idUsuario nuevo identificador del usuario propietario. */
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    /** @return saldo actual de la cuenta. */
    public double getSaldo() { return saldo; }
    /** @param saldo nuevo saldo de la cuenta. */
    public void setSaldo(double saldo) { this.saldo = saldo; }
    
    /** @return número de cuenta asignado. */
    public String getNumeroCuenta() { return numeroCuenta; }
    
    /**
     * Deposita dinero en la cuenta, incrementando el saldo.
     *
     * @param monto cantidad a depositar.
     */
    public void depositar(double monto) {
        this.saldo += monto;
    }
    
    /**
     * Retira dinero de la cuenta si hay saldo suficiente.
     *
     * @param monto cantidad a retirar.
     * @return {@code true} si el retiro fue exitoso,
     *         {@code false} si el saldo es insuficiente.
     */
    public boolean retirar(double monto) {
        if (monto > this.saldo) {
            return false; // Saldo insuficiente
        }
        this.saldo -= monto;
        return true; // Retiro exitoso
    }
}
