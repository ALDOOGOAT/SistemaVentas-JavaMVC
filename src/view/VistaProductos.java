package view;

import javax.swing.JFrame;

/**
 * Vista para la gestión de Productos (CRUD).
 * @author Jared
 */
public class VistaProductos extends JFrame {

    public VistaProductos() {
        setTitle("Gestión de Productos");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // TODO: Implementar interfaz gráfica
        // - JTable para listar productos
        // - Campos: nombre, precio_base, stock
        // - ComboBox dinámico para seleccionar Proveedor
        // - Botones: Agregar, Editar, Eliminar, Buscar
    }
}
