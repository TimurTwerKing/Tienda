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

	public Cine(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, String actor) {
		super(nombre, precioUn, cantidad, stock, genero);
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

}
