package data;

import java.util.Calendar;

import logic.GestionPedido;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * @param Clase para gestionar la generación de tickets.
 */
public class Tiket {

	/**
	 * Crea un ticket usando la lista de productos en la cesta.
	 *
	 * @param gestionPedido La instancia de GestionPedido.
	 * @return Una cadena con los detalles del ticket.
	 */
	public String crearTicket(GestionPedido gestionPedido) {
		String numeroTicket = generarNumeroTicket();
		StringBuilder ticketBuilder = new StringBuilder();
		ticketBuilder.append("Ticket Número: ").append(numeroTicket).append("\n");
		ticketBuilder.append("Fecha: ").append(Calendar.getInstance().getTime()).append("\n\n");
		ticketBuilder.append("Artículos:\n");

		ticketBuilder.append(gestionPedido.mostrarProductosCesta());

		double total = gestionPedido.mostrarImporteTotal();
		ticketBuilder.append("Total a pagar: ").append(total).append("\n");

		return ticketBuilder.toString();
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
