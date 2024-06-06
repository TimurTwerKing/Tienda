package data;

/**
 * Clase Videojuego: Contiene métodos para añadir productos de videojuegos y
 * mostrar datos.
 * 
 * @autor Timur Bogach
 * @date 11 feb 2024
 */
public class Videojuego extends Producto {
	private String desarrollador;

	public Videojuego(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			Integer idCategoria, String desarrollador) {
		super(nombre, precioUn, cantidad, stock, genero, idProducto, idCategoria);
		this.desarrollador = desarrollador;
	}

	public String getDesarrollaador() {
		return desarrollador;
	}

	public void setDesarrollaador(String desarrollaador) {
		this.desarrollador = desarrollaador;
	}
}
