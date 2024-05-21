package logic;

import java.util.ArrayList;
import java.util.List;
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
	public GestionCliente() {
		this.clientes = new ArrayList<>();
	}

	/**
	 * Agrega un cliente a la lista.
	 * 
	 * @param cliente El cliente a agregar.
	 */
	public void agregarCliente(Cliente cliente) {
		this.clientes.add(cliente);
		System.out.println("Cliente agregado exitosamente.");
	}

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
			if (Integer.parseInt(cliente.getNumerocliente()) == clienteId) {
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
			resultado.append("ID: ").append(cliente.getNumerocliente()).append("\n").append("Nombre: ")
					.append(cliente.getNombre()).append("\n\n");
		}
		return resultado.toString();
	}
}
