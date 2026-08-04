-- ==============================================================================
-- SCRIPT DE CREACIÓN DE BASE DE DATOS
-- Basado en el Diagrama Entidad-Relación: Gestión de Proveedores, Productos, Clientes y Ventas
-- Motor de Base de Datos: PostgreSQL
-- ==============================================================================
Antes que nada crea la base de datos si no existe y selecciona la base de datos para trabajar en ella.
CREATE DATABASE IF NOT EXISTS tiendita;

\c tiendita;
-- Comienza una transacción para asegurar la atomicidad de la creación.
BEGIN;

-- ==============================================================================
-- 1. CREACIÓN DE TABLAS MAESTRAS (INDEPENDIENTES)
-- ==============================================================================

-- Tabla: Proveedores
-- Almacena la información de contacto de las empresas que suministran productos.
CREATE TABLE Proveedores (
    id_proveedor SERIAL PRIMARY KEY, -- Clave Primaria Autoincremental
    nombre_empresa VARCHAR(255) NOT NULL,
    contacto VARCHAR(255),
    telefono VARCHAR(20)
);

-- Tabla: Clientes
-- Almacena los datos personales de los clientes que realizan compras.
CREATE TABLE Clientes (
    id_cliente SERIAL PRIMARY KEY, -- Clave Primaria Autoincremental
    nombre_completo VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE, -- El email debe ser único
    telefono VARCHAR(20)
);

-- ==============================================================================
-- 2. CREACIÓN DE TABLAS CON DEPENDENCIAS SIMPLES
-- ==============================================================================

-- Tabla: Productos
-- Almacena la información de los productos, vinculados a un proveedor.
CREATE TABLE Productos (
    id_producto SERIAL PRIMARY KEY, -- Clave Primaria Autoincremental
    nombre VARCHAR(255) NOT NULL,
    precio_base NUMERIC(10, 2) NOT NULL CHECK (precio_base >= 0), -- Precisión financiera, >= 0
    stock INTEGER NOT NULL CHECK (stock >= 0), -- Cantidad en inventario, >= 0
    id_proveedor INTEGER NOT NULL,
    
    -- Restricción de Clave Foránea a Proveedores
    CONSTRAINT fk_proveedor_producto FOREIGN KEY (id_proveedor) 
        REFERENCES Proveedores(id_proveedor)
        ON DELETE RESTRICT -- Impide borrar un proveedor si tiene productos asociados
        ON UPDATE CASCADE  -- Actualiza la FK si cambia la PK del proveedor
);

-- Tabla: Ventas
-- Cabecera de la venta, almacena datos generales y vincula al cliente.
CREATE TABLE Ventas (
    id_venta SERIAL PRIMARY KEY, -- Clave Primaria Autoincremental
    fecha_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL, -- Fecha y hora actuales por defecto
    total_venta NUMERIC(10, 2) NOT NULL CHECK (total_venta >= 0), -- Precisión financiera, >= 0
    id_cliente INTEGER NOT NULL,
    
    -- Restricción de Clave Foránea a Clientes
    CONSTRAINT fk_cliente_venta FOREIGN KEY (id_cliente) 
        REFERENCES Clientes(id_cliente)
        ON DELETE RESTRICT -- Impide borrar un cliente si tiene ventas asociadas
        ON UPDATE CASCADE  -- Actualiza la FK si cambia la PK del cliente
);

-- ==============================================================================
-- 3. CREACIÓN DE TABLAS DE DETALLE (DEPENDENCIAS MÚLTIPLES)
-- ==============================================================================

-- Tabla: Detalle_Ventas
-- Entidad intermedia que desglosa los productos de cada venta.
CREATE TABLE Detalle_Ventas (
    id_detalle SERIAL PRIMARY KEY, -- Clave Primaria Autoincremental
    id_venta INTEGER NOT NULL,
    id_producto INTEGER NOT NULL,
    cantidad INTEGER NOT NULL CHECK (cantidad > 0), -- La cantidad debe ser mayor que 0
    precio_venta NUMERIC(10, 2) NOT NULL CHECK (precio_venta >= 0), -- Precio al momento de la venta
    
    -- Restricciones de Clave Foránea
    CONSTRAINT fk_venta_detalle FOREIGN KEY (id_venta) 
        REFERENCES Ventas(id_venta)
        ON DELETE CASCADE -- Si se borra la venta, se borran sus detalles automáticamente
        ON UPDATE CASCADE,
        
    CONSTRAINT fk_producto_detalle FOREIGN KEY (id_producto) 
        REFERENCES Productos(id_producto)
        ON DELETE RESTRICT -- Impide borrar un producto si está en un detalle de venta
        ON UPDATE CASCADE
);

-- ==============================================================================
-- FIN DEL SCRIPT
-- ==============================================================================

-- Confirma la transacción si no hubo errores.
COMMIT;