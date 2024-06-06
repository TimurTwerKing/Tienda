package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import data.Producto;
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
	 */
	public List<Producto> cargarProductos() {
		this.catalogo.clear(); // Limpiar la lista antes de recargar
		String consulta = "SELECT * FROM Producto";
		try (PreparedStatement pstmt = this.conn.prepareStatement(consulta); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Producto producto = crearProductoDesdeResultSet(rs);
				this.catalogo.add(producto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return this.catalogo;
	}

	/**
	 * Muestra los productos activos del catálogo en un formato legible.
	 * 
	 * @return Una cadena con los detalles de los productos activos en el catálogo.
	 */
	public String mostrarProductosActivos() {
		StringBuilder resultado = new StringBuilder();
		for (Producto producto : this.catalogo) {
			if (producto.isActivo()) {
				resultado.append("ID: ").append(producto.getId()).append("\n");
				resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
				resultado.append("Precio: ").append(producto.getPrecio()).append(" euros\n");
				resultado.append("Cantidad: ").append(producto.getCantidad()).append("\n\n");
			}
		}
		return resultado.toString();
	}

	/**
	 * Muestra los productos inactivos del catálogo en un formato legible.
	 * 
	 * @return Una cadena con los detalles de los productos inactivos en el
	 *         catálogo.
	 */
	public String mostrarProductosInactivos() {
		StringBuilder resultado = new StringBuilder();
		for (Producto producto : this.catalogo) {
			if (!producto.isActivo()) {
				resultado.append("ID: ").append(producto.getId()).append("\n");
				resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
				resultado.append("Precio: ").append(producto.getPrecio()).append(" euros\n");
				resultado.append("Cantidad: ").append(producto.getCantidad()).append("\n\n");
			}
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
		int idAlbaran = rs.getInt("id_albaran");
		boolean activo = rs.getBoolean("activo");

		// String atributoEspecifico = obtenerAtributoEspecifico(rs, idProducto);

		return new Producto(idProducto, nombre, precio, cantidad, stock, genero, idCategoria, idAlbaran, activo);
	}

	/**
	 * Obtiene un atributo específico del producto basado en su categoría.
	 *
	 * @param rs         El ResultSet de la consulta.
	 * @param idProducto El ID del producto.
	 * @return El atributo específico del producto.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
//	private String obtenerAtributoEspecifico(ResultSet rs, int idProducto) throws SQLException {
//		String consultaDetalle = "SELECT valor_detalle FROM Detalles_Producto WHERE id_producto = ?";
//		String atributoEspecifico = null;
//		try (PreparedStatement pstmtDetalle = this.conn.prepareStatement(consultaDetalle)) {
//			pstmtDetalle.setInt(1, idProducto);
//			try (ResultSet rsDetalle = pstmtDetalle.executeQuery()) {
//				if (rsDetalle.next()) {
//					atributoEspecifico = rsDetalle.getString("valor_detalle");
//				}
//			}
//		}
//		return atributoEspecifico;
//	}

	/**
	 * Agrega un producto al catálogo.
	 * 
	 * @param nombre      El nombre del producto.
	 * @param precio      El precio del producto.
	 * @param cantidad    La cantidad del producto.
	 * @param stock       El stock del producto.
	 * @param genero      El género del producto.
	 * @param idCategoria La categoría del producto.
	 * @param idAlbaran   El albarán del producto.
	 */
	public void agregarProducto(String nombre, float precio, int cantidad, boolean stock, String genero,
			int idCategoria, int idAlbaran) {
		Producto producto = new Producto(nombre, precio, cantidad, stock, genero, idCategoria, idAlbaran);
		this.catalogo.add(producto);

		agregarProductoABaseDeDatos(producto);
	}

	/**
	 * Agrega un producto a la base de datos.
	 * 
	 * @param producto El producto a agregar.
	 */
	private void agregarProductoABaseDeDatos(Producto producto) {
		String sql = "INSERT INTO Producto (nombre, precio, cantidad, stock, genero, id_categoria, id_albaran) VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			pstmt.setString(1, producto.getNombre());
			pstmt.setFloat(2, producto.getPrecio());
			pstmt.setInt(3, producto.getCantidad());
			pstmt.setBoolean(4, producto.getStock());
			pstmt.setString(5, producto.getGenero());
			pstmt.setInt(6, producto.getIdCategoria());
			pstmt.setInt(7, producto.getIdAlbaran());
			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					producto.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("No se pudo obtener el ID del producto.");
				}
			}
		} catch (SQLException e) {
			System.out.println("No ha sido posible registrar el producto en la base de datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Método para crear un producto con entrada de usuario.
	 * 
	 * @return El producto creado.
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
				e.printStackTrace();
			}
		}

		int idAlbaran;
		while (true) {
			System.out.println("Ingrese la ID del albarán del producto:");
			idAlbaran = Leer.datoInt();
			try {
				if (verificarAlbaranExiste(idAlbaran)) {
					break;
				} else {
					System.out.println("ID de albarán no válida. Intente de nuevo.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return new Producto(nombre, precio, cantidad, stock, genero, idCategoria, idAlbaran);
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
	 * Verifica si un albarán existe en la base de datos.
	 * 
	 * @param idAlbaran El ID del albarán a verificar.
	 * @return true si el albarán existe, false en caso contrario.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public boolean verificarAlbaranExiste(int idAlbaran) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Albaran WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idAlbaran);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	/**
	 * Inactiva un producto en el catálogo y en la base de datos.
	 * 
	 * @param conn La conexión a la base de datos.
	 */
	public void desactivarProducto(Connection conn) {
		try {
			// Cargar productos activos antes de mostrarlos
			cargarProductos();
			System.out.println("Productos en el catálogo:");
			System.out.println(mostrarProductosActivos());
			System.out.println("Ingrese el ID del producto a marcar como inactivo:");
			int idProducto = Leer.datoInt();
			if (!hayProductoRegistrado(idProducto, conn)) {
				System.out.println("El producto que se desea marcar como inactivo no existe.");
			} else {
				// Marcar el producto como inactivo
				marcarProductoComoInactivo(idProducto, conn);
				System.out.println("El producto ha sido marcado como inactivo con éxito.");
				cargarProductos(); // Recargar productos después de la actualización
			}
		} catch (SQLException e) {
			System.out.println("Hay un error al comprobar si el producto existe en la base de datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Activa un producto inactivo en el catálogo y en la base de datos.
	 * 
	 * @param conn La conexión a la base de datos.
	 */
	public void activarProducto(Connection conn) {
		try {
			// Cargar productos inactivos antes de mostrarlos
			cargarProductos();
			System.out.println("Productos inactivos en el catálogo:");
			System.out.println(mostrarProductosInactivos());
			System.out.println("Ingrese el ID del producto a marcar como activo:");
			int idProducto = Leer.datoInt();
			if (!hayProductoRegistrado(idProducto, conn)) {
				System.out.println("El producto que se desea marcar como activo no existe.");
			} else {
				// Marcar el producto como activo
				marcarProductoComoActivo(idProducto, conn);
				System.out.println("El producto ha sido marcado como activo con éxito.");
				cargarProductos(); // Recargar productos después de la actualización
			}
		} catch (SQLException e) {
			System.out.println("Hay un error al comprobar si el producto existe en la base de datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Marca un producto como inactivo en la base de datos.
	 * 
	 * @param idProducto El ID del producto a marcar como inactivo.
	 * @param conn       La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void marcarProductoComoInactivo(int idProducto, Connection conn) throws SQLException {
		String sql = "UPDATE Producto SET activo = FALSE WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idProducto);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("No ha sido posible marcar el producto como inactivo en la base de datos.");
			throw e;
		}
	}

	/**
	 * Marca un producto como activo en la base de datos.
	 * 
	 * @param idProducto El ID del producto a marcar como activo.
	 * @param conn       La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void marcarProductoComoActivo(int idProducto, Connection conn) throws SQLException {
		String sql = "UPDATE Producto SET activo = TRUE WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idProducto);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("No ha sido posible marcar el producto como activo en la base de datos.");
			throw e;
		}
	}

	/**
	 * Borra un producto de la base de datos por su ID.
	 * 
	 * @param idProducto El ID del producto a borrar.
	 * @param conn       La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void borrarProductoDeBaseDeDatos(int idProducto, Connection conn) throws SQLException {
		String sql = "DELETE FROM Producto WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idProducto);
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
		this.catalogo = cargarProductos();
		for (Producto producto : this.catalogo) {
			if (producto.getId() == id) {
				return producto;
			}
		}
		return null;
	}

	/**
	 * Verifica si un producto está registrado en la base de datos por su ID.
	 * 
	 * @param id   El ID del producto.
	 * @param conn La conexión a la base de datos.
	 * @return true si el producto está registrado, false en caso contrario.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public boolean hayProductoRegistrado(int id, Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Producto WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	/**
	 * Borra un producto del catálogo y de la base de datos.
	 * 
	 * @param conn La conexión a la base de datos.
	 */
	public void borrarProducto(Connection conn) {
		try {
			System.out.println("Productos en el catálogo:");
			System.out.println(mostrarProductosActivos());
			System.out.println("Ingrese el ID del producto a borrar:");
			int idProducto = Leer.datoInt();
			if (!hayProductoRegistrado(idProducto, conn)) {
				System.out.println("El producto que se desea borrar no existe.");
			} else {
				borrarProductoDeBaseDeDatos(idProducto, conn);
				System.out.println("El producto ha sido eliminado con éxito.");
			}
		} catch (SQLException e) {
			System.out.println("Hay un error al comprobar si el producto existe en la base de datos.");
			e.printStackTrace();
		}
	}

	public void setCatalogo(List<Producto> catalogo) {
		this.catalogo = catalogo;
	}

	public List<Producto> getCatalogo() {
		return catalogo;
	}
}
