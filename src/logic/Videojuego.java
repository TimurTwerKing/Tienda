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
	private String desarrollador;

	public Videojuego(Producto productoCatalogo) {
		super(productoCatalogo);
	}

	public Videojuego(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero,
			String desarrollaador) {
		super(nombre, precioUn, cantidad, stock, genero);
	}

	public Videojuego(Producto productoCatalogo, String desarrollador) {
		super(productoCatalogo);
		this.desarrollador = desarrollador;
	}

	public String getDesarrollaador() {
		return desarrollador;
	}

	public void setDesarrollaador(String desarrollaador) {
		this.desarrollador = desarrollaador;
	}

}
