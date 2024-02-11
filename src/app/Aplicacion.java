/**
 * 
 */
package app;

import leer.Leer;
import menu.Menu;
import productos.Cine;
import productos.Musica;
import productos.Videojuego;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 * @param Clase Aplicacion: Clase principal que gestiona la aplicación y
 *              orquesta otras clases.
 */

public class Aplicacion {

	public static void main(String[] args) {
		Cine productoCine1 = new Cine("Rambo", 4.95f, 5, true);
		Musica productoMusica1 = new Musica("Slayer", 6.66f, 6, true);
		Videojuego ProductoVideojuego1 = new Videojuego("Mafia", 9.95f, 2, true);

		Menu.Mensaje_Inicial();

		boolean continuar = true;

		do {

			Menu.Opciones_Menu();

			switch (Leer.datoInt()) {
			case 1:
				productoCine1.mostrarDatos();
				productoMusica1.mostrarDatos();
				ProductoVideojuego1.mostrarDatos();
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