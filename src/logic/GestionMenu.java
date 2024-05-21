package logic;

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

	public static void manejarMenuLogin(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket) {
		boolean volverLogin = false;
		while (!volverLogin) {
			Menu.mostrarMenuLogin();
			int opcionLogin = sc.nextInt();

			switch (opcionLogin) {
			case 1:
				boolean volverComprar = false;
				while (!volverComprar) {
					Menu.mostrarMenuComprar();
					System.out.println(gestionProductos.mostrarProductosCatalogo());
					int opcionComprar = sc.nextInt();

					switch (opcionComprar) {
					case 1:
						gestionPedido.realizarPedido(gestionProductos, gestionPago, cliente, fichero, sc, tiket);
						volverLogin = true; // Volver al menú de cliente después de realizar la compra
						volverComprar = true;
						break;
					case 2:
						volverComprar = true;
						break;
					default:
						System.out.println("Opción no válida. Intente de nuevo.");
					}
				}
				break;
			case 2:
				boolean volverCarrito = false;
				while (!volverCarrito) {
					Menu.mostrarMenuCarrito();
					int opcionCarrito = sc.nextInt();

					switch (opcionCarrito) {
					case 1:
						// Aquí deberías implementar la lógica para borrar un producto del carrito
						System.out.println("Funcionalidad para borrar producto no implementada.");
						break;
					case 2:
						volverCarrito = true;
						break;
					default:
						System.out.println("Opción no válida. Intente de nuevo.");
					}
				}
				break;
			case 3:
				volverLogin = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}

	public static void manejarMenuRegistrar(Scanner sc, GestionProducto gestionProductos, GestionPedido gestionPedido,
			GestionPago gestionPago, Cliente cliente, Fichero fichero, Tiket tiket) {
		boolean volverRegistrar = false;
		while (!volverRegistrar) {
			Menu.mostrarMenuRegistrar();
			int opcionRegistrar = sc.nextInt();

			switch (opcionRegistrar) {
			case 1:
				boolean volverComprarReg = false;
				while (!volverComprarReg) {
					Menu.mostrarMenuComprar();
					System.out.println(gestionProductos.mostrarProductosCatalogo());
					int opcionComprarReg = sc.nextInt();

					switch (opcionComprarReg) {
					case 1:
						gestionPedido.realizarPedido(gestionProductos, gestionPago, cliente, fichero, sc, tiket);
						volverRegistrar = true; // Volver al menú de cliente después de realizar la compra
						volverComprarReg = true;
						break;
					case 2:
						volverComprarReg = true;
						break;
					default:
						System.out.println("Opción no válida. Intente de nuevo.");
					}
				}
				break;
			case 2:
				boolean volverCarritoReg = false;
				while (!volverCarritoReg) {
					Menu.mostrarMenuCarrito();
					int opcionCarritoReg = sc.nextInt();

					switch (opcionCarritoReg) {
					case 1:
						// Aquí deberías implementar la lógica para borrar un producto del carrito
						System.out.println("Funcionalidad para borrar producto no implementada.");
						break;
					case 2:
						volverCarritoReg = true;
						break;
					default:
						System.out.println("Opción no válida. Intente de nuevo.");
					}
				}
				break;
			case 3:
				volverRegistrar = true;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
			}
		}
	}
}
