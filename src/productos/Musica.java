/**
 * 
 */
package productos;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Musica: Contiene métodos para añadir de productos-Musica y mostr
 *       datos.
 */

public class Musica extends Producto {

	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Musica(String nombre, Float precioUn, Integer cantidad, Boolean stock) {
		super(nombre, precioUn, cantidad, stock);
	}

}
