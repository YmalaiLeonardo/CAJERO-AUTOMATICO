/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.modelo;

/**
 *
 * @author ymala
 */
public class Cuenta {
    //Atributos privados que representan los datos de la cuenta
    private int id;
    private int idUsuario;
    private double saldo;
    
    // Constructor vacío
    public Cuenta() {}
    
    // Constructor con todos los atributos
    public Cuenta(int id, int idUsuario, double saldo) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.saldo = saldo;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    
    public double getSaldo() { return saldo; }
    public void setSaldo(double saldo) { this.saldo = saldo; }
    
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
