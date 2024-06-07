package data;

/**
 * Clase Cine: Contiene métodos para añadir productos de cine y mostrar datos.
 * 
 * @autor Timur Bogach
 * @date 11 feb 2024
 */
public class Cine extends Producto {
	private String actor;

	/**
	 * Constructor de la clase Cine.
	 * 
	 * @param nombre      Nombre del producto.
	 * @param precioUn    Precio unitario del producto.
	 * @param cantidad    Cantidad del producto.
	 * @param stock       Indica si el producto está en stock.
	 * @param genero      Género del producto.
	 * @param idProducto  ID del producto.
	 * @param actor       Actor principal del producto de cine.
	 * @param idCategoria ID de la categoría del producto.
	 */
	public Cine(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			String actor, Integer idCategoria) {
		super(nombre, precioUn, cantidad, stock, genero, idProducto, idCategoria);
		this.actor = actor;
	}

	/**
	 * Obtiene el actor principal del producto de cine.
	 * 
	 * @return El actor principal.
	 */
	public String getActor() {
		return actor;
	}

	/**
	 * Establece el actor principal del producto de cine.
	 * 
	 * @param actor El actor principal.
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}
}
