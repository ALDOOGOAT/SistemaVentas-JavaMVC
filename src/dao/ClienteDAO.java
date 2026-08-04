package dao;

import config.Conexion;
import model.Cliente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa de acceso a datos para la tabla Clientes.
 * @author Daniel
 */
public class ClienteDAO {

    public List<Cliente> listar() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, nombre_completo, email, telefono FROM Clientes";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombreCompleto(rs.getString("nombre_completo"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
                lista.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Error al listar clientes: " + e.getMessage());
        } finally {
            cerrarRecursos(con, ps, rs);
        }
        return lista;
    }

    public Cliente buscarPorId(int id) {
        Cliente c = null;
        String sql = "SELECT id_cliente, nombre_completo, email, telefono FROM Clientes WHERE id_cliente = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                c = new Cliente();
                c.setIdCliente(rs.getInt("id_cliente"));
                c.setNombreCompleto(rs.getString("nombre_completo"));
                c.setEmail(rs.getString("email"));
                c.setTelefono(rs.getString("telefono"));
            }
        } catch (SQLException e) {
            System.err.println("Error al buscar cliente: " + e.getMessage());
        } finally {
            cerrarRecursos(con, ps, rs);
        }
        return c;
    }

    public boolean insertar(Cliente c) {
        String sql = "INSERT INTO Clientes (nombre_completo, email, telefono) VALUES (?, ?, ?)";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombreCompleto());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar cliente: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(con, ps, null);
        }
    }

    public boolean actualizar(Cliente c) {
        String sql = "UPDATE Clientes SET nombre_completo = ?, email = ?, telefono = ? WHERE id_cliente = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setString(1, c.getNombreCompleto());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getTelefono());
            ps.setInt(4, c.getIdCliente());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al actualizar cliente: " + e.getMessage());
            return false;
        } finally {
            cerrarRecursos(con, ps, null);
        }
    }

    public boolean eliminar(int id) {
        String sql = "DELETE FROM Clientes WHERE id_cliente = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = Conexion.getInstancia().getConexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error al eliminar cliente: " + e.getMessage());
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
