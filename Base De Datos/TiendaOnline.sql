drop database if exists Tienda;
create database if not exists Tienda;
use Tienda;

-- Tabla Categoría
CREATE TABLE Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla Proveedor
CREATE TABLE Proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

-- Tabla Producto
CREATE TABLE Producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio FLOAT NOT NULL,
    cantidad INT NOT NULL,
    id_categoria INT,
    id_proveedor INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id),
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id)
);

-- Tabla Cliente
CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    email VARCHAR(255),
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

-- Tabla Método de Pago
CREATE TABLE Metodo_Pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

-- Tabla Transacción
CREATE TABLE Transaccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    id_cliente INT,
    id_metodo_pago INT,
    FOREIGN KEY (id_cliente) REFERENCES Cliente(id),
    FOREIGN KEY (id_metodo_pago) REFERENCES Metodo_Pago(id)
);

-- Tabla DetalleTransaccion
CREATE TABLE Detalle_Transaccion (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_transaccion INT,
    id_producto INT,
    cantidad INT NOT NULL,
    precio_unitario FLOAT NOT NULL,
    FOREIGN KEY (id_transaccion) REFERENCES Transaccion(id),
    FOREIGN KEY (id_producto) REFERENCES Producto(id)
);
