/** 
 * 
 */
package app;

import java.sql.SQLException;
import java.util.Scanner;

import data.GestionProducto;
import menu.Menu;
import store.Fichero;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 * @param Clase Aplicacion: Clase principal que gestiona la aplicación y
 *              orquesta otras clases.
 */

public class Aplicacion {

	public static void main(String[] args) {
		GestionProducto gestionProductos = new GestionProducto();
		try {
			gestionProductos.cargarProductos();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Fichero f = new Fichero();

		Menu.Mensaje_Inicial();
		boolean continuar = true;
		Scanner sc = new Scanner(System.in);

		while (continuar) {
			Menu.Opciones_Menu();
			int opcion = -1;

			// Leer y validar la entrada del usuario
			while (true) {
				System.out.print("Ingrese una opción: ");
				String input = sc.nextLine().trim();

				try {
					opcion = Integer.parseInt(input);
					break; // Salir del bucle si la entrada es válida
				} catch (NumberFormatException e) {
					System.out.println("Entrada no válida. Por favor, ingrese un número.");
				}
			}

			switch (opcion) {
			case 1:
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				break;

			case 2:
				GestionProducto.realizarCompra(gestionProductos, f, sc);
				break;

			case 3:
				// Otras opciones si es necesario
				break;

			default:
				continuar = false;
				break;
			}
		}

		Menu.Mensaje_Fin();
		sc.close();
	}
}

//		GestionProducto gestionProductos = new GestionProducto();
//		try {
//			gestionProductos.cargarProductos();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		Fichero f = new Fichero();
//
//		Menu.Mensaje_Inicial();
//		boolean continuar = true;
//		do {
//			Menu.Opciones_Menu();
//			switch (Leer.datoInt()) {
//			case 1:
//				System.out.println(gestionProductos.mostrarProductosCatalogo());
//				continuar = false;
//				break;
//
//			case 2:
//				boolean pagar = false;
//				System.out.println(gestionProductos.mostrarProductosCatalogo());
//				Menu.seguirComprando_Pagar();
//				Scanner sc = new Scanner(System.in);
//				do {
//					System.out.println("Escriba ID del producto: ");
//					int productoID = sc.nextInt();// Leer.datoInt(); NO FUNCIONA!
//					System.out.println("Escriba la cantidad del producto seleccionado: ");
//					int cantidadProducto = Leer.datoInt();
//					gestionProductos.agregarCesta(productoID, cantidadProducto);
//					Menu.seguirComprando_Pagar();
//					String opcionPagar = Leer.datoString();
//					if (opcionPagar.equalsIgnoreCase("pagar")) {
//						pagar = true;
//					} else if (opcionPagar.equalsIgnoreCase("cancelar")) {
//						pagar = false;
//					}
//				} while (!pagar);
//				Cliente cliente = new Cliente();
//				PagarTarjeta.pagar(cliente);
//				
//				//SUBIR TIIKET BD
//				
//				
//				String tiketAUX = gestionProductos.crearTiket();
//				System.out.println(tiketAUX);
//				Menu.deseaTiket();
//				String opcionTiket = Leer.datoString();
//				if (opcionTiket.equals("si")) {
//					f.escribirFichero(tiketAUX);
//				} else if (opcionTiket.equals("no")) {
//				}
//				continuar = false;
//				break;
//
//			case 3:
//
//				break;
//			default:
//				// Se sale del programa
//				continuar = false;
//			}
//
//		} while (continuar);
//
//		Menu.Mensaje_Fin();
//
//	}