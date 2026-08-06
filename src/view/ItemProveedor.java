/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 * Pequeño envoltorio para mostrar un Proveedor dentro de un JComboBox:
 * en pantalla se ve el nombre de la empresa, pero por dentro guardamos el id.
 *
 * @author Jared
 */
public class ItemProveedor {

    private final int id;
    private final String nombre;

    public ItemProveedor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    // El JComboBox muestra lo que devuelva toString(): el nombre de la empresa.
    @Override
    public String toString() {
        return nombre;
    }
}
