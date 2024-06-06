package data;

/**
 * Clase Cine: Contiene métodos para añadir productos de cine y mostrar datos.
 * 
 * @autor Timur Bogach
 * @date 11 feb 2024
 */
public class Cine extends Producto {
	private String actor;

	public Cine(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			String actor, Integer idCategoria) {
		super(nombre, precioUn, cantidad, stock, genero, idProducto, idCategoria);
		this.actor = actor;
	}

	public String getActor() {
		return actor;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}
}
