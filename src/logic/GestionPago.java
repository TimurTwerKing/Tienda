package logic;

import java.sql.Connection;
import java.util.NoSuchElementException;
import java.util.Scanner;

import modelo.Cliente;

/**
 * Clase para gestionar el pago de los productos en la cesta.
 * 
 * @autor Timur Bogach
 * @date 14 may 2024
 */
public class GestionPago {

	/**
	 * Constructor de la clase.
	 * 
	 * @param gestionProducto La instancia de GestionProducto.
	 * @param gestionPedido   La instancia de GestionPedido.
	 */
	public GestionPago(GestionProducto gestionProducto, GestionPedido gestionPedido, Connection conn) {

	}

	/**
	 * Método para seleccionar el método de pago.
	 * 
	 * @param cliente El cliente que realiza el pago.
	 * @param sc      El objeto Scanner para leer la entrada del usuario.
	 */
	public void metodoDePago(Cliente cliente, Scanner sc) {
		boolean aux = true;
		while (aux) {
			System.out.println("Opciones a pagar:\n");
			System.out.println("1. Pago con tarjeta");
			System.out.println("2. Atras");
			int opcion = sc.nextInt();
			switch (opcion) {
			case 1:
				pagarConTarjeta(cliente, sc);
				aux = false;
				break;
			case 2:
				// Volver al menú anterior
				aux = false;
				break;
			default:
				System.out.println("Opción no válida. Intente de nuevo.");
				break;
			}
		}
	}

	/**
	 * Método para realizar el pago con tarjeta.
	 * 
	 * @param cliente El cliente que realiza el pago.
	 * @param sc      El objeto Scanner para leer la entrada del usuario.
	 */
	public void pagarConTarjeta(Cliente cliente, Scanner sc) throws NoSuchElementException, NumberFormatException {
		boolean tarjetaValida = false;
		while (!tarjetaValida) {
			try {
				sc.nextLine(); // Limpiar el buffer del scanner después de una excepción
				System.out.println("Introduzca su tarjeta con los espacios correspondientes: \nEj 3444 666666 55555");
				String tipo = sc.nextLine(); // Ejemplo: 3444 666666 55555
				tipo = tipo.replace(" ", "");
				int longitud = tipo.length();
				if (longitud == 15 || longitud == 16) {
					if (tipo.startsWith("3") && longitud == 15) {
						System.out.println("Gracias por pagar con American Express\n");
						tarjetaValida = true;
					} else if (tipo.startsWith("4") && longitud == 16) {
						System.out.println("Gracias por pagar con Visa\n");
						tarjetaValida = true;
					} else if (tipo.startsWith("5") && longitud == 16) {
						System.out.println("Gracias por pagar con Master Card\n");
						tarjetaValida = true;
					} else {
						System.out.println("Número de tarjeta no válido. Inténtelo de nuevo.");
					}
				} else {
					System.out.println("Número de tarjeta no válido. Inténtelo de nuevo.");
				}
			} catch (Exception e) {
				System.out.println("Ha ocurrido un error con la entrada de la tarjeta. Por favor, inténtelo de nuevo.");
				sc.nextLine(); // Limpiar el buffer del scanner después de una excepción
			}
		}
	}
}