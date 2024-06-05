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

	/**
	 * Constructor de la clase.
	 * 
	 * @param conn La conexión a la base de datos.
	 * @param sc   El escáner para la entrada de usuario.
	 */
	public OrquestradorMenu(Connection conn, Scanner sc) {
		this.sc = sc;
		this.conn = conn;
	}

	/**
	 * Maneja el menú para usuarios.
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param gestionPedido    La instancia de GestionPedido.
	 * @param gestionPago      La instancia de GestionPago.
	 * @param gestionCliente   La instancia de GestionCliente.
	 * @param cliente          El cliente actual.
	 * @param fichero          La instancia de Fichero.
	 * @param tiket            La instancia de Tiket.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
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
					// Obtener el ID generado automáticamente en la BD
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

	/**
	 * Maneja el menú para administradores.
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param gestionCliente   La instancia de GestionCliente.
	 * @param gestionAlbaran   La instancia de GestionAlbaran.
	 * @param conn             La conexión a la base de datos.
	 */
	public void manejarMenuAdministrador(GestionProducto gestionProductos, GestionCliente gestionCliente,
			GestionAlbaran gestionAlbaran, Connection conn) {
		// Dado a la estructura de la BD entidad relacion, no nos interesa borrar los
		// productos ni los clientes ya que para ello debemos borrar tambien las
		// referencias de las ventas ya realizadas en otras tablas.
		// Por ello se ha optado por dar de baja a los clientes y inactivar los
		// productos.
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
				gestionProductos.inactivizarProducto(conn);
				break;
			case 3:
				// Dar de baja usuarios
				gestionCliente.darDeBajaCliente(conn);
				break;
			case 4:
				// Dar de alta usuarios
				gestionCliente.darDeAltaCliente(conn);
				break;
			case 5:
				volverAdmin = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	/**
	 * Muestra el menú para un usuario logueado.
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param gestionPedido    La instancia de GestionPedido.
	 * @param gestionPago      La instancia de GestionPago.
	 * @param cliente          El cliente logueado.
	 * @param fichero          La instancia de Fichero.
	 * @param tiket            La instancia de Tiket.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
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

	/**
	 * Muestra la cesta de compras del usuario.
	 * 
	 * @param gestionProductos La instancia de GestionProducto.
	 * @param gestionPedido    La instancia de GestionPedido.
	 * @param gestionPago      La instancia de GestionPago.
	 * @param cliente          El cliente actual.
	 * @param fichero          La instancia de Fichero.
	 * @param tiket            La instancia de Tiket.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
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
}
