/**
 * 
 */
package productos;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Videojuego: Contiene métodos para añadir de productos-Videojuego
 *       y mostr datos
 * 
 */

public class Videojuego extends Producto {

	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Videojuego(String nombre, Float precioUn, Integer cantidad, Boolean stock) {
		super(nombre, precioUn, cantidad, stock);
	}

}
