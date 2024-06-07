package menu;

/**
 * Clase para mostrar menús y mensajes en la consola.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Menu {

	/**
	 * Muestra el logo de la tienda online.
	 */
	public static void Logo() {
		System.out.println("************************************");
		System.out.println("*         TIENDA ONLINE            *");
		System.out.println("************************************");
		System.out.println("*   Videojuegos   Música   Cine    *");
		System.out.println("************************************");
		System.out.println("*       [>]  [♫]  [☼]              *");
		System.out.println("************************************\n");
	}

	/**
	 * Muestra un mensaje de bienvenida inicial.
	 */
	public static void Mensaje_Inicial() {
		System.out.println("Bienvenido a la aplicación de gestión de productos.");
	}

	/**
	 * Muestra un mensaje de despedida al finalizar la aplicación.
	 */
	public static void Mensaje_Fin() {
		System.out.println("Gracias por utilizar la aplicación. ¡Hasta luego!\n");
	}

	/**
	 * Muestra un mensaje de agradecimiento tras la compra.
	 */
	public static void Mensaje_Fin_Compra() {
		System.out.println("¡Gracias por su compra! ¡Hasta luego!\n");
	}

	/**
	 * Muestra el menú principal con opciones de usuario y administrador.
	 */
	public static void mostrarMenuPrincipal() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Usuario");
		System.out.println("2. Administrador");
		System.out.println("3. Salir");
	}

	/**
	 * Muestra el menú de opciones para el usuario.
	 */
	public static void mostrarMenuUsuario() {
		System.out.println("Seleccione una opción de usuario:");
		System.out.println("1. Login");
		System.out.println("2. Registro");
		System.out.println("3. Volver");
	}

	/**
	 * Muestra el menú principal para el usuario tras el login.
	 */
	public static void mostrarMenuPrincipalUsuario() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Agregar productos a la cesta");
		System.out.println("2. Ver cesta");
		System.out.println("3. Volver");
	}

	/**
	 * Muestra el menú de opciones para el administrador.
	 */
	public static void mostrarMenuAdministrador() {
		System.out.println("Seleccione una opción de administrador:");
		System.out.println("1. Productos");
		System.out.println("2. Usuarios");
		System.out.println("3. Albaranes");
		System.out.println("4. Volver");
	}

	/**
	 * Muestra el menú de opciones para la gestión de productos.
	 */
	public static void mostrarMenuProductos() {
		System.out.println("Seleccione una opción para productos:");
		System.out.println("1. Crear producto nuevo");
		System.out.println("2. Dar de baja productos");
		System.out.println("3. Dar de alta productos");
		System.out.println("4. Volver");
	}

	/**
	 * Muestra el menú de opciones para la gestión de usuarios.
	 */
	public static void mostrarMenuUsuarios() {
		System.out.println("Seleccione una opción para usuarios:");
		System.out.println("1. Dar de baja usuarios");
		System.out.println("2. Dar de alta usuarios");
		System.out.println("3. Volver");
	}

	/**
	 * Muestra el menú de opciones para la gestión de albaranes.
	 */
	public static void mostrarMenuAlbaranes() {
		System.out.println("Seleccione una opción para albaranes:");
		System.out.println("1. Crear albarán");
		System.out.println("2. Ver albaranes");
		System.out.println("3. Volver");
	}

	/**
	 * Muestra el menú de opciones para la cesta de compras.
	 */
	public static void mostrarMenuCesta() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Realizar la compra");
		System.out.println("2. Borrar producto");
		System.out.println("3. Volver");
	}

	/**
	 * Muestra un mensaje para agregar un nuevo producto.
	 */
	public static void mensajeAgregarProducto() {
		System.out.println("Agregar nuevo producto:");
	}

	/**
	 * Muestra las opciones para seguir comprando o pagar.
	 */
	public static void seguirComprando_Pagar() {
		System.out.println("Seleccione una opción:");
		System.out.println("1. Pagar");
		System.out.println("2. Seguir comprando");
		System.out.println("3. Atras");
	}

	/**
	 * Pregunta al usuario si desea imprimir el ticket.
	 */
	public static void deseaTiket() {
		System.out.println("¿Desea imprimir el tiket?");
		System.out.println("1. Sí");
		System.out.println("2. No");
	}
}
