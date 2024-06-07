package data;

/**
 * Clase Musica: Contiene métodos para añadir productos de música y mostrar
 * datos.
 * 
 * @autor Timur Bogach
 * @date 11 feb 2024
 */
public class Musica extends Producto {
	private String compositor; // Autor de la canción

	/**
	 * Constructor de la clase Musica.
	 * 
	 * @param nombre      Nombre del producto.
	 * @param precioUn    Precio unitario del producto.
	 * @param cantidad    Cantidad del producto.
	 * @param stock       Indica si el producto está en stock.
	 * @param genero      Género del producto.
	 * @param idProducto  ID del producto.
	 * @param idCategoria ID de la categoría del producto.
	 * @param compositor  Compositor de la canción.
	 */
	public Musica(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			Integer idCategoria, String compositor) {
		super(nombre, precioUn, cantidad, stock, genero, idProducto, idCategoria);
		this.compositor = compositor;
	}

	/**
	 * Obtiene el compositor del producto de música.
	 * 
	 * @return El compositor.
	 */
	public String getCompositor() {
		return compositor;
	}

	/**
	 * Establece el compositor del producto de música.
	 * 
	 * @param compositor El compositor.
	 */
	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}
}
