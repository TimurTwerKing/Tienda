package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import data.Producto;
import data.Tiket;
import menu.Menu;
import modelo.Cliente;
import util.Fichero;

/**
 * Clase para gestionar los pedidos y la cesta de compra.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class GestionPedido {

	private List<Producto> cesta;
	private GestionCliente gestionCliente;

	/**
	 * Constructor de la clase.
	 */
	public GestionPedido(Connection conn) {
		this.cesta = new ArrayList<>();
		this.gestionCliente = new GestionCliente(conn);
	}

	/**
	 * Guarda el pedido en la base de datos.
	 * 
	 * @param cliente   El cliente que realiza el pedido.
	 * @param productos La lista de productos en el pedido.
	 * @param conn      La conexión a la base de datos.
	 * @return El ID del pedido generado.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public int guardarPedidoEnBaseDeDatos(Cliente cliente, List<Producto> productos, Connection conn)
			throws SQLException {
		if (!gestionCliente.verificarClienteExiste(cliente.getId(), conn)) {
			throw new SQLException("El cliente con ID " + cliente.getId() + " no existe.");
		}

		String sqlPedido = "INSERT INTO Pedido (codigo_cliente) VALUES (?)";
		try (PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
			pstmtPedido.setInt(1, cliente.getId());
			pstmtPedido.executeUpdate();

			try (ResultSet generatedKeys = pstmtPedido.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					int idPedido = generatedKeys.getInt(1);
					guardarDetallePedido(idPedido, productos, conn);
					return idPedido;
				} else {
					throw new SQLException("No se pudo obtener el ID del pedido.");
				}
			}
		}
	}

	/**
	 * Guarda los detalles del pedido en la base de datos.
	 * 
	 * @param idPedido  El ID del pedido.
	 * @param productos La lista de productos en el pedido.
	 * @param conn      La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void guardarDetallePedido(int idPedido, List<Producto> productos, Connection conn) throws SQLException {
		String sqlDetalle = "INSERT INTO Detalle_Pedido (orden_de_pedido, codigo_producto, cantidad) VALUES (?, ?, ?)";
		try (PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle)) {
			for (Producto producto : productos) {
				pstmtDetalle.setInt(1, idPedido);
				pstmtDetalle.setInt(2, producto.getId());
				pstmtDetalle.setInt(3, producto.getCantidad());
				pstmtDetalle.addBatch();
			}
			pstmtDetalle.executeBatch();
		}
	}

	/**
	 * Vende los artículos de la cesta y actualiza la base de datos.
	 * 
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void venderArticulos(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket, Connection conn)
			throws SQLException {

		try {
			int idPedido = gestionPedido.guardarPedidoEnBaseDeDatos(cliente, gestionPedido.getCesta(), conn);
			gestionPedido.guardarDetallePedido(idPedido, cesta, conn);
			tiket.guardarTicketEnBaseDeDatos(idPedido, tiket.getNumeroTiket(), tiket.getTotal(), conn);

			gestionPedido.getCesta().clear();
		} finally {
			if (conn != null) {
				conn.close(); // Cerrar la conexión porque hemos terminado
			}
		}
	}

	/**
	 * Realiza un pedido agregando productos a la cesta y manejando el pago.
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param gestionPago      La instancia de GestionPago.
	 * @param cliente          El cliente que realiza el pedido.
	 * @param fichero          La instancia de Fichero para guardar el ticket.
	 * @param sc               El objeto Scanner para leer la entrada del usuario.
	 * @param tiket            La instancia de Tiket para generar el ticket.
	 * @throws SQLException
	 */
	public void generarPedido(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket, Connection conn)
			throws SQLException {
		boolean continuarCompra = true;
		while (continuarCompra) {
			System.out.println("Escriba ID del producto: ");
			int productoID = sc.nextInt();
			System.out.println("Escriba la cantidad del producto seleccionado: ");
			int cantidadProducto = sc.nextInt();

			agregarCesta(gestionProductos, productoID, cantidadProducto);
			Menu.seguirComprando_Pagar();
			int opcionPagar = sc.nextInt();
			if (opcionPagar == 1) { // PAGAR
				realizarPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
				// Volver al menú de compra
				continuarCompra = false;
			} else if (opcionPagar == 2) { // SEGUIR COMPRANDO
				continuarCompra = true;
			} else if (opcionPagar == 3) { // ATRAS
				continuarCompra = false;
			}
		}
	}

	/**
	 * Realiza la compra del pedido realizado previamente y guarda datos en BD
	 * (pedido y tiket).
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param gestionPago      La instancia de GestionPago.
	 * @param cliente          El cliente que realiza el pedido.
	 * @param fichero          La instancia de Fichero para guardar el ticket.
	 * @param sc               El objeto Scanner para leer la entrada del usuario.
	 * @param tiket            La instancia de Tiket para generar el ticket.
	 * @throws SQLException
	 */
	public void realizarPedido(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket, Connection conn)
			throws SQLException {
		String ticket = tiket.crearTicket(cesta, gestionProductos);
		System.out.println(ticket);
		gestionPago.metodoDePago(cliente, sc);

		gestionPedido.venderArticulos(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
//			guardarPedidoEnBaseDeDatos(cliente.getCodigo(), cesta, conn);
//			guardarTiketEnBaseDeDatos(tiket, cliente.getCodigo(), conn);

		Menu.deseaTiket();
		int opcionTiket = sc.nextInt();
		if (opcionTiket == 1) {
			fichero.escribirFichero(ticket); 
		}
		Menu.Mensaje_Fin_Compra();
		// Menú para usuarios
		//GestionMenu.menuUsuarioLogueado(sc, gestionProductos, gestionPedido, gestionPago, cliente,
		//		fichero, tiket, conn);
	}

//	/**
//	 * Guarda el pedido en la base de datos.
//	 * 
//	 * @param cliente   El cliente que realiza el pedido.
//	 * @param productos La lista de productos en el pedido.
//	 * @param conn      La conexión a la base de datos.
//	 * @return El ID del pedido generado.
//	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
//	 */
//	public int guardarPedidoEnBaseDeDatos(Cliente cliente, List<Producto> productos, Connection conn)
//			throws SQLException {
//		String sqlPedido = "INSERT INTO Pedido (codigo_cliente) VALUES (?)";
//		try (PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
//			pstmtPedido.setInt(1, cliente.getId());
//			pstmtPedido.executeUpdate();
//
//			try (ResultSet generatedKeys = pstmtPedido.getGeneratedKeys()) {
//				if (generatedKeys.next()) {
//					int idPedido = generatedKeys.getInt(1);
//					guardarDetallePedido(idPedido, productos, conn);
//					return idPedido;
//				} else {
//					throw new SQLException("No se pudo obtener el ID del pedido.");
//				}
//			}
//		}
//	}

//	public void guardarPedidoEnBaseDeDatos(int idCliente, List<Producto> cesta, Connection conn) throws SQLException {
//		if (!verificarClienteExiste(idCliente, conn)) {
//			throw new SQLException("El cliente con ID " + idCliente + " no existe.");
//		}
//
//		String sqlPedido = "INSERT INTO Pedido (codigo_cliente) VALUES (?)";
//		String sqlDetalle = "INSERT INTO Detalle_Pedido (orden_de_pedido, codigo_producto, cantidad) VALUES (?, ?, ?)";
//
//		try (PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, PreparedStatement.RETURN_GENERATED_KEYS);
//				PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetalle)) {
//
//			// Insertar pedido
//			pstmtPedido.setInt(1, idCliente);
//			pstmtPedido.executeUpdate();
//
//			// Obtener el ID del pedido generado
//			int idPedido;
//			try (var rs = pstmtPedido.getGeneratedKeys()) {
//				if (rs.next()) {
//					idPedido = rs.getInt(1);
//				} else {
//					throw new SQLException("Error al obtener el ID del pedido.");
//				}
//			}
//
//			// Insertar detalles del pedido
//			for (Producto producto : cesta) {
//				pstmtDetalle.setInt(1, idPedido);
//				pstmtDetalle.setInt(2, producto.getId());
//				pstmtDetalle.setInt(3, producto.getCantidad());
//				pstmtDetalle.addBatch();
//			}
//			pstmtDetalle.executeBatch();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}

//	private void guardarTiketEnBaseDeDatos(Tiket tiket, int idPedido, Connection conn) throws SQLException {
//		String sql = "INSERT INTO Tiket (id_pedido, numero_tiket, total) VALUES (?, ?, ?)";
//
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setInt(1, idPedido);
//			pstmt.setString(2, tiket.getNumeroTiket());
//			pstmt.setFloat(3, tiket.getTotal());
//			pstmt.executeUpdate();
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw e;
//		}
//	}
//
//	private boolean verificarClienteExiste(int idCliente, Connection conn) throws SQLException {
//		String sql = "SELECT COUNT(*) FROM Cliente WHERE idCliente = ?";
//		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
//			pstmt.setInt(1, idCliente);
//			try (ResultSet rs = pstmt.executeQuery()) {
//				if (rs.next()) {
//					return rs.getInt(1) > 0;
//				}
//			}
//		}
//		return false;
//	}

	/**
	 * Agrega un producto a la cesta de compra.
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param productoId       El ID del producto a agregar.
	 * @param cantidad         La cantidad del producto a agregar.
	 */
	public void agregarCesta(GestionProducto gestionProductos, int productoId, int cantidad) {
		Producto producto = gestionProductos.buscarProductoPorIdCatalogo(productoId);
		if (producto != null) {
			if (gestionProductos.haySuficienteStock(producto, cantidad)) {
				cesta.add(crearProductoParaCesta(producto, cantidad));
			} else {
				System.out.println("No hay suficiente stock para el producto: " + producto.getNombre());
			}
		} else {
			System.out.println("Producto no encontrado en el catálogo.");
		}
	}

	/**
	 * Elimina un producto de la cesta por su ID.
	 * 
	 * @param id El ID del producto a eliminar.
	 */
	public void borrarProductoPorIdCesta(int id) {
		// Utilizamos un Iterator para evitar ConcurrentModificationException
		Iterator<Producto> iterator = cesta.iterator();
		while (iterator.hasNext()) {
			Producto producto = iterator.next();
			if (producto.getId() == id) {
				iterator.remove();
				System.out.println("Producto eliminado de la cesta.");
				return; // Salimos del método después de eliminar el producto
			}
		}
		System.out.println("Producto no encontrado en la cesta.");
	}

	/**
	 * Crea un objeto Producto para la cesta de compra.
	 * 
	 * @param producto El producto original.
	 * @param cantidad La cantidad del producto a agregar a la cesta.
	 * @return Un objeto Producto.
	 */
	private Producto crearProductoParaCesta(Producto producto, int cantidad) {
		return new Producto(producto.getNombre(), producto.getPrecioUnidad(), cantidad, producto.hayStock(),
				producto.getGenero(), producto.getId(), producto.getIdCategoria());
	}

	public boolean cestaVacia() {
		return cesta.isEmpty();
	}

	/**
	 * Muestra los productos en la cesta de compra.
	 * 
	 * @return Una cadena con los detalles de los productos en la cesta.
	 */
	public String mostrarProductosCesta() {
		StringBuilder resultado = new StringBuilder();
		float precioTotal = 0;

		for (Producto producto : cesta) {
			float precioTotalProducto = calcularPrecioTotalProductoCesta(producto);
			precioTotal += precioTotalProducto;
			resultado.append(obtenerDetalleProductoEnCesta(producto, precioTotalProducto)).append("\n\n");
		}

		resultado.append("Precio Total de la Cesta: ").append(precioTotal).append(" euros.\n");
		return resultado.toString();
	}

	/**
	 * Calcula el precio total de un producto en la cesta.
	 * 
	 * @param producto El producto.
	 * @return El precio total del producto.
	 */
	private float calcularPrecioTotalProductoCesta(Producto producto) {
		return producto.getPrecioUnidad() * producto.getCantidad();
	}

	/**
	 * Obtiene los detalles de un producto en la cesta.
	 * 
	 * @param producto            El producto.
	 * @param precioTotalProducto El precio total del producto.
	 * @return Una cadena con los detalles del producto.
	 */
	private String obtenerDetalleProductoEnCesta(Producto producto, float precioTotalProducto) {
		return "ID: " + producto.getId() + "\n" + "Nombre: " + producto.getNombre() + "\n" + "Cantidad en cesta: "
				+ producto.getCantidad() + "\n" + "Precio Unitario: " + producto.getPrecioUnidad() + " euros.\n"
				+ "Precio Total: " + precioTotalProducto + " euros.";
	}

	/**
	 * Calcula el importe total de la compra actual.
	 * 
	 * @return El importe total de la compra.
	 */
	public double mostrarImporteTotal() {
		float total = 0.0f;
		for (Producto producto : cesta) {
			total += producto.getPrecioUnidad() * producto.getCantidad();
		}
		return total;
	}

	/**
	 * Obtiene la lista de productos en la cesta.
	 * 
	 * @return La lista de productos en la cesta.
	 */
	public List<Producto> getCesta() {
		return cesta;
	}
}