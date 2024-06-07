package logic;

import java.sql.Connection;
import java.sql.SQLException;

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
	private GestionProducto gestionProductos;
	private GestionCliente gestionCliente;
	private GestionPedido gestionPedido;
	private GestionPago gestionPago;
	private GestionAlbaran gestionAlbaran;
	private Tiket tiket;
	private Fichero fichero;

	/**
	 * Constructor de la clase.
	 * 
	 * @param conn La conexión a la base de datos.
	 */
	public OrquestradorMenu(Connection conn) {
		this.conn = conn;
		// Inicialización de las clases de gestión
		this.gestionProductos = new GestionProducto(conn); // USUARIO/ADMIN
		this.gestionCliente = new GestionCliente(conn); // USUARIO/ADMIN
		this.gestionPedido = new GestionPedido(conn); // USUARIO
		this.gestionPago = new GestionPago(gestionProductos, gestionPedido, conn); // USUARIO
		this.gestionAlbaran = new GestionAlbaran(conn); // ADMIN
		this.tiket = new Tiket(); // USUARIO
		this.fichero = new Fichero(); // USUARIO
	}

	/**
	 * Maneja el menú para usuarios.
	 * 
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void manejarMenuUsuario() throws SQLException {
		boolean volverUsuario = false;
		while (!volverUsuario) {
			Menu.mostrarMenuUsuario();
			int opcionUsuario = Leer.datoInt();

			switch (opcionUsuario) {
			case 1: // Login de usuario
				Cliente cliente = gestionCliente.elegirCliente(this.conn);
				if (cliente != null) {
					this.menuUsuarioLogueado(cliente);
				} else {
					System.out.println("No se seleccionó un cliente válido.");
				}
				break;
			case 2: // Registro de usuario
				cliente = gestionCliente.crearCliente(this.conn);
				if (cliente != null) {
					if (cliente.getId() > 0) {
						this.menuUsuarioLogueado(cliente);
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
	 */
	public void manejarMenuAdministrador() {
		boolean volverAdmin = false;
		while (!volverAdmin) {
			Menu.mostrarMenuAdministrador();
			int opcionAdmin = Leer.datoInt();

			switch (opcionAdmin) {
			case 1:
				manejarSubmenuProductos();
				break;
			case 2:
				manejarSubmenuUsuarios();
				break;
			case 3:
				manejarSubmenuAlbaranes();
				break;
			case 4:
				volverAdmin = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	/**
	 * Maneja el submenú de productos.
	 */
	private void manejarSubmenuProductos() {
		boolean volverProductos = false;
		while (!volverProductos) {
			Menu.mostrarMenuProductos();
			int opcionProductos = Leer.datoInt();

			switch (opcionProductos) {
			case 1: // Crear producto
				Producto nuevoProducto = gestionProductos.crearProducto();
				if (nuevoProducto != null) {
					gestionProductos.agregarProducto(nuevoProducto.getNombre(), nuevoProducto.getPrecio(),
							nuevoProducto.getCantidad(), nuevoProducto.getStock(), nuevoProducto.getGenero(),
							nuevoProducto.getIdCategoria(), nuevoProducto.getIdAlbaran());
				} else {
					System.out.println("No se pudo crear el producto.");
				}
				break;
			case 2: // Dar de baja productos
				gestionProductos.desactivarProducto(conn);
				break;
			case 3: // Dar de alta productos
				gestionProductos.activarProducto(conn);
				break;
			case 4:
				volverProductos = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	/**
	 * Maneja el submenú de usuarios.
	 */
	private void manejarSubmenuUsuarios() {
		boolean volverUsuarios = false;
		while (!volverUsuarios) {
			Menu.mostrarMenuUsuarios();
			int opcionUsuarios = Leer.datoInt();

			switch (opcionUsuarios) {
			case 1: // Dar de baja usuarios
				System.out.println(gestionCliente.mostrarClientesActivos(conn));
				System.out.println("Escriba el id del cliente que desea dar de baja");
				int clienteIdActivo = Leer.datoInt();
				gestionCliente.darDeBajaCliente(clienteIdActivo, conn);
				break;
			case 2: // Dar de alta usuarios
				System.out.println(gestionCliente.mostrarClientesInactivos(conn));
				System.out.println("Escriba el id del cliente que desea dar de alta");
				int clienteIdInactivo = Leer.datoInt();
				gestionCliente.darDeAltaCliente(clienteIdInactivo, conn);
				break;
			case 3:
				volverUsuarios = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	/**
	 * Maneja el submenú de albaranes.
	 */
	private void manejarSubmenuAlbaranes() {
		boolean volverAlbaranes = false;
		while (!volverAlbaranes) {
			Menu.mostrarMenuAlbaranes();
			int opcionAlbaranes = Leer.datoInt();

			switch (opcionAlbaranes) {
			case 1: // Crear albarán
				gestionAlbaran.crearAlbaran();
				break;
			case 2: // Ver albaranes
				System.out.println(gestionAlbaran.mostrarAlbaranes());
				break;
			case 3:
				volverAlbaranes = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	/**
	 * Muestra el menú para un usuario logueado.
	 * 
	 * @param cliente El cliente logueado.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void menuUsuarioLogueado(Cliente cliente) throws SQLException {
		System.out.println("Bienvenido " + cliente.getNombre());
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuPrincipalUsuario();
			int opcion = Leer.datoInt();

			switch (opcion) {
			case 1: // Agregar productos a la cesta
				System.out.println(gestionProductos.mostrarProductosActivos());
				gestionPedido.generarPedido(gestionProductos, gestionPago, cliente, fichero, tiket, this.conn);
				break;
			case 2: // Ver cesta
				this.mostrarCesta(cliente);
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
	 * @param cliente El cliente actual.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	private void mostrarCesta(Cliente cliente) throws SQLException {
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
					gestionPedido.gestionarPagoPedido(gestionProductos, gestionPago, cliente, fichero, tiket,
							this.conn);
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
