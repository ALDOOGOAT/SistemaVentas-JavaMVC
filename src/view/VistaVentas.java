package view;

import controller.VentaController;
import controller.ClienteController;
import dao.ProductoDAO;
import model.Cliente;
import model.DetalleVenta;
import model.Producto;
import model.Venta;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Vista para la gestión de Ventas.
 * Permite registrar ventas con múltiples productos, calcula el total
 * automáticamente y muestra el historial de ventas.
 * 
 * @author Aldo
 */
public class VistaVentas extends JFrame {

    private VentaController ventaController;
    private ClienteController clienteController;
    private ProductoDAO productoDAO;

    // Componentes de selección
    private JComboBox<ItemCliente> comboCliente;
    private JComboBox<ItemProducto> comboProducto;
    private JSpinner spinnerCantidad;
    private JLabel lblTotal;

    // Tabla de detalle (carrito actual)
    private JTable tablaDetalle;
    private DefaultTableModel modeloDetalle;

    // Tabla de historial de ventas
    private JTable tablaHistorial;
    private DefaultTableModel modeloHistorial;

    // Botones
    private JButton btnAgregarProducto;
    private JButton btnQuitarProducto;
    private JButton btnRegistrarVenta;
    private JButton btnLimpiar;
    private JButton btnRegresar;

    // Lista interna de productos en el carrito
    private List<DetalleVenta> detallesActuales;
    private List<Producto> productosCache;
    private double totalActual = 0.0;

    // Paleta de colores
    private final Color PRIMARY_COLOR = new Color(0, 120, 215);
    private final Color SUCCESS_COLOR = new Color(40, 167, 69);
    private final Color DANGER_COLOR = new Color(220, 53, 69);
    private final Color WARNING_COLOR = new Color(255, 193, 7);
    private final Color BG_COLOR = new Color(245, 246, 250);
    private final Color PANEL_BG = Color.WHITE;
    private final Color TEXT_COLOR = new Color(50, 50, 50);

    public VistaVentas() {
        this.ventaController = new VentaController();
        this.clienteController = new ClienteController();
        this.productoDAO = new ProductoDAO();
        this.detallesActuales = new ArrayList<>();
        this.productosCache = new ArrayList<>();
        initComponents();
        cargarClientes();
        cargarProductos();
        cargarHistorial();
    }

    private void initComponents() {
        setTitle("Sistema de Gestión - Módulo de Ventas");
        setSize(1050, 750);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(BG_COLOR);
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        setContentPane(mainPanel);

        // --- HEADER ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(15, 20, 15, 20));
        JLabel lblTitle = new JLabel("Registro de Ventas");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.WEST);

        btnRegresar = createStyledButton("← Regresar", new Color(108, 117, 125), Color.WHITE);
        headerPanel.add(btnRegresar, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- CONTENT ---
        JPanel contentPanel = new JPanel(new BorderLayout(10, 10));
        contentPanel.setBackground(BG_COLOR);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        // === PANEL SUPERIOR: Formulario de venta ===
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(PANEL_BG);
        panelFormulario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                new EmptyBorder(15, 15, 15, 15)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.BOLD, 13);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        // Título del formulario
        JLabel formTitle = new JLabel("Nueva Venta");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        formTitle.setForeground(PRIMARY_COLOR);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 6;
        panelFormulario.add(formTitle, gbc);
        gbc.gridwidth = 1;

        // Fila 1: Cliente
        JLabel lblCliente = new JLabel("Cliente:");
        lblCliente.setFont(labelFont);
        lblCliente.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 1;
        panelFormulario.add(lblCliente, gbc);

