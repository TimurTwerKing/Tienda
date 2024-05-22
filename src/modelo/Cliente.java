package modelo;

/**
 * Clase Cliente: Contiene información y métodos para gestionar clientes.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Cliente {
	private String numerocliente;
	private String nombre;
	private String apellidos;
	private String direccion;
	private String localidad;
	private String provincia;
	private String pais;
	private String codigopostal;
	private String telefono;
	private String mail;
	private String observaciones;
	private int codigo; // Código de cliente

	// Constructores
	public Cliente() {
	}

	public Cliente(String numerocliente, String nombre, String apellidos, String direccion, String localidad,
			String provincia, String pais, String codigopostal, String telefono, String mail, String observaciones) {
		this.numerocliente = numerocliente;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.direccion = direccion;
		this.localidad = localidad;
		this.provincia = provincia;
		this.pais = pais;
		this.codigopostal = codigopostal;
		this.telefono = telefono;
		this.mail = mail;
		this.observaciones = observaciones;
	}

	// Getters y Setters
	public String getNumerocliente() {
		return numerocliente;
	}

	public void setNumerocliente(String numerocliente) {
		this.numerocliente = numerocliente;
	}

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

	public String getCodigopostal() {
		return codigopostal;
	}

	public void setCodigopostal(String codigopostal) {
		this.codigopostal = codigopostal;
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	// Método para mostrar los datos del cliente
	@Override
	public String toString() {
		return "Cliente [numerocliente=" + numerocliente + ", nombre=" + nombre + ", apellidos=" + apellidos
				+ ", direccion=" + direccion + ", localidad=" + localidad + ", provincia=" + provincia + ", pais="
				+ pais + ", codigopostal=" + codigopostal + ", telefono=" + telefono + ", mail=" + mail
				+ ", observaciones=" + observaciones + "]";
	}
}