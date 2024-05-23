package menu;

/**
 * Clase para mostrar menús y mensajes en la consola.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Menu {

    public static void Mensaje_Inicial() {
        System.out.println("Bienvenido a la aplicación de gestión de productos.");
    }

    public static void Mensaje_Fin() {
        System.out.println("Gracias por utilizar la aplicación. ¡Hasta luego!\n");
    }

    public static void Mensaje_Fin_Compra() {
        System.out.println("¡Gracias por su compra! ¡Hasta luego!");
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Usuario");
        System.out.println("2. Administrador");
        System.out.println("3. Salir");
    }

    public static void mostrarMenuUsuario() {
        System.out.println("Seleccione una opción de usuario:");
        System.out.println("1. Login");
        System.out.println("2. Registro");
        System.out.println("3. Volver");
    }

    public static void mostrarMenuPrincipalUsuario() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Agregar productos a la cesta");
        System.out.println("2. Ver cesta");
        System.out.println("3. Volver");
    }

    public static void mostrarMenuAdministrador() {
        System.out.println("Seleccione una opción de administrador:");
        System.out.println("1. Agregar productos");
        System.out.println("2. Borrar productos");
        System.out.println("3. Eliminar usuarios");
        System.out.println("4. Guardar albarán");
        System.out.println("5. Volver");
    }

    public static void mostrarMenuRegistrar() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Comprar");
        System.out.println("2. Ver cesta");
        System.out.println("3. Volver");
    }

    public static void mostrarMenuComprar_Pagar() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Pagar");
        System.out.println("2. Volver");
    }

    public static void mostrarMenuCesta() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Realizar la compra");
        System.out.println("2. Borrar producto");
        System.out.println("3. Volver");
    }

    public static void mensajeAgregarProducto() {
        System.out.println("Agregar nuevo producto:");
    }

    public static void seguirComprando_Pagar() {
        System.out.println("Seleccione una opción:");
        System.out.println("1. Pagar");
        System.out.println("2. Seguir comprando");
        System.out.println("3. Atras");
    }

    public static void deseaTiket() {
        System.out.println("¿Desea imprimir el tiket?");
        System.out.println("1. Sí");
        System.out.println("2. No");
    }
}