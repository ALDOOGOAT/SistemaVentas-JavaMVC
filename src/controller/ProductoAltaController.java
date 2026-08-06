/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.ProductoDAO;
import model.Producto;
import model.Proveedor;
import view.ItemProveedor;
import view.ProductoAltaView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Jared
 *
 * Este controlador maneja la lógica para crear un nuevo producto.
 */
public class ProductoAltaController implements ActionListener {

    ProductoAltaView vista;
    ProductoDAO productoDAO;

    /**
     * Constructor.
     * @param vista La vista de 'Alta de Producto' que este controlador manejará.
     */
    public ProductoAltaController(ProductoAltaView vista) {
        this.vista = vista;
        this.productoDAO = new ProductoDAO(); // Abre conexión a productos

        // --- 1. Llenar el ComboBox de proveedores ---
        cargarProveedores();

        // --- 2. Suscribirse a los botones de la vista ---
        this.vista.botonCrear.addActionListener(this);    // Botón "Crear"
        this.vista.botonCancelar.addActionListener(this);  // Botón "Cancelar"
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // --- LÓGICA DEL BOTÓN "CREAR" ---
        if (e.getSource() == vista.botonCrear) {
            crearNuevoProducto();
        }

        // --- LÓGICA DEL BOTÓN "CANCELAR" ---
        if (e.getSource() == vista.botonCancelar) {
            productoDAO.cerrarConexion(); // Cierra la conexión de productos
            vista.dispose();              // Cierra la ventana
        }
    }

    /**
     * Lee todos los campos del formulario, los valida y crea un nuevo producto.
     */
    private void crearNuevoProducto() {

        // --- 1. Validar campos obligatorios ---
        if (vista.campoNombre.getText().isEmpty()
                || vista.campoPrecioBase.getText().isEmpty()
                || vista.campoStock.getText().isEmpty()) {

            JOptionPane.showMessageDialog(vista, "Nombre, Precio base y Stock son obligatorios.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ItemProveedor proveedorSel = (ItemProveedor) vista.comboProveedor.getSelectedItem();
        if (proveedorSel == null) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un proveedor.", "Error de Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // --- 2. Leer datos del formulario ---
        Producto nuevoProducto = new Producto();

        try {
            // (El DAO 'create' NO espera un ID de producto, porque es SERIAL)
            nuevoProducto.setNombre(vista.campoNombre.getText());
            nuevoProducto.setPrecioBase(Double.parseDouble(vista.campoPrecioBase.getText()));
            nuevoProducto.setStock(Integer.parseInt(vista.campoStock.getText()));
            nuevoProducto.setIdProveedor(proveedorSel.getId());

            // --- 3. Enviar al DAO para crear ---
            productoDAO.setObjProducto(nuevoProducto);
            productoDAO.create();

            JOptionPane.showMessageDialog(vista, "Producto '" + nuevoProducto.getNombre() + "' creado con éxito.");

            // --- 4. Limpiar formulario ---
            limpiarFormulario();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(vista, "Precio base y Stock deben ser números válidos.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
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
     * Limpia todos los campos de texto del formulario.
     */
    private void limpiarFormulario() {
        vista.campoNombre.setText("");
        vista.campoPrecioBase.setText("");
        vista.campoStock.setText("");
        if (vista.comboProveedor.getItemCount() > 0) {
            vista.comboProveedor.setSelectedIndex(0);
        }

        vista.campoNombre.requestFocus(); // Pone el cursor en el campo Nombre
    }
}
