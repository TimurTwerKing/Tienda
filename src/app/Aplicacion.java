/** 
 * 
 */
package app;

import java.util.TreeMap;

import data.GestionProducto;
import leer.Leer;
import logic.Producto;
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
		TreeMap<String, Producto> catalogo = new TreeMap<>();
		TreeMap<String, Producto> cesta = new TreeMap<>();
		GestionProducto gestionProductos = new GestionProducto(catalogo);
		gestionProductos.cargarProductos(catalogo);
		Fichero f = new Fichero();
		
		Menu.Mensaje_Inicial();
		boolean continuar = true;
		do {
			Menu.Opciones_Menu();
			switch (Leer.datoInt()) {
			case 1:
				System.out.println(gestionProductos.mostrarProductosCatalogo(catalogo));
				continuar = false;
				break;

			case 2:
				boolean pagar = false;
				System.out.println(gestionProductos.mostrarProductosCatalogo(catalogo));
				Menu.seguirComprando_Pagar();
				do {
					System.out.println("Escriba nombre del producto: ");
					String nombrePorducto = Leer.datoString();
					System.out.println("Escriba la cantidad del producto seleccionado: ");
					Integer cantidadProducto = Leer.datoInt();
					gestionProductos.agregarA_CestaPorNombre(cesta, catalogo, nombrePorducto, cantidadProducto);
					Menu.seguirComprando_Pagar();
					String opcionPagar = Leer.datoString();
					if (opcionPagar.equalsIgnoreCase("pagar")) {
						pagar = true;
					}
				} while (!pagar);
				String tiketAUX = gestionProductos.venderProductosDesdeCesta(cesta, catalogo);
				System.out.println(tiketAUX);
				Menu.deseaTiket();
				String opcionTiket = Leer.datoString();
				if (opcionTiket.equals("si")) {
					f.escribirFichero(tiketAUX);
				} else if (opcionTiket.equals("no")) {
				}
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