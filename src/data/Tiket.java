package data;

import java.util.Calendar;
import java.util.List;

import logic.Producto;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
 */
public class Tiket {

	// Método para crear un ticket usando la lista de cesta
	public String crearTicket(List<Producto> cesta) {
		String numeroTicket = generarNumeroTicket();
		StringBuilder ticketBuilder = new StringBuilder();
		ticketBuilder.append("Ticket Número: ").append(numeroTicket).append("\n");
		ticketBuilder.append("Fecha: ").append(Calendar.getInstance().getTime()).append("\n\n");
		ticketBuilder.append("Artículos:\n");

		GestionProducto gestionProducto = new GestionProducto(); // Instanciar la clase GestionProducto
		ticketBuilder.append(gestionProducto.mostrarProductosCesta()); // Llamar al método mostrarProductosCesta

		float total = 0;

		for (Producto producto : cesta) {
			int cantidad = contarCantidadEnCesta(cesta, producto);
			float precioTotalProducto = producto.getPrecioUnidad() * cantidad;
			total += precioTotalProducto;

			ticketBuilder.append("Producto: ").append(producto.getNombre()).append("\n").append("Cantidad: ")
					.append(cantidad).append("\n").append("Precio Unitario: ").append(producto.getPrecioUnidad())
					.append("\n").append("Precio Total: ").append(precioTotalProducto).append("\n\n");
		}

		ticketBuilder.append("Total a pagar: ").append(total).append("\n");

		return ticketBuilder.toString();
	}

	// Método para contar la cantidad de un producto en la cesta
	private int contarCantidadEnCesta(List<Producto> cesta, Producto producto) {
		int cantidad = 0;
		for (Producto p : cesta) {
			if (p.equals(producto)) {
				cantidad++;
			}
		}
		return cantidad;
	}

	// Método para generar un número de ticket único
	private String generarNumeroTicket() {
		return "TKT" + System.currentTimeMillis();
	}

//	// Método para crear un ticket
//	public String crearTiket(TreeMap<Integer, Producto> catalogo, TreeMap<Integer,Producto>cesta ) {
//		String numeroTicket = generarNumeroTicket();
//		StringBuilder ticketBuilder = new StringBuilder();
//		ticketBuilder.append("Ticket Número: ").append(numeroTicket).append("\n");
//		ticketBuilder.append("Fecha: ").append(Calendar.getInstance().getTime()).append("\n\n");
//		ticketBuilder.append("Artículos:\n");
//
//		float total = 0;
//
//		for (Map.Entry<Integer, Producto> entry : cesta.entrySet()) {
//			int productoId = entry.getKey();
//			Producto cantidad = catalogo.get(cantidad);
//			Producto producto = catalogo.get(productoId);
//			Producto precioUni = catalogo.get(precioUnidad);
//
//			float precioTotalProducto = catalogo.get(precioUnidad) * cantidad;
//			total += precioTotalProducto;
//
//			ticketBuilder.append("Producto: ").append(producto.getNombre()).append("\n").append("Cantidad: ")
//					.append(cantidad).append("\n").append("Precio Unitario: ").append(producto.getPrecioUnidad())
//					.append("\n").append("Precio Total: ").append(precioTotalProducto).append("\n\n");
//		}
//
//		ticketBuilder.append("Total a pagar: ").append(total).append("\n");
//
//		return ticketBuilder.toString();
//	}
//
//	// Método para generar un número de ticket único
//	private String generarNumeroTicket() {
//		return "TKT" + System.currentTimeMillis();
//	}
}
