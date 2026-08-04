package dao;

import config.Conexion;
import model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos para la tabla Productos.
 * @author Jared
 */
public class ProductoDAO {

    public List<Producto> listar() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id_producto, nombre, precio_base, stock, id_proveedor FROM Productos";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Producto p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecioBase(rs.getDouble("precio_base"));
                p.setStock(rs.getInt("stock"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar productos: " + e.getMessage());
        } finally {
            cerrarRecursos(con, ps, rs);
        }
        return lista;
    }

    public Producto buscarPorId(int id) {
        Producto p = null;
        String sql = "SELECT id_producto, nombre, precio_base, stock, id_proveedor FROM Productos WHERE id_producto = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Producto();
                p.setIdProducto(rs.getInt("id_producto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecioBase(rs.getDouble("precio_base"));
                p.setStock(rs.getInt("stock"));
                p.setIdProveedor(rs.getInt("id_proveedor"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar producto: " + e.getMessage());
        } finally {
            cerrarRecursos(con, ps, rs);
        }
        return p;
    }

    public boolean insertar(Producto p) {
        String sql = "INSERT INTO Productos (nombre, precio_base, stock, id_proveedor) VALUES (?, ?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioBase());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getIdProveedor());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar producto: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(con, ps, null);
        }
    }

    public boolean actualizar(Producto p) {
        String sql = "UPDATE Productos SET nombre = ?, precio_base = ?, stock = ?, id_proveedor = ? WHERE id_producto = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setDouble(2, p.getPrecioBase());
            ps.setInt(3, p.getStock());
            ps.setInt(4, p.getIdProveedor());
            ps.setInt(5, p.getIdProducto());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar producto: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(con, ps, null);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Productos WHERE id_producto = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar producto: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(con, ps, null);
        }
    }

    private void cerrarRecursos(Connection con, PreparedStatement ps, ResultSet rs) {
        try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignorar */ }
        try { if (ps != null) ps.close(); } catch (SQLException e) { /* ignorar */ }
        try { if (con != null) con.close(); } catch (SQLException e) { /* ignorar */ }
    }
}
