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
    precio FLOAT NOT NULL,
    cantidad INT NOT NULL,
    stock BOOLEAN NOT NULL,
    genero VARCHAR(100),
    actor VARCHAR(100),
    compositor VARCHAR(100),
    desarrollador VARCHAR(100),
    id_categoria INT,
    id_albaran INT,
    FOREIGN KEY (id_categoria) REFERENCES Categoria(id),
    FOREIGN KEY (id_albaran) REFERENCES Albaran(id)
);

-- Tabla Cliente con campo único para correo electrónico
CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numerocliente VARCHAR(50) NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    apellidos VARCHAR(100) NOT NULL,
    direccion VARCHAR(255),
    localidad VARCHAR(100),
    provincia VARCHAR(100),
    pais VARCHAR(100),
    codigopostal VARCHAR(20),
    telefono VARCHAR(20),
    mail VARCHAR(255) UNIQUE,
    observaciones TEXT
);

-- Tabla Pedido con campo para fecha de pedido
CREATE TABLE Pedido (
    ordendepedido INT AUTO_INCREMENT PRIMARY KEY,
    codigocliente INT,
    fecha_pedido TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (codigocliente) REFERENCES Cliente(id)
);

-- Tabla detalle_pedido
CREATE TABLE Detalle_Pedido (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ordendepedido INT,
    producto INT,
    cantidad INT,
    FOREIGN KEY (ordendepedido) REFERENCES Pedido(ordendepedido),
    FOREIGN KEY (producto) REFERENCES Producto(id)
);

-- Tabla Tiket
CREATE TABLE Tiket (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_pedido INT,
    numero_tiket VARCHAR(20),
    fecha_compra TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total FLOAT,
    FOREIGN KEY (id_pedido) REFERENCES Pedido(ordendepedido)
);

-- ------------------------------------------------------------------------
-- Insertar proveedores ficticios
INSERT INTO Proveedor (nombre, direccion, telefono) VALUES
    ('CineWorld', '123 Calle de la Película', '123-456-7890'),
    ('GameHaven', '456 Avenida de los Videojuegos', '987-654-3210'),
    ('MusicEmporium', '789 Carretera de la Música', '555-123-4567');

-- ------------------------------------------------------------------------
-- Insertar algunas categorías ficticias
INSERT INTO Categoria (nombre, descripcion) VALUES
    ('Cine', 'Películas y series'),
    ('Videojuego', 'Videojuegos y consolas'),
    ('Música', 'Álbumes y canciones');

-- ------------------------------------------------------------------------
-- Insertar albaranes ficticios
INSERT INTO Albaran (id_proveedor, fecha_entrega) VALUES
    (1, '2024-01-15'),
    (2, '2024-01-20'),
    (3, '2024-01-25');

-- ------------------------------------------------------------------------
-- Insertar clientes ficticios
INSERT INTO Cliente (numerocliente, nombre, apellidos, direccion, localidad, provincia, pais, codigopostal, telefono, mail, observaciones) VALUES
    ('C001', 'Juan', 'Pérez', 'Calle Falsa 123', 'Ciudad', 'Provincia', 'España', '28080', '600123456', 'juan.perez@example.com', 'Cliente preferente'),
    ('C002', 'María', 'García', 'Avenida Siempre Viva 742', 'Pueblo', 'Provincia', 'España', '28081', '600654321', 'maria.garcia@example.com', 'Requiere factura'),
    ('C003', 'Carlos', 'Sánchez', 'Plaza Mayor 1', 'Villa', 'Provincia', 'España', '28082', '600987654', 'carlos.sanchez@example.com', 'Compra frecuente de productos de música'),
    ('C004', 'Ana', 'López', 'Paseo del Prado 45', 'Ciudad', 'Provincia', 'España', '28083', '600321654', 'ana.lopez@example.com', 'Cliente VIP'),
    ('C005', 'Luis', 'Martínez', 'Calle Gran Vía 22', 'Metropolis', 'Provincia', 'España', '28084', '600456789', 'luis.martinez@example.com', 'Interesado en videojuegos nuevos'),
    ('C006', 'Isabel', 'Fernández', 'Ronda de Atocha 8', 'Ciudad', 'Provincia', 'España', '28085', '600789123', 'isabel.fernandez@example.com', 'Cliente ocasional'),
    ('C007', 'Miguel', 'González', 'Camino Real 33', 'Pueblo', 'Provincia', 'España', '28086', '600213546', 'miguel.gonzalez@example.com', 'Prefiere productos en oferta'),
    ('C008', 'Laura', 'Rodríguez', 'Calle Mayor 10', 'Villa', 'Provincia', 'España', '28087', '600354768', 'laura.rodriguez@example.com', 'Cliente nuevo'),
    ('C009', 'Javier', 'Hernández', 'Avenida Principal 100', 'Ciudad', 'Provincia', 'España', '28088', '600465879', 'javier.hernandez@example.com', 'Compra en grandes cantidades'),
    ('C010', 'Sofía', 'Jiménez', 'Callejón del Gato 9', 'Pueblo', 'Provincia', 'España', '28089', '600576890', 'sofia.jimenez@example.com', 'Cliente con recomendaciones'),
    ('C011', 'Jhoonny', 'Meentero', 'Carrera 15 #123', 'Ciudad', 'Provincia', 'España', '28090', '601234567', 'jhoonny.meentero@example.com', 'Cliente frecuente de productos de tecnología');

