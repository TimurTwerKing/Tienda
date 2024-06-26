package logic;

import java.sql.Statement;
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
	 * 
	 * @param conn La conexión a la base de datos.
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
	 */
	public void agregarClienteBaseDeDatos(Cliente cliente, Connection conn) {
		// Verificar si el cliente ya existe por su correo electrónico
		if (!verificarClienteExisteMail(cliente.getMail(), conn)) {
			String sql = "INSERT INTO Cliente (nombre, apellidos, direccion, localidad, provincia, pais, codigo_postal, telefono, mail, observaciones, activo) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

			try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
				pstmt.setString(1, cliente.getNombre());
				pstmt.setString(2, cliente.getApellidos());
				pstmt.setString(3, cliente.getDireccion());
				pstmt.setString(4, cliente.getLocalidad());
				pstmt.setString(5, cliente.getProvincia());
				pstmt.setString(6, cliente.getPais());
				pstmt.setString(7, cliente.getCodigoPostal());
				pstmt.setString(8, cliente.getTelefono());
				pstmt.setString(9, cliente.getMail());
				pstmt.setString(10, cliente.getObservaciones());
				pstmt.setBoolean(11, cliente.isActivo());

				pstmt.executeUpdate();

				try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						cliente.setId(generatedKeys.getInt(1)); // Obtenemos el ID generado
					} else {
						throw new SQLException("No se pudo obtener el ID del cliente.");
					}
				}
			} catch (SQLException e) {
				System.out.println("No ha sido posible registrar el cliente en la base de datos.");
				e.printStackTrace();
			}
		} else {
			System.out.println("El cliente con correo " + cliente.getMail() + " ya existe.");
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
			System.out.println("Ingrese el nombre (o '0' para volver):");
			String nombre = Leer.leerStringNoVacio();
			if (nombre.equals("0"))
				return null;

			System.out.println("Ingrese los apellidos (o '0' para volver):");
			String apellidos = Leer.leerStringNoVacio();
			if (apellidos.equals("0"))
				return null;

			System.out.println("Ingrese la dirección (o '0' para volver):");
			String direccion = Leer.datoString();
			if (direccion.equals("0"))
				return null;

			System.out.println("Ingrese la localidad (o '0' para volver):");
			String localidad = Leer.datoString();
			if (localidad.equals("0"))
				return null;

			System.out.println("Ingrese la provincia (o '0' para volver):");
			String provincia = Leer.datoString();
			if (provincia.equals("0"))
				return null;

			System.out.println("Ingrese el país (o '0' para volver):");
			String pais = Leer.datoString();
			if (pais.equals("0"))
				return null;

			System.out.println("Ingrese el código postal (o '0' para volver):");
			String codigopostal = Leer.datoString();
			if (codigopostal.equals("0"))
				return null;

			System.out.println("Ingrese el teléfono (o '0' para volver):");
			String telefono = Leer.leerNumTelefono();
			if (telefono.equals("0"))
				return null;

			System.out.println("Ingrese el correo electrónico (o '0' para volver):");
			String mail = Leer.validarEntradaMail();
			if (mail.equals("0"))
				return null;

			System.out.println("Ingrese las observaciones (o '0' para volver):");
			String observaciones = Leer.datoString();
			if (observaciones.equals("0"))
				return null;

			// Verificar si el cliente ya existe por su correo electrónico
			if (verificarClienteExisteMail(mail, conn)) {
				System.out.println("El cliente con correo " + mail + " ya existe.");
				return null;
			}

			// Crear un nuevo objeto Cliente sin ID y activo
			Cliente nuevoCliente = new Cliente(nombre, apellidos, direccion, localidad, provincia, pais, codigopostal,
					telefono, mail, observaciones);

			// Agregar el cliente a la base de datos y obtener el ID generado
			agregarClienteBaseDeDatos(nuevoCliente, conn);

			// Devolver el objeto Cliente con el ID y el estado activo
			return nuevoCliente;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al agregar el cliente.");
			return null;
		}
	}

	/**
	 * Método para verificar si un cliente existe por su correo electrónico.
	 * 
	 * @param mail El correo electrónico del cliente.
	 * @param conn La conexión a la base de datos.
	 * @return true si el cliente existe, false en caso contrario.
	 */
	public boolean verificarClienteExisteMail(String mail, Connection conn) {
		String sql = "SELECT COUNT(*) FROM Cliente WHERE mail = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, mail);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return rs.getInt(1) > 0;
				}
			} catch (SQLException e) {
				System.out.println("No ha sido posible verificar al cliente en la base de datos.");
				e.printStackTrace();
			}
		} catch (SQLException e1) {
			System.out.println("No ha sido posible verificar al cliente en la base de datos.");
			e1.printStackTrace();
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
		String sql = "SELECT id, nombre, apellidos, direccion, localidad, provincia, pais, codigo_postal, telefono, mail, observaciones, activo FROM Cliente";

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Cliente cliente = new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"),
						rs.getString("direccion"), rs.getString("localidad"), rs.getString("provincia"),
						rs.getString("pais"), rs.getString("codigo_postal"), rs.getString("telefono"),
						rs.getString("mail"), rs.getString("observaciones"), rs.getBoolean("activo"));
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
		// Refrescar clientes desde la base de datos
		List<Cliente> clientes = cargarClientes(conn);
		System.out.println(mostrarClientesActivos(conn));
		System.out.println("Seleccione un cliente:");
		int opcion = Leer.datoInt(); // Opción ingresada es el ID del cliente

		// Verificar que la opción seleccionada sea válida
		if (opcion > 0 && opcion <= clientes.size()) {
			// Devolver el cliente seleccionado (restando 1 para obtener el índice correcto)
			return clientes.get(opcion - 1);
		} else {
			System.out.println("Opción no válida.");
			return null;
		}
	}

	/**
	 * Marca un cliente como inactivo en la base de datos.
	 * 
	 * @param clienteId El ID del cliente a marcar como inactivo.
	 * @param conn      La conexión a la base de datos.
	 */
	public void darDeBajaCliente(int clienteId, Connection conn) {
		String sql = "UPDATE Cliente SET activo = FALSE WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, clienteId);
			int rowsAffected = pstmt.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Cliente dado de baja exitosamente.");
			} else {
				System.out.println("No se encontró el cliente con ID: " + clienteId);
			}
		} catch (SQLException e) {
			System.out.println("No ha sido posible dar de baja al cliente.");
			e.printStackTrace();
		}
	}

	/**
	 * Marca un cliente como activo en la base de datos.
	 * 
	 * @param clienteId El ID del cliente a marcar como activo.
	 * @param conn      La conexión a la base de datos.
	 */
	public void darDeAltaCliente(int clienteId, Connection conn) {
		String sql = "UPDATE Cliente SET activo = true WHERE id = ?";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, clienteId);
			int filasActualizadas = pstmt.executeUpdate();

			if (filasActualizadas > 0) {
				System.out.println("El cliente con ID " + clienteId + " ha sido dado de alta exitosamente.");
			} else {
				System.out.println("No se encontró un cliente con el ID " + clienteId + ".");
			}
		} catch (SQLException e) {
			System.out.println("No ha sido posible dar de alta al cliente en la base de datos.");
			e.printStackTrace();
		}
	}

	/**
	 * Muestra los clientes activos en un formato legible.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return Una cadena con los detalles de los clientes activos.
	 */
	public String mostrarClientesActivos(Connection conn) {
		try {
			this.clientes = cargarClientes(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuilder resultado = new StringBuilder();
		boolean hayActivos = false;
		for (Cliente cliente : this.clientes) {
			if (cliente.isActivo()) {
				resultado.append("ID: ").append(cliente.getId()).append("\n");
				resultado.append("Nombre: ").append(cliente.getNombre()).append("\n\n");
				hayActivos = true;
			}
		}
		if (!hayActivos) {
			resultado.append("No hay usuarios dados de alta");
		}
		return resultado.toString();
	}

	/**
	 * Muestra los clientes inactivos en un formato legible.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return Una cadena con los detalles de los clientes inactivos.
	 */
	public String mostrarClientesInactivos(Connection conn) {
		try {
			this.clientes = cargarClientes(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		StringBuilder resultado = new StringBuilder();
		boolean hayInactivos = false;
		for (Cliente cliente : this.clientes) {
			if (!cliente.isActivo()) {
				resultado.append("ID: ").append(cliente.getId()).append("\n");
				resultado.append("Nombre: ").append(cliente.getNombre()).append("\n\n");
				hayInactivos = true;
			}
		}
		if (!hayInactivos) {
			resultado.append("No hay usuarios dados de baja");
		}
		return resultado.toString();
	}

	/**
	 * Verifica si hay clientes activos en la base de datos.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return true si hay clientes activos, false en caso contrario.
	 */
	public boolean hayClientesActivos(Connection conn) {
		try {
			this.clientes = cargarClientes(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Cliente cliente : this.clientes) {
			if (cliente.isActivo()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Verifica si hay clientes inactivos en la base de datos.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return true si hay clientes inactivos, false en caso contrario.
	 */
	public boolean hayClientesInactivos(Connection conn) {
		try {
			this.clientes = cargarClientes(conn);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (Cliente cliente : this.clientes) {
			if (!cliente.isActivo()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Orquesta el proceso para gestionar clientes inactivos.
	 * 
	 * @param conn La conexión a la base de datos.
	 */
	public void orquestadorClientesInactivos(Connection conn) {
		if (!hayClientesActivos(conn)) {
			System.out.println("No hay usuarios dados de alta.");
		} else {
			System.out.println("Clientes en el sistema:");
			System.out.println(mostrarClientesActivos(conn));
			System.out.println("Ingrese el ID del cliente que desea dar de baja:");
			int idCliente = Leer.datoInt();
			darDeBajaCliente(idCliente, conn);
		}
	}

	/**
	 * Orquesta el proceso para gestionar clientes activos.
	 * 
	 * @param conn La conexión a la base de datos.
	 */
	public void orquestadorClientesActivos(Connection conn) {
		if (!hayClientesInactivos(conn)) {
			System.out.println("No hay usuarios dados de baja.");
		} else {
			System.out.println("Clientes en el sistema:");
			System.out.println(mostrarClientesInactivos(conn));
			System.out.println("Ingrese el ID del cliente que desea dar de alta:");
			int idCliente = Leer.datoInt();
			darDeAltaCliente(idCliente, conn);
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

	/**
	 * Obtiene el ID de un cliente por su correo electrónico.
	 * 
	 * @param mail El correo electrónico del cliente.
	 * @param conn La conexión a la base de datos.
	 * @return El ID del cliente.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
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
}
