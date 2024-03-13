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

	public Videojuego(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero,
			String desarrollaador) {
		super(nombre, precioUn, cantidad, stock, genero);
	}

	public String getDesarrollaador() {
		return desarrollaador;
	}

	public void setDesarrollaador(String desarrollaador) {
		this.desarrollaador = desarrollaador;
	}

}
