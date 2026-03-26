
package cajero.modelo;

/**
 *
 * @author ymala
 */
public class Cuenta {
    //Atributos privados que representan los datos de la cuenta
    private int id;              // usa int, no Integer
    private int idUsuario;
    private double saldo;
    private String numeroCuenta;
    
    // Constructor con todos los atributos
    public Cuenta(int id, int idUsuario, double saldo, String numeroCuenta) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.saldo = saldo;
        this.numeroCuenta = numeroCuenta;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    
    public String getNumeroCuenta() { return numeroCuenta; }
    
    // Método para depositar dinero a la cuenta
    public void depositar(double monto) {
        this.saldo += monto;
    }
    
    // Método para retirar dinero de la cuenta
    // Retorna true si hay saldo suficiente, false si no
    public boolean retirar(double monto) {
        if (monto > this.saldo) {
            return false; // Saldo insuficiente
        }
        this.saldo -= monto;
        return true; // Retiro exitoso
    }
}
