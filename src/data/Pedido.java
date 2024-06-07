package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelo.Cliente;
import util.Conexion;

/**
 * Clase Pedido: Contiene métodos para gestionar los pedidos.
 * 
 * @autor Timur Bogach
 * @date 14 may 2024
 */
public class Pedido {
	private int ordendepedido;
	private int codigocliente;
	private int producto;
	private int cantidad;

	/**
	 * Constructor vacío de la clase Pedido.
	 */
	public Pedido() {
	}

	/**
	 * Constructor de la clase Pedido.
	 * 
	 * @param codigocliente El código del cliente.
	 * @param producto      El ID del producto.
	 * @param cantidad      La cantidad del producto.
	 */
	public Pedido(int codigocliente, int producto, int cantidad) {
		this.codigocliente = codigocliente;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	/**
	 * Método para hacer un pedido y guardarlo en la base de datos.
	 * 
	 * @param cliente  El cliente que realiza el pedido.
	 * @param producto El ID del producto.
	 * @param cantidad La cantidad del producto.
	 * @return true si el pedido se realizó con éxito, false en caso contrario.
	 */
	public static boolean hacerPedido(Cliente cliente, int producto, int cantidad) {
		// Validar entradas
		if (cliente == null || producto <= 0 || cantidad <= 0) {
			System.out.println("Datos de entrada no válidos.");
			return false;
		}

		String sql = "INSERT INTO Pedido (codigo_cliente, codigo_producto, cantidad) VALUES (?, ?, ?)";

		try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, cliente.getId());
			stmt.setInt(2, producto);
			stmt.setInt(3, cantidad);

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			System.out.println("No ha sido posible registrar el pedido en la base de datos.");
			e.printStackTrace();
			return false;
		}
	}

	// Getters y setters con comentarios
	/**
	 * Obtiene el número de orden del pedido.
	 * 
	 * @return El número de orden del pedido.
	 */
	public int getOrdendepedido() {
		return ordendepedido;
	}

	/**
	 * Establece el número de orden del pedido.
	 * 
	 * @param ordendepedido El número de orden del pedido.
	 */
	public void setOrdendepedido(int ordendepedido) {
		this.ordendepedido = ordendepedido;
	}

	/**
	 * Obtiene el código del cliente.
	 * 
	 * @return El código del cliente.
	 */
	public int getCodigocliente() {
		return codigocliente;
	}

	/**
	 * Establece el código del cliente.
	 * 
	 * @param codigocliente El código del cliente.
	 */
	public void setCodigocliente(int codigocliente) {
		this.codigocliente = codigocliente;
	}

	/**
	 * Obtiene el ID del producto.
	 * 
	 * @return El ID del producto.
	 */
	public int getProducto() {
		return producto;
	}

	/**
	 * Establece el ID del producto.
	 * 
	 * @param producto El ID del producto.
	 */
	public void setProducto(int producto) {
		this.producto = producto;
	}

	/**
	 * Obtiene la cantidad del producto.
	 * 
	 * @return La cantidad del producto.
	 */
	public int getCantidad() {
		return cantidad;
	}

	/**
	 * Establece la cantidad del producto.
	 * 
	 * @param cantidad La cantidad del producto.
	 */
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
}
