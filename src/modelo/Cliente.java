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

	/**
	 * Constructor vacío para la clase Cliente.
	 */
	public Cliente() {
	}

	/**
	 * Constructor sin ID, utilizado para crear nuevos clientes.
	 * 
	 * @param nombre        El nombre del cliente.
	 * @param apellidos     Los apellidos del cliente.
	 * @param direccion     La dirección del cliente.
	 * @param localidad     La localidad del cliente.
	 * @param provincia     La provincia del cliente.
	 * @param pais          El país del cliente.
	 * @param codigoPostal  El código postal del cliente.
	 * @param telefono      El teléfono del cliente.
	 * @param mail          El correo electrónico del cliente.
	 * @param observaciones Las observaciones sobre el cliente.
	 */
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

	/**
	 * Constructor con ID, utilizado para cargar clientes desde la base de datos.
	 * 
	 * @param id            El ID del cliente.
	 * @param nombre        El nombre del cliente.
	 * @param apellidos     Los apellidos del cliente.
	 * @param direccion     La dirección del cliente.
	 * @param localidad     La localidad del cliente.
	 * @param provincia     La provincia del cliente.
	 * @param pais          El país del cliente.
	 * @param codigoPostal  El código postal del cliente.
	 * @param telefono      El teléfono del cliente.
	 * @param mail          El correo electrónico del cliente.
	 * @param observaciones Las observaciones sobre el cliente.
	 * @param activo        El estado de actividad del cliente.
	 */
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

	/**
	 * Obtiene el nombre del cliente.
	 * 
	 * @return El nombre del cliente.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del cliente.
	 * 
	 * @param nombre El nombre del cliente.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene los apellidos del cliente.
	 * 
	 * @return Los apellidos del cliente.
	 */
	public String getApellidos() {
		return apellidos;
	}

	/**
	 * Establece los apellidos del cliente.
	 * 
	 * @param apellidos Los apellidos del cliente.
	 */
	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	/**
	 * Obtiene la dirección del cliente.
	 * 
	 * @return La dirección del cliente.
	 */
	public String getDireccion() {
		return direccion;
	}

	/**
	 * Establece la dirección del cliente.
	 * 
	 * @param direccion La dirección del cliente.
	 */
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	 * Obtiene la localidad del cliente.
	 * 
	 * @return La localidad del cliente.
	 */
	public String getLocalidad() {
		return localidad;
	}

	/**
	 * Establece la localidad del cliente.
	 * 
	 * @param localidad La localidad del cliente.
	 */
	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	/**
	 * Obtiene la provincia del cliente.
	 * 
	 * @return La provincia del cliente.
	 */
	public String getProvincia() {
		return provincia;
	}

	/**
	 * Establece la provincia del cliente.
	 * 
	 * @param provincia La provincia del cliente.
	 */
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	/**
	 * Obtiene el país del cliente.
	 * 
	 * @return El país del cliente.
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Establece el país del cliente.
	 * 
	 * @param pais El país del cliente.
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Obtiene el código postal del cliente.
	 * 
	 * @return El código postal del cliente.
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Establece el código postal del cliente.
	 * 
	 * @param codigoPostal El código postal del cliente.
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * Obtiene el teléfono del cliente.
	 * 
	 * @return El teléfono del cliente.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el teléfono del cliente.
	 * 
	 * @param telefono El teléfono del cliente.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el correo electrónico del cliente.
	 * 
	 * @return El correo electrónico del cliente.
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Establece el correo electrónico del cliente.
	 * 
	 * @param mail El correo electrónico del cliente.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Obtiene las observaciones sobre el cliente.
	 * 
	 * @return Las observaciones sobre el cliente.
	 */
	public String getObservaciones() {
		return observaciones;
	}

	/**
	 * Establece las observaciones sobre el cliente.
	 * 
	 * @param observaciones Las observaciones sobre el cliente.
	 */
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * Verifica si el cliente está activo.
	 * 
	 * @return true si el cliente está activo, false en caso contrario.
	 */
	public boolean isActivo() {
		return activo;
	}

	/**
	 * Establece el estado de actividad del cliente.
	 * 
	 * @param activo El estado de actividad del cliente.
	 */
	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	/**
	 * Obtiene el ID del cliente.
	 * 
	 * @return El ID del cliente.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del cliente.
	 * 
	 * @param id El ID del cliente.
	 */
	public void setId(int id) {
		this.id = id;
	}
}
