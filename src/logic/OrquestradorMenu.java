package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import data.Producto;
import data.Tiket;
import menu.Menu;
import modelo.Cliente;
import util.Fichero;
import util.Leer;

/**
 * Clase para gestionar los menús de la aplicación.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class OrquestradorMenu {
	private Connection conn;
	private Scanner sc;

	public OrquestradorMenu(Connection conn, Scanner sc) {
		this.sc = sc;
		this.conn = conn;
	}

	public void manejarMenuUsuario(GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, GestionCliente gestionCliente, Cliente cliente, Fichero fichero, Tiket tiket)
			throws SQLException {
		boolean volverUsuario = false;
		while (!volverUsuario) {
			Menu.mostrarMenuUsuario();
			int opcionUsuario = Leer.datoInt();

			switch (opcionUsuario) {
			case 1: // Login de usuario
				cliente = gestionCliente.elegirCliente(this.conn);
				if (cliente != null) {
					this.menuUsuarioLogueado(gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket);
				} else {
					System.out.println("No se seleccionó un cliente válido.");
				}
				break;
			case 2: // Registro de usuario
				cliente = gestionCliente.crearCliente(this.conn);
				if (cliente != null) {
					gestionCliente.agregarCliente(cliente, this.conn);
					// Obtener el ID generado automático en BD
					cliente.setId(gestionCliente.obtenerIDClientePorMail_BD(cliente.getMail(), conn));
					if (cliente.getId() > 0) {
						this.menuUsuarioLogueado(gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket);
					} else {
						System.out.println("No se pudo obtener el ID del cliente.");
					}
				}
				break;
			case 3:
				volverUsuario = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	public void manejarMenuAdministrador(GestionProducto gestionProductos, GestionCliente gestionCliente,
			GestionAlbaran gestionAlbaran, Connection conn) {
		// Menú para administradores
		boolean volverAdmin = false;
		while (!volverAdmin) {
			Menu.mostrarMenuAdministrador();
			int opcionAdmin = Leer.datoInt();

			switch (opcionAdmin) {
			case 1:
				// Agregar productos
				Producto nuevoProducto = gestionProductos.crearProducto();
				if (nuevoProducto != null) {
					gestionProductos.agregarProducto(nuevoProducto.getNombre(), nuevoProducto.getPrecioUnidad(),
							nuevoProducto.getCantidad(), nuevoProducto.hayStock(), nuevoProducto.getGenero(),
							nuevoProducto.getIdCategoria());
				} else {
					System.out.println("No se pudo crear el producto.");
				}
				break;
			case 2:
				// Borrar productos
				System.out.println("Productos en el catálogo:");
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				System.out.println("Ingrese el ID del producto a borrar:");
				int idProducto = Leer.datoInt();
				gestionProductos.borrarProductoDeBaseDeDatos(idProducto);
				break;
			case 3:
				// Eliminar usuarios
				System.out.println("Clientes en el sistema:");
				System.out.println(gestionCliente.mostrarClientes());
				System.out.println("Ingrese el ID del cliente a eliminar:");
				int idCliente = Leer.datoInt();
				gestionCliente.borrarClienteDeBaseDeDatos(idCliente, conn);
				break;
			case 4:
				// Guardar albarán
				System.out.println("Ingrese el ID del proveedor:");
				int idProveedor = Leer.datoInt();
				System.out.println("Ingrese la fecha de entrega (YYYY-MM-DD):");
				String fechaEntrega = Leer.datoString();

				try {
					gestionAlbaran.guardarAlbaranEnBaseDeDatos(idProveedor, fechaEntrega, conn);
					System.out.println("Albarán guardado exitosamente.");
				} catch (SQLException e) {
					System.out.println("Error al guardar el albarán: " + e.getMessage());
				}
				break;
			case 5:
				volverAdmin = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	public void menuUsuarioLogueado(GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket) throws SQLException {
		System.out.println("Bienvenido " + cliente.getNombre());
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuPrincipalUsuario();
			int opcion = Leer.datoInt();

			switch (opcion) {
			case 1: // Agregar productos a la cesta
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				gestionPedido.generarPedido(this.sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero,
						tiket, this.conn);
				break;
			case 2: // Ver cesta
				this.mostrarCesta(gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket);
				break;
			case 3: // Volver
				volver = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	private void mostrarCesta(GestionProducto gestionProductos, GestionPedido gestionPedido, GestionPago gestionPago,
			Cliente cliente, Fichero fichero, Tiket tiket) throws SQLException {
		boolean volver = false;
		while (!volver) {
			if (gestionPedido.cestaVacia()) {
				System.out.println("La cesta de su compra está vacía.\n");
				return;
			} else {
				System.out.println(gestionPedido.mostrarProductosCesta());
				Menu.mostrarMenuCesta();
				int opcion = Leer.datoInt();

				switch (opcion) {
				case 1: // Realizar la compra
					gestionPedido.gestionarPagoPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente,
							fichero, tiket, this.conn);
					volver = true;
					break;
				case 2: // Borrar producto
					System.out.println("Escriba el ID del producto que desea eliminar: ");
					int idParaEliminar = Leer.datoInt();
					gestionPedido.borrarProductoPorIdCesta(idParaEliminar);
					break;
				case 3: // Volver
					volver = true;
					break;
				default:
					System.out.println("Opción no válida. Intente de nuevo.");
				}
			}
		}
	}

//	public void manejarMenuRegistrar(GestionProducto gestionProductos, GestionPedido gestionPedido,
//			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket) throws SQLException {
//		boolean volver = false;
//		while (!volver) {
//			Menu.mostrarMenuRegistrar();
//			int opcion = Leer.datoInt();
//
//			switch (opcion) {
//			case 1: // Comprar
//				this.realizarCompraRegistrada(gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket);
//				break;
//			case 2: // Ver cesta
//				this.mostrarCesta(gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket);
//				break;
//			case 3: // Volver
//				volver = true;
//				break;
//			default:
//				System.out.println("Opción no válida. Intente de nuevo.");
//			}
//		}
//	}
//
//	private void realizarCompraRegistrada(GestionProducto gestionProductos, GestionPedido gestionPedido,
//			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket) throws SQLException {
//		boolean volver = false;
//		while (!volver) {
//			Menu.mostrarMenuComprar_Pagar();
//			System.out.println(gestionProductos.mostrarProductosCatalogo());
//			int opcion = Leer.datoInt();
//
//			switch (opcion) {
//			case 1: // Pagar
//				gestionPedido.gestionarPagoPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero,
//						tiket, conn);
//				volver = true;
//				break;
//			case 2: // Volver
//				volver = true;
//				break;
//			default:
//				System.out.println("Opción no válida. Intente de nuevo.");
//			}
//		}
//	}
}
