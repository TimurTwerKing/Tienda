package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import data.Producto;
import util.Conexion;

/**
 * Clase para gestionar la generación de tickets.
 * 
 * @autor Timur Bogach
 * @date 14 may 2024
 * @param Clase para gestionar el pago de los productos en la cesta.
 */
public class GestionPago {
	private GestionProducto gestionProducto;
	private GestionPedido gestionPedido;

	/**
	 * Constructor de la clase.
	 * 
	 * @param gestionProducto La instancia de GestionProducto.
	 * @param gestionPedido   La instancia de GestionPedido.
	 */
	public GestionPago(GestionProducto gestionProducto, GestionPedido gestionPedido) {
		this.gestionProducto = gestionProducto;
		this.gestionPedido = gestionPedido;
	}

	/**
	 * Vende los artículos de la cesta y actualiza la base de datos.
	 * 
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void venderArticulos() throws SQLException {
		Connection conn = Conexion.conectar();
		try {
			for (Producto productoEnCesta : gestionPedido.getCesta()) {
				actualizarProductoEnBaseDeDatos(conn, productoEnCesta);
				actualizarProductoEnCatalogo(productoEnCesta);
			}

			gestionPedido.getCesta().clear();
		} finally {
			if (conn != null) {
				conn.close(); // Cerrar la conexión cuando hayamos terminado
			}
		}
	}

	/**
	 * Actualiza el producto en la base de datos después de la venta.
	 * 
	 * @param conn            La conexión a la base de datos.
	 * @param productoEnCesta El producto vendido.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void actualizarProductoEnBaseDeDatos(Connection conn, Producto productoEnCesta) throws SQLException {
		Producto producto = gestionProducto.buscarProductoPorId(productoEnCesta.getId());
		if (producto != null) {
			int cantidadVendida = productoEnCesta.getCantidad();
			int nuevaCantidad = producto.getCantidad() - cantidadVendida;

			String updateQuery = "UPDATE Producto SET cantidad = ? WHERE id = ?";
			try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
				pstmt.setInt(1, nuevaCantidad);
				pstmt.setInt(2, producto.getId());
				pstmt.executeUpdate();
			}
		}
	}

	/**
	 * Actualiza el producto en el catálogo después de la venta.
	 * 
	 * @param productoEnCesta El producto vendido.
	 */
	private void actualizarProductoEnCatalogo(Producto productoEnCesta) {
		Producto producto = gestionProducto.buscarProductoPorId(productoEnCesta.getId());
		if (producto != null) {
			int cantidadVendida = productoEnCesta.getCantidad();
			int nuevaCantidad = producto.getCantidad() - cantidadVendida;
			producto.setCantidad(nuevaCantidad);
		}
	}
}
