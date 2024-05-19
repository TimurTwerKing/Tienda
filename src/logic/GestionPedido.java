package logic;

import java.sql.SQLException;
import java.util.ArrayList;
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
	private GestionProducto gestionProducto;

	/**
	 * Constructor de la clase.
	 * 
	 * @param gestionProducto La instancia de GestionProducto.
	 */
	public GestionPedido(GestionProducto gestionProducto) {
		this.cesta = new ArrayList<>();
		this.gestionProducto = gestionProducto;
	}

	/**
	 * Realiza un pedido agregando productos a la cesta y manejando el pago.
	 * 
	 * @param gestionPago La instancia de GestionPago.
	 * @param cliente     El cliente que realiza el pedido.
	 * @param fichero     La instancia de Fichero para guardar el ticket.
	 * @param sc          El objeto Scanner para leer la entrada del usuario.
	 * @param tiket       La instancia de Tiket para generar el ticket.
	 */
	public void realizarPedido(GestionPago gestionPago, Cliente cliente, Fichero fichero, Scanner sc, Tiket tiket) {
		boolean pagar = false;
		while (!pagar) {
			System.out.println("Escriba ID del producto: ");
			int productoID = sc.nextInt();
			System.out.println("Escriba la cantidad del producto seleccionado: ");
			int cantidadProducto = sc.nextInt();
			agregarCesta(productoID, cantidadProducto);
			Menu.seguirComprando_Pagar();
			int opcionPagar = sc.nextInt();
			if (opcionPagar == 1) {
				gestionPago.metodoDePago(cliente, sc);
				pagar = true;
			} else if (opcionPagar == 2) {
				pagar = false;
			} else if (opcionPagar == 3) {
				cesta.clear();
				System.out.println("Compra cancelada.");
				return;
			}
		}

		try {
			gestionPago.venderArticulos();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Generar y mostrar el ticket después de la venta
		String ticket = tiket.crearTicket(this);
		System.out.println(ticket);

		Menu.deseaTiket();
		String opcionTiket = sc.next();
		if (opcionTiket.equalsIgnoreCase("si")) {
			fichero.escribirFichero(ticket);
		}
	}

	/**
	 * Agrega un producto a la cesta de compra.
	 * 
	 * @param productoId El ID del producto a agregar.
	 * @param cantidad   La cantidad del producto a agregar.
	 */
	public void agregarCesta(int productoId, int cantidad) {
		Producto producto = gestionProducto.buscarProductoPorId(productoId);
		if (producto != null) {
			if (gestionProducto.haySuficienteStock(producto, cantidad)) {
				cesta.add(crearProductoParaCesta(producto, cantidad));
			} else {
				System.out.println("No hay suficiente stock para el producto: " + producto.getNombre());
			}
		} else {
			System.out.println("Producto no encontrado en el catálogo.");
		}
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
