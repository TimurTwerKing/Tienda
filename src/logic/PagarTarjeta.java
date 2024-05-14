package logic;

import java.util.Scanner;
import java.util.NoSuchElementException;
import user.Cliente;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
 */
public class PagarTarjeta {

	public static void pagar(Cliente cliente) throws NoSuchElementException, NumberFormatException {
		Scanner sc = new Scanner(System.in);
		boolean tarjetaValida = false;

		do {
			try {
				System.out.println("Introduzca su tarjeta con los espacios correspondientes: Ej 3444 666666 55555");
				String tipo = sc.nextLine(); // Ejemplo: 3444 666666 55555
				tipo = tipo.replace(" ", "");
				int longitud = tipo.length();

				if (longitud == 15 || longitud == 16) {
					if (tipo.startsWith("3") && longitud == 15) {
						System.out.println("Gracias por pagar con American Express");
						tarjetaValida = true;
					} else if (tipo.startsWith("4") && longitud == 16) {
						System.out.println("Gracias por pagar con Visa");
						tarjetaValida = true;
					} else if (tipo.startsWith("5") && longitud == 16) {
						System.out.println("Gracias por pagar con Master Card");
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
		} while (!tarjetaValida);

		sc.close();
	}

}
