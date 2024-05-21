package app;

import java.sql.SQLException;
import java.util.Scanner;
import data.Tiket;
import logic.GestionCliente;
import logic.GestionMenu;
import logic.GestionPago;
import logic.GestionPedido;
import logic.GestionProducto;
import menu.Menu;
import modelo.Cliente;
import util.Fichero;

/**
 * Clase principal que gestiona la aplicación y orquesta otras clases.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Aplicacion {
	public static void main(String[] args) {
		// Inicialización de las clases de gestión
		GestionProducto gestionProductos = new GestionProducto();
		GestionPedido gestionPedido = new GestionPedido();
		GestionPago gestionPago = new GestionPago(gestionProductos, gestionPedido);
		GestionCliente gestionCliente = new GestionCliente();
		Cliente cliente = new Cliente();
		Tiket tiket = new Tiket();
		Fichero fichero = new Fichero();
		Scanner sc = new Scanner(System.in);

		// Cargar productos desde la base de datos
		try {
			gestionProductos.cargarProductos();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// Mostrar mensaje inicial
		Menu.Mensaje_Inicial();

		// Bucle principal de la aplicación
		boolean continuar = true;
		while (continuar) {
			Menu.mostrarMenuPrincipal();
			int opcionPrincipal = sc.nextInt();

			switch (opcionPrincipal) {
			case 1:
				// Menú para usuarios
				boolean volverUsuario = false;
				while (!volverUsuario) {
					Menu.mostrarMenuUsuario();
					int opcionUsuario = sc.nextInt();

					switch (opcionUsuario) {
					case 1:
						// Login de usuario
						GestionMenu.manejarMenuLogin(sc, gestionProductos, gestionPedido, gestionPago, cliente, fichero,
								tiket);
						break;
					case 2:
						// Registro de usuario
						GestionMenu.manejarMenuRegistrar(sc, gestionProductos, gestionPedido, gestionPago, cliente,
								fichero, tiket);
						break;
					case 3:
						volverUsuario = true;
						break;
					default:
						System.out.println("Opción no válida. Intente de nuevo.");
					}
				}
				break;
			case 2:
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
						volverAdmin = true;
						break;
					default:
						System.out.println("Opción no válida. Intente de nuevo.");
					}
				}
				break;
			case 3:
				// Salir de la aplicación
				continuar = false;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}

		// Mostrar mensaje de finalización
		Menu.Mensaje_Fin();
		sc.close();
	}
}
