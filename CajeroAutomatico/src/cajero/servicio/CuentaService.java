
package cajero.servicio;

/**
 *
 * @author ymala
 */
import cajero.modelo.Cuenta;
import cajero.modelo.Deposito;
import cajero.modelo.Retiro;
import cajero.modelo.Transferencia;

// Clase que maneja toda la lógica de las operaciones bancarias
public class CuentaService {
    
    // Realiza un retiro de la cuenta
    // Retorna true si fue exitoso, false si no
    public boolean realizarRetiro(Cuenta cuenta, double monto) {
        
        // Crear objeto Retiro con los datos de la operación
        Retiro retiro = new Retiro(cuenta.getId(), monto);
        
        // Ejecutar el retiro y retornar el resultado
        return retiro.ejecutar(cuenta);
    }
    
    // Realiza un depósito en la cuenta
    // Retorna true si fue exitoso, false si no
    public boolean realizarDeposito(Cuenta cuenta, double monto) {
        
        // Crear objeto Deposito con los datos de la operación
        Deposito deposito = new Deposito(cuenta.getId(), monto);
        
        // Ejecutar el depósito y retornar el resultado
        return deposito.ejecutar(cuenta);
    }
    
    // Realiza una transferencia entre dos cuentas
    // Retorna true si fue exitosa, false si no
    public boolean realizarTransferencia(Cuenta cuentaOrigen, 
                                         Cuenta cuentaDestino, 
                                         double monto) {
        
        // Verificar que la cuenta destino no sea la misma que la origen
        if (cuentaOrigen.getId() == cuentaDestino.getId()) {
            return false; // No se puede transferir a la misma cuenta
        }
        
        // Crear objeto Transferencia con los datos de la operación
        Transferencia transferencia = new Transferencia(
            cuentaOrigen.getId(), monto, cuentaDestino
        );
        
        // Ejecutar la transferencia y retornar el resultado
        return transferencia.ejecutar(cuentaOrigen);
    }
    
    // Retorna el saldo actual de la cuenta
    public double consultarSaldo(Cuenta cuenta) {
        return cuenta.getSaldo();
    }
    
}
