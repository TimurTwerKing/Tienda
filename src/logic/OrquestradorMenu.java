package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import menu.Menu;
import modelo.Cliente;
import util.Conexion;
import util.Fichero;
import data.Tiket;

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
			GestionPago gestionPago, GestionCliente gestionCliente, Cliente cliente, Fichero fichero, Tiket tiket) throws SQLException {
		// Menú para usuarios
		boolean volverUsuario = false;
		while (!volverUsuario) {
			Menu.mostrarMenuUsuario();
			int opcionUsuario = sc.nextInt();

			switch (opcionUsuario) {
			case 1: // Login de usuario
				// CARGA CLIENTE TODO: implementar seccion para elegir cleintes.
//				try {
//					gestionCliente.cargarClientes(conn);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				try {
//					gestionCliente.elegirCliente(sc, conn);
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}

				// Cliente clienteCreado = gestionCliente.crearCliente(sc); //TODO CREACION
				// CLIENTE PARA SU USO

				// TODO: Implementar el login de usuario con la base de datos
				this.menuUsuarioLogueado(gestionProductos, gestionPedido, gestionPago, cliente, fichero,
						tiket);
				break;
			case 2:
				// Registro de usuario
				// gestionCliente.crearCliente(sc);
				this.manejarMenuRegistrar(gestionProductos, gestionPedido, gestionPago, cliente, fichero,
						tiket);
				break;
			case 3:
				volverUsuario = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	public void manejarMenuAdministrador(GestionProducto gestionProductos,
			GestionCliente gestionCliente, GestionAlbaran gestionAlbaran) {
		// Menú para administradores
		boolean volverAdmin = false;
		while (!volverAdmin) {
			Menu.mostrarMenuAdministrador();
			int opcionAdmin = sc.nextInt();

			switch (opcionAdmin) {
			case 1:
				// Agregar productos
				Menu.mensajeAgregarProducto();
				sc.nextLine(); // Limpiar el buffer
				String nombre = sc.nextLine();
				float precio = sc.nextFloat();
				int cantidad = sc.nextInt();
				boolean stock = sc.nextBoolean();
				sc.nextLine(); // Limpiar el buffer
				String genero = sc.nextLine();
				int idCategoria = sc.nextInt();

				gestionProductos.agregarProducto(nombre, precio, cantidad, stock, genero, idCategoria);
				break;
			case 2:
				// Borrar productos
				System.out.println("Productos en el catálogo:");
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				System.out.println("Ingrese el ID del producto a borrar:");
				int idProducto = sc.nextInt();
				gestionProductos.borrarProducto(idProducto);
				break;
			case 3:
				// Eliminar usuarios
				System.out.println("Clientes en el sistema:");
				System.out.println(gestionCliente.mostrarClientes());
				System.out.println("Ingrese el ID del cliente a eliminar:");
				int idCliente = sc.nextInt();
				gestionCliente.borrarCliente(idCliente);
				break;
			case 4:
				// Guardar albarán
				System.out.println("Ingrese el ID del proveedor:");
				int idProveedor = sc.nextInt();
				sc.nextLine(); // Limpiar el buffer
				System.out.println("Ingrese la fecha de entrega (YYYY-MM-DD):");
				String fechaEntrega = sc.nextLine();

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
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket)
			throws SQLException {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuPrincipalUsuario();
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1: // Agregar productos a la cesta
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				gestionPedido.generarPedido(this.sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket,
						this.conn);
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

	private void mostrarCesta(GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket)
			throws SQLException {
		boolean volver = false;
		while (!volver) {
			if (gestionPedido.cestaVacia()) {
				System.out.println("La cesta de su compra está vacía.\n");
				return;
			} else {
				System.out.println(gestionPedido.mostrarProductosCesta());
				Menu.mostrarMenuCesta();
				int opcion = sc.nextInt();

				switch (opcion) {
				case 1: // Realizar la compra
					gestionPedido.realizarPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero,
							tiket, conn);
					volver = true;
					break;
				case 2: // Borrar producto
					System.out.println("Escriba el ID del producto que desea eliminar: ");
					int idParaEliminar = sc.nextInt();
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

	public void manejarMenuRegistrar(GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket)
			throws SQLException {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuRegistrar();
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1: // Comprar
				this.realizarCompraRegistrada(gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket);
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

	private void realizarCompraRegistrada(GestionProducto gestionProductos,
			GestionPedido gestionPedido, GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket) throws SQLException {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuComprar_Pagar();
			System.out.println(gestionProductos.mostrarProductosCatalogo());
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1: // Pagar
				gestionPedido.realizarPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket,
						conn);
				volver = true;
				break;
			case 2: // Volver
				volver = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}
}