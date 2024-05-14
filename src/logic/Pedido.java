package logic;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
 */
public class Pedido {
	private int id;
	private int clienteId;
	private int cantidad;
	private String producto;

	public Pedido(int id, int clienteId, int cantidad, String producto) {
		this.id = id;
		this.clienteId = clienteId;
		this.cantidad = cantidad;
		this.producto = producto;
	}

	// Getters y setters

	public int getId() {
		return id;
	}

	public int getClienteId() {
		return clienteId;
	}

	public int getCantidad() {
		return cantidad;
	}

	public String getProducto() {
		return producto;
	}
}
