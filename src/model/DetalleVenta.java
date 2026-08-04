/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Clase que representa el Detalle de una Venta en el sistema.
 * Mapea la tabla correspondiente en la base de datos (relación item-venta).
 * 
 * @author aldo
 */
public class DetalleVenta {

    /** Identificador único del detalle de venta */
    private int idDetalle;

    /** Identificador de la venta a la que pertenece */
    private int idVenta;

    /** Identificador del producto vendido */
    private int idProducto;

    /** Cantidad vendida del producto */
    private int cantidad;

    /** Precio unitario al que se vendió el producto */
    private double precioVenta;

    /**
     * Constructor por defecto sin argumentos.
     */
    public DetalleVenta() {
    }

    /**
     * Constructor completo con todos los atributos del detalle de venta.
     * 
     * @param idDetalle Identificador único del detalle.
     * @param idVenta Identificador de la venta asociada.
     * @param idProducto Identificador del producto vendido.
     * @param cantidad Cantidad de unidades vendidas.
     * @param precioVenta Precio unitario de venta.
     */
    public DetalleVenta(int idDetalle, int idVenta, int idProducto, int cantidad, double precioVenta) {
        this.idDetalle = idDetalle;
        this.idVenta = idVenta;
        this.idProducto = idProducto;
        this.cantidad = cantidad;
        this.precioVenta = precioVenta;
    }

    /**
     * Obtiene el ID del detalle de venta.
     * 
     * @return El identificador del detalle.
     */
    public int getIdDetalle() {
        return idDetalle;
    }

    /**
     * Establece el ID del detalle de venta.
     * 
     * @param idDetalle El identificador del detalle a asignar.
     */
    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    /**
     * Obtiene el ID de la venta asociada.
     * 
     * @return El identificador de la venta.
     */
    public int getIdVenta() {
        return idVenta;
    }

    /**
     * Establece el ID de la venta asociada.
     * 
     * @param idVenta El identificador de la venta a asignar.
     */
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    /**
     * Obtiene el ID del producto vendido.
     * 
     * @return El identificador del producto.
     */
    public int getIdProducto() {
        return idProducto;
    }

    /**
     * Establece el ID del producto vendido.
     * 
     * @param idProducto El identificador del producto a asignar.
     */
    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    /**
     * Obtiene la cantidad de productos vendidos.
     * 
     * @return La cantidad vendida.
     */
    public int getCantidad() {
        return cantidad;
    }

    /**
     * Establece la cantidad de productos vendidos.
     * 
     * @param cantidad La cantidad a asignar.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el precio de venta unitario.
     * 
     * @return El precio de venta.
     */
    public double getPrecioVenta() {
        return precioVenta;
    }

    /**
     * Establece el precio de venta unitario.
     * 
     * @param precioVenta El precio de venta a asignar.
     */
    public void setPrecioVenta(double precioVenta) {
        this.precioVenta = precioVenta;
    }
}
