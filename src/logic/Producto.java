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
	private Integer idProducto;
	private Integer idCategoria;

//CONSTRUCTORES
	public Producto(Producto productoCatalogo) {
	}

	public Producto() {
	}

	public Producto(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			Integer idCategoria) {
		this.nombre = nombre;
		this.precioUnidad = precioUn;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.idProducto = idProducto;
		this.idCategoria=idCategoria;
	}
//METODOS

	public void mostrarDatos() {
		System.out.println("\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock="
				+ stock + ", genero=" + genero + "]" + ", id=" + idProducto + "]");
	}

	public String toString() {
		return "\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock=" + stock
				+ ", genero=" + genero + "]" + ", id=" + idProducto + "]";
	}

//	private String generarId() {
//		Random random = new Random();
//		long randomNumber = random.nextLong() % 10000000000L; // Limita el número generado a 10 dígitos
//		// Si el número es negativo, lo convertimos a positivo
//		if (randomNumber < 0) {
//			randomNumber = -randomNumber;
//		}
//		return Long.toString(randomNumber);
//	}

//	public void consumir(int cant) {
//		this.cantidad -= cant;
//	}

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

	public Integer getId() {
		return idProducto;
	}

	public void setId(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getIdProducto() {
		return idProducto;
	}

	public void setIdProducto(Integer idProducto) {
		this.idProducto = idProducto;
	}

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setStock(Boolean stock) {
		this.stock = stock;
	}

}
