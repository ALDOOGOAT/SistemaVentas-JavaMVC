package controller;

import dao.VentaDAO;
import model.Venta;
import model.DetalleVenta;
import java.util.List;

/**
 * Controlador para el módulo de Ventas.
 * Conecta la vista con la capa de acceso a datos.
 * Maneja la lógica de registrar ventas con transacciones atómicas.
 * @author Aldo
 */
public class VentaController {

    private final VentaDAO dao;

    public VentaController() {
        this.dao = new VentaDAO();
    }

    public boolean registrarVenta(Venta venta, List<DetalleVenta> detalles) {
        return dao.registrarVenta(venta, detalles);
    }

    public List<Venta> listar() {
        return dao.listar();
    }

    public Venta buscarPorId(int id) {
        return dao.buscarPorId(id);
    }
}
