-- Eliminar la base de datos si existe y crearla de nuevo
DROP DATABASE IF EXISTS Tienda;
CREATE DATABASE IF NOT EXISTS Tienda;
USE Tienda;

-- Tabla Categoría
CREATE TABLE Categoria (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    descripcion VARCHAR(100) NOT NULL
);

-- Tabla Proveedor
CREATE TABLE Proveedor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL UNIQUE,
    direccion VARCHAR(255),
    telefono VARCHAR(20)
);

-- Tabla Albaran
CREATE TABLE Albaran (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_proveedor INT,
    fecha_entrega DATE,
    FOREIGN KEY (id_proveedor) REFERENCES Proveedor(id)
);

-- Tabla Producto
CREATE TABLE Producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    cantidad INT NOT NULL,
    stock BOOLEAN NOT NULL,
    genero VARCHAR(100),
    id_categoria INT,
    id_albaran INT,
    activo BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id),
    FOREIGN KEY (id_albaran) REFERENCES Albaran(id)
);

-- Nueva tabla para detalles específicos del Producto
CREATE TABLE Detalles_Producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_producto INT,
    tipo_detalle VARCHAR(100),
    valor_detalle VARCHAR(255),
    FOREIGN KEY (id_producto) REFERENCES Producto(id)
);

-- Tabla Cliente con campo único para correo electrónico
CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_cliente VARCHAR(50) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    localidad VARCHAR(100),
    provincia VARCHAR(100),
    pais VARCHAR(100),
    codigo_postal VARCHAR(20),
    telefono VARCHAR(20),
    mail VARCHAR(255) UNIQUE,
    observaciones TEXT,
    activo BOOLEAN DEFAULT TRUE
);

-- Tabla Pedido con campo para fecha de pedido
CREATE TABLE Pedido (
    orden_de_pedido INT AUTO_INCREMENT PRIMARY KEY,
    codigo_cliente INT,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (codigo_cliente) REFERENCES Cliente(id)
);

-- Tabla Detalle_Pedido con nombres consistentes
CREATE TABLE Detalle_Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    orden_de_pedido INT,
    codigo_producto INT,
    cantidad INT,
    FOREIGN KEY (orden_de_pedido) REFERENCES Pedido(orden_de_pedido),
    FOREIGN KEY (codigo_producto) REFERENCES Producto(id)
);

-- Tabla Tiket
CREATE TABLE Tiket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    numero_tiket VARCHAR(20),
    fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10, 2),
    FOREIGN KEY (id_pedido) REFERENCES Pedido(orden_de_pedido)
);

-- Insertar proveedores ficticios
INSERT INTO Proveedor (nombre, direccion, telefono) VALUES
    ('CineWorld', '123 Calle de la Película', '123-456-7890'),
    ('GameHaven', '456 Avenida de los Videojuegos', '987-654-3210'),
    ('MusicEmporium', '789 Carretera de la Música', '555-123-4567');

-- Insertar algunas categorías ficticias
INSERT INTO Categoria (nombre, descripcion) VALUES
    ('Cine', 'Películas y series'),
    ('Videojuego', 'Videojuegos y consolas'),
    ('Música', 'Álbumes y canciones');

-- Insertar albaranes ficticios
INSERT INTO Albaran (id_proveedor, fecha_entrega) VALUES
    (1, '2024-01-15'),
    (2, '2024-01-20'),
    (3, '2024-01-25');

-- Insertar clientes ficticios
INSERT INTO Cliente (numero_cliente, nombre, apellidos, direccion, localidad, provincia, pais, codigo_postal, telefono, mail, observaciones) VALUES
	('C001', 'Pedro', 'Serna', 'El Campico', 'Murcia City', 'Murcia', 'Españistan', '03400', '666777888', 'pedroserna@elcampico.org', 'pro'),
    ('C002', 'Timur', 'Bogach', 'C/Campoamor n8', 'Orihuela', 'Alicante', 'Mongolia', '03300', '658440125', 'Timurnator_@outlook.com', 'uno mas...'),
    ('C003', 'Jhoonny', 'Meentero', 'Carrera 15 #123', 'Ciudad', 'Provincia', 'España', '28090', '601234567', 'jhoonny.meentero@example.com', 'Cliente frecuente de productos de tecnología');

