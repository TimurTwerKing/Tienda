package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase para gestionar la conexión a la base de datos.
 * 
 * @autor Timur Bogach
 * @date 14 may 2024
 */
public class Conexion {

	private static final String NOMBRE_BD = "tienda";
	private static final String UBICACION = "localhost";
	private static final String PUERTO = "7777";
	private static final String USUARIO = "root";
	private static final String CLAVE = "33142";

	// Para versión mysql-conector-java-5.1.6.jar + mysql Server 5.7
	private static final String CONTROLADOR = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://" + UBICACION + ":" + PUERTO + "/" + NOMBRE_BD
			+ "?useUnicode=true&characterEncoding=utf-8";

	static {
		try {
			// Cargar el controlador de MySQL
			Class.forName(CONTROLADOR);
		} catch (ClassNotFoundException e) {
			System.out.println("Error al cargar el controlador");
			e.printStackTrace();
		}
	}

	/**
	 * Establece y retorna una conexión a la base de datos.
	 * 
	 * @return La conexión a la base de datos.
	 */
	public static Connection conectar() {
		Connection conexion = null;
		try {
			// Establecer la conexión
			conexion = DriverManager.getConnection(URL, USUARIO, CLAVE);
			System.out.println("Conexión correctamente establecida con la base de datos " + NOMBRE_BD);
		} catch (SQLException e) {
			System.out.println("Error en la conexión");
			e.printStackTrace();
		}
		return conexion;
	}
}
