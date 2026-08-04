package dao;

import config.Conexion;
import model.Proveedor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos para la tabla Proveedores.
 * @author Ilde
 */
public class ProveedorDAO {

    /**
     * Lista todos los proveedores registrados.
     */
    public List<Proveedor> listar() {
        List<Proveedor> lista = new ArrayList<>();
        String sql = "SELECT id_proveedor, nombre_empresa, contacto, telefono FROM Proveedores";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Proveedor p = new Proveedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setNombreEmpresa(rs.getString("nombre_empresa"));
                p.setContacto(rs.getString("contacto"));
                p.setTelefono(rs.getString("telefono"));
                lista.add(p);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar proveedores: " + e.getMessage());
        } finally {
            cerrarRecursos(con, ps, rs);
        }
        return lista;
    }

    /**
     * Busca un proveedor por su ID.
     */
    public Proveedor buscarPorId(int id) {
        Proveedor p = null;
        String sql = "SELECT id_proveedor, nombre_empresa, contacto, telefono FROM Proveedores WHERE id_proveedor = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                p = new Proveedor();
                p.setIdProveedor(rs.getInt("id_proveedor"));
                p.setNombreEmpresa(rs.getString("nombre_empresa"));
                p.setContacto(rs.getString("contacto"));
                p.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar proveedor: " + e.getMessage());
        } finally {
            cerrarRecursos(con, ps, rs);
        }
        return p;
    }

    /**
     * Inserta un nuevo proveedor.
     */
    public boolean insertar(Proveedor p) {
        String sql = "INSERT INTO Proveedores (nombre_empresa, contacto, telefono) VALUES (?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombreEmpresa());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar proveedor: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(con, ps, null);
        }
    }

    /**
     * Actualiza un proveedor existente.
     */
    public boolean actualizar(Proveedor p) {
        String sql = "UPDATE Proveedores SET nombre_empresa = ?, contacto = ?, telefono = ? WHERE id_proveedor = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombreEmpresa());
            ps.setString(2, p.getContacto());
            ps.setString(3, p.getTelefono());
            ps.setInt(4, p.getIdProveedor());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar proveedor: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(con, ps, null);
        }
    }

    /**
     * Elimina un proveedor por su ID.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM Proveedores WHERE id_proveedor = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar proveedor: " + e.getMessage());
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
