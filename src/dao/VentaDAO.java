package dao;

import config.Conexion;
import model.Venta;
import model.DetalleVenta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos para las tablas Ventas y Detalle_Ventas.
 * Utiliza transacciones atómicas para registrar ventas con sus detalles
 * y descontar automáticamente el stock de los productos.
 * @author Aldo
 */
public class VentaDAO {

    /**
     * Registra una venta completa con sus detalles en una transacción atómica.
     * Si algo falla, se hace rollback de toda la operación.
     * Descuenta automáticamente el stock de cada producto vendido.
     */
    public boolean registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        Connection con = null;
        PreparedStatement psVenta = null;
        PreparedStatement psDetalle = null;
        PreparedStatement psStock = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            con.setAutoCommit(false); // Iniciar transacción

            // 1. Insertar la cabecera de la venta
            String sqlVenta = "INSERT INTO Ventas (total_venta, id_cliente) VALUES (?, ?) RETURNING id_venta";
            psVenta = con.prepareStatement(sqlVenta);
            psVenta.setDouble(1, venta.getTotalVenta());
            psVenta.setInt(2, venta.getIdCliente());
            rs = psVenta.executeQuery();

            int idVentaGenerado = 0;
            if (rs.next()) {
                idVentaGenerado = rs.getInt("id_venta");
            }

            // 2. Insertar cada línea de detalle y descontar stock
            String sqlDetalle = "INSERT INTO Detalle_Ventas (id_venta, id_producto, cantidad, precio_venta) VALUES (?, ?, ?, ?)";
            String sqlStock = "UPDATE Productos SET stock = stock - ? WHERE id_producto = ? AND stock >= ?";

            for (DetalleVenta det : detalles) {
                // Insertar detalle
                psDetalle = con.prepareStatement(sqlDetalle);
                psDetalle.setInt(1, idVentaGenerado);
                psDetalle.setInt(2, det.getIdProducto());
                psDetalle.setInt(3, det.getCantidad());
                psDetalle.setDouble(4, det.getPrecioVenta());
                psDetalle.executeUpdate();
                psDetalle.close();

                // Descontar stock
                psStock = con.prepareStatement(sqlStock);
                psStock.setInt(1, det.getCantidad());
                psStock.setInt(2, det.getIdProducto());
                psStock.setInt(3, det.getCantidad());
                int filasAfectadas = psStock.executeUpdate();
                psStock.close();

                if (filasAfectadas == 0) {
                    // Stock insuficiente, abortar transacción
                    con.rollback();
                    System.err.println("Stock insuficiente para el producto ID: " + det.getIdProducto());
                    return false;
                }
            }

            con.commit(); // Confirmar transacción
            System.out.println("✅ Venta #" + idVentaGenerado + " registrada exitosamente.");
            return true;

        } catch (SQLException e) {
            System.err.println("Error al registrar venta: " + e.getMessage());
            try {
                if (con != null) con.rollback();
            } catch (SQLException ex) {
                System.err.println("Error en rollback: " + ex.getMessage());
            }
            return false;
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (psVenta != null) psVenta.close(); } catch (SQLException e) { /* ignorar */ }
            try {
                if (con != null) {
                    con.setAutoCommit(true);
                    con.close();
                }
            } catch (SQLException e) { /* ignorar */ }
        }
    }

    /**
     * Lista todas las ventas registradas.
     */
    public List<Venta> listar() {
        List<Venta> lista = new ArrayList<>();
        String sql = "SELECT id_venta, fecha_hora, total_venta, id_cliente FROM Ventas ORDER BY fecha_hora DESC";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta v = new Venta();
                v.setIdVenta(rs.getInt("id_venta"));
                v.setFechaHora(rs.getString("fecha_hora"));
                v.setTotalVenta(rs.getDouble("total_venta"));
                v.setIdCliente(rs.getInt("id_cliente"));
                lista.add(v);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar ventas: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (con != null) con.close(); } catch (SQLException e) { /* ignorar */ }
        }
        return lista;
    }

    /**
     * Busca una venta por su ID.
     */
    public Venta buscarPorId(int id) {
        Venta v = null;
        String sql = "SELECT id_venta, fecha_hora, total_venta, id_cliente FROM Ventas WHERE id_venta = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                v = new Venta();
                v.setIdVenta(rs.getInt("id_venta"));
                v.setFechaHora(rs.getString("fecha_hora"));
                v.setTotalVenta(rs.getDouble("total_venta"));
                v.setIdCliente(rs.getInt("id_cliente"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar venta: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (ps != null) ps.close(); } catch (SQLException e) { /* ignorar */ }
            try { if (con != null) con.close(); } catch (SQLException e) { /* ignorar */ }
        }
        return v;
    }
}
