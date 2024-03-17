/**
 * 
 */
package logic;

import java.util.Random;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Producto: Contiene métodos para la gestion de productos.
 */

public class Producto {
	private String nombre;
	private Float precioUnidad;
	private Integer cantidad;
	private Boolean stock;
	private String genero;
	private String id;

//CONSTRUCTORES
	public Producto(Producto productoCatalogo) {
	}

	public Producto() {
	}

	public Producto(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero) {
		this.nombre = nombre;
		this.precioUnidad = precioUn;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.id = generarId();
	}
//METODOS

	public void mostrarDatos() {
		System.out.println("\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock="
				+ stock + ", genero=" + genero + "]" + ", id=" + id + "]");
	}

	public String toString() {
		return "\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock=" + stock
				+ ", genero=" + genero + "]" + ", id=" + id + "]";
	}

	private String generarId() {
		Random random = new Random();
		long randomNumber = random.nextLong() % 10000000000L; // Limita el número generado a 10 dígitos
		// Si el número es negativo, lo convertimos a positivo
		if (randomNumber < 0) {
			randomNumber = -randomNumber;
		}
		return Long.toString(randomNumber);
	}

	public void consumir(int cant) {
		this.cantidad -= cant;
	}

	/**
	 * Nos devuelve si hay stock de un producto o no
	 * 
	 * @return
	 */
	public boolean hayStock() {
		if (getCantidad() > 0) {
			this.stock = true;
		}
		return this.stock;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Float getPrecioUnidad() {
		return precioUnidad;
	}

	public void setPrecioUnidad(Float precioUn) {
		this.precioUnidad = precioUn;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public Boolean getStock() {
		return stock;
	}

	public String getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = generarId();
	}

}