        comboCliente = new JComboBox<>();
        comboCliente.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 5; gbc.weightx = 1.0;
        panelFormulario.add(comboCliente, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;

        // Fila 2: Producto + Cantidad + Botón agregar
        JLabel lblProducto = new JLabel("Producto:");
        lblProducto.setFont(labelFont);
        lblProducto.setForeground(TEXT_COLOR);
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulario.add(lblProducto, gbc);

        comboProducto = new JComboBox<>();
        comboProducto.setFont(fieldFont);
        gbc.gridx = 1; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1.0;
        panelFormulario.add(comboProducto, gbc);
        gbc.gridwidth = 1; gbc.weightx = 0;

        JLabel lblCantidad = new JLabel("Cantidad:");
        lblCantidad.setFont(labelFont);
        lblCantidad.setForeground(TEXT_COLOR);
        gbc.gridx = 3; gbc.gridy = 2;
        panelFormulario.add(lblCantidad, gbc);

        spinnerCantidad = new JSpinner(new SpinnerNumberModel(1, 1, 9999, 1));
        spinnerCantidad.setFont(fieldFont);
        gbc.gridx = 4; gbc.gridy = 2;
        panelFormulario.add(spinnerCantidad, gbc);

        btnAgregarProducto = createStyledButton("+ Agregar", SUCCESS_COLOR, Color.WHITE);
        gbc.gridx = 5; gbc.gridy = 2;
        panelFormulario.add(btnAgregarProducto, gbc);

        contentPanel.add(panelFormulario, BorderLayout.NORTH);

        // === PANEL CENTRAL: Tablas ===
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        splitPane.setResizeWeight(0.5);
        splitPane.setDividerLocation(200);
        splitPane.setBorder(null);

        // Tabla de detalle (carrito)
        JPanel panelCarrito = new JPanel(new BorderLayout(5, 5));
        panelCarrito.setBackground(BG_COLOR);

        JPanel carritoHeader = new JPanel(new BorderLayout());
        carritoHeader.setBackground(BG_COLOR);
        JLabel lblCarrito = new JLabel("Detalle de la Venta Actual");
        lblCarrito.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblCarrito.setForeground(TEXT_COLOR);
        carritoHeader.add(lblCarrito, BorderLayout.WEST);

        JPanel carritoActions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        carritoActions.setBackground(BG_COLOR);
        btnQuitarProducto = createStyledButton("Quitar Seleccionado", DANGER_COLOR, Color.WHITE);
        carritoActions.add(btnQuitarProducto);
        carritoHeader.add(carritoActions, BorderLayout.EAST);

        panelCarrito.add(carritoHeader, BorderLayout.NORTH);

        String[] colsDetalle = {"Producto", "Precio Unit.", "Cantidad", "Subtotal"};
        modeloDetalle = new DefaultTableModel(colsDetalle, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaDetalle = new JTable(modeloDetalle);
        styleTable(tablaDetalle);
        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        panelCarrito.add(scrollDetalle, BorderLayout.CENTER);

        // Panel total + botón registrar
        JPanel panelTotal = new JPanel(new BorderLayout(10, 0));
        panelTotal.setBackground(PANEL_BG);
        panelTotal.setBorder(new EmptyBorder(8, 15, 8, 15));

        lblTotal = new JLabel("Total: $0.00");
        lblTotal.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTotal.setForeground(PRIMARY_COLOR);
        panelTotal.add(lblTotal, BorderLayout.WEST);

        JPanel panelBotonesVenta = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBotonesVenta.setBackground(PANEL_BG);
        btnLimpiar = createStyledButton("Limpiar", new Color(108, 117, 125), Color.WHITE);
        btnRegistrarVenta = createStyledButton("Registrar Venta", SUCCESS_COLOR, Color.WHITE);
        btnRegistrarVenta.setFont(new Font("Segoe UI", Font.BOLD, 15));
        panelBotonesVenta.add(btnLimpiar);
        panelBotonesVenta.add(btnRegistrarVenta);
        panelTotal.add(panelBotonesVenta, BorderLayout.EAST);

        panelCarrito.add(panelTotal, BorderLayout.SOUTH);
        splitPane.setTopComponent(panelCarrito);

        // Tabla de historial
        JPanel panelHistorial = new JPanel(new BorderLayout(5, 5));
        panelHistorial.setBackground(BG_COLOR);
        JLabel lblHistorial = new JLabel("Historial de Ventas");
        lblHistorial.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblHistorial.setForeground(TEXT_COLOR);
        panelHistorial.add(lblHistorial, BorderLayout.NORTH);

        String[] colsHistorial = {"ID Venta", "Fecha/Hora", "Total", "ID Cliente"};
        modeloHistorial = new DefaultTableModel(colsHistorial, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        tablaHistorial = new JTable(modeloHistorial);
        styleTable(tablaHistorial);
        JScrollPane scrollHistorial = new JScrollPane(tablaHistorial);
        scrollHistorial.setBorder(new LineBorder(new Color(220, 220, 220), 1, true));
        panelHistorial.add(scrollHistorial, BorderLayout.CENTER);

        splitPane.setBottomComponent(panelHistorial);
        contentPanel.add(splitPane, BorderLayout.CENTER);

        // --- EVENT LISTENERS ---
        btnAgregarProducto.addActionListener(e -> agregarProductoAlCarrito());
        btnQuitarProducto.addActionListener(e -> quitarProductoDelCarrito());
        btnRegistrarVenta.addActionListener(e -> registrarVenta());
        btnLimpiar.addActionListener(e -> limpiarCarrito());
        btnRegresar.addActionListener(e -> {
            productoDAO.cerrarConexion();
            this.dispose();
            new MenuPrincipal().setVisible(true);
        });
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setSelectionBackground(new Color(204, 232, 255));
        table.setSelectionForeground(Color.BLACK);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(230, 230, 230));

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(PANEL_BG);
        header.setForeground(TEXT_COLOR);
        header.setPreferredSize(new Dimension(100, 35));
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
    }

    private JButton createStyledButton(String text, Color bg, Color fg) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(8, 14, 8, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // =====================================================================
    // CARGA DE DATOS
    // =====================================================================

    private void cargarClientes() {
        comboCliente.removeAllItems();
        List<Cliente> clientes = clienteController.listar();
        for (Cliente c : clientes) {
            comboCliente.addItem(new ItemCliente(c.getIdCliente(),
                    c.getNombreCompleto() + " (" + c.getEmail() + ")"));
        }
    }

    private void cargarProductos() {
        comboProducto.removeAllItems();
        productosCache = productoDAO.obtenerTodosProductos();
        for (Producto p : productosCache) {
            comboProducto.addItem(new ItemProducto(p.getIdProducto(),
                    p.getNombre() + " - $" + String.format("%.2f", p.getPrecioBase())
                    + " (Stock: " + p.getStock() + ")", p.getPrecioBase(), p.getStock()));
        }
    }

    private void cargarHistorial() {
        modeloHistorial.setRowCount(0);
        List<Venta> ventas = ventaController.listar();
        for (Venta v : ventas) {
            modeloHistorial.addRow(new Object[]{
                v.getIdVenta(),
                v.getFechaHora(),
                String.format("$%.2f", v.getTotalVenta()),
                v.getIdCliente()
            });
        }
    }

    // =====================================================================
    // LÓGICA DE CARRITO
    // =====================================================================

    private void agregarProductoAlCarrito() {
        ItemProducto itemProducto = (ItemProducto) comboProducto.getSelectedItem();
        if (itemProducto == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int cantidad = (Integer) spinnerCantidad.getValue();

        if (cantidad > itemProducto.getStock()) {
            JOptionPane.showMessageDialog(this,
                    "Stock insuficiente. Disponible: " + itemProducto.getStock(),
                    "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Verificar si ya existe en el carrito
        for (int i = 0; i < detallesActuales.size(); i++) {
            if (detallesActuales.get(i).getIdProducto() == itemProducto.getId()) {
                int nuevaCantidad = detallesActuales.get(i).getCantidad() + cantidad;
                if (nuevaCantidad > itemProducto.getStock()) {
                    JOptionPane.showMessageDialog(this,
                            "No hay suficiente stock. Ya tienes " 
                            + detallesActuales.get(i).getCantidad() 
                            + " en el carrito. Disponible: " + itemProducto.getStock(),
                            "Stock Insuficiente", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                detallesActuales.get(i).setCantidad(nuevaCantidad);
                detallesActuales.get(i).setPrecioVenta(itemProducto.getPrecio());
                actualizarTablaDetalle();
                return;
            }
        }

        // Agregar nuevo detalle
        DetalleVenta detalle = new DetalleVenta();
        detalle.setIdProducto(itemProducto.getId());
        detalle.setCantidad(cantidad);
        detalle.setPrecioVenta(itemProducto.getPrecio());
        detallesActuales.add(detalle);

        actualizarTablaDetalle();
        spinnerCantidad.setValue(1);
    }

    private void quitarProductoDelCarrito() {
        int filaSeleccionada = tablaDetalle.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto del carrito para quitar.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }
        detallesActuales.remove(filaSeleccionada);
        actualizarTablaDetalle();
    }

    private void actualizarTablaDetalle() {
        modeloDetalle.setRowCount(0);
        totalActual = 0.0;

        for (DetalleVenta det : detallesActuales) {
            String nombreProducto = buscarNombreProducto(det.getIdProducto());
            double subtotal = det.getCantidad() * det.getPrecioVenta();
            totalActual += subtotal;

            modeloDetalle.addRow(new Object[]{
                nombreProducto,
                String.format("$%.2f", det.getPrecioVenta()),
                det.getCantidad(),
                String.format("$%.2f", subtotal)
            });
        }

        lblTotal.setText("Total: $" + String.format("%.2f", totalActual));
    }

    private String buscarNombreProducto(int idProducto) {
        for (Producto p : productosCache) {
            if (p.getIdProducto() == idProducto) {
                return p.getNombre();
            }
        }
        return "(ID " + idProducto + ")";
    }

    // =====================================================================
    // REGISTRAR VENTA
    // =====================================================================

    private void registrarVenta() {
        // Validaciones
        ItemCliente itemCliente = (ItemCliente) comboCliente.getSelectedItem();
        if (itemCliente == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un cliente.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (detallesActuales.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Agregue al menos un producto al carrito.",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Registrar la venta por $" + String.format("%.2f", totalActual) + "?",
                "Confirmar Venta", JOptionPane.YES_NO_OPTION);

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        // Crear objeto Venta
        Venta venta = new Venta();
        venta.setTotalVenta(totalActual);
        venta.setIdCliente(itemCliente.getId());

        // Registrar con transacción atómica
        boolean exito = ventaController.registrarVenta(venta, detallesActuales);

        if (exito) {
            JOptionPane.showMessageDialog(this,
                    "¡Venta registrada exitosamente!\nTotal: $" + String.format("%.2f", totalActual),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCarrito();
            cargarHistorial();
            cargarProductos(); // Recargar para ver stock actualizado
        } else {
            JOptionPane.showMessageDialog(this,
                    "Error al registrar la venta.\nVerifique el stock de los productos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCarrito() {
        detallesActuales.clear();
        modeloDetalle.setRowCount(0);
        totalActual = 0.0;
        lblTotal.setText("Total: $0.00");
        spinnerCantidad.setValue(1);
    }

    // =====================================================================
    // CLASES INTERNAS PARA COMBOS
    // =====================================================================

    /**
     * Wrapper para el ComboBox de Clientes.
     */
    private static class ItemCliente {
        private final int id;
        private final String display;

        public ItemCliente(int id, String display) {
            this.id = id;
            this.display = display;
        }

        public int getId() { return id; }

        @Override
        public String toString() { return display; }
    }

    /**
     * Wrapper para el ComboBox de Productos con precio y stock.
     */
    private static class ItemProducto {
        private final int id;
        private final String display;
        private final double precio;
        private final int stock;

        public ItemProducto(int id, String display, double precio, int stock) {
            this.id = id;
            this.display = display;
            this.precio = precio;
            this.stock = stock;
        }

        public int getId() { return id; }
        public double getPrecio() { return precio; }
        public int getStock() { return stock; }

        @Override
        public String toString() { return display; }
    }

    /**
     * Permite ejecutar SOLO este módulo para pruebas individuales.
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Failed to set LookAndFeel");
        }
        SwingUtilities.invokeLater(() -> new VistaVentas().setVisible(true));
    }
}
