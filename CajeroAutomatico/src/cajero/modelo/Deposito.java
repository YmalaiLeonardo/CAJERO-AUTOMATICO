
package cajero.modelo;

import cajero.bd.CuentaDAO;
import cajero.bd.OperacionDAO;

/**
 * Clase Deposito
 * --------------
 * Representa una operación de depósito dentro del sistema de cajero automático.
 * Hereda de la clase {@link Operacion}, lo que significa que un depósito es un tipo específico de operación.
 *
 * <p>Funciones principales:</p>
 * - Validar que el monto del depósito sea mayor o igual a 100.
 * - Actualizar el saldo de la cuenta en memoria mediante el método {@link cajero.modelo.Cuenta#depositar(double)}.
 * - Registrar la operación en la base de datos utilizando {@link CuentaDAO} y {@link OperacionDAO}.
 * - Proporcionar mensajes de error en caso de validaciones fallidas o problemas con la base de datos.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
public class Deposito extends Operacion {
    private String mensajeError;
    
    /**
     * Constructor que inicializa un depósito con los datos de la cuenta y el monto.
     * 
     * @param idCuenta identificador único de la cuenta donde se realizará el depósito.
     * @param monto cantidad de dinero a depositar.
     */
    public Deposito(int idCuenta, double monto) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "deposito" automáticamente
        super(idCuenta, monto, "Deposito");
    }
    
    /**
     * Obtiene el mensaje de error asociado a la operación de depósito.
     *
     * @return mensaje de error si ocurrió algún problema, o {@code null} si no hubo errores.
     */
    public String getMensajeError() {
        return mensajeError;
    }
    
    
    /**
     * Ejecuta la operación de depósito sobre una cuenta.
     * 
     * <p>Validaciones:</p>
     * - El monto debe ser mayor o igual a 100.
     * - Se actualiza el saldo de la cuenta en memoria.
     * - Se actualiza el saldo en la base de datos.
     * - Se registra la operación en la tabla de transacciones.
     *
     * @param cuenta objeto {@link Cuenta} sobre el cual se aplicará el depósito.
     * @return {@code true} si el depósito fue exitoso,
     *         {@code false} si ocurrió un error o el monto no es válido.
     */
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