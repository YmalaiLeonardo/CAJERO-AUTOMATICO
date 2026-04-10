
package cajero.modelo;

import java.util.Date;

/**
 * Clase Operacion
 * ---------------
 * Representa una operación bancaria genérica dentro del sistema de cajero automático.
 * Es una clase abstracta que define los atributos y comportamientos comunes
 * a todas las operaciones (depósitos, retiros, transferencias).
 *
 * <p>Características principales:</p>
 * - Almacena información básica de la operación: id, cuenta asociada, monto, fecha y tipo.
 * - Define el método abstracto {@code ejecutar()} que cada subclase debe implementar
 *   según la lógica específica de la operación.
 * - No puede ser instanciada directamente; solo puede ser utilizada a través de sus clases hijas.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
public abstract class Operacion {
    // Atributos comunes a todas las operaciones
    private int id;              // Identificador único de la operación
    private int idCuenta;        // Identificador de la cuenta asociada
    private double monto;        // Monto de la operación
    private Date fecha;          // Fecha en que se realiza la operación
    private String tipo;         // Tipo de operación (Depósito, Retiro, Transferencia)
    
    /**
     * Constructor que inicializa los atributos comunes de una operación.
     *
     * @param idCuenta identificador de la cuenta asociada.
     * @param monto monto de la operación.
     * @param tipo tipo de operación (ejemplo: "Deposito", "Retiro").
     */
    public Operacion(int idCuenta, double monto, String tipo) {
        this.idCuenta = idCuenta;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = new Date(); // Fecha actual automáticamente
    }
    
    /**
     * Método abstracto que cada operación debe implementar.
     * Define la lógica específica de ejecución según el tipo de operación.
     *
     * @param cuenta objeto {@link Cuenta} sobre el cual se aplicará la operación.
     * @return {@code true} si la operación fue exitosa,
     *         {@code false} si ocurrió algún error o validación fallida.
     */
    public abstract boolean ejecutar(Cuenta cuenta);
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdCuenta() { return idCuenta; }
    public void setIdCuenta(int idCuenta) { this.idCuenta = idCuenta; }
    
    public double getMonto() { return monto; }
    public void setMonto(double monto) { this.monto = monto; }
    
    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
    
    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }
}
