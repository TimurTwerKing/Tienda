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
import util.Conexion;

/**
 * @autor Timur Bogach
 * @date 19 may 2024
 * @param Clase para gestionar los productos del catálogo.
 */
public class GestionProducto {

	private List<Producto> catalogo;

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
		String consulta = "SELECT * FROM PRODUCTO";
		try (Connection conn = Conexion.conectar();
				PreparedStatement pstmt = conn.prepareStatement(consulta);
				ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Producto producto = crearProductoDesdeResultSet(rs);
				this.catalogo.add(producto);
			}
		}
		return this.catalogo;
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
		switch (idCategoria) {
		case 1:
			return rs.getString("actor");
		case 2:
			return rs.getString("desarrollador");
		case 3:
			return rs.getString("compositor");
		default:
			return "";
		}
	}

	/**
	 * Crea un objeto Producto que tiene stock, basado en su categoría.
	 *
	 * @param idProducto         El ID del producto.
	 * @param nombre             El nombre del producto.
	 * @param precio             El precio del producto.
	 * @param cantidad           La cantidad disponible del producto.
	 * @param stock              Si el producto está en stock.
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
	 * Busca un producto en el catálogo por su ID.
	 *
	 * @param productoId El ID del producto.
	 * @return El producto encontrado, o null si no se encuentra.
	 */
	public Producto buscarProductoPorId(int productoId) {
		return catalogo.stream().filter(producto -> producto.getId() == productoId).findFirst().orElse(null);
	}

	/**
	 * Muestra los productos del catálogo en un formato legible.
	 *
	 * @return Una cadena con los detalles de los productos del catálogo.
	 */
	public String mostrarProductosCatalogo() {
		StringBuilder resultado = new StringBuilder();
		for (Producto producto : catalogo) {
			resultado.append(obtenerDetalleProducto(producto)).append("\n\n");
		}
		return resultado.toString();
	}

	/**
	 * Obtiene los detalles de un producto en un formato legible.
	 *
	 * @param producto El producto.
	 * @return Una cadena con los detalles del producto.
	 */
	private String obtenerDetalleProducto(Producto producto) {
		StringBuilder detalle = new StringBuilder();
		int genero = producto.getIdCategoria();
		if (genero == 1) {
			detalle.append("Pelicula\n");
		} else if (genero == 2) {
			detalle.append("Videojuegos\n");
		} else {
			detalle.append("Musica\n");
		}
		detalle.append("ID: ").append(producto.getId()).append("\n").append("Nombre: ").append(producto.getNombre())
				.append("\n").append("Disponible: ").append(producto.getCantidad()).append("\n")
				.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.");
		return detalle.toString();
	}

	/**
	 * Agrega un producto al catálogo.
	 *
	 * @param producto El producto a agregar.
	 */
	public void agregarProducto(Producto producto) {
		this.catalogo.add(producto);
	}
}