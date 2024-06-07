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

	/**
	 * Constructor vacío de la clase Producto.
	 */
	public Producto() {
	}

	/**
	 * Constructor sin ID, utilizado para crear nuevos productos.
	 * 
	 * @param nombre      El nombre del producto.
	 * @param precio      El precio del producto.
	 * @param cantidad    La cantidad del producto.
	 * @param stock       Si el producto está en stock.
	 * @param genero      El género del producto.
	 * @param idCategoria La ID de la categoría del producto.
	 * @param idAlbaran   La ID del albarán del producto.
	 */
	public Producto(String nombre, float precio, int cantidad, boolean stock, String genero, int idCategoria,
			int idAlbaran) {
		if (nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacío.");
		}
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo.");
		}
		if (cantidad < 0) {
			throw new IllegalArgumentException("La cantidad no puede ser negativa.");
		}

		this.nombre = nombre;
		this.precio = precio;
		this.cantidad = cantidad;
		this.stock = stock;
		this.genero = genero;
		this.idCategoria = idCategoria;
		this.idAlbaran = idAlbaran;
		this.activo = true; // Por defecto, un nuevo producto está activo
	}

	/**
	 * Constructor con ID, utilizado para cargar productos desde la base de datos.
	 * 
	 * @param id          El ID del producto.
	 * @param nombre      El nombre del producto.
	 * @param precio      El precio del producto.
	 * @param cantidad    La cantidad del producto.
	 * @param stock       Si el producto está en stock.
	 * @param genero      El género del producto.
	 * @param idCategoria La ID de la categoría del producto.
	 * @param idAlbaran   La ID del albarán del producto.
	 * @param activo      Si el producto está activo.
	 */
	public Producto(int id, String nombre, float precio, int cantidad, boolean stock, String genero, int idCategoria,
			int idAlbaran, boolean activo) {
		if (id <= 0) {
			throw new IllegalArgumentException("El ID debe ser positivo.");
		}
		if (nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacío.");
		}
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo.");
		}
		if (cantidad < 0) {
			throw new IllegalArgumentException("La cantidad no puede ser negativa.");
		}

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

	// Getters y Setters con comentarios

	/**
	 * Obtiene el ID del producto.
	 * 
	 * @return El ID del producto.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del producto.
	 * 
	 * @param id El ID del producto.
	 */
	public void setId(int id) {
		if (id <= 0) {
			throw new IllegalArgumentException("El ID debe ser positivo.");
		}
		this.id = id;
	}

	/**
	 * Obtiene el nombre del producto.
	 * 
	 * @return El nombre del producto.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del producto.
	 * 
	 * @param nombre El nombre del producto.
	 */
	public void setNombre(String nombre) {
		if (nombre == null || nombre.isEmpty()) {
			throw new IllegalArgumentException("El nombre no puede estar vacío.");
		}
		this.nombre = nombre;
	}

	/**
	 * Obtiene el precio del producto.
	 * 
	 * @return El precio del producto.
	 */
	public float getPrecio() {
		return precio;
	}

	/**
	 * Establece el precio del producto.
	 * 
	 * @param precio El precio del producto.
	 */
	public void setPrecio(float precio) {
		if (precio < 0) {
			throw new IllegalArgumentException("El precio no puede ser negativo.");
		}
		this.precio = precio;
	}

	/**
	 * Obtiene la cantidad del producto.
	 * 
	 * @return La cantidad del producto.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad del producto.
	 * 
	 * @param cantidad La cantidad del producto.
	 */
	public void setCantidad(int cantidad) {
		if (cantidad < 0) {
			throw new IllegalArgumentException("La cantidad no puede ser negativa.");
		}
		this.cantidad = cantidad;
	}

	/**
	 * Obtiene si el producto está en stock.
	 * 
	 * @return true si el producto está en stock, false en caso contrario.
	 */
	public boolean getStock() {
		return stock;
	}

	/**
	 * Establece si el producto está en stock.
	 * 
	 * @param stock true si el producto está en stock, false en caso contrario.
	 */
	public void setStock(boolean stock) {
		this.stock = stock;
	}

	/**
	 * Obtiene el género del producto.
	 * 
	 * @return El género del producto.
	 */
	public String getGenero() {
		return genero;
	}

	/**
	 * Establece el género del producto.
	 * 
	 * @param genero El género del producto.
	 */
	public void setGenero(String genero) {
		this.genero = genero;
	}

	/**
	 * Obtiene la ID de la categoría del producto.
	 * 
	 * @return La ID de la categoría del producto.
	 */
	public int getIdCategoria() {
		return idCategoria;
	}

	/**
	 * Establece la ID de la categoría del producto.
	 * 
	 * @param idCategoria La ID de la categoría del producto.
	 */
	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

	/**
	 * Obtiene la ID del albarán del producto.
	 * 
	 * @return La ID del albarán del producto.
	 */
	public int getIdAlbaran() {
		return idAlbaran;
	}

	/**
	 * Establece la ID del albarán del producto.
	 * 
	 * @param idAlbaran La ID del albarán del producto.
	 */
	public void setIdAlbaran(int idAlbaran) {
		this.idAlbaran = idAlbaran;
	}

	/**
	 * Obtiene si el producto está activo.
	 * 
	 * @return true si el producto está activo, false en caso contrario.
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Establece si el producto está activo.
	 * 
	 * @param activo true si el producto está activo, false en caso contrario.
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
}
