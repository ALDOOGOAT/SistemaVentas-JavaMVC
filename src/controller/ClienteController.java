package controller;

import dao.ClienteDAO;
import model.Cliente;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Controlador para el módulo de Clientes. Conecta la vista con la capa de
 * acceso a datos.
 *
 * @author Daniel
 */
public class ClienteController {

    private final ClienteDAO dao;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private static final String PHONE_REGEX = "^\\d{10}$";

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
        if (validarCliente(c)) {
            return dao.insertar(c); 
        }
        return false;
    }

    public boolean eliminar(int id) {
        return dao.eliminar(id);
    }

    private boolean validarCliente(Cliente c) {
        if (c.getNombreCompleto() == null || c.getNombreCompleto().trim().isEmpty()) {
            System.err.println("El nombre no puede estar incompleto");
            return false;
        }

        if (c.getEmail() == null || !Pattern.matches(EMAIL_REGEX, c.getEmail())) {
            System.err.println("Error.formato de correo incorrecto");
            return false;
        }

        if (c.getTelefono() == null || !Pattern.matches(PHONE_REGEX, c.getTelefono())) {
            System.err.println("Error de validacion: son obligatorios 10 digitos");
            return false;
        }

        return true;
    }
    public boolean actualizar(Cliente c) {
        if (validarCliente(c)) {
            return dao.actualizar(c);
        }
        return false;
    }
    
}
