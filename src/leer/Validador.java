package leer;

import java.util.Scanner;

public class Validador {
	private Scanner sc;

	/**
	 * Constructor de la clase Validador que instancia a la clase Scanner
	 */
	public Validador() {
		this.sc = new Scanner(System.in);
	}

	/**
	 * 
	 * @return Doble con dos decimales y positivo
	 */
	public double leerDoubleDosDecimales() {
		while (true) {
			try {
				String doubleString = this.sc.next();
				double myDouble = Double.parseDouble(doubleString);
				if (myDouble < 0) {
					System.out.println("MAAAAL");
				} else {
					myDouble = Math.round(myDouble * 100) / 100d;
					return myDouble;
				}
			} catch (NumberFormatException ex) {
				System.out.println("Por favor, introduzca un numero");
			}
		}
	}

	// Método para solicitar al usuario un número entero válido
	public static int pedirInteger(Scanner scanner) {
		int numero;
		do {
			System.out.print("Ingrese un número ID valido: ");
			while (!scanner.hasNextInt()) {
				System.out.println("¡Error! Debe ingresar un número ID valido.");
				System.out.print("Ingrese un número entero: ");
				scanner.next(); // Limpiar el buffer de entrada
			}
			numero = scanner.nextInt();
		} while (numero <= 0); // Puedes modificar esta condición según tus necesidades

		return numero;
	}

	/**
	 * 
	 * @return
	 */
	public String leerStringNoVacio() {
		while (true) {
			String aux = this.sc.nextLine();
			if (!aux.isEmpty() && !aux.isBlank()) {
				return aux;
			} else {
				System.out.println("Ingrese una cadena valida");
			}
		}
	}

	public String leerNumTelefono() {
		while (true) {
			String telefono = this.sc.next();
			boolean condicionCumplida = ((telefono.startsWith("6") || telefono.startsWith("7")
					|| telefono.startsWith("8") || telefono.startsWith("9")) && (telefono.length() == 9))
					&& !telefono.matches(".*\\D.*");

			if (condicionCumplida) {
				return telefono;
			} else {
				System.out.println("Por favor, introduzca un telefono valido");
			}
		}
	}
}
