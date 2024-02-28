/**
 * 
 */
package logic;

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
//	public static int dimesionArray;

	public void mostrarDatos() {
		System.out.println("\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock="
				+ stock + ", genero=" + genero + "]");
	}

	public String toString() {
		return "\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock=" + stock
				+ ", genero=" + genero + "]";
	}

	public Producto() {
//		dimesionArray++;
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
//		dimesionArray++;
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
