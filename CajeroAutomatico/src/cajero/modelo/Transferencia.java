/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cajero.modelo;

/**
 *
 * @author ymala
 */
// Transferencia hereda de Operacion (herencia)
// Esto significa que Transferencia ES una Operacion
public class Transferencia extends Operacion {
    
    // Atributo extra que solo tiene la transferencia
    // La cuenta destino a donde se enviará el dinero
    private Cuenta cuentaDestino;
    
    // Constructor que recibe los datos de la transferencia
    public Transferencia(int idCuenta, double monto, Cuenta cuentaDestino) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "transferencia" automáticamente
        super(idCuenta, monto, "transferencia");
        this.cuentaDestino = cuentaDestino;
    }
    
    // Implementación del método abstracto ejecutar()
    // Aquí definimos cómo funciona específicamente una transferencia
    @Override
    public boolean ejecutar(Cuenta cuenta) {
        
        // Verificar que el monto sea mayor a 0
        if (getMonto() <= 0) {
            return false; // Monto inválido
        }
        
        // Verificar que la cuenta destino no sea nula
        if (cuentaDestino == null) {
            return false; // No hay cuenta destino
        }
        
        // Intentar retirar de la cuenta origen
        // Si no hay saldo suficiente retorna false
        boolean retiroExitoso = cuenta.retirar(getMonto());
        
        if (retiroExitoso) {
            // Si el retiro fue exitoso, depositar en la cuenta destino
            cuentaDestino.depositar(getMonto());
            return true; // Transferencia exitosa
        }
        
        return false; // Saldo insuficiente
    }
    
    // Getter de la cuenta destino
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(Cuenta cuentaDestino) { 
        this.cuentaDestino = cuentaDestino; 
    }
}
