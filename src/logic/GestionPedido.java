package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import data.Producto;
import data.Tiket;
import menu.Menu;
import modelo.Cliente;
import util.Fichero;
import util.Leer;

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
	 * 
	 * @param conn La conexión a la base de datos.
	 */
	public GestionPedido(Connection conn) {
		this.cesta = new ArrayList<>();
		this.gestionCliente = new GestionCliente(conn);
	}

	/**
	 * Vende los artículos de la cesta y actualiza la base de datos.
	 * 
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public static void venderArticulos(GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket, Connection conn)
			throws SQLException {

		// Guardar el pedido en la base de datos y obtener el ID del pedido
		int idPedido = gestionPedido.guardarPedidoEnBaseDeDatos(cliente, gestionPedido.getCesta(), conn);

		// Guardar el detalle del pedido en la base de datos
		gestionPedido.guardarDetallePedido(idPedido, gestionPedido.getCesta(), conn);

		// Guardar el ticket en la base de datos
		tiket.guardarTicketEnBaseDeDatos(idPedido, tiket.getNumeroTiket(), tiket.getTotal(), conn);

		// Actualizar los productos en la base de datos
		for (Producto productoEnCesta : gestionPedido.getCesta()) {
			gestionProductos.actualizarProductoEnBaseDeDatos(conn, productoEnCesta);
		}

		// Recargar productos para actualizar el catálogo en memoria
		gestionProductos.cargarProductos();

		// Limpiar la cesta después de la venta
		gestionPedido.getCesta().clear();
	}

	/**
	 * Realiza un pedido agregando productos a la cesta y manejando el pago.
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param gestionPago      La instancia de GestionPago.
	 * @param cliente          El cliente que realiza el pedido.
	 * @param fichero          La instancia de Fichero para guardar el ticket.
	 * @param tiket            La instancia de Tiket para generar el ticket.
	 * @param conn             La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void generarPedido(GestionProducto gestionProductos, GestionPago gestionPago, Cliente cliente,
			Fichero fichero, Tiket tiket, Connection conn) throws SQLException {
		boolean continuarCompra = true;
		while (continuarCompra) {
			System.out.println("Escriba ID del producto: ");
			int productoID = Leer.datoInt();
			System.out.println("Escriba la cantidad del producto seleccionado: ");
			int cantidadProducto = Leer.datoInt();

			agregarCesta(gestionProductos, productoID, cantidadProducto);
			Menu.seguirComprando_Pagar();
			int opcionPagar = Leer.datoInt();
			if (opcionPagar == 1) { // PAGAR
				gestionarPagoPedido(gestionProductos, gestionPago, cliente, fichero, tiket, conn);
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
	 * @param tiket            La instancia de Tiket para generar el ticket.
	 * @param conn             La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void gestionarPagoPedido(GestionProducto gestionProductos, GestionPago gestionPago, Cliente cliente,
			Fichero fichero, Tiket tiket, Connection conn) throws SQLException {
		String ticket = tiket.crearTicket(this.cesta, gestionProductos);
		System.out.println(ticket);
		gestionPago.metodoDePago(cliente);

		GestionPedido.venderArticulos(gestionProductos, this, gestionPago, cliente, fichero, tiket, conn);

		Menu.deseaTiket();
		int opcionTiket = Leer.datoInt();
		if (opcionTiket == 1) {
			fichero.escribirFichero(ticket);
		}
		Menu.Mensaje_Fin_Compra();
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
		// Verificar que el cliente exista en la base de datos por ID
		if (!gestionCliente.verificarClienteExisteID(cliente.getId(), conn)) {
			throw new SQLException("El cliente con ID " + cliente.getId() + " no existe.");
		}

		String sqlPedido = "INSERT INTO Pedido (codigo_cliente) VALUES (?)";
		try (PreparedStatement pstmtPedido = conn.prepareStatement(sqlPedido, Statement.RETURN_GENERATED_KEYS)) {
			// Establecer el ID del cliente en la consulta
			pstmtPedido.setInt(1, cliente.getId());
			// Ejecutar la inserción del pedido
			pstmtPedido.executeUpdate();

			// Obtener las claves generadas (ID del pedido)
			try (ResultSet generatedKeys = pstmtPedido.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					// Obtener el ID del pedido generado
					int idPedido = generatedKeys.getInt(1);
					// Guardar los detalles del pedido
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
		String sqlDetallePedido = "INSERT INTO Detalle_Pedido (orden_de_pedido, codigo_producto, cantidad) VALUES (?, ?, ?)";
		try (PreparedStatement pstmtDetalle = conn.prepareStatement(sqlDetallePedido)) {
			for (Producto producto : productos) {
				if (verificarProductoExiste(producto.getId(), conn)) {
					pstmtDetalle.setInt(1, idPedido);
					pstmtDetalle.setInt(2, producto.getId());
					pstmtDetalle.setInt(3, producto.getCantidad());
					pstmtDetalle.addBatch();
				} else {
					System.out.println("El producto con ID " + producto.getId() + " no existe en la base de datos.");
				}
			}
			pstmtDetalle.executeBatch();
		} catch (SQLException e) {
			System.out.println("Error al guardar los detalles del pedido: " + e.getMessage());
			throw e;
		}
	}

	/**
	 * Verifica si un producto existe en la base de datos por su ID.
	 * 
	 * @param idProducto El ID del producto.
	 * @param conn       La conexión a la base de datos.
	 * @return true si el producto existe, false en caso contrario.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private boolean verificarProductoExiste(int idProducto, Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Producto WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idProducto);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

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
				boolean productoEnCesta = false;
				for (Producto p : this.cesta) {
					if (p.getId() == producto.getId()) {
						p.setCantidad(p.getCantidad() + cantidad);
						productoEnCesta = true;
						break;
					}
				}
				if (!productoEnCesta) {
					this.cesta.add(crearProductoParaCesta(producto, cantidad));
				}
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
		Iterator<Producto> iterator = this.cesta.iterator();
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
		return new Producto(producto.getId(), producto.getNombre(), producto.getPrecio(), cantidad, producto.getStock(),
				producto.getGenero(), producto.getIdCategoria(), producto.getIdAlbaran(), producto.isActivo());
	}

	/**
	 * Muestra los productos en la cesta de compra.
	 * 
	 * @return Una cadena con los detalles de los productos en la cesta.
	 */
	public String mostrarProductosCesta() {
		StringBuilder resultado = new StringBuilder();
		float precioTotal = 0;

		for (Producto producto : this.cesta) {
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
		return producto.getPrecio() * producto.getCantidad();
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
				+ producto.getCantidad() + "\n" + "Precio Unitario: " + producto.getPrecio() + " euros.\n"
				+ "Precio Total: " + precioTotalProducto + " euros.";
	}

	/**
	 * Calcula el importe total de la compra actual.
	 * 
	 * @return El importe total de la compra.
	 */
	public double mostrarImporteTotal() {
		float total = 0.0f;
		for (Producto producto : this.cesta) {
			total += producto.getPrecio() * producto.getCantidad();
		}
		return total;
	}

	/**
	 * Obtiene la lista de productos en la cesta.
	 * 
	 * @return La lista de productos en la cesta.
	 */
	public List<Producto> getCesta() {
		return this.cesta;
	}

	/**
	 * Verifica si la cesta está vacía.
	 * 
	 * @return true si la cesta está vacía, false en caso contrario.
	 */
	public boolean cestaVacia() {
		return this.cesta.isEmpty();
	}
}
