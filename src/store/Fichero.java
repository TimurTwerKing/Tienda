package store;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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

	public void escribirFichero() {
		try {
			// Añadir flag a true para no machacar contenido del fichero de escritura
			fichero = new FileWriter(ruta, false);
			pw = new PrintWriter(fichero);

			for (int i = 0; i < 10; i++) {
				pw.println("Papel " + i);
			}

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

	public static void muestraContenido(String archivo) throws FileNotFoundException, IOException {
		String cadena;
		FileReader fichero = new FileReader(archivo);
		BufferedReader buffer = new BufferedReader(fichero);
		while ((cadena = buffer.readLine()) != null) {
			System.out.println(cadena);
		}
		buffer.close();
		fichero.close();
	}

}