-- Insertar productos de Cine
INSERT INTO Producto (nombre, precio, cantidad, stock, genero, id_categoria, id_albaran) VALUES
    ('The Shawshank Redemption', 16.99, 55, 1, 'Drama', 1, 1),
    ('Inception', 19.99, 60, 1, 'Sci-Fi', 1, 1),
    ('The Godfather', 18.99, 45, 1, 'Crime', 1, 1),
    ('The Dark Knight', 17.99, 70, 1, 'Action', 1, 1),
    ('Forrest Gump', 14.99, 80, 1, 'Drama', 1, 1),
    ('The Matrix', 12.99, 65, 1, 'Sci-Fi', 1, 1),
    ('Fight Club', 13.99, 40, 1, 'Drama', 1, 1),
    ('Interstellar', 20.99, 50, 1, 'Sci-Fi', 1, 1),
    ('Gladiator', 15.99, 75, 1, 'Action', 1, 1),
    ('Pulp Fiction', 16.99, 30, 1, 'Crime', 1, 1);

-- Insertar productos de Videojuego
INSERT INTO Producto (nombre, precio, cantidad, stock, genero, id_categoria, id_albaran) VALUES
    ('The Witcher 3: Wild Hunt', 29.99, 100, 1, 'RPG', 2, 2),
    ('Red Dead Redemption 2', 49.99, 90, 1, 'Action', 2, 2),
    ('The Legend of Zelda: Breath of the Wild', 59.99, 80, 1, 'Adventure', 2, 2),
    ('Super Mario Odyssey', 54.99, 70, 1, 'Platform', 2, 2),
    ('God of War', 39.99, 85, 1, 'Action', 2, 2),
    ('Persona 5', 44.99, 75, 1, 'RPG', 2, 2),
    ('Dark Souls III', 24.99, 60, 1, 'RPG', 2, 2),
    ('Bloodborne', 34.99, 50, 1, 'RPG', 2, 2),
    ('Cyberpunk 2077', 49.99, 55, 1, 'RPG', 2, 2),
    ('Horizon Zero Dawn', 29.99, 65, 1, 'Action', 2, 2);

-- Insertar productos de Música
INSERT INTO Producto (nombre, precio, cantidad, stock, genero, id_categoria, id_albaran) VALUES
    ('Thriller - Michael Jackson', 14.99, 100, 1, 'Pop', 3, 3),
    ('Back in Black - AC/DC', 13.99, 90, 1, 'Rock', 3, 3),
    ('The Dark Side of the Moon - Pink Floyd', 17.99, 80, 1, 'Rock', 3, 3),
    ('The Wall - Pink Floyd', 18.99, 70, 1, 'Rock', 3, 3),
    ('Rumours - Fleetwood Mac', 15.99, 60, 1, 'Rock', 3, 3),
    ('Abbey Road - The Beatles', 12.99, 85, 1, 'Rock', 3, 3),
    ('Hotel California - Eagles', 11.99, 75, 1, 'Rock', 3, 3),
    ('Born in the U.S.A. - Bruce Springsteen', 13.99, 65, 1, 'Rock', 3, 3),
    ('Sgt. Pepper\'s Lonely Hearts Club Band - The Beatles', 16.99, 50, 1, 'Rock', 3, 3),
    ('21 - Adele', 14.99, 55, 1, 'Pop', 3, 3);

-- Consultas de prueba
USE Tienda;

SELECT nombre FROM Producto WHERE id_categoria = 1;
SELECT * FROM Cliente WHERE nombre = 'Jhoonny';
SELECT * FROM Producto;
SELECT * FROM Cliente;
SELECT * FROM Tiket;
select * from Pedido;
SELECT * FROM detalle_pedido;

DELETE FROM Cliente
WHERE nombre = '?';

-- Consulta para ver la información conjunta de Pedido, detalle_pedido y Tiket
SELECT 
    p.orden_de_pedido AS idPedido,
    p.codigo_cliente,
    p.fecha_pedido,
    dp.id AS idDetallePedido,
    dp.codigo_producto,
    dp.cantidad,
    t.id AS idTiket,
    t.numero_tiket,
    t.fecha_compra,
    t.total
FROM 
    Pedido p
JOIN 
    detalle_pedido dp ON p.orden_de_pedido = dp.orden_de_pedido
JOIN 
    Tiket t ON p.orden_de_pedido = t.id_pedido;
    

SELECT 
    p.orden_de_pedido AS idPedido,
    p.codigo_cliente,
    p.fecha_pedido,
    dp.id AS idDetallePedido,
    dp.codigo_producto,
    dp.cantidad,
    t.id AS idTiket,
    t.numero_tiket,
    t.fecha_compra,
    t.total
FROM 
    Pedido p
JOIN 
    Tiket t ON p.orden_de_pedido = t.id_pedido
LEFT JOIN 
    (SELECT 
         dp.orden_de_pedido,
         dp.id,
         dp.codigo_producto,
         dp.cantidad
     FROM 
         detalle_pedido dp) dp ON p.orden_de_pedido = dp.orden_de_pedido;
