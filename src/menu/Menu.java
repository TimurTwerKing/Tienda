package menu;

/**
 * Clase para mostrar los menús e instrucciones en la consola.
 */
public class Menu {

	/**
	 * Muestra el mensaje inicial de la aplicación.
	 */
	public static void Mensaje_Inicial() {
		System.out.println("Bienvenido a la Tienda Online");
	}

	/**
	 * Muestra las opciones del menú principal.
	 */
	public static void Opciones_Menu() {
		System.out.println("\nSeleccione una opción:");
		System.out.println("1. Mostrar productos");
		System.out.println("2. Realizar pedido");
		System.out.println("3. Mostrar importe total");
		System.out.println("4. Salir");
	}

	/**
	 * Muestra las opciones de seguir comprando o pagar.
	 */
	public static void seguirComprando_Pagar() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Pagar");
		System.out.println("2. Seguir comprando");
		System.out.println("3. Cancelar compra");
	}

	/**
	 * Muestra el mensaje de finalización de la aplicación.
	 */
	public static void Mensaje_Fin() {
		System.out.println("Gracias por usar la Tienda Online");
	}

	/**
	 * Pregunta al usuario si desea un ticket.
	 */
	public static void deseaTiket() {
		System.out.println("¿Desea un ticket? (si/no)");
	}
}
