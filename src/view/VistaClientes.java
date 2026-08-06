package view;

import controller.ClienteController;
import model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;

/**
 * Graphical User Interface for the Clientes module.
 * Hidden ID tracking implemented. Form positioned above the table.
 */
public class VistaClientes extends JFrame {

    private ClienteController controller;

    // The hidden state variable to track the selected row for Updates/Deletes
    private int clienteSeleccionadoId = -1;

    private JTextField txtNombre, txtEmail, txtTelefono;
    private JButton btnGuardar, btnActualizar, btnEliminar, btnLimpiar;
    private JTable tablaClientes;
    private DefaultTableModel tableModel;

    // Modern Color Palette
    private final Color PRIMARY_COLOR = new Color(0, 120, 215);
    private final Color DANGER_COLOR = new Color(220, 53, 69);
    private final Color BG_COLOR = new Color(245, 246, 250);
    private final Color PANEL_BG = Color.WHITE;
    private final Color TEXT_COLOR = new Color(50, 50, 50);

    public VistaClientes() {
        this.controller = new ClienteController();
        initComponents();
        cargarDatos();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión - Módulo Clientes");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- HEADER PANEL ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel("Directorio de Clientes");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);
        
        JButton btnRegresar = createStyledButton("← Regresar", new Color(108, 117, 125), Color.WHITE);
        btnRegresar.addActionListener(e -> {
            this.dispose();
            new MenuPrincipal().setVisible(true);
        });
        headerPanel.add(btnRegresar, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- CONTENT WRAPPER ---
        JPanel contentPanel = new JPanel(new BorderLayout(15, 15));
        contentPanel.setBackground(BG_COLOR);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // --- FORM PANEL (Top Side) ---
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(PANEL_BG);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(20, 20, 20, 20)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel formTitle = new JLabel("Datos del Registro");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitle.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4; gbc.insets = new Insets(0, 10, 15, 10);
        panelFormulario.add(formTitle, gbc);

        gbc.gridwidth = 1; gbc.insets = new Insets(5, 10, 5, 10);

        // Row 1: Nombre & Email (Side by Side)
        JLabel lblNombre = new JLabel("Nombre Completo:"); lblNombre.setFont(labelFont); lblNombre.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 1; panelFormulario.add(lblNombre, gbc);
        txtNombre = createStyledTextField(fieldFont); 
        gbc.gridx = 1; panelFormulario.add(txtNombre, gbc);

        JLabel lblEmail = new JLabel("Correo Electrónico:"); lblEmail.setFont(labelFont); lblEmail.setForeground(TEXT_COLOR);
        gbc.gridx = 2; gbc.gridy = 1; panelFormulario.add(lblEmail, gbc);
        txtEmail = createStyledTextField(fieldFont); 
        gbc.gridx = 3; panelFormulario.add(txtEmail, gbc);

        // Row 2: Teléfono & Botones
        JLabel lblPhone = new JLabel("Teléfono (10 dígitos):"); lblPhone.setFont(labelFont); lblPhone.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 2; panelFormulario.add(lblPhone, gbc);
        txtTelefono = createStyledTextField(fieldFont); 
        gbc.gridx = 1; panelFormulario.add(txtTelefono, gbc);

        // --- BUTTONS PANEL ---
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotones.setBackground(PANEL_BG);
        
        btnGuardar = createStyledButton("Guardar", PRIMARY_COLOR, Color.WHITE);
        btnActualizar = createStyledButton("Actualizar", new Color(40, 167, 69), Color.WHITE);
        btnEliminar = createStyledButton("Eliminar", DANGER_COLOR, Color.WHITE);
        btnLimpiar = createStyledButton("Limpiar", new Color(108, 117, 125), Color.WHITE);

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 2; gbc.gridy = 2; gbc.gridwidth = 2; 
        panelFormulario.add(panelBotones, gbc);

        JPanel topWrapper = new JPanel(new BorderLayout());
        topWrapper.setBackground(BG_COLOR);
        topWrapper.add(panelFormulario, BorderLayout.NORTH);
        contentPanel.add(topWrapper, BorderLayout.NORTH);

        // --- TABLE PANEL (Bottom Side) ---
        String[] columnNames = {"ID", "Nombre", "Email", "Teléfono"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaClientes = new JTable(tableModel);
        
        tablaClientes.setRowHeight(35);
        tablaClientes.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tablaClientes.setSelectionBackground(new Color(204, 232, 255));
        tablaClientes.setSelectionForeground(Color.BLACK);
        tablaClientes.setShowVerticalLines(false);
        tablaClientes.setGridColor(new Color(230, 230, 230));
        
        JTableHeader header = tablaClientes.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(PANEL_BG);
        header.setForeground(TEXT_COLOR);
        header.setPreferredSize(new Dimension(100, 40));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        tablaClientes.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablaClientes.getSelectedRow() != -1) {
                llenarFormularioDesdeTabla();
            }
        });

