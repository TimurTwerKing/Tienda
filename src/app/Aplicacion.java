package app;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import data.Tiket;
import logic.GestionAlbaran;
import logic.GestionCliente;
import logic.OrquestradorMenu;
import logic.GestionPago;
import logic.GestionPedido;
import logic.GestionProducto;
import menu.Menu;
import modelo.Cliente;
import util.Conexion;
import util.Fichero;
import util.Leer;

/**
 * Clase principal que gestiona la aplicación y orquesta otras clases.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Aplicacion {
	public static void main(String[] args) throws SQLException {
		Connection conn = null;
		Scanner sc = new Scanner(System.in);
		try {
			conn = Conexion.conectar();
			OrquestradorMenu orquestrador = new OrquestradorMenu(conn, sc);

			// Inicialización de las clases de gestión
			GestionProducto gestionProductos = new GestionProducto(conn);
			GestionPedido gestionPedido = new GestionPedido(conn);
			GestionPago gestionPago = new GestionPago(gestionProductos, gestionPedido, conn);
			GestionCliente gestionCliente = new GestionCliente(conn);
			GestionAlbaran gestionAlbaran = new GestionAlbaran();
			Cliente cliente = new Cliente("C011", "Jhoonny", "Meentero", "Carrera 15 #123", "Ciudad", "Provincia",
					"España", "28090", "601234567", "jhoonny.meentero@example.com",
					"Cliente frecuente de productos de tecnología", 11);

			Tiket tiket = new Tiket();
			Fichero fichero = new Fichero();

			Menu.Mensaje_Inicial();

			boolean continuar = true;
			while (continuar) {
				Menu.mostrarMenuPrincipal();

				int opcionPrincipal = Leer.datoInt();

				switch (opcionPrincipal) {
				case 1:
					// Menú para usuarios
					orquestrador.manejarMenuUsuario(gestionProductos, gestionPedido, gestionPago, gestionCliente,
							cliente, fichero, tiket);
					break;
				case 2:
					// Menú para administradores
					orquestrador.manejarMenuAdministrador(gestionProductos, gestionCliente, gestionAlbaran);
					break;
				case 3:
					// Salir de la aplicación
					continuar = false;
					break;
				default:
					System.out.println("Opción no válida. Intente de nuevo.");
				}
			}
			// Mostrar mensaje de finalización
			Menu.Mensaje_Fin();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// Cerrar la conexión porque hemos terminado
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			sc.close();
		}
	}
}
