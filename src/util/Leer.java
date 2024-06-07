package util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Clase Leer: Contiene métodos para la lectura de datos desde la consola.
 * Utilizada para la entrada de datos por teclado.
 * 
 * @autor Timur Bogach
 * @date 11 feb 2024
 */
public class Leer {

	/**
	 * Limpia el buffer de entrada.
	 */
	public static void limpiarBuffer() {
		try {
			while (System.in.available() > 0) {
				System.in.read();
			}
		} catch (IOException e) {
			System.out.println("Error al limpiar el buffer: " + e.getMessage());
		}
	}

	/**
	 * Lee una fecha del usuario en el formato YYYY-MM-DD y la valida.
	 * 
	 * @return La fecha leída como cadena.
	 */
	public static String datoFechaString() {
		while (true) {
			String fecha = datoString();
			if (validarFecha(fecha)) {
				return fecha;
			} else {
				System.out.println("Formato de fecha incorrecto. Intente de nuevo.");
				System.out.println("Ingrese la fecha (YYYY-MM-DD):");
			}
		}
	}

	/**
	 * Valida que una cadena tenga el formato de fecha YYYY-MM-DD.
	 * 
	 * @param fecha La cadena a validar.
	 * @return true si la fecha es válida, false en caso contrario.
	 */
	public static boolean validarFecha(String fecha) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setLenient(false);
		try {
			sdf.parse(fecha);
			return true;
		} catch (ParseException e) {
			return false;
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

	/**
	 * Lee un número de punto flotante con dos decimales y positivo desde la
	 * consola.
	 * 
	 * @return El número ingresado por el usuario, redondeado a dos decimales y
	 *         positivo.
	 */
	public static double leerDoubleDosDecimales() {
		while (true) {
			try {
				String doubleString = datoString();
				double myDouble = Double.parseDouble(doubleString);
				if (myDouble < 0) {
					System.out.println("El número debe ser positivo. Por favor, intente de nuevo.");
				} else {
					myDouble = Math.round(myDouble * 100) / 100d;
					return myDouble;
				}
			} catch (NumberFormatException ex) {
				System.out.println("Entrada no válida. Por favor, introduzca un número.");
			}
		}
	}

	/**
	 * Lee una cadena no vacía desde la consola.
	 * 
	 * @return La cadena ingresada por el usuario.
	 */
	public static String leerStringNoVacio() {
		while (true) {
			String aux = datoString();
			if (!aux.isEmpty() && !aux.isBlank()) {
				return aux;
			} else {
				System.out.println("Este campo es obligatorio.");
			}
		}
	}

	/**
	 * Lee un número de teléfono válido desde la consola.
	 * 
	 * @return El número de teléfono ingresado por el usuario.
	 */
	public static String leerNumTelefono() {
		while (true) {
			String telefono = datoString();
			boolean condicionCumplida = ((telefono.startsWith("6") || telefono.startsWith("7")
					|| telefono.startsWith("8") || telefono.startsWith("9")) && (telefono.length() == 9))
					&& !telefono.matches(".*\\D.*");

			if (condicionCumplida) {
				return telefono;
			} else {
				System.out.println("Por favor, introduzca un número de teléfono válido.");
			}
		}
	}

	/**
	 * Método para validar una entrada de tipo email.
	 * 
	 * @return La entrada validada.
	 */
	public static String validarEntradaMail() {
		String input;
		do {
			input = Leer.datoString();
			if (!input.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
				System.out.println("Correo no válido. Por favor, intente nuevamente.");
			}
		} while (!input.matches("^[A-Za-z0-9+_.-]+@(.+)$"));
		return input;
	}
}
