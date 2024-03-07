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

	public void mostrarDatos() {
		System.out.println("\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock="
				+ stock + ", genero=" + genero + "]" + ", id=" + id + "]");
	}

	public String toString() {
		return "\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock=" + stock
				+ ", genero=" + genero + "]" + ", id=" + id + "]";
	}

	public Producto() {
	}

	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Producto(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero) {
		this.nombre = nombre;
		this.precioUnidad = precioUn;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.id = generarId();
	}

	public String generarId() {
		Random random = new Random();
		long randomNumber = random.nextLong() % 10000000000L; // Limita el número generado a 10 dígitos
		// Si el número es negativo, lo convertimos a positivo
		if (randomNumber < 0) {
			randomNumber = -randomNumber;
		}
		return Long.toString(randomNumber);
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the precioUn
	 */
	public Float getPrecioUnidad() {
		return precioUnidad;
	}

	/**
	 * @param precioUn the precioUn to set
	 */
	public void setPrecioUnidad(Float precioUn) {
		this.precioUnidad = precioUn;
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the genero
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * @param genero the genero to set
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * @return the stock
	 */
	public Boolean getStock() {
		return stock;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = generarId();
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

}
