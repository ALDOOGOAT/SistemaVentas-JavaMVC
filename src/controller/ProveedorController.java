package controller;

import dao.ProveedorDAO;
import model.Proveedor;
import java.util.List;

/**
 * Controlador para el módulo de Proveedores.
 * Conecta la vista con la capa de acceso a datos.
 * @author Ilde
 */
public class ProveedorController {

    private final ProveedorDAO dao;

    public ProveedorController() {
        this.dao = new ProveedorDAO();
    }

    public List<Proveedor> listar() {
        return dao.listar();
    }

    public Proveedor buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean insertar(Proveedor p) {
        return dao.insertar(p);
    }

    public boolean actualizar(Proveedor p) {
        return dao.actualizar(p);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
}
