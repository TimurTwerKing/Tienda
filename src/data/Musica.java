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

	public Musica(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			Integer idCategoria, String compositor) {
		super(nombre, precioUn, cantidad, stock, genero, idProducto, idCategoria);
		this.compositor = compositor;
	}

	public String getCompositor() {
		return compositor;
	}

	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}
}
