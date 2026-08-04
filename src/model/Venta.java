/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * Clase que representa una Venta realizada en el sistema.
 * Mapea la tabla correspondiente en la base de datos.
 * 
 * @author aldo
 */
public class Venta {

    /** Identificador único de la venta */
    private int idVenta;

    /** Fecha y hora en que se realizó la venta */
    private String fechaHora;

    /** Monto total de la venta */
    private double totalVenta;

    /** Identificador del cliente que realizó la compra */
    private int idCliente;

    /**
     * Constructor por defecto sin argumentos.
     */
    public Venta() {
    }

    /**
     * Constructor completo con todos los atributos de la venta.
     * 
     * @param idVenta Identificador único de la venta.
     * @param fechaHora Fecha y hora de la transacción.
     * @param totalVenta Monto total de la venta.
     * @param idCliente Identificador del cliente asociado.
     */
    public Venta(int idVenta, String fechaHora, double totalVenta, int idCliente) {
        this.idVenta = idVenta;
        this.fechaHora = fechaHora;
        this.totalVenta = totalVenta;
        this.idCliente = idCliente;
    }

    /**
     * Obtiene el ID de la venta.
     * 
     * @return El identificador de la venta.
     */
    public int getIdVenta() {
        return idVenta;
    }

    /**
     * Establece el ID de la venta.
     * 
     * @param idVenta El identificador de la venta a asignar.
     */
    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    /**
     * Obtiene la fecha y hora de la venta.
     * 
     * @return La fecha y hora registrada.
     */
    public String getFechaHora() {
        return fechaHora;
    }

    /**
     * Establece la fecha y hora de la venta.
     * 
     * @param fechaHora La fecha y hora a asignar.
     */
    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }

    /**
     * Obtiene el monto total de la venta.
     * 
     * @return El total de la venta.
     */
    public double getTotalVenta() {
        return totalVenta;
    }

    /**
     * Establece el monto total de la venta.
     * 
     * @param totalVenta El monto total a asignar.
     */
    public void setTotalVenta(double totalVenta) {
        this.totalVenta = totalVenta;
    }

    /**
     * Obtiene el ID del cliente asociado a la venta.
     * 
     * @return El identificador del cliente.
     */
    public int getIdCliente() {
        return idCliente;
    }

    /**
     * Establece el ID del cliente asociado a la venta.
     * 
     * @param idCliente El identificador del cliente a asignar.
     */
    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
}
