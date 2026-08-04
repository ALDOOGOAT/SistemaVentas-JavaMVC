package view;

import javax.swing.JFrame;

/**
 * Menú Principal / Dashboard del Sistema de Gestión de Ventas.
 * Permite navegar a los diferentes módulos del sistema.
 * @author Ilde
 */
public class MenuPrincipal extends JFrame {

    public MenuPrincipal() {
        setTitle("Sistema de Gestión de Ventas - Menú Principal");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // TODO: Implementar interfaz gráfica
        // - Botón: Gestión de Proveedores  -> abre VistaProveedores
        // - Botón: Gestión de Clientes     -> abre VistaClientes
        // - Botón: Gestión de Productos    -> abre VistaProductos
        // - Botón: Gestión de Ventas       -> abre VistaVentas
        // - Botón: Salir
    }
}
