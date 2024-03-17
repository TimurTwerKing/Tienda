/**
 * 
 */
package menu;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Menu: Proporciona métodos para mostrar mensajes relacionados con
 *       la tienda virtual.
 */

public class Menu {
	/**
	 * Muestra un mensaje de bienvenida a la tienda virtual. Informa sobre los
	 * productos disponibles y las reglas de venta.
	 */
	/**
	 * @todo Implementar funccion para mostrar productos disponibles al usuario
	 */

	public static void mostrarProductosCesta() {
		System.out.println("\n Su cesta de compra: \n");
	}

	public static void deseaTiket() {
		System.out.println("\n Desea la copia de su Tiket? \nsi/no");
	}

	public static void comprarProductosDisponibles() {
		System.out.println(
				"\nSeleccione productos para agregar a su cesta de compra. \n Escriba nombre del producto deseado y despues "
						+ "la cantidad. CCunado quiera pagar, esciba pagar:\n");
	}

	public static void seguirComprandoPagar() {
		System.out.println(
				"\n Si desea seguir comprando presione INTRO y siga agregando productos a su cesta. \n Escriba pagar si quiere terminar su compra.");
	}

	public static void Mensaje_Inicial() {
		System.out.println(
				"Bienvenido a la tienda virtual\n\n" + "El programa simula una tienda que vende juegos, música y cine\n"
						+ "Solamente se pueden vender productos si aparecen disponibles en la tienda\n");
	}

	/**
	 * Muestra las opciones del menú de la tienda virtual. Permite al usuario
	 * seleccionar acciones como mostrar productos, comprar, mostrar la caja o
	 * salir.
	 */
	public static void Opciones_Menu() {
		System.out.println("\nSeleccione qué desea realizar:\n\n" + "\t1. Mostrar productos. (Listar productos)\n"
				+ "\t2. Comprar productos\n" + "\t3. Mostrar caja. (Importe total de la compra actual)\n"
				+ "\tSALIR --> Pulse cualquier otro número\n");
	}

	/**
	 * Muestra un mensaje de despedida al usuario.
	 */
	public static void Mensaje_Fin() {
		System.out.println("\n---- Gracias por usar este software. ----");
	}
}
