
package cajero.modelo;

/**
 *
 * @author ymala
 */
// Retiro hereda de Operacion (herencia)
// Esto significa que Retiro ES una Operacion
public class Retiro extends Operacion {
    
    // Constructor que recibe los datos del retiro
    public Retiro(int idCuenta, double monto) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "retiro" automáticamente
        super(idCuenta, monto, "retiro");
    }
    
    // Implementación del método abstracto ejecutar()
    // Aquí definimos cómo funciona específicamente un retiro
    @Override
    public boolean ejecutar(Cuenta cuenta) {
        
        // Verificar que el monto sea mayor a 0
        if (getMonto() <= 0) {
            return false; // Monto inválido
        }
        
        // Intentar retirar el dinero de la cuenta
        // El método retirar() de Cuenta verifica si hay saldo suficiente
        return cuenta.retirar(getMonto());
    }
}