package modelo;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
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

	// Constructor
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCif() {
		return cif;
	}

	public void setCif(String cif) {
		this.cif = cif;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getNombreContacto() {
		return nombreContacto;
	}

	public void setNombreContacto(String nombreContacto) {
		this.nombreContacto = nombreContacto;
	}

	public String getCargoContacto() {
		return cargoContacto;
	}

	public void setCargoContacto(String cargoContacto) {
		this.cargoContacto = cargoContacto;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCodigoContable() {
		return codigoContable;
	}

	public void setCodigoContable(String codigoContable) {
		this.codigoContable = codigoContable;
	}

	public String getMonedaPorDefecto() {
		return monedaPorDefecto;
	}

	public void setMonedaPorDefecto(String monedaPorDefecto) {
		this.monedaPorDefecto = monedaPorDefecto;
	}

	public String getTipoIVA() {
		return tipoIVA;
	}

	public void setTipoIVA(String tipoIVA) {
		this.tipoIVA = tipoIVA;
	}

	public String getTipoDePago() {
		return tipoDePago;
	}

	public void setTipoDePago(String tipoDePago) {
		this.tipoDePago = tipoDePago;
	}

	public int getPlazoDePago() {
		return plazoDePago;
	}

	public void setPlazoDePago(int plazoDePago) {
		this.plazoDePago = plazoDePago;
	}

	public String getNumeroDeCuenta() {
		return numeroDeCuenta;
	}

	public void setNumeroDeCuenta(String numeroDeCuenta) {
		this.numeroDeCuenta = numeroDeCuenta;
	}

}
