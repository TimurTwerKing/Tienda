package logic;

import java.sql.Connection;
import java.util.NoSuchElementException;

import modelo.Cliente;
import util.Leer;

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
	 * @param conn            La conexión a la base de datos.
	 */
	public GestionPago(GestionProducto gestionProducto, GestionPedido gestionPedido, Connection conn) {
		// Constructor sin implementación específica
	}

	/**
	 * Método para seleccionar el método de pago.
	 * 
	 * @param cliente El cliente que realiza el pago.
	 */
	public void metodoDePago(Cliente cliente) {
		boolean aux = true;
		while (aux) {
			System.out.println("Opciones a pagar:\n");
			System.out.println("1. Pago con tarjeta");
			System.out.println("2. Atras");
			int opcion = Leer.datoInt();
			switch (opcion) {
			case 1:
				pagarConTarjeta(cliente);
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
	 */
	public void pagarConTarjeta(Cliente cliente) throws NoSuchElementException, NumberFormatException {
		boolean tarjetaValida = false;
		while (!tarjetaValida) {
			try {
				System.out.println("Introduzca su tarjeta con los espacios correspondientes: \nEj: 3444 666666 55555");
				String tipo = Leer.datoString().replace(" ", ""); // Ejemplo: 3444 666666 55555
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
				Leer.limpiarBuffer(); // Limpiar el buffer después de una excepción
			}
		}
	}
}
