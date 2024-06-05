package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Cliente;
import util.Leer;

/**
 * Clase para gestionar los clientes.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class GestionCliente {

	private List<Cliente> clientes;

	/**
	 * Constructor de la clase. Inicializa la lista de clientes.
	 */
	public GestionCliente(Connection conn) {
		try {
			this.clientes = cargarClientes(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método para agregar un cliente a la base de datos.
	 * 
	 * @param cliente El cliente a agregar.
	 * @param conn    La conexión a la base de datos.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void agregarCliente(Cliente cliente, Connection conn) throws SQLException {
		// Verificar si el cliente ya existe por su correo electrónico
		if (!verificarClienteExisteMail(cliente.getMail(), conn)) {

			String sql = "INSERT INTO Cliente (numero_cliente, nombre, apellidos, direccion, localidad, provincia, pais, codigo_postal, telefono, mail, observaciones) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, cliente.getNumeroCliente());
				pstmt.setString(2, cliente.getNombre());
				pstmt.setString(3, cliente.getApellidos());
				pstmt.setString(4, cliente.getDireccion());
				pstmt.setString(5, cliente.getLocalidad());
				pstmt.setString(6, cliente.getProvincia());
				pstmt.setString(7, cliente.getPais());
				pstmt.setString(8, cliente.getCodigoPostal());
				pstmt.setString(9, cliente.getTelefono());
				pstmt.setString(10, cliente.getMail());
				pstmt.setString(11, cliente.getObservaciones());

				pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						cliente.setId(generatedKeys.getInt(1)); // Set the generated ID to the cliente object
					} else {
						throw new SQLException("No se pudo obtener el ID del cliente.");
					}
				}
			}
		} else {
			throw new SQLException("El cliente con correo " + cliente.getMail() + " ya existe.");
		}
	}

	/**
	 * Método para verificar si un cliente existe por su correo electrónico.
	 * 
	 * @param mail El correo electrónico del cliente.
	 * @param conn La conexión a la base de datos.
	 * @return true si el cliente existe, false en caso contrario.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public boolean verificarClienteExisteMail(String mail, Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Cliente WHERE mail = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, mail);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	/**
	 * Método para verificar si un cliente existe por su ID.
	 * 
	 * @param idCliente El ID del cliente.
	 * @param conn      La conexión a la base de datos.
	 * @return true si el cliente existe, false en caso contrario.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public boolean verificarClienteExisteID(int idCliente, Connection conn) throws SQLException {
		String sql = "SELECT COUNT(*) FROM Cliente WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idCliente);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			}
		}
		return false;
	}

	/**
	 * Método para obtener una lista de clientes de la base de datos.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return Lista de clientes.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public List<Cliente> cargarClientes(Connection conn) throws SQLException {
		List<Cliente> clientes = new ArrayList<>();
		String sql = "SELECT id, numero_cliente, nombre, apellidos, direccion, localidad, provincia, pais, codigo_postal, telefono, mail, observaciones FROM Cliente";

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNumeroCliente(rs.getString("numero_cliente"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellidos(rs.getString("apellidos"));
				cliente.setDireccion(rs.getString("direccion"));
				cliente.setLocalidad(rs.getString("localidad"));
				cliente.setProvincia(rs.getString("provincia"));
				cliente.setPais(rs.getString("pais"));
				cliente.setCodigoPostal(rs.getString("codigo_postal"));
				cliente.setTelefono(rs.getString("telefono"));
				cliente.setMail(rs.getString("mail"));
				cliente.setObservaciones(rs.getString("observaciones"));
				clientes.add(cliente);
			}
		}
		return clientes;
	}

	/**
	 * Método para elegir un cliente del menú.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return El cliente elegido.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public Cliente elegirCliente(Connection conn) throws SQLException {
		List<Cliente> clientes = cargarClientes(conn);

		System.out.println("Seleccione un cliente:");
		for (int i = 0; i < clientes.size(); i++) {
			Cliente cliente = clientes.get(i);
			System.out.println((i + 1) + ". " + cliente.getNumeroCliente() + " - " + cliente.getNombre() + " "
					+ cliente.getApellidos());
		}

		int opcion = Leer.datoInt();

		if (opcion > 0 && opcion <= clientes.size()) {
			return clientes.get(opcion - 1);
		} else {
			System.out.println("Opción no válida.");
			return null;
		}
	}

	/**
	 * Método para crear un cliente nuevo.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return El cliente creado.
	 */
	public Cliente crearCliente(Connection conn) {
		try {
			System.out.println("Ingrese el número de cliente:");
			String numerocliente = Leer.datoString();
			System.out.println("Ingrese el nombre:");
			String nombre = Leer.datoString();
			System.out.println("Ingrese los apellidos:");
			String apellidos = Leer.datoString();
			System.out.println("Ingrese la dirección:");
			String direccion = Leer.datoString();
			System.out.println("Ingrese la localidad:");
			String localidad = Leer.datoString();
			System.out.println("Ingrese la provincia:");
			String provincia = Leer.datoString();
			System.out.println("Ingrese el país:");
			String pais = Leer.datoString();
			System.out.println("Ingrese el código postal:");
			String codigopostal = Leer.datoString();
			System.out.println("Ingrese el teléfono:");
			String telefono = Leer.datoString();
			System.out.println("Ingrese el correo electrónico:");
			String mail = Leer.datoString();
			System.out.println("Ingrese las observaciones:");
			String observaciones = Leer.datoString();

			// Verificar si el cliente ya existe por su correo electrónico
			if (verificarClienteExisteMail(mail, conn)) {
				System.out.println("El cliente con correo " + mail + " ya existe.");
				return null;
			}

			Cliente cliente = new Cliente(numerocliente, nombre, apellidos, direccion, localidad, provincia, pais,
					codigopostal, telefono, mail, observaciones);
			return cliente;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al agregar el cliente.");
			return null;
		}
	}

	/**
	 * Borra un cliente de la lista por su ID.
	 * 
	 * @param clienteId El ID del cliente a borrar.
	 */
	public void borrarCliente(int clienteId) {
		Cliente cliente = seleccionarClientePorId(clienteId);
		if (cliente != null) {
			clientes.remove(cliente);
			System.out.println("Cliente eliminado exitosamente.");
		} else {
			System.out.println("Cliente no encontrado.");
		}
	}

	/**
	 * Busca un cliente por su ID.
	 * 
	 * @param clienteId El ID del cliente a buscar.
	 * @return El cliente encontrado, o null si no se encuentra.
	 */
	public Cliente seleccionarClientePorId(int clienteId) {
		for (Cliente cliente : this.clientes) {
			if (cliente.getId() == clienteId) {
				return cliente;
			}
		}
		return null;
	}

	public int obtenerIDClientePorMail_BD(String mail, Connection conn) throws SQLException {
		String sql = "SELECT id FROM Cliente WHERE mail = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, mail);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt("id");
				}
			}
		}
		return 0; // ID no encontrado
	}

	/**
	 * Muestra los clientes en un formato legible.
	 * 
	 * @return Una cadena con los detalles de los clientes.
	 */
	public String mostrarClientes() {
		StringBuilder resultado = new StringBuilder();
		for (Cliente cliente : clientes) {
			resultado.append("ID: ").append(cliente.getNumeroCliente()).append("\n").append("Nombre: ")
					.append(cliente.getNombre()).append("\n\n");
		}
		return resultado.toString();
	}
}
