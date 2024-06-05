package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Clase Leer: Contiene métodos para la lectura de datos desde la consola.
 * Utilizada para la entrada de datos por teclado.
 * 
 * @author Timur Bogach
 * @date 11 feb 2024
 */
public class Leer {

	/**
	 * Limpia el buffer de entrada.
	 */
	private static void limpiarBuffer() {
		try {
			while (System.in.available() > 0) {
				System.in.read();
			}
		} catch (IOException e) {
			System.out.println("Error al limpiar el buffer: " + e.getMessage());
		}
	}

	/**
	 * Lee una línea de texto desde la consola.
	 *
	 * @return El texto ingresado por el usuario.
	 */
	public static String datoString() {
		String sdato = "";
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader flujoE = new BufferedReader(isr);
			sdato = flujoE.readLine().trim();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return sdato;
	}

	/**
	 * Lee un entero desde la consola.
	 *
	 * @return El entero ingresado por el usuario, o vuelve a pedirlo si no es un
	 *         entero válido.
	 */
	public static int datoInt() {
		while (true) {
			String input = datoString();
			try {
				return Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
			}
		}
	}

	/**
	 * Lee un número de punto flotante desde la consola.
	 *
	 * @return El número de punto flotante ingresado por el usuario, o vuelve a
	 *         pedirlo si no es válido.
	 */
	public static float datoFloat() {
		while (true) {
			String input = datoString();
			try {
				return Float.parseFloat(input);
			} catch (NumberFormatException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número decimal.");
			}
		}
	}

	/**
	 * Lee un carácter desde la consola.
	 *
	 * @return El carácter ingresado por el usuario.
	 */
	public static char datoChar() {
		while (true) {
			String input = datoString();
			if (input.length() == 1) {
				return input.charAt(0);
			} else {
				System.out.println("Entrada no válida. Por favor, ingrese un solo carácter.");
			}
		}
	}

	/**
	 * Lee un número largo desde la consola.
	 *
	 * @return El número largo ingresado por el usuario, o vuelve a pedirlo si no es
	 *         válido.
	 */
	public static long datoLong() {
		while (true) {
			String input = datoString();
			try {
				return Long.parseLong(input);
			} catch (NumberFormatException e) {
				System.out.println("Entrada no válida. Por favor, ingrese un número largo.");
			}
		}
	}

	/**
	 * Lee un valor booleano desde la consola.
	 * 
	 * @return El valor booleano ingresado por el usuario, o vuelve a pedirlo si no
	 *         es válido.
	 */
	public static boolean datoBoolean() {
		while (true) {
			String input = datoString().toLowerCase();
			if (input.equals("true") || input.equals("false")) {
				return Boolean.parseBoolean(input);
			} else {
				System.out.println("Entrada no válida. Por favor, ingrese 'true' o 'false'.");
			}
		}
	}
}
