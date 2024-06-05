package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import data.Cine;
import data.Musica;
import data.Producto;
import data.Videojuego;
import util.Leer;

/**
 * Clase para gestionar los productos del catálogo.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class GestionProducto {

	private List<Producto> catalogo;
	private Connection conn;

	/**
	 * Constructor de la clase. Inicializa la lista de productos del catálogo.
	 */
	public GestionProducto(Connection conn) {
		this.catalogo = new ArrayList<>();
		this.conn = conn;
		// Cargar productos desde la base de datos
		this.catalogo = cargarProductos();
	}

	/**
	 * Constructor de la clase. Inicializa la lista de productos del catálogo.
	 */
	public GestionProducto() {
		this.catalogo = new ArrayList<>();
	}

	/**
	 * Actualiza el producto en la base de datos después de la venta.
	 * 
	 * @param conn            La conexión a la base de datos.
	 * @param productoEnCesta El producto vendido.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void actualizarProductoEnBaseDeDatos(Connection conn, Producto productoEnCesta) throws SQLException {
		Producto producto = buscarProductoPorIdCatalogo(productoEnCesta.getId());
		if (producto != null) {
			int cantidadVendida = productoEnCesta.getCantidad();
			int nuevaCantidad = producto.getCantidad() - cantidadVendida;

			// Verificar que la nueva cantidad no sea negativa
			if (nuevaCantidad < 0) {
				throw new IllegalArgumentException("La cantidad vendida excede el stock disponible.");
			}

			String updateQuery = "UPDATE Producto SET cantidad = ? WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
				pstmt.setInt(1, nuevaCantidad);
				pstmt.setInt(2, producto.getId());
				pstmt.executeUpdate();
			}
		}
	}

	/**
	 * Actualiza el producto en el catálogo después de la venta.
	 * 
	 * @param productoEnCesta El producto vendido.
	 */
	public void actualizarProductoEnCatalogo(Producto productoEnCesta) {
		Producto producto = buscarProductoPorIdCatalogo(productoEnCesta.getId());
		if (producto != null) {
			int cantidadVendida = productoEnCesta.getCantidad();
			int nuevaCantidad = producto.getCantidad() - cantidadVendida;

			// Verificar que la nueva cantidad no sea negativa
			if (nuevaCantidad < 0) {
				throw new IllegalArgumentException("La cantidad vendida excede el stock disponible.");
			}

			producto.setCantidad(nuevaCantidad);
		}
	}

	/**
	 * Carga los productos desde la base de datos al catálogo.
	 *
	 * @return Lista de productos cargados.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public List<Producto> cargarProductos() {
		String consulta = "SELECT * FROM Producto";
		try (PreparedStatement pstmt = this.conn.prepareStatement(consulta); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Producto producto = crearProductoDesdeResultSet(rs);
				this.catalogo.add(producto);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this.catalogo;
	}

	/**
	 * Muestra los productos del catálogo en un formato legible.
	 * 
	 * @return Una cadena con los detalles de los productos en el catálogo.
	 */
	public String mostrarProductosCatalogo() {

		this.catalogo = cargarProductos();

		StringBuilder resultado = new StringBuilder();
		for (Producto producto : this.catalogo) {
			resultado.append("ID: ").append(producto.getId()).append("\n");
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Precio: ").append(producto.getPrecioUnidad()).append(" euros\n");
			resultado.append("Cantidad: ").append(producto.getCantidad()).append("\n\n");
		}
		return resultado.toString();
	}

	/**
	 * Verifica si hay suficiente stock de un producto.
	 * 
	 * @param producto El producto a verificar.
	 * @param cantidad La cantidad solicitada.
	 * @return true si hay suficiente stock, false en caso contrario.
	 */
	public boolean haySuficienteStock(Producto producto, int cantidad) {
		return producto.getCantidad() >= cantidad;
	}

	/**
	 * Crea un objeto Producto a partir de un ResultSet.
	 *
	 * @param rs El ResultSet de la consulta.
	 * @return Un objeto Producto.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private Producto crearProductoDesdeResultSet(ResultSet rs) throws SQLException {
		int idProducto = rs.getInt("id");
		String nombre = rs.getString("nombre");
		float precio = rs.getFloat("precio");
		int cantidad = rs.getInt("cantidad");
		boolean stock = rs.getBoolean("stock");
		String genero = rs.getString("genero");
		int idCategoria = rs.getInt("id_categoria");

		String atributoEspecifico = obtenerAtributoEspecifico(rs, idCategoria);

		if (stock) {
			return crearProductoConStock(idProducto, nombre, precio, cantidad, stock, genero, idCategoria,
					atributoEspecifico);
		} else {
			return new Producto(nombre, precio, cantidad, stock, genero, idProducto, idCategoria);
		}
	}

	/**
	 * Obtiene un atributo específico del producto basado en su categoría.
	 *
	 * @param rs          El ResultSet de la consulta.
	 * @param idCategoria La categoría del producto.
	 * @return El atributo específico del producto.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private String obtenerAtributoEspecifico(ResultSet rs, int idCategoria) throws SQLException {
		String consultaDetalle = "SELECT valor_detalle FROM Detalles_Producto WHERE id_producto = ?";
		String atributoEspecifico = null;
		try (PreparedStatement pstmtDetalle = this.conn.prepareStatement(consultaDetalle)) {
			pstmtDetalle.setInt(1, rs.getInt("id"));
			try (ResultSet rsDetalle = pstmtDetalle.executeQuery()) {
				if (rsDetalle.next()) {
					atributoEspecifico = rsDetalle.getString("valor_detalle");
				}
			}
		}
		return atributoEspecifico;
	}

	/**
	 * Crea un producto con stock basado en su categoría.
	 *
	 * @param idProducto         El ID del producto.
	 * @param nombre             El nombre del producto.
	 * @param precio             El precio del producto.
	 * @param cantidad           La cantidad del producto.
	 * @param stock              El stock del producto.
	 * @param genero             El género del producto.
	 * @param idCategoria        La categoría del producto.
	 * @param atributoEspecifico El atributo específico del producto.
	 * @return Un objeto Producto.
	 */
	private Producto crearProductoConStock(int idProducto, String nombre, float precio, int cantidad, boolean stock,
			String genero, int idCategoria, String atributoEspecifico) {
		switch (idCategoria) {
		case 1:
			return new Cine(nombre, precio, cantidad, stock, genero, idProducto, atributoEspecifico, idCategoria);
		case 2:
			return new Videojuego(nombre, precio, cantidad, stock, genero, idProducto, idCategoria, atributoEspecifico);
		case 3:
			return new Musica(nombre, precio, cantidad, stock, genero, idProducto, idCategoria, atributoEspecifico);
		default:
			return new Producto(nombre, precio, cantidad, stock, genero, idProducto, idCategoria);
		}
	}

	/**
	 * Agrega un producto al catálogo.
	 * 
	 * @param nombre      El nombre del producto.
	 * @param precio      El precio del producto.
	 * @param cantidad    La cantidad del producto.
	 * @param stock       El stock del producto.
	 * @param genero      El género del producto.
	 * @param idCategoria La categoría del producto.
	 */
	public void agregarProducto(String nombre, float precio, int cantidad, boolean stock, String genero,
			int idCategoria) {
		Producto producto = new Producto(nombre, precio, cantidad, stock, genero, null, idCategoria);
		this.catalogo.add(producto);

		agregarProductoABaseDeDatos(producto);

	}

	/**
	 * Agrega un producto al catálogo.
	 * 
	 * @param producto de la clase producto
	 */
	public void agregarProductoTest(Producto producto) {
		this.catalogo.add(producto);
	}

	/**
	 * Agrega un producto a la base de datos.
	 * 
	 * @param producto El producto a agregar.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void agregarProductoABaseDeDatos(Producto producto) {
		String sql = "INSERT INTO Producto (nombre, precio, cantidad, stock, genero, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
			pstmt.setString(1, producto.getNombre());
			pstmt.setFloat(2, producto.getPrecioUnidad());
			pstmt.setInt(3, producto.getCantidad());
			pstmt.setBoolean(4, producto.getStock());
			pstmt.setString(5, producto.getGenero());
			pstmt.setInt(6, producto.getIdCategoria());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("No ha sido posible registrar el producto en la base de datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Método para crear un producto.
	 * 
	 * @param sc El objeto Scanner para leer la entrada del usuario.
	 * @return El producto creado.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public Producto crearProducto() {
		System.out.println("Ingrese el nombre del producto:");
		String nombre = Leer.datoString();
		System.out.println("Ingrese el precio del producto:");
		float precio = Leer.datoFloat();
		System.out.println("Ingrese la cantidad del producto:");
		int cantidad = Leer.datoInt();
		System.out.println("Hay stock disponible? (true/false):");
		boolean stock = Leer.datoBoolean();
		System.out.println("Ingrese el género del producto:");
		String genero = Leer.datoString();

		int idCategoria;
		while (true) {
			System.out.println("Ingrese la ID de la categoría del producto:");
			idCategoria = Leer.datoInt();
			try {
				if (verificarCategoriaExiste(idCategoria)) {
					break;
				} else {
					System.out.println("ID de categoría no válida. Intente de nuevo.");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return new Producto(nombre, precio, cantidad, stock, genero, null, idCategoria);
	}

	/**
	 * Verifica si una categoría existe en la base de datos.
	 * 
	 * @param idCategoria El ID de la categoría a verificar.
	 * @return true si la categoría existe, false en caso contrario.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public boolean verificarCategoriaExiste(int idCategoria) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Categoria WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idCategoria);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	/**
	 * Borra un producto de la base de datos.
	 * 
	 * @param id El ID del producto a borrar.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void borrarProductoDeBaseDeDatos(int id) {
		String sql = "DELETE FROM Producto WHERE id = ?";

		try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("No ha sido posible borrar el producto en la base de datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Busca un producto en el catálogo por su ID.
	 * 
	 * @param id El ID del producto.
	 * @return El producto encontrado, o null si no se encuentra.
	 */
	public Producto buscarProductoPorIdCatalogo(int id) {
		this.catalogo = cargarProductos();
		for (Producto producto : this.catalogo) {
			if (producto.getId() == id) {
				return producto;
			}
		}
		return null;
	}

	public List<Producto> getCatalogo() {
		return this.catalogo;
	}

	public List<Producto> setCatalogo(List<Producto> catalogo) {
		return this.catalogo = catalogo;
	}

}
