/**
 * 
 */
package util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Timur Bogach
 * @date 11 feb 2024
 * 
 *       Clase Leer: Contiene métodos para la lectura de datos desde la consola.
 *       Utilizada para la entrada de datos por teclado.
 */

public class Leer {

	/**
	 * Lee una línea de texto desde la consola.
	 *
	 * @return El texto ingresado por el usuario.
	 */
	public static String datoString() {
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
		return Integer.parseInt(datoString());
	}

	/**
	 * Lee un número de punto flotante desde la consola y de no ser asi lo devuelve
	 * como tal.
	 *
	 * @return El número de punto flotante ingresado por el usuario.
	 */
	public static float datoFloat() {
		return Float.parseFloat(datoString());
	}

	/**
	 * Lee un carácter desde la consola.
	 *
	 * @return El carácter ingresado por el usuario.
	 */
	public static char datoChar() {
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
		return Long.parseLong(datoString());
	}

}