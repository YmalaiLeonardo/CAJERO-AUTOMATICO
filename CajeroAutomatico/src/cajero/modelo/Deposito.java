
package cajero.modelo;

import cajero.bd.CuentaDAO;
import cajero.bd.OperacionDAO;

/**
 *
 * @author ymala
 */
// Deposito hereda de Operacion (herencia)
// Esto significa que Deposito ES una Operacion
public class Deposito extends Operacion {
    private String mensajeError;
    
    // Constructor que recibe los datos del depósito
    public Deposito(int idCuenta, double monto) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "deposito" automáticamente
        super(idCuenta, monto, "Deposito");
    }
    
    public String getMensajeError() {
        return mensajeError;
    }
    
    
    // Implementación del método abstracto ejecutar()
    // Aquí definimos cómo funciona específicamente un depósito
    @Override
    public boolean ejecutar(Cuenta cuenta) {
        double monto = getMonto();
        
        // Validar que el monto sea mayor a 100
        if (monto < 100) {
            mensajeError = "El monto debe ser mayor o igual a 100 pesos.";
            return false;
        }       
        
        
         // Si pasa las validaciones, realizar el depósito
        cuenta.depositar(monto);
        
        
        // Aquí también deberías actualizar la BD con CuentaDAO
        CuentaDAO cuentaDAO = new CuentaDAO();
        OperacionDAO operacionDAO = new OperacionDAO();
        
        boolean actualizado = cuentaDAO.procesarDeposito(cuenta.getId(), monto);
        boolean registrada = operacionDAO.registrarDeposito(cuenta.getId(), getMonto());
        
        if (!actualizado || !registrada) {
            mensajeError = "Error al actualizar la base de datos.";
            return false;
        }
        
        return true;
    }
}