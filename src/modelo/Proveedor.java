package modelo;

/**
 * Clase Proveedor: Contiene información y métodos para gestionar proveedores.
 * 
 * @autor Timur Bogach
 * @date 14 may 2024
 */
public class Proveedor {
	private int codigo;
	private String nombre;
	private String cif;
	private String codigoPostal;
	private String pais;
	private String nombreContacto;
	private String cargoContacto;
	private String mail;
	private String telefono;
	private String codigoContable;
	private String monedaPorDefecto;
	private String tipoIVA;
	private String tipoDePago;
	private int plazoDePago;
	private String numeroDeCuenta;

	/**
	 * Constructor de la clase Proveedor.
	 * 
	 * @param codigo           El código del proveedor.
	 * @param nombre           El nombre del proveedor.
	 * @param cif              El CIF del proveedor.
	 * @param codigoPostal     El código postal del proveedor.
	 * @param pais             El país del proveedor.
	 * @param nombreContacto   El nombre del contacto del proveedor.
	 * @param cargoContacto    El cargo del contacto del proveedor.
	 * @param mail             El correo electrónico del proveedor.
	 * @param telefono         El teléfono del proveedor.
	 * @param codigoContable   El código contable del proveedor.
	 * @param monedaPorDefecto La moneda por defecto del proveedor.
	 * @param tipoIVA          El tipo de IVA del proveedor.
	 * @param tipoDePago       El tipo de pago del proveedor.
	 * @param plazoDePago      El plazo de pago del proveedor.
	 * @param numeroDeCuenta   El número de cuenta del proveedor.
	 */
	public Proveedor(int codigo, String nombre, String cif, String codigoPostal, String pais, String nombreContacto,
			String cargoContacto, String mail, String telefono, String codigoContable, String monedaPorDefecto,
			String tipoIVA, String tipoDePago, int plazoDePago, String numeroDeCuenta) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.cif = cif;
		this.codigoPostal = codigoPostal;
		this.pais = pais;
		this.nombreContacto = nombreContacto;
		this.cargoContacto = cargoContacto;
		this.mail = mail;
		this.telefono = telefono;
		this.codigoContable = codigoContable;
		this.monedaPorDefecto = monedaPorDefecto;
		this.tipoIVA = tipoIVA;
		this.tipoDePago = tipoDePago;
		this.plazoDePago = plazoDePago;
		this.numeroDeCuenta = numeroDeCuenta;
	}

	// Getters y Setters

	/**
	 * Obtiene el código del proveedor.
	 * 
	 * @return El código del proveedor.
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Establece el código del proveedor.
	 * 
	 * @param codigo El código del proveedor.
	 */
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	/**
	 * Obtiene el nombre del proveedor.
	 * 
	 * @return El nombre del proveedor.
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Establece el nombre del proveedor.
	 * 
	 * @param nombre El nombre del proveedor.
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Obtiene el CIF del proveedor.
	 * 
	 * @return El CIF del proveedor.
	 */
	public String getCif() {
		return cif;
	}

	/**
	 * Establece el CIF del proveedor.
	 * 
	 * @param cif El CIF del proveedor.
	 */
	public void setCif(String cif) {
		this.cif = cif;
	}

	/**
	 * Obtiene el código postal del proveedor.
	 * 
	 * @return El código postal del proveedor.
	 */
	public String getCodigoPostal() {
		return codigoPostal;
	}

	/**
	 * Establece el código postal del proveedor.
	 * 
	 * @param codigoPostal El código postal del proveedor.
	 */
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	/**
	 * Obtiene el país del proveedor.
	 * 
	 * @return El país del proveedor.
	 */
	public String getPais() {
		return pais;
	}

	/**
	 * Establece el país del proveedor.
	 * 
	 * @param pais El país del proveedor.
	 */
	public void setPais(String pais) {
		this.pais = pais;
	}

	/**
	 * Obtiene el nombre del contacto del proveedor.
	 * 
	 * @return El nombre del contacto del proveedor.
	 */
	public String getNombreContacto() {
		return nombreContacto;
	}

	/**
	 * Establece el nombre del contacto del proveedor.
	 * 
	 * @param nombreContacto El nombre del contacto del proveedor.
	 */
	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	/**
	 * Obtiene el cargo del contacto del proveedor.
	 * 
	 * @return El cargo del contacto del proveedor.
	 */
	public String getCargoContacto() {
		return cargoContacto;
	}

	/**
	 * Establece el cargo del contacto del proveedor.
	 * 
	 * @param cargoContacto El cargo del contacto del proveedor.
	 */
	public void setCargoContacto(String cargoContacto) {
		this.cargoContacto = cargoContacto;
	}

	/**
	 * Obtiene el correo electrónico del proveedor.
	 * 
	 * @return El correo electrónico del proveedor.
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * Establece el correo electrónico del proveedor.
	 * 
	 * @param mail El correo electrónico del proveedor.
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * Obtiene el teléfono del proveedor.
	 * 
	 * @return El teléfono del proveedor.
	 */
	public String getTelefono() {
		return telefono;
	}

	/**
	 * Establece el teléfono del proveedor.
	 * 
	 * @param telefono El teléfono del proveedor.
	 */
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	/**
	 * Obtiene el código contable del proveedor.
	 * 
	 * @return El código contable del proveedor.
	 */
	public String getCodigoContable() {
		return codigoContable;
	}

	/**
	 * Establece el código contable del proveedor.
	 * 
	 * @param codigoContable El código contable del proveedor.
	 */
	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}

	/**
	 * Obtiene la moneda por defecto del proveedor.
	 * 
	 * @return La moneda por defecto del proveedor.
	 */
	public String getMonedaPorDefecto() {
		return monedaPorDefecto;
	}

	/**
	 * Establece la moneda por defecto del proveedor.
	 * 
	 * @param monedaPorDefecto La moneda por defecto del proveedor.
	 */
	public void setMonedaPorDefecto(String monedaPorDefecto) {
		this.monedaPorDefecto = monedaPorDefecto;
	}

	/**
	 * Obtiene el tipo de IVA del proveedor.
	 * 
	 * @return El tipo de IVA del proveedor.
	 */
	public String getTipoIVA() {
		return tipoIVA;
	}

	/**
	 * Establece el tipo de IVA del proveedor.
	 * 
	 * @param tipoIVA El tipo de IVA del proveedor.
	 */
	public void setTipoIVA(String tipoIVA) {
		this.tipoIVA = tipoIVA;
	}

	/**
	 * Obtiene el tipo de pago del proveedor.
	 * 
	 * @return El tipo de pago del proveedor.
	 */
	public String getTipoDePago() {
		return tipoDePago;
	}

	/**
	 * Establece el tipo de pago del proveedor.
	 * 
	 * @param tipoDePago El tipo de pago del proveedor.
	 */
	public void setTipoDePago(String tipoDePago) {
		this.tipoDePago = tipoDePago;
	}

	/**
	 * Obtiene el plazo de pago del proveedor.
	 * 
	 * @return El plazo de pago del proveedor.
	 */
	public int getPlazoDePago() {
		return plazoDePago;
	}

	/**
	 * Establece el plazo de pago del proveedor.
	 * 
	 * @param plazoDePago El plazo de pago del proveedor.
	 */
	public void setPlazoDePago(int plazoDePago) {
		this.plazoDePago = plazoDePago;
	}

	/**
	 * Obtiene el número de cuenta del proveedor.
	 * 
	 * @return El número de cuenta del proveedor.
	 */
	public String getNumeroDeCuenta() {
		return numeroDeCuenta;
	}

	/**
	 * Establece el número de cuenta del proveedor.
	 * 
	 * @param numeroDeCuenta El número de cuenta del proveedor.
	 */
	public void setNumeroDeCuenta(String numeroDeCuenta) {
		this.numeroDeCuenta = numeroDeCuenta;
	}
}
