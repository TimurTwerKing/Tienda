package user;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
 */
public class Cliente {

	private int id;
	private String nombre;
	private String email;
	private String direccion;
	private String telefono;

	// Constructor vacío
	public Cliente() {
	}

	// Constructor con parámetros
	public Cliente(int id, String nombre, String email, String direccion, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.email = email;
		this.direccion = direccion;
		this.telefono = telefono;
	}

	public static Cliente getCliente(Connection conn) throws SQLException {
		String sql = "SELECT * FROM Cliente where nombre='Jhonny Meentero'";
		Cliente clientes = new Cliente();
		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

			clientes = (new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"),
					rs.getString("direccion"), rs.getString("telefono")));
		}
		return clientes;
	}

	// Getters y setters
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	// Método para guardar el cliente en la base de datos
	public boolean save(Connection conn) throws SQLException {
		String sql;
		if (this.id > 0) {
			// Actualizar cliente existente
			sql = "UPDATE Cliente SET nombre = ?, email = ?, direccion = ?, telefono = ? WHERE id = ?";
		} else {
			// Insertar nuevo cliente
			sql = "INSERT INTO Cliente (nombre, email, direccion, telefono) VALUES (?, ?, ?, ?)";
		}
		try (PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, this.nombre);
			stmt.setString(2, this.email);
			stmt.setString(3, this.direccion);
			stmt.setString(4, this.telefono);
			if (this.id > 0) {
				stmt.setInt(5, this.id);
			}
			int affectedRows = stmt.executeUpdate();
			if (affectedRows > 0 && this.id == 0) {
				try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						this.id = generatedKeys.getInt(1);
					}
				}
			}
			return affectedRows > 0;
		}
	}

	// Método para eliminar el cliente de la base de datos
	public boolean delete(Connection conn) throws SQLException {
		if (this.id > 0) {
			String sql = "DELETE FROM Cliente WHERE id = ?";
			try (PreparedStatement stmt = conn.prepareStatement(sql)) {
				stmt.setInt(1, this.id);
				return stmt.executeUpdate() > 0;
			}
		}
		return false;
	}

	// Método estático para obtener un cliente por su ID
	public static Cliente getById(Connection conn, int id) throws SQLException {
		String sql = "SELECT * FROM Cliente WHERE id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, id);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"),
							rs.getString("direccion"), rs.getString("telefono"));
				}
			}
		}
		return null;
	}

	// Método estático para obtener todos los clientes
	public static List<Cliente> getAll(Connection conn) throws SQLException {
		String sql = "SELECT * FROM Cliente";
		List<Cliente> clientes = new ArrayList<>();
		try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				clientes.add(new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"),
						rs.getString("direccion"), rs.getString("telefono")));
			}
		}
		return clientes;
	}
}
