/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Vista para el ALTA de un nuevo Producto.
 * Réplica del estilo de "ProductoAltaView" de Tiendita, adaptada al esquema
 * de este proyecto (nombre, precio_base, stock, id_proveedor).
 *
 * @author Jared
 */
public class ProductoAltaView extends javax.swing.JFrame {

    // --- Componentes accesibles por el controlador ---
    public JTextField campoNombre;
    public JTextField campoPrecioBase;
    public JTextField campoStock;
    public JComboBox<ItemProveedor> comboProveedor;
    public JButton botonCrear;
    public JButton botonCancelar;

    public ProductoAltaView() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Alta de Producto");
        setSize(420, 300);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Nuevo producto"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        campoNombre = new JTextField(16);
        campoPrecioBase = new JTextField(16);
        campoStock = new JTextField(16);
        comboProveedor = new JComboBox<>();

        c.gridx = 0; c.gridy = 0; panel.add(new JLabel("Nombre:"), c);
        c.gridx = 1; c.gridy = 0; panel.add(campoNombre, c);

        c.gridx = 0; c.gridy = 1; panel.add(new JLabel("Precio base:"), c);
        c.gridx = 1; c.gridy = 1; panel.add(campoPrecioBase, c);

        c.gridx = 0; c.gridy = 2; panel.add(new JLabel("Stock:"), c);
        c.gridx = 1; c.gridy = 2; panel.add(campoStock, c);

        c.gridx = 0; c.gridy = 3; panel.add(new JLabel("Proveedor:"), c);
        c.gridx = 1; c.gridy = 3; panel.add(comboProveedor, c);

        JPanel botones = new JPanel();
        botonCrear = new JButton("Crear");
        botonCancelar = new JButton("Cancelar");
        botones.add(botonCrear);
        botones.add(botonCancelar);

        c.gridx = 0; c.gridy = 4; c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        panel.add(botones, c);

        add(panel);
    }

    /**
     * Permite ejecutar SOLO el alta (clic derecho → Run File) para probarla
     * sin pasar por la ventana de gestión. Normalmente se abre desde
     * AdministrarProductos con el botón "Nuevo...".
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            ProductoAltaView vista = new ProductoAltaView();
            new controller.ProductoAltaController(vista);
            vista.setVisible(true);
        });
    }
}
