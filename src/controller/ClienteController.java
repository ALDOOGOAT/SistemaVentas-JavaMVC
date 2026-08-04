package controller;

import dao.ClienteDAO;
import model.Cliente;
import java.util.List;

/**
 * Controlador para el módulo de Clientes.
 * Conecta la vista con la capa de acceso a datos.
 * @author Daniel
 */
public class ClienteController {

    private final ClienteDAO dao;

    public ClienteController() {
        this.dao = new ClienteDAO();
    }

    public List<Cliente> listar() {
        return dao.listar();
    }

    public Cliente buscarPorId(int id) {
        return dao.buscarPorId(id);
    }

    public boolean insertar(Cliente c) {
        return dao.insertar(c);
    }

    public boolean actualizar(Cliente c) {
        return dao.actualizar(c);
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }
}
