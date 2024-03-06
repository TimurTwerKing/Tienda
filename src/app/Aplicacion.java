/**
 * 
 */
package app;

import java.util.TreeMap;

import data.GestionProducto;
import leer.Leer;
import logic.Cine;
import logic.Musica;
import logic.Producto;
import logic.Videojuego;
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

		Producto productoCine1 = new Cine("Rambo", 4.95f, 5, true, "Accion", "Sylvester Stallone");
		Producto productoMusica1 = new Musica("Slayer", 6.66f, 6, true, "Thrash Metal", "Kerry Fuckin King");
		Producto ProductoVideojuego1 = new Videojuego("Mafia", 9.95f, 2, true, "Shooter", "2K");

		TreeMap<String, Producto> catalogoTreeMap = new TreeMap<>();
		GestionProducto gestionProductos = new GestionProducto();

		catalogoTreeMap.put(gestionProductos.calcularTiket(), productoCine1);
		catalogoTreeMap.put(gestionProductos.calcularTiket(), productoMusica1);
		catalogoTreeMap.put(gestionProductos.calcularTiket(), ProductoVideojuego1);

//		gestionProductos.agregarProducto(catalogoTreeMap.put(gestionProductos.calcularTiket(),new Producto("Rafghmbo", 4.95f, 5, true, "Accion", "Sylvester Stallone"));

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
				gestionProductos.mostrarTiket(catalogoTreeMap);
				
				
				
				
//				System.out.println(catalogoTreeMap.remove(catalogoTreeMap, gestionProductos));
								
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