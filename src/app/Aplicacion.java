package app;

import java.sql.SQLException;
import java.util.Scanner;

import data.Tiket;
import logic.GestionProducto;
import logic.GestionPedido;
import logic.GestionPago;
import menu.Menu;
import util.Fichero;

/**
 * @autor Timur Bogach
 * @date 19 may 2024
 * @param Clase Aplicacion: Clase principal que gestiona la aplicación y
 *              orquesta otras clases.
 */

public class Aplicacion {
	public static void main(String[] args) {
		GestionProducto gestionProductos = new GestionProducto();
		GestionPedido gestionPedido = new GestionPedido(gestionProductos);
		GestionPago gestionPago = new GestionPago(gestionProductos, gestionPedido);
		Tiket tiket = new Tiket();

		try {
			gestionProductos.cargarProductos();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Fichero fichero = new Fichero();

		Menu.Mensaje_Inicial();
		boolean continuar = true;
		Scanner sc = new Scanner(System.in);

		while (continuar) {
			Menu.Opciones_Menu();
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				break;
			case 2:
				realizarCompra(gestionPedido, gestionPago, fichero, sc, tiket);
				break;
			case 3:
				double total = gestionPedido.mostrarImporteTotal();
				System.out.println("El importe total de la compra actual es: " + total + " euros.");
				break;
			default:
				continuar = false;
				break;
			}
		}

		Menu.Mensaje_Fin();
		sc.close();
	}

	private static void realizarCompra(GestionPedido gestionPedido, GestionPago gestionPago, Fichero fichero,
			Scanner sc, Tiket tiket) {
		boolean pagar = false;
		while (!pagar) {
			System.out.println("Escriba ID del producto: ");
			int productoID = sc.nextInt();
			System.out.println("Escriba la cantidad del producto seleccionado: ");
			int cantidadProducto = sc.nextInt();
			gestionPedido.agregarCesta(productoID, cantidadProducto);
			Menu.seguirComprando_Pagar();
			String opcionPagar = sc.next();
			if (opcionPagar.equalsIgnoreCase("pagar")) {
				pagar = true;
			} else if (opcionPagar.equalsIgnoreCase("cancelar")) {
				pagar = false;
			}
		}

		try {
			gestionPago.venderArticulos();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		String ticket = tiket.crearTicket(gestionPedido);
		System.out.println(ticket);
		Menu.deseaTiket();
		String opcionTiket = sc.next();
		if (opcionTiket.equalsIgnoreCase("si")) {
			fichero.escribirFichero(ticket);
		}
	}
}
