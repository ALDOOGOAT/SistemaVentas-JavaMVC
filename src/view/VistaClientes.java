package view;

import javax.swing.JFrame;

/**
 * Vista para la gestión de Clientes (CRUD).
 * @author Daniel
 */
public class VistaClientes extends JFrame {

    public VistaClientes() {
        setTitle("Gestión de Clientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // TODO: Implementar interfaz gráfica
        // - JTable para listar clientes
        // - Campos: nombre_completo, email, telefono
        // - Validaciones de Email y Teléfono
        // - Botones: Agregar, Editar, Eliminar, Buscar
    }
}
