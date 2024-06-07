package data;

/**
 * Clase Videojuego: Contiene métodos para añadir productos de videojuegos y
 * mostrar datos.
 * 
 * @autor Timur Bogach
 * @date 11 feb 2024
 */
public class Videojuego extends Producto {
	private String desarrollador;

	/**
	 * Constructor con parámetros, utilizado para crear un nuevo videojuego.
	 * 
	 * @param nombre        El nombre del videojuego.
	 * @param precioUn      El precio unitario del videojuego.
	 * @param cantidad      La cantidad disponible del videojuego.
	 * @param stock         Indica si hay stock disponible.
	 * @param genero        El género del videojuego.
	 * @param idProducto    El ID del producto.
	 * @param idCategoria   El ID de la categoría del producto.
	 * @param desarrollador El desarrollador del videojuego.
	 */
	public Videojuego(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			Integer idCategoria, String desarrollador) {
		super(nombre, precioUn, cantidad, stock, genero, idProducto, idCategoria);
		this.desarrollador = desarrollador;
	}

	/**
	 * Obtiene el desarrollador del videojuego.
	 * 
	 * @return El desarrollador del videojuego.
	 */
	public String getDesarrollador() {
		return desarrollador;
	}

	/**
	 * Establece el desarrollador del videojuego.
	 * 
	 * @param desarrollador El desarrollador del videojuego.
	 */
	public void setDesarrollador(String desarrollador) {
		if (desarrollador == null || desarrollador.isEmpty()) {
			throw new IllegalArgumentException("El nombre del desarrollador no puede estar vacío.");
		}
		this.desarrollador = desarrollador;
	}
}
