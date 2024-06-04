package util;

import java.io.BufferedInputStream;
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
		limpiarBuffer();
		String sdato = " ";
		try {
			InputStreamReader isr = new InputStreamReader(System.in);
			BufferedReader flujoE = new BufferedReader(isr);
			sdato = flujoE.readLine();
		} catch (IOException e) {
			System.out.println("Error: " + e.getMessage());
		}
		return sdato;
	}

	/**
	 * Lee un entero desde la consola.
	 *
	 * @return El entero ingresado por el usuario y de no ser asi lo devuelve como
	 *         tal.
	 */
	public static int datoInt() {
		limpiarBuffer();
		return Integer.parseInt(datoString());
	}

	/**
	 * Lee un número de punto flotante desde la consola y de no ser asi lo devuelve
	 * como tal.
	 *
	 * @return El número de punto flotante ingresado por el usuario.
	 */
	public static float datoFloat() {
		limpiarBuffer();
		return Float.parseFloat(datoString());
	}

	/**
	 * Lee un carácter desde la consola.
	 *
	 * @return El carácter ingresado por el usuario.
	 */
	public static char datoChar() {
		limpiarBuffer();
		char c = ' ';
		try {
			java.io.BufferedInputStream b = new BufferedInputStream(System.in);
			c = (char) b.read();
		} catch (IOException e) {
			System.out.println("Error al leer");
			e.printStackTrace();
		}
		return c;
	}

	/**
	 * Lee un número largo desde la consola.
	 *
	 * @return El número largo ingresado por el usuario y de no ser asi lo devuelve
	 *         como tal.
	 */
	public static long datoLong() {
		limpiarBuffer();
		return Long.parseLong(datoString());
	}

	/**
	 * Lee un valor booleano desde la consola.
	 * 
	 * @return El valor booleano ingresado por el usuario.
	 */
	public static boolean datoBoolean() {
		limpiarBuffer();
		boolean b = false;
		try {
			String sdato = datoString().toLowerCase();
			if (sdato.equals("true") || sdato.equals("false")) {
				b = Boolean.parseBoolean(sdato);
			} else {
				System.out.println("Entrada no válida. Por favor, ingrese 'true' o 'false'.");
				b = datoBoolean(); // Recurre hasta obtener una entrada válida
			}
		} catch (Exception e) {
			System.out.println("Error al leer el booleano");
			e.printStackTrace();
		}
		return b;
	}
}
