package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import modelo.Cliente;

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
		this.clientes = new ArrayList<>();
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
		String sql = "SELECT id, numero_cliente, nombre, apellidos FROM Cliente";

		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setId(rs.getInt("id"));
				cliente.setNumeroCliente(rs.getString("numero_cliente"));
				cliente.setNombre(rs.getString("nombre"));
				cliente.setApellidos(rs.getString("apellidos"));
				clientes.add(cliente);
			}
		}
		return clientes;
	}

	public boolean verificarClienteExiste(int idCliente, Connection conn) throws SQLException {
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
	 * Método para elegir un cliente del menú.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @return El cliente elegido.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public Cliente elegirCliente(Scanner sc, Connection conn) throws SQLException {
		List<Cliente> clientes = cargarClientes(conn);

		System.out.println("Seleccione un cliente:");
		for (int i = 0; i < clientes.size(); i++) {
			Cliente cliente = clientes.get(i);
			System.out.println((i + 1) + ". " + cliente.getNumeroCliente() + " - " + cliente.getNombre() + " "
					+ cliente.getApellidos());
		}

		int opcion = sc.nextInt();
		sc.nextLine(); // Limpiar el buffer

		if (opcion > 0 && opcion <= clientes.size()) {
			return clientes.get(opcion - 1);
		} else {
			System.out.println("Opción no válida.");
			return null;
		}
	}

	/**
	 * Agrega un cliente a la lista.
	 * 
	 * @param cliente El cliente a agregar.
	 */
	public boolean agregarCliente(Cliente cliente) {
		try {
			this.clientes.add(cliente);
			System.out.println("Cliente agregado exitosamente.");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

//	public void crearCliente(Scanner sc) {
//		System.out.println("Ingrese el número de cliente:");
//		String numerocliente = sc.nextLine();
//		System.out.println("Ingrese el nombre:");
//		String nombre = sc.nextLine();
//		System.out.println("Ingrese los apellidos:");
//		String apellidos = sc.nextLine();
//		System.out.println("Ingrese la dirección:");
//		String direccion = sc.nextLine();
//		System.out.println("Ingrese la localidad:");
//		String localidad = sc.nextLine();
//		System.out.println("Ingrese la provincia:");
//		String provincia = sc.nextLine();
//		System.out.println("Ingrese el país:");
//		String pais = sc.nextLine();
//		System.out.println("Ingrese el código postal:");
//		String codigopostal = sc.nextLine();
//		System.out.println("Ingrese el teléfono:");
//		String telefono = sc.nextLine();
//		System.out.println("Ingrese el correo electrónico:");
//		String mail = sc.nextLine();
//		System.out.println("Ingrese las observaciones:");
//		String observaciones = sc.nextLine();
//
//		Cliente cliente = new Cliente(numerocliente, nombre, apellidos, direccion, localidad, provincia, pais,
//				codigopostal, telefono, mail, observaciones,id);
//		boolean resultado = agregarCliente(cliente);
//		if (resultado) {
//			System.out.println("Cliente agregado con éxito.");
//		} else {
//			System.out.println("Error al agregar el cliente.");
//		}
//	}

	/**
	 * Borra un cliente de la lista por su ID.
	 * 
	 * @param clienteId El ID del cliente a borrar.
	 */
	public void borrarCliente(int clienteId) {
		Cliente cliente = buscarClientePorId(clienteId);
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
	public Cliente buscarClientePorId(int clienteId) {
		for (Cliente cliente : clientes) {
			if (Integer.parseInt(cliente.getNumeroCliente()) == clienteId) {
				return cliente;
			}
		}
		return null;
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