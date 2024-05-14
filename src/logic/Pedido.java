package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import modelo.Cliente;
import mySQL_DB.Conexion;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
 */
public class Pedido {
	private int ordendepedido;
	private int codigocliente;
	private int producto;
	private int cantidad;

	// Constructor, getters y setters
	public Pedido() {
	}

	// Constructor con parámetros
	public Pedido(int codigocliente, int producto, int cantidad) {
		this.codigocliente = codigocliente;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	// Método para hacer un pedido usando un cliente existente
	public static boolean hacerPedido(Cliente cliente, int producto, int cantidad) {
		String sql = "INSERT INTO Pedido (codigocliente, producto, cantidad) VALUES (?, ?, ?)";

		try (Connection conn = Conexion.conectar(); PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, cliente.getCodigo());
			stmt.setInt(2, producto);
			stmt.setInt(3, cantidad);

			int rowsInserted = stmt.executeUpdate();
			return rowsInserted > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public int getOrdendepedido() {
		return ordendepedido;
	}

	public void setOrdendepedido(int ordendepedido) {
		this.ordendepedido = ordendepedido;
	}

	public int getCodigocliente() {
		return codigocliente;
	}

	public void setCodigocliente(int codigocliente) {
		this.codigocliente = codigocliente;
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
