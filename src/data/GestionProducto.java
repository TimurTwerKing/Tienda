package data;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import logic.Producto;

public class GestionProducto {

	private TreeMap<String, Producto> productosList = null;
	private String randomNum;

	public GestionProducto() {
	}

	public GestionProducto(TreeMap<String, Producto> productos) {
		this.productosList = productos;
	}

	/**
	 * @return the productos
	 */
	public TreeMap<String, Producto> getProductos() {
		return productosList;
	}

	public void agregarProducto(TreeMap<String, Producto> productos) {
		productos.put(calcularTiket(), new Producto());

	}

	public void borrarProducto(TreeMap<String, Producto> productos, String codigo) {
		
	}
	
	public String calcularTiket() {
		Random random = new Random();
		long randomNumber = random.nextLong() % 10000000000L; // Limita el número generado a 10 dígitos
		// Si el número es negativo, lo convertimos a positivo
		if (randomNumber < 0) {
			randomNumber = -randomNumber;
		}
		return Long.toString(randomNumber);
	}

	public void mostrarTiket(TreeMap<String, Producto> productos) {
		for (Map.Entry<String, Producto> entry : productos.entrySet()) {
			Producto producto = entry.getValue();
			System.out.println("tiket: " + producto.getNombre());
		}
	}

	public void mostrarProductos(TreeMap<String, Producto> productos) {
		for (Map.Entry<String, Producto> entry : productos.entrySet()) {
			Producto producto = entry.getValue();
			System.out.println("Nombre: " + producto.getNombre());
			System.out.println("Disponible: " + producto.getCantidad());
			System.out.println("Precio Unitario: " + producto.getPrecioUnidad() + " euros.\n");
		}
	}

	/**
	 * @return the productosList
	 */
	public TreeMap<String, Producto> getProductosList() {
		return productosList;
	}

	/**
	 * @param productosList the productosList to set
	 */
	public void setProductosList(TreeMap<String, Producto> productosList) {
		this.productosList = productosList;
	}

	/**
	 * @return the randomNum
	 */
	public String getRandomNum() {
		return randomNum;
	}

	

}
