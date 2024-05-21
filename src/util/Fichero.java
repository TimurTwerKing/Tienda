package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Clase para manejar la lectura y escritura en ficheros.
 * 
 * @autor Timur Bogach
 * @date 6 mar 2024
 */
public class Fichero {

	private String ruta = "C:/Users/Timur/projects/Tienda/FICHERO/RutasFichero.txt";
	private FileWriter fichero = null;
	private PrintWriter pw = null;

	/**
	 * Escribe en un fichero.
	 * 
	 * @param contenido El contenido a escribir en el fichero.
	 */
	public void escribirFichero(String contenido) {
		try {
			// Añadir flag a true para no machacar contenido del fichero de escritura
			fichero = new FileWriter(leerRuta(ruta), false);
			pw = new PrintWriter(fichero);

			pw.println(contenido);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero) {
					System.out.println("La escritura en el fichero se ha completado con éxito.");
					fichero.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * Muestra el contenido de un fichero.
	 * 
	 * @param ruta La ruta del fichero.
	 * @return El contenido del fichero.
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public String muestraContenido(String ruta) throws FileNotFoundException, IOException {
		FileReader fichero = new FileReader(ruta);
		BufferedReader buffer = new BufferedReader(fichero);
		StringBuilder cadena = new StringBuilder();
		String linea;

		while ((linea = buffer.readLine()) != null) {
			cadena.append(linea).append("\n");
		}

		buffer.close();
		fichero.close();

		return cadena.toString();
	}

	/**
	 * Lee la ruta desde un fichero.
	 * 
	 * @param ruta La ruta del fichero.
	 * @return La ruta leída.
	 * @throws IOException
	 */
	public String leerRuta(String ruta) throws IOException {
		String rutaAux = "";
		// Creamos un FileReader especificando la ruta
		// en la que se encuentra nuestro archivo de configuración
		FileReader file = new FileReader(ruta);

		// Creamos el Objeto Scanner a partir del FileReader creado
		Scanner scanner = new Scanner(file);

		// Este bucle nos va a permitir recorrer nuestro fichero
		// hasta el final
		while (scanner.hasNextLine()) {
			// Obtenemos la siguiente línea del fichero
			rutaAux = scanner.nextLine();
		}

		// Cerramos scanner y fichero
		scanner.close();
		file.close();
		return rutaAux;
	}
}
