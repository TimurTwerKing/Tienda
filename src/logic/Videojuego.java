/**
 * 
 */
package logic;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Videojuego: Contiene métodos para añadir de productos-Videojuego
 *       y mostr datos
 * 
 */

public class Videojuego extends Producto {
	private String desarrollaador;

	public Videojuego() {

	}

	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Videojuego(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero,
			String desarrollaador) {
		super(nombre, precioUn, cantidad, stock, genero);
	}

	/**
	 * @return the desarrollaador
	 */
	public String getDesarrollaador() {
		return desarrollaador;
	}

	/**
	 * @param desarrollaador the desarrollaador to set
	 */
	public void setDesarrollaador(String desarrollaador) {
		this.desarrollaador = desarrollaador;
	}

}
