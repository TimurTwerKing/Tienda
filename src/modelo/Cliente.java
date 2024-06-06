package modelo;

/**
 * Clase Cliente: Contiene información y métodos para gestionar clientes.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Cliente {
	private String nombre;
	private String apellidos;
	private String direccion;
	private String localidad;
	private String provincia;
	private String pais;
	private String codigoPostal;
	private String telefono;
	private String mail;
	private String observaciones;
	private boolean activo;
	private int id; // ID CLIENTE AUTO INCREMENTADO

	// Constructor vacío
	public Cliente() {
	}

	// Constructor sin ID, utilizado para crear nuevos clientes
	public Cliente(String nombre, String apellidos, String direccion, String localidad, String provincia, String pais,
			String codigoPostal, String telefono, String mail, String observaciones) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.pais = pais;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.mail = mail;
		this.observaciones = observaciones;
		this.activo = true; // Por defecto, un nuevo cliente está activo
	}

	// Constructor con ID, utilizado para cargar clientes desde la base de datos
	public Cliente(int id, String nombre, String apellidos, String direccion, String localidad, String provincia,
			String pais, String codigoPostal, String telefono, String mail, String observaciones, boolean activo) {
		this.id = id;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.pais = pais;
		this.codigoPostal = codigoPostal;
		this.telefono = telefono;
		this.mail = mail;
		this.observaciones = observaciones;
		this.activo = activo;
	}

	// Getters y Setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	// Método para mostrar los datos del cliente
	@Override
	public String toString() {
		return "Cliente [nombre=" + nombre + ", apellidos=" + apellidos + ", direccion=" + direccion + ", localidad="
				+ localidad + ", provincia=" + provincia + ", pais=" + pais + ", codigoPostal=" + codigoPostal
				+ ", telefono=" + telefono + ", mail=" + mail + ", observaciones=" + observaciones + ", activo="
				+ activo + ", id=" + id + "]";
	}
}
