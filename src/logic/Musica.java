/**
 * 
 */
package logic;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Musica: Contiene métodos para añadir de productos-Musica y mostr
 *       datos.
 */

public class Musica extends Producto {
	private String compositor;// Autor de la canción

	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Musica(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, String compositor) {
		super(nombre, precioUn, cantidad, stock, genero);
		this.setCompositor(compositor);
	}

	/**
	 * @return the compositor
	 */
	public String getCompositor() {
		return compositor;
	}

	/**
	 * @param compositor the compositor to set
	 */
	public void setCompositor(String compositor) {
		this.compositor = compositor;
	}

}
