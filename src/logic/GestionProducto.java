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
	}

	/**
	 * Constructor de la clase. Inicializa la lista de productos del catálogo.
	 */
	public GestionProducto() {
		this.catalogo = new ArrayList<>();
	}

	/**
	 * Carga los productos desde la base de datos al catálogo.
	 *
	 * @return Lista de productos cargados.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public List<Producto> cargarProductos() throws SQLException {
		String consulta = "SELECT * FROM Producto";
		try (PreparedStatement pstmt = conn.prepareStatement(consulta); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Producto producto = crearProductoDesdeResultSet(rs);
				this.catalogo.add(producto);
			}
		}
		return this.catalogo;
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
		try (PreparedStatement pstmtDetalle = conn.prepareStatement(consultaDetalle)) {
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
	 * Muestra los productos del catálogo en un formato legible.
	 * 
	 * @return Una cadena con los detalles de los productos en el catálogo.
	 */
	public String mostrarProductosCatalogo() {
		StringBuilder resultado = new StringBuilder();
		for (Producto producto : catalogo) {
			resultado.append("ID: ").append(producto.getId()).append("\n").append("Nombre: ")
					.append(producto.getNombre()).append("\n").append("Precio: ").append(producto.getPrecioUnidad())
					.append(" euros\n").append("Cantidad: ").append(producto.getCantidad()).append("\n\n");
		}
		return resultado.toString();
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
		catalogo.add(producto);

		try {
			agregarProductoABaseDeDatos(producto);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Agrega un producto al catálogo y Base de Datos.
	 * 
	 * @param producto de la clase producto
	 */
	public void agregarProducto(Producto producto) {
		catalogo.add(producto);
		try {
			agregarProductoABaseDeDatos(producto);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("No ha sido posible registrar el producto en la base de datos.");
		}
	}

	/**
	 * Agrega un producto al catálogo.
	 * 
	 * @param producto de la clase producto
	 */
	public void agregarProductoTest(Producto producto) {
		catalogo.add(producto);
	}
 
	/**
	 * Agrega un producto a la base de datos.
	 * 
	 * @param producto El producto a agregar.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void agregarProductoABaseDeDatos(Producto producto) throws SQLException {
		String sql = "INSERT INTO Producto (nombre, precio, cantidad, stock, genero, id_categoria) VALUES (?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, producto.getNombre());
			pstmt.setFloat(2, producto.getPrecioUnidad());
			pstmt.setInt(3, producto.getCantidad());
			pstmt.setBoolean(4, producto.getStock());
			pstmt.setString(5, producto.getGenero());
			pstmt.setInt(6, producto.getIdCategoria());
			pstmt.executeUpdate();
		}
	}

	/**
	 * Borra un producto del catálogo y de la base de datos.
	 * 
	 * @param id El ID del producto a borrar.
	 */
	public void borrarProducto(int id) {
		Producto producto = buscarProductoPorIdCatalogo(id);
		if (producto != null) {
			catalogo.remove(producto);
			try {
				borrarProductoDeBaseDeDatos(id);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("Producto eliminado exitosamente.");
		} else {
			System.out.println("Producto no encontrado.");
		}
	}

	/**
	 * Borra un producto de la base de datos.
	 * 
	 * @param id El ID del producto a borrar.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void borrarProductoDeBaseDeDatos(int id) throws SQLException {
		String sql = "DELETE FROM Producto WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}

	/**
	 * Busca un producto en el catálogo por su ID.
	 * 
	 * @param id El ID del producto.
	 * @return El producto encontrado, o null si no se encuentra.
	 */
	public Producto buscarProductoPorIdCatalogo(int id) {
		for (Producto producto : catalogo) {
			if (producto.getId() == id) {
				return producto;
			}
		}
		return null;
	}
}
