/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Clase que representa un Producto en el sistema de ventas.
 * Mapea la tabla correspondiente en la base de datos.
 * 
 * @author aldo
 */
public class Producto {

    /** Identificador único del producto */
    private int idProducto;

    /** Nombre del producto */
    private String nombre;

    /** Precio base del producto */
    private double precioBase;

    /** Cantidad disponible en inventario (stock) */
    private int stock;

    /** Identificador del proveedor del producto */
    private int idProveedor;

    /**
     * Constructor por defecto sin argumentos.
     */
    public Producto() {
    }

    /**
     * Constructor completo con todos los atributos del producto.
     * 
     * @param idProducto Identificador único del producto.
     * @param nombre Nombre del producto.
     * @param precioBase Precio base del producto.
     * @param stock Cantidad disponible en stock.
     * @param idProveedor Identificador del proveedor asociado.
     */
    public Producto(int idProducto, String nombre, double precioBase, int stock, int idProveedor) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.precioBase = precioBase;
        this.stock = stock;
        this.idProveedor = idProveedor;
    }

    /**
     * Obtiene el ID del producto.
     * 
     * @return El identificador del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el ID del producto.
     * 
     * @param idProducto El identificador del producto a asignar.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene el nombre del producto.
     * 
     * @return El nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * 
     * @param nombre El nombre a asignar.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio base del producto.
     * 
     * @return El precio base del producto.
     */
    public double getPrecioBase() {
        return precioBase;
    }

    /**
     * Establece el precio base del producto.
     * 
     * @param precioBase El precio base a asignar.
     */
    public void setPrecioBase(double precioBase) {
        this.precioBase = precioBase;
    }

    /**
     * Obtiene el stock disponible del producto.
     * 
     * @return La cantidad en stock.
     */
    public int getStock() {
        return stock;
    }

    /**
     * Establece el stock disponible del producto.
     * 
     * @param stock La cantidad en stock a asignar.
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Obtiene el ID del proveedor del producto.
     * 
     * @return El identificador del proveedor.
     */
    public int getIdProveedor() {
        return idProveedor;
    }

    /**
     * Establece el ID del proveedor del producto.
     * 
     * @param idProveedor El identificador del proveedor a asignar.
     */
    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }
}
