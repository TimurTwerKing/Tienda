/**
 * 
 */
package logic;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Cine: Contiene métodos para añadir de productos-Cine y mostr
 *       datos.
 */

public class Cine extends Producto {
	private String actor;

	public Cine() {

	}

	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Cine(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, String actor) {
		super(nombre, precioUn, cantidad, stock, genero);
	}

	/**
	 * @return the actor
	 */
	public String getActor() {
		return actor;
	}

	/**
	 * @param actor the actor to set
	 */
	public void setActor(String actor) {
		this.actor = actor;
	}

}
