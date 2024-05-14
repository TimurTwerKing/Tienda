package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mySQL_DB.Conexion;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
 */
public class Cliente {

	private int codigo;
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

	// Constructor, getters y setters
	public Cliente() {
	}

	// Constructor con parámetros
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

	// Método para obtener un cliente por nombre
	public static Cliente getClienteByNombre(String nombre) {
		Cliente cliente = null;
		String sql = "SELECT * FROM Cliente WHERE nombre = 'Jhoonny'";

		try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, nombre);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				cliente = new Cliente();
				cliente.setCodigo(rs.getInt("codigo"));
				cliente.setNumerocliente(rs.getString("numerocliente"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellidos(rs.getString("apellidos"));
				cliente.setDireccion(rs.getString("direccion"));
				cliente.setLocalidad(rs.getString("localidad"));
				cliente.setProvincia(rs.getString("provincia"));
				cliente.setPais(rs.getString("pais"));
				cliente.setCodigopostal(rs.getString("codigopostal"));
				cliente.setTelefono(rs.getString("telefono"));
				cliente.setMail(rs.getString("mail"));
				cliente.setObservaciones(rs.getString("observaciones"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return cliente;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

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

}