-- ------------------------------------------------------------------------
-- Insertar productos de Cine
INSERT INTO Producto (nombre, precio, cantidad, stock, genero, actor, id_categoria, id_albaran) VALUES
    ('The Shawshank Redemption', 16.99, 55, 1, 'Drama', 'Tim Robbins', 1, 1),
    ('Inception', 19.99, 60, 1, 'Sci-Fi', 'Leonardo DiCaprio', 1, 1),
    ('The Godfather', 18.99, 45, 1, 'Crime', 'Marlon Brando', 1, 1),
    ('The Dark Knight', 17.99, 70, 1, 'Action', 'Christian Bale', 1, 1),
    ('Forrest Gump', 14.99, 80, 1, 'Drama', 'Tom Hanks', 1, 1),
    ('The Matrix', 12.99, 65, 1, 'Sci-Fi', 'Keanu Reeves', 1, 1),
    ('Fight Club', 13.99, 40, 1, 'Drama', 'Brad Pitt', 1, 1),
    ('Interstellar', 20.99, 50, 1, 'Sci-Fi', 'Matthew McConaughey', 1, 1),
    ('Gladiator', 15.99, 75, 1, 'Action', 'Russell Crowe', 1, 1),
    ('Pulp Fiction', 16.99, 30, 1, 'Crime', 'John Travolta', 1, 1);

-- Insertar productos de Videojuego
INSERT INTO Producto (nombre, precio, cantidad, stock, genero, desarrollador, id_categoria, id_albaran) VALUES
    ('The Witcher 3: Wild Hunt', 29.99, 100, 1, 'RPG', 'CD Projekt Red', 2, 2),
    ('Red Dead Redemption 2', 49.99, 90, 1, 'Action', 'Rockstar Games', 2, 2),
    ('The Legend of Zelda: Breath of the Wild', 59.99, 80, 1, 'Adventure', 'Nintendo', 2, 2),
    ('Super Mario Odyssey', 54.99, 70, 1, 'Platform', 'Nintendo', 2, 2),
    ('God of War', 39.99, 85, 1, 'Action', 'Santa Monica Studio', 2, 2),
    ('Persona 5', 44.99, 75, 1, 'RPG', 'Atlus', 2, 2),
    ('Dark Souls III', 24.99, 60, 1, 'RPG', 'FromSoftware', 2, 2),
    ('Bloodborne', 34.99, 50, 1, 'RPG', 'FromSoftware', 2, 2),
    ('Cyberpunk 2077', 49.99, 55, 1, 'RPG', 'CD Projekt Red', 2, 2),
    ('Horizon Zero Dawn', 29.99, 65, 1, 'Action', 'Guerrilla Games', 2, 2);

-- Insertar productos de Música
INSERT INTO Producto (nombre, precio, cantidad, stock, genero, compositor, id_categoria, id_albaran) VALUES
    ('Thriller - Michael Jackson', 14.99, 100, 1, 'Pop', 'Michael Jackson', 3, 3),
    ('Back in Black - AC/DC', 13.99, 90, 1, 'Rock', 'AC/DC', 3, 3),
    ('The Dark Side of the Moon - Pink Floyd', 17.99, 80, 1, 'Rock', 'Pink Floyd', 3, 3),
    ('The Wall - Pink Floyd', 18.99, 70, 1, 'Rock', 'Pink Floyd', 3, 3),
    ('Rumours - Fleetwood Mac', 15.99, 60, 1, 'Rock', 'Fleetwood Mac', 3, 3),
    ('Abbey Road - The Beatles', 12.99, 85, 1, 'Rock', 'The Beatles', 3, 3),
    ('Hotel California - Eagles', 11.99, 75, 1, 'Rock', 'Eagles', 3, 3),
    ('Born in the U.S.A. - Bruce Springsteen', 13.99, 65, 1, 'Rock', 'Bruce Springsteen', 3, 3),
    ('Sgt. Pepper''s Lonely Hearts Club Band - The Beatles', 16.99, 50, 1, 'Rock', 'The Beatles', 3, 3),
    ('21 - Adele', 14.99, 55, 1, 'Pop', 'Adele', 3, 3);

-- Consultas de prueba
select nombre from Producto where id_categoria=1;
SELECT * FROM Cliente WHERE nombre = 'Jhoonny';
select * from tiket;