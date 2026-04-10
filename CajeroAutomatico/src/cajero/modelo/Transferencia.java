
package cajero.modelo;

import cajero.bd.CuentaDAO;
import cajero.bd.OperacionDAO;

/**
 * Clase Transferencia
 * -------------------
 * Representa una operación de transferencia dentro del sistema de cajero automático.
 * Hereda de la clase {@link Operacion}, lo que significa que una transferencia es un tipo específico de operación.
 *
 * <p>Funciones principales:</p>
 * - Validar que el monto de la transferencia sea mayor o igual a 100.
 * - Validar que el monto no supere el saldo disponible en la cuenta origen.
 * - Retirar el monto de la cuenta origen y depositarlo en la cuenta destino.
 * - Actualizar los saldos de ambas cuentas en la base de datos mediante {@link CuentaDAO}.
 * - Registrar la operación en la tabla de transacciones mediante {@link OperacionDAO}.
 * - Proporcionar mensajes de error en caso de validaciones fallidas o problemas con la base de datos.
 *
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
public class Transferencia extends Operacion {
    
    // Atributo extra que solo tiene la transferencia
    // La cuenta destino a donde se enviará el dinero
    private Cuenta cuentaDestino;
    private String mensajeError;
    
    /**
     * Constructor que inicializa una transferencia con los datos de la cuenta origen,
     * el monto y la cuenta destino.
     *
     * @param idCuenta identificador único de la cuenta origen.
     * @param monto cantidad de dinero a transferir.
     * @param cuentaDestino objeto {@link Cuenta} que representa la cuenta destino.
     */
    public Transferencia(int idCuenta, double monto, Cuenta cuentaDestino) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "transferencia" automáticamente
        super(idCuenta, monto, "Transferencia");
        this.cuentaDestino = cuentaDestino;
    }     
    
    /**
     * Obtiene el mensaje de error asociado a la operación de transferencia.
     *
     * @return mensaje de error si ocurrió algún problema, o {@code null} si no hubo errores.
     */
    public String getMensajeError() {
        return mensajeError;
    }
    
    /**
     * Ejecuta la operación de transferencia entre dos cuentas.
     *
     * <p>Validaciones:</p>
     * - El monto debe ser mayor o igual a 100.
     * - El monto no puede ser mayor al saldo disponible en la cuenta origen.
     * - Se retira el monto de la cuenta origen y se deposita en la cuenta destino.
     * - Se actualizan los saldos en la base de datos.
     * - Se registra la operación en la tabla de transacciones.
     *
     * @param cuentaOrigen objeto {@link Cuenta} que representa la cuenta origen.
     * @return {@code true} si la transferencia fue exitosa,
     *         {@code false} si ocurrió un error o el monto no es válido.
     */
    @Override
    public boolean ejecutar(Cuenta cuentaOrigen) {
        
        double monto = getMonto();

        if (monto < 100) {
            mensajeError = "El monto debe ser mayor o igual a 100 pesos.";
            return false;
        }

        if (monto > cuentaOrigen.getSaldo()) {
            mensajeError = "El monto no puede ser mayor al saldo disponible.";
            return false;
        }

        boolean retiroExitoso = cuentaOrigen.retirar(monto);

        if (retiroExitoso) {
            cuentaDestino.depositar(monto);

            CuentaDAO cuentaDAO = new CuentaDAO();
            OperacionDAO operacionDAO = new OperacionDAO();
            boolean actualizadoOrigen = cuentaDAO.actualizarSaldo(cuentaOrigen.getId(), cuentaOrigen.getSaldo());
            boolean actualizadoDestino = cuentaDAO.actualizarSaldo(cuentaDestino.getId(), cuentaDestino.getSaldo());
           
            boolean registrada = operacionDAO.registrarTransferencia(
                cuentaOrigen.getId(),
                monto,
                cuentaDestino.getId()
            );

            if (!actualizadoOrigen || !actualizadoDestino || !registrada) {
                mensajeError = "Error al actualizar la base de datos.";                
                return false;
            }

            return true;
        }

        mensajeError = "Saldo insuficiente.";
        return false;
    }
    
    /** @return la cuenta destino de la transferencia. */
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    
    /** @param cuentaDestino nueva cuenta destino para la transferencia. */
    public void setCuentaDestino(Cuenta cuentaDestino) { 
        this.cuentaDestino = cuentaDestino; 
    }    
   
}
    
 
