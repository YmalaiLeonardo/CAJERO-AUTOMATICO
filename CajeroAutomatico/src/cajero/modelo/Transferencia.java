
package cajero.modelo;

import cajero.bd.CuentaDAO;

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
    public boolean ejecutar(Cuenta cuenta) {
        
        double monto = getMonto();

        if (monto < 100) {
            return false; // monto inválido
        }

        if (monto > cuentaOrigen.getSaldo()) {
            return false; // saldo insuficiente
        }

        // Retirar de origen
        boolean retiroExitoso = cuentaOrigen.retirar(monto);

        if (retiroExitoso) {
            // Depositar en destino
            cuentaDestino.depositar(monto);

            CuentaDAO cuentaDAO = new CuentaDAO();
            boolean actualizadoOrigen = cuentaDAO.procesarRetiro(cuentaOrigen.getId(), monto);
            boolean actualizadoDestino = cuentaDAO.procesarDeposito(cuentaDestino.getId(), monto);

            return actualizadoOrigen && actualizadoDestino;
        }

        return false;
    }
    
    // Getter de la cuenta destino
    public Cuenta getCuentaDestino() { return cuentaDestino; }
    public void setCuentaDestino(Cuenta cuentaDestino) { 
        this.cuentaDestino = cuentaDestino; 
    }
}
