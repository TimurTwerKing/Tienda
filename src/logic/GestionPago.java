package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import data.Producto;
import modelo.Cliente;
import util.Conexion;

/**
 * Clase para gestionar el pago de los productos en la cesta.
 * 
 * @autor Timur Bogach
 * @date 14 may 2024
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
	 * Método para seleccionar el método de pago.
	 * 
	 * @param cliente El cliente que realiza el pago.
	 * @param sc      El objeto Scanner para leer la entrada del usuario.
	 */
	public void metodoDePago(Cliente cliente, Scanner sc) {
		int opcion;
		boolean aux = true;
		do {
			System.out.println("Opciones a pagar:\n");
			System.out.println("1. Pago con tarjeta");
			opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				pagar(cliente, sc);
				aux = false;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
				break;
			}
		} while (aux);
	}

	/**
	 * Método para realizar el pago con tarjeta.
	 * 
	 * @param cliente El cliente que realiza el pago.
	 * @param sc      El objeto Scanner para leer la entrada del usuario.
	 */
	public void pagar(Cliente cliente, Scanner sc) throws NoSuchElementException, NumberFormatException {
		boolean tarjetaValida = false;
		do {
			try {
				sc.nextLine(); // Limpiar el buffer del scanner después de una excepción
				System.out.println("Introduzca su tarjeta con los espacios correspondientes: \nEj 3444 666666 55555");
				String tipo = sc.nextLine(); // Ejemplo: 3444 666666 55555
				tipo = tipo.replace(" ", "");
				int longitud = tipo.length();
				if (longitud == 15 || longitud == 16) {
					if (tipo.startsWith("3") && longitud == 15) {
						System.out.println("Gracias por pagar con American Express\n");
						tarjetaValida = true;
					} else if (tipo.startsWith("4") && longitud == 16) {
						System.out.println("Gracias por pagar con Visa\n");
						tarjetaValida = true;
					} else if (tipo.startsWith("5") && longitud == 16) {
						System.out.println("Gracias por pagar con Master Card\n");
						tarjetaValida = true;
					} else {
						System.out.println("Número de tarjeta no válido. Inténtelo de nuevo.");
					}
				} else {
					System.out.println("Número de tarjeta no válido. Inténtelo de nuevo.");
				}
			} catch (Exception e) {
				System.out.println("Ha ocurrido un error con la entrada de la tarjeta. Por favor, inténtelo de nuevo.");
				sc.nextLine(); // Limpiar el buffer del scanner después de una excepción
			}
		} while (!tarjetaValida);
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
