/**
 * 
 */
package productos;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Producto: Contiene métodos para la gestion de productos.
 */

public class Producto {
	private String nombre;
	private Float precioUn;
	private Integer cantidad;
	private Boolean stock;

	public void mostrarDatos() {
		System.out.println(
				"\t[nombre=" + nombre + ", precioUn=" + precioUn + ", cantidad=" + cantidad + ", stock=" + stock + "]");
	}

	public String toString() {
		return "\t[nombre=" + nombre + ", precioUn=" + precioUn + ", cantidad=" + cantidad + ", stock=" + stock + "]";
	}

	/**
	 * @param nombre
	 * @param precioUn
	 * @param cantidad
	 * @param stock
	 */
	public Producto(String nombre, Float precioUn, Integer cantidad, Boolean stock) {
		this.nombre = nombre;
		this.precioUn = precioUn;
		this.cantidad = cantidad;
		this.stock = stock;
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
	public Float getPrecioUn() {
		return precioUn;
	}

	/**
	 * @param precioUn the precioUn to set
	 */
	public void setPrecioUn(Float precioUn) {
		this.precioUn = precioUn;
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
	 * @return the stock
	 */
	public Boolean getStock() {
		return stock;
	}

	/**
	 * @param stock the stock to set
	 */
	public void setStock(Boolean stock) {
		this.stock = stock;
	}

}
