package controller;

import dao.ProductoDAO;
import model.Producto;
import java.util.List;

/**
 * Controlador para el módulo de Productos.
 * Conecta la vista con la capa de acceso a datos.
 * @author Jared
 */
public class ProductoController {

    private final ProductoDAO dao;

    public ProductoController() {
        this.dao = new ProductoDAO();
    }

    public List<Producto> listar() {
        return dao.listar();
    }

    public Producto buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean insertar(Producto p) {
        return dao.insertar(p);
    }

    public boolean actualizar(Producto p) {
        return dao.actualizar(p);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
}
