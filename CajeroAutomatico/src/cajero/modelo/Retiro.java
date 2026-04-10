
package cajero.modelo;

import cajero.bd.CuentaDAO;
import cajero.bd.OperacionDAO;

/**
 * Clase Retiro
 * ------------
 * Representa una operación de retiro dentro del sistema de cajero automático.
 * Hereda de la clase {@link Operacion}, lo que significa que un retiro es un tipo específico de operación.
 *
 * <p>Funciones principales:</p>
 * - Validar que el monto del retiro sea mayor o igual a 100.
 * - Validar que el monto no supere el saldo disponible en la cuenta.
 * - Actualizar el saldo de la cuenta en memoria mediante el método {@link cajero.modelo.Cuenta#retirar(double)}.
 * - Actualizar el saldo en la base de datos utilizando {@link CuentaDAO}.
 * - Registrar la operación en la base de datos mediante {@link OperacionDAO}.
 * - Proporcionar mensajes de error en caso de validaciones fallidas o problemas con la base de datos.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
public class Retiro extends Operacion {
    private String mensajeError;
    
    /**
     * Constructor que inicializa un retiro con los datos de la cuenta y el monto.
     *
     * @param idCuenta identificador único de la cuenta donde se realizará el retiro.
     * @param monto cantidad de dinero a retirar.
     */
    public Retiro(int idCuenta, double monto) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "retiro" automáticamente
        super(idCuenta, monto, "Retiro");
    }
    
    /**
     * Obtiene el mensaje de error asociado a la operación de retiro.
     *
     * @return mensaje de error si ocurrió algún problema, o {@code null} si no hubo errores.
     */
    public String getMensajeError() {
        return mensajeError;
    }
    
    /**
     * Ejecuta la operación de retiro sobre una cuenta.
     *
     * <p>Validaciones:</p>
     * - El monto debe ser mayor o igual a 100.
     * - El monto no puede ser mayor al saldo actual.
     * - Se actualiza el saldo de la cuenta en memoria.
     * - Se actualiza el saldo en la base de datos.
     * - Se registra la operación en la tabla de transacciones.
     *
     * @param cuenta objeto {@link Cuenta} sobre el cual se aplicará el retiro.
     * @return {@code true} si el retiro fue exitoso,
     *         {@code false} si ocurrió un error o el monto no es válido.
     */
    @Override
    public boolean ejecutar(Cuenta cuenta) {
        
        double monto = getMonto();
                
        if (monto < 100) {
            mensajeError = "El monto debe ser mayor o igual a 100 pesos.";
            return false;
        }
                
        if (monto > cuenta.getSaldo()) {
            mensajeError = "El monto no puede ser mayor al saldo actual.";
            return false;
        }
        
        boolean retiroExitoso = cuenta.retirar(getMonto());

        if (retiroExitoso) {
            CuentaDAO cuentaDAO = new CuentaDAO();
            OperacionDAO operacionDAO = new OperacionDAO();

            boolean actualizado = cuentaDAO.actualizarSaldo(cuenta.getId(), cuenta.getSaldo());
            boolean registrada = operacionDAO.registrarRetiro(cuenta.getId(), getMonto());

            if (!actualizado || !registrada) {
                mensajeError = "Error al actualizar la base de datos.";
                return false;
            }

            return true;
        }

        mensajeError = "No se pudo realizar el retiro.";
        return false;
    }
}