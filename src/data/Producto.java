package data;

/**
 * Clase Producto: Contiene métodos para la gestión de productos.
 * 
 * @autor Timur Bogach
 * @date 11 feb 2024
 */
public class Producto {
	private String nombre;
	private Float precioUnidad;
	private Integer cantidad;
	private Boolean stock;
	private String genero;
	private Integer idProducto;
	private Integer idCategoria;
	private boolean activo; // Nuevo atributo

	// Constructores
	public Producto(Producto productoCatalogo) {
	}

	public Producto() {
	}

	public Producto(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			Integer idCategoria, boolean activo) {
		this.nombre = nombre;
		this.precioUnidad = precioUn;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.idProducto = idProducto;
		this.idCategoria = idCategoria;
		this.activo = activo;
	}
	public Producto(String nombre, Float precioUn, Integer cantidad, Boolean stock, String genero, Integer idProducto,
			Integer idCategoria) {
		this.nombre = nombre;
		this.precioUnidad = precioUn;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.idProducto = idProducto;
		this.idCategoria = idCategoria;
	}

	// Métodos
	public void mostrarDatos() {
		System.out.println("\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock="
				+ stock + ", genero=" + genero + "]" + ", id=" + idProducto + "]");
	}

	public String toString() {
		return "\t[nombre=" + nombre + ", precioUn=" + precioUnidad + ", cantidad=" + cantidad + ", stock=" + stock
				+ ", genero=" + genero + "]" + ", id=" + idProducto + "]";
	}

	public boolean hayStock() {
		if (getCantidad() > 0) {
			this.stock = true;
		}
		return this.stock;
	}

	// Getters y Setters
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

	public Integer getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public void setStock(Boolean stock) {
		this.stock = stock;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
