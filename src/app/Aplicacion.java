/**
 * 
 */
package app;

import java.util.TreeMap;

import data.GestionProducto;
import leer.Leer;
import logic.Producto;
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

		TreeMap<String, Producto> catalogoTreeMap = new TreeMap<>();
		GestionProducto gestionProductos = new GestionProducto();

		gestionProductos.cargarProductos(catalogoTreeMap);
		Menu.Mensaje_Inicial();

		boolean continuar = true;

		do {

			Menu.Opciones_Menu();

			switch (Leer.datoInt()) {
			case 1:
				gestionProductos.mostrarProductos(catalogoTreeMap);
				continuar = false;
				break;
			case 2:
				System.out.println("Seleccione producto para su compra: \n");

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