package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import logic.GestionProducto;

/**
 * Clase para gestionar la generación de tickets.
 * 
 * @autor Timur Bogach
 * @date 14 may 2024
 */
public class Tiket {

	private String numeroTiket;
	private float total;

	/**
	 * Constructor con parámetros.
	 * 
	 * @param numeroTiket El número del ticket.
	 * @param total       El total de la compra.
	 */
	public Tiket(String numeroTiket, float total) {
		this.numeroTiket = numeroTiket;
		this.total = total;
	}

	/**
	 * Constructor vacío.
	 */
	public Tiket() {
	}

	/**
	 * Guarda el ticket en la base de datos.
	 * 
	 * @param idPedido    El ID del pedido relacionado con el ticket.
	 * @param numeroTiket El número del ticket.
	 * @param total       El total de la compra.
	 * @param conn        La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void guardarTicketEnBaseDeDatos(int idPedido, String numeroTiket, double total, Connection conn)
			throws SQLException {
		String sqlTicket = "INSERT INTO Tiket (id_pedido, numero_tiket, total) VALUES (?, ?, ?)";
		try (PreparedStatement pstmtTicket = conn.prepareStatement(sqlTicket)) {
			pstmtTicket.setInt(1, idPedido);
			pstmtTicket.setString(2, numeroTiket);
			pstmtTicket.setBigDecimal(3, new java.math.BigDecimal(total));
			pstmtTicket.executeUpdate();
		}
	}

	/**
	 * Crea un ticket usando la lista de productos en la cesta.
	 *
	 * @param cesta            La lista de productos en la cesta.
	 * @param gestionProductos La instancia de GestionProducto.
	 * @return Una cadena con los detalles del ticket.
	 */
	public String crearTicket(List<Producto> cesta, GestionProducto gestionProductos) {
		this.numeroTiket = generarNumeroTiket();
		StringBuilder ticketBuilder = new StringBuilder();
		ticketBuilder.append("\n");
		ticketBuilder.append("Ticket Número: ").append(numeroTiket).append("\n");
		ticketBuilder.append("Fecha: ").append(Calendar.getInstance().getTime()).append("\n\n");
		ticketBuilder.append("Artículos:\n");

		// Mostrar los productos en la cesta
		for (Producto producto : cesta) {
			ticketBuilder.append(obtenerDetalleProducto(producto)).append("\n\n");
		}

		this.total = calcularImporteTotal(cesta);
		ticketBuilder.append("Total a pagar: ").append(total).append(" euros\n");

		return ticketBuilder.toString();
	}

	/**
	 * Obtiene los detalles de un producto en un formato legible.
	 *
	 * @param producto El producto.
	 * @return Una cadena con los detalles del producto.
	 */
	private String obtenerDetalleProducto(Producto producto) {
		StringBuilder detalle = new StringBuilder();
		detalle.append("ID: ").append(producto.getId()).append("\n");
		detalle.append("Nombre: ").append(producto.getNombre()).append("\n");
		detalle.append("Cantidad: ").append(producto.getCantidad()).append("\n");
		detalle.append("Precio Unitario: ").append(producto.getPrecio()).append(" euros\n");
		detalle.append("Total: ").append(producto.getPrecio() * producto.getCantidad()).append(" euros");
		return detalle.toString();
	}

	/**
	 * Calcula el importe total de la compra actual.
	 * 
	 * @param cesta La lista de productos en la cesta.
	 * @return El importe total de la compra.
	 */
	private float calcularImporteTotal(List<Producto> cesta) {
		float total = 0.0f;
		for (Producto producto : cesta) {
			total += producto.getPrecio() * producto.getCantidad();
		}
		return total;
	}

	/**
	 * Genera un número de ticket único.
	 *
	 * @return El número de ticket generado.
	 */
	private String generarNumeroTiket() {
		return "TKT" + System.currentTimeMillis();
	}

	// Getters y Setters con comentarios

	/**
	 * Obtiene el número del ticket.
	 * 
	 * @return El número del ticket.
	 */
	public String getNumeroTiket() {
		return numeroTiket;
	}

	/**
	 * Establece el número del ticket.
	 * 
	 * @param numeroTiket El número del ticket.
	 */
	public void setNumeroTiket(String numeroTiket) {
		if (numeroTiket == null || numeroTiket.isEmpty()) {
			throw new IllegalArgumentException("El número del ticket no puede estar vacío.");
		}
		this.numeroTiket = numeroTiket;
	}

	/**
	 * Obtiene el total del ticket.
	 * 
	 * @return El total del ticket.
	 */
	public float getTotal() {
		return total;
	}

	/**
	 * Establece el total del ticket.
	 * 
	 * @param total El total del ticket.
	 */
	public void setTotal(float total) {
		if (total < 0) {
			throw new IllegalArgumentException("El total no puede ser negativo.");
		}
		this.total = total;
	}
}
