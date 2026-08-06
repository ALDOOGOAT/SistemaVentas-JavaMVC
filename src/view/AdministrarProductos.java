/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 * Vista para la GESTIÓN de Productos (listar, actualizar y eliminar).
 * Réplica del estilo de "AdministrarProductos" de Tiendita, adaptada al
 * esquema de este proyecto (nombre, precio_base, stock, id_proveedor).
 *
 * Los componentes se declaran públicos a propósito para que el controlador
 * (AdministrarProductoController) los manipule directamente, tal como se
 * programó el módulo anterior.
 *
 * @author Jared
 */
public class AdministrarProductos extends javax.swing.JFrame {

    // --- Componentes accesibles por el controlador ---
    public JTable tablaProductos;
    public JTextField campoID;
    public JTextField campoNombre;
    public JTextField campoPrecioBase;
    public JTextField campoStock;
    public JComboBox<ItemProveedor> comboProveedor;
    public JButton botonGuardar;
    public JButton botonEliminar;
    public JButton botonNuevo;
    public JButton botonRegresar;

    public AdministrarProductos() {
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestión de Productos");
        setSize(760, 560);
        setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- Tabla ----------
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[]{"ID", "Nombre", "Precio Base", "Stock", "Proveedor"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla es solo de lectura
            }
        };
        tablaProductos = new JTable(modelo);
        tablaProductos.getSelectionModel().setSelectionMode(
                javax.swing.ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder("Productos registrados"));
        add(scroll, BorderLayout.CENTER);

        // ---------- Formulario ----------
        JPanel formulario = new JPanel(new GridBagLayout());
        formulario.setBorder(BorderFactory.createTitledBorder("Datos del producto"));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.anchor = GridBagConstraints.WEST;
        c.fill = GridBagConstraints.HORIZONTAL;

        campoID = new JTextField(6);
        campoID.setEditable(false); // El ID lo asigna la BD (SERIAL)
        campoNombre = new JTextField(18);
        campoPrecioBase = new JTextField(10);
        campoStock = new JTextField(8);
        comboProveedor = new JComboBox<>();

        // Fila 0: ID y Nombre
        c.gridx = 0; c.gridy = 0; formulario.add(new JLabel("ID:"), c);
        c.gridx = 1; c.gridy = 0; formulario.add(campoID, c);
        c.gridx = 2; c.gridy = 0; formulario.add(new JLabel("Nombre:"), c);
        c.gridx = 3; c.gridy = 0; formulario.add(campoNombre, c);

        // Fila 1: Precio y Stock
        c.gridx = 0; c.gridy = 1; formulario.add(new JLabel("Precio base:"), c);
        c.gridx = 1; c.gridy = 1; formulario.add(campoPrecioBase, c);
        c.gridx = 2; c.gridy = 1; formulario.add(new JLabel("Stock:"), c);
        c.gridx = 3; c.gridy = 1; formulario.add(campoStock, c);

        // Fila 2: Proveedor
        c.gridx = 0; c.gridy = 2; formulario.add(new JLabel("Proveedor:"), c);
        c.gridx = 1; c.gridy = 2; c.gridwidth = 3;
        formulario.add(comboProveedor, c);
        c.gridwidth = 1;

        // ---------- Botones ----------
        JPanel botones = new JPanel();
        botonNuevo = new JButton("Nuevo...");
        botonGuardar = new JButton("Guardar cambios");
        botonEliminar = new JButton("Eliminar");
        botonRegresar = new JButton("Regresar");
        botones.add(botonNuevo);
        botones.add(botonGuardar);
        botones.add(botonEliminar);
        botones.add(botonRegresar);

        JPanel sur = new JPanel(new BorderLayout());
        sur.add(formulario, BorderLayout.CENTER);
        sur.add(botones, BorderLayout.SOUTH);
        add(sur, BorderLayout.SOUTH);
    }

    /**
     * Permite ejecutar SOLO este módulo (clic derecho → Run File) para probar
     * la gestión de productos sin depender del menú principal.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            AdministrarProductos vista = new AdministrarProductos();
            new controller.AdministrarProductoController(vista);
            vista.setVisible(true);
        });
    }
}
