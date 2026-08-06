/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ProductoDAO;
import model.Producto;
import model.Proveedor;
import view.AdministrarProductos;
import view.ItemProveedor;
import view.ProductoAltaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Jared
 *
 * Controlador de la GESTIÓN de productos: llena la tabla, permite seleccionar
 * una fila para editarla, guarda cambios y elimina. También abre la ventana de
 * alta. Sigue el mismo estilo del módulo de Tiendita.
 */
public class AdministrarProductoController implements ActionListener, ListSelectionListener {

    AdministrarProductos vista;
    ProductoDAO productoDAO;
    DefaultTableModel modeloTabla;

    public AdministrarProductoController(AdministrarProductos vista) {
        this.vista = vista;
        this.productoDAO = new ProductoDAO(); // Esto abre la conexión

        // 1. Obtenemos el modelo de la tabla
        this.modeloTabla = (DefaultTableModel) this.vista.tablaProductos.getModel();

        // 2. Llenamos el ComboBox de proveedores y la tabla
        cargarProveedores();
        cargarTabla();

        // 3. Hacemos la tabla "clickeable"
        this.vista.tablaProductos.getSelectionModel().addListSelectionListener(this);

        // 4. Suscribimos los botones
        this.vista.botonGuardar.addActionListener(this);   // Botón "Guardar cambios"
        this.vista.botonEliminar.addActionListener(this);  // Botón "Eliminar"
        this.vista.botonNuevo.addActionListener(this);     // Botón "Nuevo..."
        this.vista.botonRegresar.addActionListener(this);  // Botón "Regresar"
    }

    /**
     * Se llama CADA VEZ que haces clic en una fila de la tabla.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            int filaSeleccionada = vista.tablaProductos.getSelectedRow();

            if (filaSeleccionada == -1) {
                limpiarFormulario();
                return;
            }

            // Obtenemos el ID de la columna 0
            int idProducto = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);

            // Usamos el DAO para buscar el producto completo
            productoDAO.read(idProducto);
            Producto producto = productoDAO.getObjProducto();

            // Llenamos el formulario de abajo con los datos
            if (producto != null) {
                vista.campoID.setText(String.valueOf(producto.getIdProducto()));
                vista.campoNombre.setText(producto.getNombre());
                vista.campoPrecioBase.setText(String.valueOf(producto.getPrecioBase()));
                vista.campoStock.setText(String.valueOf(producto.getStock()));
                seleccionarProveedor(vista.comboProveedor, producto.getIdProveedor());
            }
        }
    }

    /**
     * Escucha los clics en los botones "Guardar", "Eliminar", "Nuevo" y "Regresar".
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == vista.botonGuardar) {
            actualizarProducto();
        }

        if (e.getSource() == vista.botonEliminar) {
            eliminarProducto();
        }

        if (e.getSource() == vista.botonNuevo) {
            abrirAlta();
        }

        if (e.getSource() == vista.botonRegresar) {
            productoDAO.cerrarConexion();
            vista.dispose();
            new view.MenuPrincipal().setVisible(true);
        }
    }

    private void actualizarProducto() {
        if (vista.campoID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ItemProveedor proveedorSel = (ItemProveedor) vista.comboProveedor.getSelectedItem();
        if (proveedorSel == null) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un proveedor.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Producto productoActualizado = new Producto();

        try {
            // Leemos los datos de los campos de texto
            productoActualizado.setIdProducto(Integer.parseInt(vista.campoID.getText()));
            productoActualizado.setNombre(vista.campoNombre.getText());
            productoActualizado.setPrecioBase(Double.parseDouble(vista.campoPrecioBase.getText()));
            productoActualizado.setStock(Integer.parseInt(vista.campoStock.getText()));
            productoActualizado.setIdProveedor(proveedorSel.getId());

            // Usar el DAO para actualizar
            productoDAO.setObjProducto(productoActualizado); // Preparamos el objeto
            productoDAO.update(); // Ejecutamos la actualización

            JOptionPane.showMessageDialog(vista, "Producto actualizado con éxito.");

            // Recargar la tabla para ver los cambios
            cargarTabla();
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Precio y Stock deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        if (vista.campoID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Seleccione un producto de la tabla primero.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(vista,
                "¿Seguro que deseas eliminar el producto " + vista.campoNombre.getText() + "?",
                "Confirmar Eliminación",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            Producto productoParaBorrar = new Producto();
            productoParaBorrar.setIdProducto(Integer.parseInt(vista.campoID.getText()));

            productoDAO.setObjProducto(productoParaBorrar); // Preparamos el objeto
            productoDAO.delete(); // Ejecutamos la eliminación

            JOptionPane.showMessageDialog(vista, "Producto eliminado.");

            cargarTabla();
            limpiarFormulario();
        }
    }

    /**
     * Abre la ventana de alta de producto y recarga la tabla cuando se cierra.
     */
    private void abrirAlta() {
        ProductoAltaView vistaAlta = new ProductoAltaView();
        new ProductoAltaController(vistaAlta);
        // Cuando la ventana de alta se cierre, refrescamos la tabla
        vistaAlta.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                cargarTabla();
            }
        });
        vistaAlta.setVisible(true);
    }

    /**
     * Llena la tabla con datos de la BD.
     */
    public void cargarTabla() {
        modeloTabla.setRowCount(0);

        List<Producto> lista = productoDAO.obtenerTodosProductos();

        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getIdProducto(),
                p.getNombre(),
                p.getPrecioBase(),
                p.getStock(),
                nombreProveedor(p.getIdProveedor())
            });
        }
    }

    /**
     * Llena el ComboBox con los proveedores disponibles.
     */
    private void cargarProveedores() {
        vista.comboProveedor.removeAllItems();
        List<Proveedor> proveedores = productoDAO.obtenerTodosProveedores();
        for (Proveedor prov : proveedores) {
            vista.comboProveedor.addItem(new ItemProveedor(prov.getIdProveedor(), prov.getNombreEmpresa()));
        }
    }

    /**
     * Devuelve el nombre de empresa de un proveedor buscándolo en el ComboBox
     * (ya cargado), para mostrarlo en la tabla en lugar del id.
     */
    private String nombreProveedor(int idProveedor) {
        for (int i = 0; i < vista.comboProveedor.getItemCount(); i++) {
            ItemProveedor item = vista.comboProveedor.getItemAt(i);
            if (item.getId() == idProveedor) {
                return item.getNombre();
            }
        }
        return "(id " + idProveedor + ")";
    }

    /**
     * Selecciona en el ComboBox el proveedor cuyo id coincida.
     */
    private void seleccionarProveedor(JComboBox<ItemProveedor> combo, int idProveedor) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            if (combo.getItemAt(i).getId() == idProveedor) {
                combo.setSelectedIndex(i);
                return;
            }
        }
    }

    /**
     * Limpia los campos del formulario.
     */
    public void limpiarFormulario() {
        vista.campoID.setText("");
        vista.campoNombre.setText("");
        vista.campoPrecioBase.setText("");
        vista.campoStock.setText("");
        if (vista.comboProveedor.getItemCount() > 0) {
            vista.comboProveedor.setSelectedIndex(0);
        }

        vista.tablaProductos.clearSelection();
    }
}
