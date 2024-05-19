package logic;

import java.util.ArrayList;
import java.util.List;

import data.Producto;

/**
 * @autor Timur Bogach
 * @date 19 may 2024
 * @param Clase para gestionar los pedidos y la cesta de compra.
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
	 * Agrega un producto a la cesta de compra.
	 * 
	 * @param productoId El ID del producto a agregar.
	 * @param cantidad   La cantidad del producto a agregar.
	 */
	public void agregarCesta(int productoId, int cantidad) {
		Producto producto = gestionProducto.buscarProductoPorId(productoId);
		if (producto != null) {
			if (haySuficienteStock(producto, cantidad)) {
				cesta.add(crearProductoParaCesta(producto, cantidad));
			} else {
				System.out.println("No hay suficiente stock para el producto: " + producto.getNombre());
			}
		} else {
			System.out.println("Producto no encontrado en el catálogo.");
		}
	}

	/**
	 * Verifica si hay suficiente stock de un producto.
	 * 
	 * @param producto El producto a verificar.
	 * @param cantidad La cantidad solicitada.
	 * @return true si hay suficiente stock, false en caso contrario.
	 */
	private boolean haySuficienteStock(Producto producto, int cantidad) {
		return producto.getCantidad() >= cantidad;
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
			float precioTotalProducto = calcularPrecioTotalProducto(producto);
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
	private float calcularPrecioTotalProducto(Producto producto) {
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
		return cesta.stream().mapToDouble(producto -> producto.getPrecioUnidad() * producto.getCantidad()).sum();
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