
package cajeroautomatico;

import cajero.vista.PantallaLogin;

/**
 * Clase principal del sistema de Cajero Automático.
 * 
 * <p>Esta clase representa el punto de entrada de la aplicación. 
 * Desde aquí se inicializa la interfaz gráfica y se decide cuál 
 * pantalla se mostrará primero al usuario.</p>
 * 
 * <p>Actualmente, el sistema abre la pantalla de inicio de sesión 
 * {@link cajero.vista.PantallaLogin}, donde el usuario debe ingresar 
 * su número de cuenta y PIN para acceder a las operaciones bancarias.</p>
 * 
 * @author Ymalai Leonardo
 * @author Luis Diaz
 * @author Manuel Alburquerque
 * @author Starlyn Escalante
 * @version 1.0.0
 */
public class main {

    /**
     * Método principal de la aplicación.
     * 
     * <p>Este método se ejecuta al iniciar el programa y es responsable 
     * de mostrar la primera ventana de la interfaz gráfica. En este caso, 
     * se instancia y se hace visible la pantalla de login.</p>
     * 
     * @param args los argumentos de la línea de comandos (no utilizados en este proyecto)
     */
    public static void main(String[] args) {
        // Aquí decides qué pantalla mostrar primero
        PantallaLogin login = new PantallaLogin();
        login.setVisible(true);
    }
    
}
