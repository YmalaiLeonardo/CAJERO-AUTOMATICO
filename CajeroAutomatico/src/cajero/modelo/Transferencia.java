
package cajero.modelo;

import cajero.bd.CuentaDAO;
import cajero.bd.OperacionDAO;

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
    private String mensajeError;
    
    // Constructor que recibe los datos de la transferencia
    public Transferencia(int idCuenta, double monto, Cuenta cuentaDestino) {
        // Llama al constructor de la clase padre (Operacion)
        // Le pasa el tipo "transferencia" automáticamente
        super(idCuenta, monto, "Transferencia");
        this.cuentaDestino = cuentaDestino;
    }     
    
   
    
    
    
    public String getMensajeError() {
        return mensajeError;
    }
    
    // Implementación del método abstracto ejecutar()
    // Aquí definimos cómo funciona específicamente una transferencia
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

            // Registrar la operación en la tabla operaciones
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
    
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(Cuenta cuentaDestino) { 
        this.cuentaDestino = cuentaDestino; 
    }
    
   
}
    
 
