/**
 * 
 */
package app;

import java.util.ArrayList;

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

		ArrayList<Producto> catalogo = new ArrayList<Producto>();

		catalogo.add(productoCine1);
		catalogo.add(productoMusica1);
		catalogo.add(ProductoVideojuego1);

		GestionProducto gestion = new GestionProducto();

		Menu.Mensaje_Inicial();

		boolean continuar = true;

		do {

			Menu.Opciones_Menu();

			switch (Leer.datoInt()) {
			case 1:
				gestion.mostrarProductos(catalogo);
				continuar = false;
				break;
			case 2:

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