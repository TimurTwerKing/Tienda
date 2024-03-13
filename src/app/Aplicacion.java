/**
 * 
 */
package app;

import data.GestionProducto;
import leer.Leer;
import menu.Menu;

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

		gestionProductos.cargarProductos();
		Menu.Mensaje_Inicial();

//		boolean pagar = false;
		boolean continuar = true;
		do {

			Menu.Opciones_Menu();

			switch (Leer.datoInt()) {
			case 1:
				gestionProductos.mostrarProductosCatalogo();
				continuar = false;
				break;
			case 2:

				Menu.opcionMostrarProductosDisponibles();
//				do {
					System.out.println(
							"\n****************************************\nMOSTRAMOS CATALOGO\n********************************************\n");
					gestionProductos.mostrarProductosCatalogo();
					//AGREGAMOS A LA CESTA
					gestionProductos.agregarA_CestaPorNombre("Rambo", 3);
					gestionProductos.agregarA_CestaPorNombre("Slayer", 5);

					System.out.println(
							"\n********************************************\nMOSTRAR LA CESTA\n********************************************\n");
					gestionProductos.mostrarProductosCesta();
					// VENDEMOS
					gestionProductos.venderProductosDesdeCesta();
					System.out.println(
							"\n****************************************\nMOSTRAMOS LO QUE QUEDA DE CATALOGO\n********************************************\n");
					gestionProductos.mostrarProductosCatalogo();

//				} while (pagar = false);

				continuar = false;
				break;
			case 3:

				break;
			default:
				// Se sale del programa
				continuar = false;
			}

		} while (continuar);

		Menu.Mensaje_Fin();

	}

}