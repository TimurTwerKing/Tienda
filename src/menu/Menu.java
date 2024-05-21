package menu;

public class Menu {

	public static void mostrarMenuPrincipal() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Usuario");
		System.out.println("2. Administrador");
		System.out.println("3. Salir");
	}

	public static void mostrarMenuUsuario() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Login");
		System.out.println("2. Registrar");
		System.out.println("3. Volver");
	}

	public static void mostrarMenuLogin() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Comprar");
		System.out.println("2. Ver carrito");
		System.out.println("3. Volver");
	}

	public static void mostrarMenuComprar() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Pagar");
		System.out.println("2. Volver");
	}

	public static void mostrarMenuCarrito() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Borrar producto");
		System.out.println("2. Volver");
	}

	public static void mostrarMenuRegistrar() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Comprar");
		System.out.println("2. Ver carrito");
		System.out.println("3. Volver");
	}

	public static void mostrarMenuAdministrador() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Agregar productos");
		System.out.println("2. Borrar productos");
		System.out.println("3. Eliminar usuarios");
		System.out.println("4. Volver");
	}

	/**
	 * Muestra el mensaje inicial de la aplicación.
	 */
	public static void Mensaje_Inicial() {
		System.out.println("\nBienvenido a la Tienda Online\n");
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
	 * Mensaje para agregar un producto con opciones de categoría.
	 */
	public static void mensajeAgregarProducto() {
		System.out.println("Ingrese los detalles del producto a agregar:");
		System.out.println("Nombre:");
		System.out.println("Precio:");
		System.out.println("Cantidad:");
		System.out.println("Stock (true/false):");
		System.out.println("Género:");
		System.out.println("ID Categoría: 1 Cine, 2 Videojuego, 3 Música");
	}

	/**
	 * Pregunta al usuario si desea un ticket.
	 */
	public static void deseaTiket() {
		System.out.println("¿Desea una copia de su ticket? si/no");
	}
}
