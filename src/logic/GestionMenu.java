package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import menu.Menu;
import modelo.Cliente;
import util.Fichero;
import data.Tiket;

/**
 * Clase para gestionar los menús de la aplicación.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class GestionMenu {

	public static void manejarMenuUsuario(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, GestionCliente gestionCliente, Cliente cliente, Fichero fichero, Tiket tiket,
			Connection conn) throws SQLException {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuUsuario();
			int opcionUsuario = sc.nextInt();

			switch (opcionUsuario) {
			case 1: // Login de usuario
				// TODO: Implementar el login de usuario con la base de datos
				menuUsuarioLogueado(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
				break;
			case 2: // Registro de usuario
//				gestionCliente.crearCliente(sc);
				manejarMenuRegistrar(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
				break;
			case 3: // Volver
				volver = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	public static void manejarMenuAdministrador(Scanner sc, GestionProducto gestionProductos,
			GestionCliente gestionCliente) {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuAdministrador();
			int opcionAdmin = sc.nextInt();

			switch (opcionAdmin) {
			case 1: // Agregar productos
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
			case 2: // Borrar productos
				System.out.println("Productos en el catálogo:");
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				System.out.println("Ingrese el ID del producto a borrar:");
				int idProducto = sc.nextInt();
				gestionProductos.borrarProducto(idProducto);
				break;
			case 3: // Eliminar usuarios
				System.out.println("Clientes en el sistema:");
				System.out.println(gestionCliente.mostrarClientes());
				System.out.println("Ingrese el ID del cliente a eliminar:");
				int idCliente = sc.nextInt();
				gestionCliente.borrarCliente(idCliente);
				break;
			case 4: // Volver
				volver = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	public static void menuUsuarioLogueado(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket, Connection conn) throws SQLException {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuPrincipalUsuario();
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1: // Agregar productos a la cesta
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				gestionPedido.generarPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket,
						conn);
				break;
			case 2: // Ver cesta
				mostrarCesta(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
				break;
			case 3: // Volver
				volver = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	private static void mostrarCesta(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket, Connection conn) throws SQLException {
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
					gestionPedido.realizarPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
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

	public static void manejarMenuRegistrar(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket, Connection conn) throws SQLException {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuRegistrar();
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1: // Comprar
				realizarCompraRegistrada(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket,
						conn);
				break;
			case 2: // Ver cesta
				mostrarCesta(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
				break;
			case 3: // Volver
				volver = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	private static void realizarCompraRegistrada(Scanner sc, GestionProducto gestionProductos,
			GestionPedido gestionPedido, GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket,
			Connection conn) throws SQLException {
		boolean volver = false;
		while (!volver) {
			Menu.mostrarMenuComprar_Pagar();
			System.out.println(gestionProductos.mostrarProductosCatalogo());
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1: // Pagar
				gestionPedido.realizarPedido(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero, tiket, conn);
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