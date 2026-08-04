/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Clase que representa un Cliente en el sistema de ventas.
 * Mapea la tabla correspondiente en la base de datos.
 * 
 * @author aldo
 */
public class Cliente {

    /** Identificador único del cliente */
    private int idCliente;

    /** Nombre completo del cliente */
    private String nombreCompleto;

    /** Correo electrónico del cliente */
    private String email;

    /** Número telefónico del cliente */
    private String telefono;

    /**
     * Constructor por defecto sin argumentos.
     */
    public Cliente() {
    }

    /**
     * Constructor completo con todos los atributos del cliente.
     * 
     * @param idCliente Identificador único del cliente.
     * @param nombreCompleto Nombre completo del cliente.
     * @param email Correo electrónico del cliente.
     * @param telefono Número telefónico del cliente.
     */
    public Cliente(int idCliente, String nombreCompleto, String email, String telefono) {
        this.idCliente = idCliente;
        this.nombreCompleto = nombreCompleto;
        this.email = email;
        this.telefono = telefono;
    }

    /**
     * Obtiene el ID del cliente.
     * 
     * @return El identificador del cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente.
     * 
     * @param idCliente El identificador del cliente a asignar.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el nombre completo del cliente.
     * 
     * @return El nombre completo del cliente.
     */
    public String getNombreCompleto() {
        return nombreCompleto;
    }

    /**
     * Establece el nombre completo del cliente.
     * 
     * @param nombreCompleto El nombre completo a asignar.
     */
    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    /**
     * Obtiene el correo electrónico del cliente.
     * 
     * @return El correo electrónico del cliente.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del cliente.
     * 
     * @param email El correo electrónico a asignar.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene el teléfono del cliente.
     * 
     * @return El número telefónico del cliente.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del cliente.
     * 
     * @param telefono El número telefónico a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
