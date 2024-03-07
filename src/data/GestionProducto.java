package data;

import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import logic.Cine;
import logic.Musica;
import logic.Producto;
import logic.Videojuego;

public class GestionProducto {

	TreeMap<String, Producto> catalogoTreeMap = new TreeMap<>();
	private String randomNum;

	public GestionProducto() {
	}

	public GestionProducto(TreeMap<String, Producto> productos) {
		this.catalogoTreeMap = productos;
	}

	/**
	 * @return the productos
	 */
	public TreeMap<String, Producto> getProductos() {
		return catalogoTreeMap;
	}

	public void agregarProducto(TreeMap<String, Producto> productos) {
		productos.put(hacerTiket(), new Producto());
	}

	public void borrarProducto(TreeMap<String, Producto> productos, String codigo) {
		// @TODO
	}

	public String hacerTiket() {
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

	public void cargarProductos(TreeMap<String, Producto> productos) {
		Producto productoCine1 = new Cine("Rambo", 4.95f, 5, true, "Accion", "Sylvester Stallone");
		Producto productoMusica1 = new Musica("Slayer", 6.66f, 6, true, "Thrash Metal", "Kerry Fuckin King");
		Producto ProductoVideojuego1 = new Videojuego("Mafia", 9.95f, 2, true, "Shooter", "2K");

		productos.put(productoCine1.getId(), productoCine1);
		productos.put(productoMusica1.getId(), productoMusica1);
		productos.put(ProductoVideojuego1.getId(), ProductoVideojuego1);
	}

	/**
	 * @return the productosList
	 */
	public TreeMap<String, Producto> getProductosList() {
		return catalogoTreeMap;
	}

	/**
	 * @param productosList the productosList to set
	 */
	public void setProductosList(TreeMap<String, Producto> productosList) {
		this.catalogoTreeMap = productosList;
	}

	/**
	 * @return the randomNum
	 */
	public String getRandomNum() {
		return randomNum;
	}

}
