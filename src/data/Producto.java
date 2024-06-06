package data;

/**
 * Clase Producto: Contiene información y métodos para gestionar productos.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Producto {
	private int id; // ID del producto auto-incrementado
	private String nombre;
	private float precio;
	private int cantidad;
	private boolean stock;
	private String genero;
	private int idCategoria;
	private int idAlbaran;
	private boolean activo;

	// Constructor vacío
	public Producto() {
	}

	// Constructor sin ID, utilizado para crear nuevos productos
	public Producto(String nombre, float precio, int cantidad, boolean stock, String genero, int idCategoria,
			int idAlbaran) {
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.idCategoria = idCategoria;
		this.idAlbaran = idAlbaran;
		this.activo = true; // Por defecto, un nuevo producto está activo
	}

	// Constructor con ID, utilizado para cargar productos desde la base de datos
	public Producto(int id, String nombre, float precio, int cantidad, boolean stock, String genero, int idCategoria,
			int idAlbaran, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.idCategoria = idCategoria;
		this.idAlbaran = idAlbaran;
		this.activo = activo;
	}

	// Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

	public boolean getStock() {
		return stock;
	}

	public void setStock(boolean stock) {
		this.stock = stock;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getIdAlbaran() {
		return idAlbaran;
	}

	public void setIdAlbaran(int idAlbaran) {
		this.idAlbaran = idAlbaran;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

}
