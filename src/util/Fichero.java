package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @autor Timur Bogach
 * @date 6 mar 2024
 * 
 */
public class Fichero {

//Metodo : leer ficher, escribiir fichero
	String ruta = "C:/Users/Timur/projects/Tienda/FICHERO/RutasFichero.txt";
	FileWriter fichero = null;
	PrintWriter pw = null;

	public void escribirFichero(String pepe) {

		try {
			// Añadir flag a true para no machacar contenido del fichero de escritura
			fichero = new FileWriter(leerRuta(ruta), false);
			pw = new PrintWriter(fichero);

			pw.println(pepe);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				// Nuevamente aprovechamos el finally para
				// asegurarnos que se cierra el fichero.
				if (null != fichero) {

					System.out.println("La escritura en el fichero escritura.txt se ha completado con exito");
					fichero.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public String muestraContenido(String ruta) throws FileNotFoundException, IOException {

		FileReader fichero = new FileReader(ruta);
		BufferedReader buffer = new BufferedReader(fichero);
		String cadena = buffer.toString();

		buffer.close();
		fichero.close();

		return cadena;
	}

	public String leerRuta(String ruta) throws IOException {
		String rutaAux = "";
		// Creamos un FileReader especificando la ruta
		// en la que se encuentra nuestro archivo de configuracion
		FileReader file = new FileReader(ruta);

		// Creamos el Objeto Scanner a partir del FileReader creado
		Scanner scanner = new Scanner(file);

		// Este bucle nos va a permitir recorrer nuestro fichero
		// hasta el final
		while (scanner.hasNextLine()) {
			// Obtenemos la siguiente linea del fichero
			Scanner line = new Scanner(scanner.nextLine());

			// Especificamos el separador introducido entre variable y
			// valor en nuestro archivo de configuracion.

			rutaAux = line.next();
			line.close();
		}

		// Cerramos scanner y fichero
		scanner.close();
		file.close();
		return rutaAux;
	}
}
