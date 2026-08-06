/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Jared
 */
import config.Conexion;
import model.Producto;
import model.Proveedor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO para la tabla Productos, siguiendo el patrón de conexión persistente.
 * ¡ADVERTENCIA! Este código es vulnerable a Inyección SQL.
 */
public class ProductoDAO {

    private Connection conexion;
    private Statement stmt;
    private Producto objProducto; // Guarda el producto a crear/actualizar o el resultado de read

    // Constructor: Abre la conexión
    public ProductoDAO() {
        try {
            // Usamos una conexión NUEVA e independiente (no la singleton compartida),
            // así este DAO mantiene su propio Statement sin que otros módulos lo cierren.
            conexion = Conexion.getInstancia().nuevaConexion();

            // Debemos inicializar el 'stmt' o siempre será null.
            if (conexion != null) {
                stmt = conexion.createStatement();
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Fallo al conectar a la base de datos", ex);
        }
    }

    // --- Setters y Getters para el objeto ---
    public void setObjProducto(Producto objProducto) {
        this.objProducto = objProducto;
    }

    public Producto getObjProducto() {
        return objProducto;
    }

    // --- CREATE (Insertar) ---
    public void create() {
        if (stmt == null || objProducto == null) {
            System.err.println("Error: Statement o Producto no inicializados para CREATE.");
            return;
        }

        // ¡CORRECCIÓN! No incluimos id_producto porque es SERIAL
        String sql = "INSERT INTO Productos (nombre, precio_base, stock, id_proveedor) VALUES ('"
                + objProducto.getNombre() + "', "
                + objProducto.getPrecioBase() + ", "
                + objProducto.getStock() + ", "
                + objProducto.getIdProveedor() + ")";

        System.out.println("Ejecutando CREATE: " + sql);
        try {
            stmt.execute(sql);
            System.out.println("Producto posiblemente agregado.");
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error en CREATE: " + sql, ex);
        }
    }

    // --- READ (Buscar por id_producto) ---
    /**
     * Busca un producto por su ID y guarda el resultado en objProducto.
     * @param idProducto El ID del producto a buscar.
     */
    public void read(int idProducto) {
        if (stmt == null) {
            System.err.println("Error: Statement no inicializado para READ.");
            this.objProducto = null;
            return;
        }
        // Construcción insegura de SQL
        String sql = "SELECT * FROM Productos WHERE id_producto = " + idProducto;

        System.out.println("Ejecutando READ: " + sql);
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            if (rs.next()) {
                // Si se encuentra, se llena el objeto interno
                this.objProducto = new Producto();
                this.objProducto.setIdProducto(rs.getInt("id_producto"));
                this.objProducto.setNombre(rs.getString("nombre"));
                this.objProducto.setPrecioBase(rs.getDouble("precio_base"));
                this.objProducto.setStock(rs.getInt("stock"));
                this.objProducto.setIdProveedor(rs.getInt("id_proveedor"));
                System.out.println("Producto encontrado.");
            } else {
                // Si no se encuentra, el objeto interno se pone a null
                this.objProducto = null;
                System.out.println("Producto no encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error en READ: " + sql, ex);
            this.objProducto = null; // Asegura null en caso de error
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorado */ }
        }
    }

    // --- UPDATE (Actualizar) ---
    public void update() {
        if (stmt == null || objProducto == null || objProducto.getIdProducto() == 0) {
            System.err.println("Error: Statement, Producto o id_producto no válidos para UPDATE.");
            return;
        }
        // Construcción insegura de SQL
        String sql = "UPDATE Productos SET "
                + "nombre = '" + objProducto.getNombre() + "', "
                + "precio_base = " + objProducto.getPrecioBase() + ", "
                + "stock = " + objProducto.getStock() + ", "
                + "id_proveedor = " + objProducto.getIdProveedor() + " "
                + "WHERE id_producto = " + objProducto.getIdProducto();

        System.out.println("Ejecutando UPDATE: " + sql);
        try {
            int filasAfectadas = stmt.executeUpdate(sql);
            if (filasAfectadas > 0) {
                System.out.println("Producto posiblemente actualizado.");
            } else {
                System.out.println("No se encontró el producto para actualizar.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error en UPDATE: " + sql, ex);
        }
    }

    // --- DELETE (Eliminar) ---
    public void delete() {
        if (stmt == null || objProducto == null || objProducto.getIdProducto() == 0) {
            System.err.println("Error: Statement, Producto o id_producto no válidos para DELETE.");
            return;
        }
        // Construcción insegura de SQL
        String sql = "DELETE FROM Productos WHERE id_producto = " + objProducto.getIdProducto();

        System.out.println("Ejecutando DELETE: " + sql);
        try {
            int filasAfectadas = stmt.executeUpdate(sql);
            if (filasAfectadas > 0) {
                System.out.println("Producto posiblemente eliminado.");
            } else {
                System.out.println("No se encontró el producto para eliminar.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error en DELETE: " + sql, ex);
        }
    }

    // --- READ ALL (Obtener todos los productos) ---
    /**
     * Obtiene una lista de todos los productos.
     * Este método NO usa la variable objProducto, sino que devuelve una lista.
     * @return Una Lista de objetos Producto.
     */
    public List<Producto> obtenerTodosProductos() {
        List<Producto> productos = new ArrayList<>();
        if (stmt == null) {
            System.err.println("Error: Statement no inicializado para READ ALL.");
            return productos; // Devuelve lista vacía
        }
        String sql = "SELECT * FROM Productos ORDER BY nombre";

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Producto producto = new Producto();
                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setNombre(rs.getString("nombre"));
                producto.setPrecioBase(rs.getDouble("precio_base"));
                producto.setStock(rs.getInt("stock"));
                producto.setIdProveedor(rs.getInt("id_proveedor"));
                productos.add(producto);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error en READ ALL: " + sql, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorado */ }
        }
        return productos;
    }

    // --- READ ALL PROVEEDORES (para el ComboBox dinámico) ---
    /**
     * Obtiene todos los proveedores para poblar el ComboBox de la vista.
     * Se apoya en el mismo Statement de este DAO para no cerrar la conexión.
     * @return Una Lista de objetos Proveedor.
     */
    public List<Proveedor> obtenerTodosProveedores() {
        List<Proveedor> proveedores = new ArrayList<>();
        if (stmt == null) {
            System.err.println("Error: Statement no inicializado para READ ALL Proveedores.");
            return proveedores;
        }
        String sql = "SELECT id_proveedor, nombre_empresa FROM Proveedores ORDER BY nombre_empresa";

        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Proveedor prov = new Proveedor();
                prov.setIdProveedor(rs.getInt("id_proveedor"));
                prov.setNombreEmpresa(rs.getString("nombre_empresa"));
                proveedores.add(prov);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error en READ ALL Proveedores: " + sql, ex);
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* Ignorado */ }
        }
        return proveedores;
    }

    // Método para cerrar conexión manualmente
    public void cerrarConexion() {
        try {
            if (stmt != null) stmt.close();
            if (conexion != null) conexion.close();
            System.out.println("Conexión cerrada.");
        } catch (SQLException ex) {
            Logger.getLogger(ProductoDAO.class.getName()).log(Level.SEVERE, "Error al cerrar conexión", ex);
        }
    }
}
