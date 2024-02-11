/**
 * 
 */
package productos;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Cine: Contiene métodos para añadir de productos-Cine y mostr
 *       datos.
 */

public class Cine extends Producto {
	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Cine(String nombre, Float precioUn, Integer cantidad, Boolean stock) {
		super(nombre, precioUn, cantidad, stock);
	}

}
