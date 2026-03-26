
package cajero.modelo;

/**
 *
 * @author ymala
 */
// Deposito hereda de Operacion (herencia)
// Esto significa que Deposito ES una Operacion
public class Deposito extends Operacion {
    
    // Constructor que recibe los datos del depósito
    public Deposito(int idCuenta, double monto) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "deposito" automáticamente
        super(idCuenta, monto, "deposito");
    }
    
    // Implementación del método abstracto ejecutar()
    // Aquí definimos cómo funciona específicamente un depósito
    @Override
    public boolean ejecutar(Cuenta cuenta) {
        
        // Verificar que el monto sea mayor a 0
        if (getMonto() <= 0) {
            return false; // Monto inválido
        }
        
        // Depositar el dinero en la cuenta
        // El depósito siempre es exitoso si el monto es válido
        cuenta.depositar(getMonto());
        return true; // Depósito exitoso
    }
}