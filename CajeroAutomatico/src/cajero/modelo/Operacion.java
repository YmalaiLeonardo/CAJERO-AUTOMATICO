/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.modelo;

import java.util.Date;

/**
 *
 * @author ymala
 */

// Clase abstracta que representa cualquier operación bancaria
// Al ser abstracta no se puede instanciar directamente
// Solo se puede usar a través de sus clases hijas
public abstract class Operacion {
    // Atributos comunes a todas las operaciones
    private int id;
    private int idCuenta;
    private double monto;
    private Date fecha;
    private String tipo;
    
    // Constructor con los atributos comunes
    public Operacion(int idCuenta, double monto, String tipo) {
        this.idCuenta = idCuenta;
        this.monto = monto;
        this.tipo = tipo;
        this.fecha = new Date(); // Fecha actual automáticamente
    }
    
    // Método abstracto que cada operación implementará a su manera
    // Esto es polimorfismo: cada clase hija tiene su propio ejecutar()
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
