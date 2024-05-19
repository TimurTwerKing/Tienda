package app;

import java.sql.SQLException;
import java.util.Scanner;
import data.Tiket;
import logic.GestionPago;
import logic.GestionPedido;
import logic.GestionProducto;
import menu.Menu;
import modelo.Cliente;
import util.Fichero;

/**
 * Clase principal que gestiona la aplicación y orquesta otras clases.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Aplicacion {
	public static void main(String[] args) {
		GestionProducto gestionProductos = new GestionProducto();
		GestionPedido gestionPedido = new GestionPedido(gestionProductos);
		GestionPago gestionPago = new GestionPago(gestionProductos, gestionPedido);
		Cliente cliente = new Cliente();
		Tiket tiket = new Tiket();

		try {
			gestionProductos.cargarProductos();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		Fichero fichero = new Fichero();

		Menu.Mensaje_Inicial();
		boolean continuar = true;
		Scanner sc = new Scanner(System.in);

		while (continuar) {
			Menu.Opciones_Menu();
			int opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				System.out.println(gestionProductos.mostrarProductosCatalogo());
				break;
			case 2:
				gestionPedido.realizarPedido(gestionPago, cliente, fichero, sc, tiket);//TODO EL TIKET!!!
				break;
			case 3:
				double total = gestionPedido.mostrarImporteTotal();
				System.out.println("El importe total de la compra actual es: " + total + " euros.");
				break;
			default:
				continuar = false;
				break;
			}
		}

		Menu.Mensaje_Fin();
		sc.close();
	}
}