        JScrollPane scrollPane = new JScrollPane(tablaClientes);
        scrollPane.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        scrollPane.getViewport().setBackground(PANEL_BG);
        
        JPanel tableWrapper = new JPanel(new BorderLayout());
        tableWrapper.setBackground(BG_COLOR);
        tableWrapper.add(scrollPane, BorderLayout.CENTER);
        
        contentPanel.add(tableWrapper, BorderLayout.CENTER);

        // --- EVENT LISTENERS ---
        btnGuardar.addActionListener(e -> guardarCliente());
        btnActualizar.addActionListener(e -> actualizarCliente());
        btnEliminar.addActionListener(e -> eliminarCliente());
        btnLimpiar.addActionListener(e -> limpiarFormulario());
    }

    private JTextField createStyledTextField(Font font) {
        JTextField field = new JTextField();
        field.setFont(font);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 15, 10, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void cargarDatos() {
        tableModel.setRowCount(0);
        List<Cliente> clientes = controller.listar();
        for (Cliente c : clientes) {
            Object[] row = {c.getIdCliente(), c.getNombreCompleto(), c.getEmail(), c.getTelefono()};
            tableModel.addRow(row);
        }
    }

    private void guardarCliente() {
        Cliente c = new Cliente();
        c.setNombreCompleto(txtNombre.getText());
        c.setEmail(txtEmail.getText());
        c.setTelefono(txtTelefono.getText());

        if (controller.insertar(c)) {
            JOptionPane.showMessageDialog(this, "Cliente guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error de validación o base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarCliente() {
        if (clienteSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Cliente c = new Cliente();
        c.setIdCliente(clienteSeleccionadoId);
        c.setNombreCompleto(txtNombre.getText());
        c.setEmail(txtEmail.getText());
        c.setTelefono(txtTelefono.getText());

        if (controller.actualizar(c)) {
            JOptionPane.showMessageDialog(this, "Cliente actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarFormulario();
            cargarDatos();
        } else {
            JOptionPane.showMessageDialog(this, "Error de validación o base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCliente() {
        if (clienteSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un cliente de la tabla.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar este registro de forma permanente?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (controller.eliminar(clienteSeleccionadoId)) {
                limpiarFormulario();
                cargarDatos();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void llenarFormularioDesdeTabla() {
        int selectedRow = tablaClientes.getSelectedRow();
        clienteSeleccionadoId = Integer.parseInt(tablaClientes.getValueAt(selectedRow, 0).toString());
        
        txtNombre.setText(tablaClientes.getValueAt(selectedRow, 1).toString());
        txtEmail.setText(tablaClientes.getValueAt(selectedRow, 2).toString());
        txtTelefono.setText(tablaClientes.getValueAt(selectedRow, 3).toString());
    }

    private void limpiarFormulario() {
        clienteSeleccionadoId = -1;
        
        txtNombre.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        tablaClientes.clearSelection();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set LookAndFeel");
        }
        SwingUtilities.invokeLater(() -> new VistaClientes().setVisible(true));
    }
}