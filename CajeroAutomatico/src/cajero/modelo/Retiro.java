
package cajero.modelo;

import cajero.bd.CuentaDAO;

/**
 *
 * @author ymala
 */
// Retiro hereda de Operacion (herencia)
// Esto significa que Retiro ES una Operacion
public class Retiro extends Operacion {
    private String mensajeError;
    
    // Constructor que recibe los datos del retiro
    public Retiro(int idCuenta, double monto) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "retiro" automáticamente
        super(idCuenta, monto, "Retiro");
    }
    
    public String getMensajeError() {
        return mensajeError;
    }
    
    // Implementación del método abstracto ejecutar()
    // Aquí definimos cómo funciona específicamente un retiro
    @Override
    public boolean ejecutar(Cuenta cuenta) {
        
        double monto = getMonto();
        
        // Validar que el monto sea mayor a 100
        if (monto < 100) {
            mensajeError = "El monto debe ser mayor o igual a 100 pesos.";
            return false;
        }
        
        // Validar que el monto no supere el saldo actual
        if (monto > cuenta.getSaldo()) {
            mensajeError = "El monto no puede ser mayor al saldo actual.";
            return false;
        }
        
        // Si pasa las validaciones, realizar el depósito
        cuenta.retirar(monto);
        
        
        // Aquí también deberías actualizar la BD con CuentaDAO
        CuentaDAO cuentaDAO = new CuentaDAO();
        boolean actualizado = cuentaDAO.procesarRetiro(cuenta.getId(), monto);
        
        if (!actualizado) {
            mensajeError = "Error al procesar el depósito.";
        }
        
        return actualizado;
    }
}