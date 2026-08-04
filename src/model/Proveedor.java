/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Clase que representa un Proveedor en el sistema de ventas.
 * Mapea la tabla correspondiente en la base de datos.
 * 
 * @author aldo
 */
public class Proveedor {

    /** Identificador único del proveedor */
    private int idProveedor;

    /** Nombre de la empresa proveedora */
    private String nombreEmpresa;

    /** Nombre o información de la persona de contacto */
    private String contacto;

    /** Número telefónico de contacto del proveedor */
    private String telefono;

    /**
     * Constructor por defecto sin argumentos.
     */
    public Proveedor() {
    }

    /**
     * Constructor completo con todos los atributos del proveedor.
     * 
     * @param idProveedor Identificador único del proveedor.
     * @param nombreEmpresa Nombre de la empresa proveedora.
     * @param contacto Nombre o información de contacto.
     * @param telefono Número telefónico de contacto.
     */
    public Proveedor(int idProveedor, String nombreEmpresa, String contacto, String telefono) {
        this.idProveedor = idProveedor;
        this.nombreEmpresa = nombreEmpresa;
        this.contacto = contacto;
        this.telefono = telefono;
    }

    /**
     * Obtiene el ID del proveedor.
     * 
     * @return El identificador del proveedor.
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * Establece el ID del proveedor.
     * 
     * @param idProveedor El identificador del proveedor a asignar.
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    /**
     * Obtiene el nombre de la empresa proveedora.
     * 
     * @return El nombre de la empresa.
     */
    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    /**
     * Establece el nombre de la empresa proveedora.
     * 
     * @param nombreEmpresa El nombre de la empresa a asignar.
     */
    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    /**
     * Obtiene la información de contacto del proveedor.
     * 
     * @return El contacto del proveedor.
     */
    public String getContacto() {
        return contacto;
    }

    /**
     * Establece la información de contacto del proveedor.
     * 
     * @param contacto El contacto a asignar.
     */
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    /**
     * Obtiene el teléfono del proveedor.
     * 
     * @return El número telefónico del proveedor.
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el teléfono del proveedor.
     * 
     * @param telefono El número telefónico a asignar.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
