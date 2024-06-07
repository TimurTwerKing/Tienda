package app;

import java.sql.Connection;
import java.sql.SQLException;

import logic.OrquestradorMenu;
import menu.Menu;
import util.Conexion;
import util.Leer;

/**
 * Clase principal que gestiona la aplicación y orquesta otras clases.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Aplicacion {

	public static void main(String[] args) {
		Connection conn = null;
		try {
			// Conectar a la base de datos
			conn = Conexion.conectar();
			OrquestradorMenu orquestrador = new OrquestradorMenu(conn);

			// Mostrar el logo y mensaje inicial
			Menu.Logo();
			Menu.Mensaje_Inicial();

			boolean continuar = true;
			while (continuar) {
				// Mostrar el menú principal
				Menu.mostrarMenuPrincipal();

				// Leer opción del usuario con validación
				int opcionPrincipal = Leer.datoInt();

				switch (opcionPrincipal) {
				case 1:
					// Menú para usuarios
					orquestrador.manejarMenuUsuario();
					break;
				case 2:
					// Menú para administradores
					orquestrador.manejarMenuAdministrador();
					break;
				case 3:
					// Salir de la aplicación
					continuar = false;
					break;
				default:
					// Opción no válida
					System.out.println("Opción no válida. Intente de nuevo.");
				}
			}
			// Mostrar mensaje de fin
			Menu.Mensaje_Fin();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
