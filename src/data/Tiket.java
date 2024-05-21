package data;

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

	/**
	 * Crea un ticket usando la lista de productos en la cesta.
	 *
	 * @param cesta            La lista de productos en la cesta.
	 * @param gestionProductos La instancia de GestionProducto.
	 * @return Una cadena con los detalles del ticket.
	 */
	public String crearTicket(List<Producto> cesta, GestionProducto gestionProductos) {
		String numeroTicket = generarNumeroTicket();
		StringBuilder ticketBuilder = new StringBuilder();
		ticketBuilder.append("\n");
		ticketBuilder.append("Ticket Número: ").append(numeroTicket).append("\n");
		ticketBuilder.append("Fecha: ").append(Calendar.getInstance().getTime()).append("\n\n");
		ticketBuilder.append("Artículos:\n");

		// Mostrar los productos en la cesta
		for (Producto producto : cesta) {
			ticketBuilder.append(obtenerDetalleProducto(producto)).append("\n\n");
		}

		double total = calcularImporteTotal(cesta);
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
		detalle.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros\n");
		detalle.append("Total: ").append(producto.getPrecioUnidad() * producto.getCantidad()).append(" euros");
		return detalle.toString();
	}

	/**
	 * Calcula el importe total de la compra actual.
	 * 
	 * @param cesta La lista de productos en la cesta.
	 * @return El importe total de la compra.
	 */
	private double calcularImporteTotal(List<Producto> cesta) {
		double total = 0.0;
		for (Producto producto : cesta) {
			total += producto.getPrecioUnidad() * producto.getCantidad();
		}
		return total;
	}

	/**
	 * Genera un número de ticket único.
	 *
	 * @return El número de ticket generado.
	 */
	private String generarNumeroTicket() {
		return "TKT" + System.currentTimeMillis();
	}
}