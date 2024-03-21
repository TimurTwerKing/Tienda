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

		boolean continuar = true;
		do {
			Menu.Opciones_Menu();
			switch (Leer.datoInt()) {
			case 1:
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				continuar = false;
				break;
			case 2:
				gestionProductos.menuTiket(gestionProductos.menuCompra());
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