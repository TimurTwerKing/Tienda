package data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import leer.Leer;
import logic.Cine;
import logic.Musica;
import logic.Producto;
import logic.Videojuego;
import menu.Menu;
import store.Fichero;

public class GestionProducto {
	// TreeMap para almacenar el catálogo y la cesta de productos
	private TreeMap<Integer, Producto> catalogo = new TreeMap<>();
	private TreeMap<Integer, Producto> cesta = new TreeMap<>();

	// Constructores
	public GestionProducto() {
	}

	public GestionProducto(TreeMap<Integer, Producto> catalogo, TreeMap<Integer, Producto> cesta) {
		this.catalogo = catalogo;
		this.cesta = cesta;
	}

	// Métodos
	// ==============================================

	/*
	 * Guía al usuario para la compra de productos
	 */
	public String menuCompra() {
		boolean pagar = false;
		System.out.println(mostrarProductosCatalogo());
		do {
			Menu.elegirProductoParaCesta();
			Integer KeyPorducto = Leer.datoInt();
			Menu.cantidadProductoParaCesta();
			Integer cantidadProducto = Leer.datoInt();
			agregarCestaID_Cantidad(KeyPorducto, cantidadProducto);
			Menu.seguirComprando_Pagar();
			String opcionPagar = Leer.datoString();
			if (opcionPagar.equalsIgnoreCase("pagar")) {
				pagar = true;
			}
		} while (!pagar);
		String tiketAUX = venderProductosDesdeCesta();
		return tiketAUX;
	}

	/*
	 * Muestra el ticket de compra y pregunta al usuario si desea guardarlo en un
	 * fichero
	 */
	public void menuTiket(String tiketAUX) {
		System.out.println(tiketAUX);
		String opcionTiket;
		do {
			Menu.deseaTiket();
			opcionTiket = Leer.datoString();
			if (opcionTiket.equals("si")) {
				Fichero f = new Fichero();
				f.escribirFichero(tiketAUX);
			} else if (opcionTiket.equals("no")) {
			} else {
				System.out.println("Opción no válida. Por favor, introduzca 'si' o 'no'.");
			}
		} while (!opcionTiket.equals("si") && !opcionTiket.equals("no"));
	}

	// Crea un ticket con la fecha y la hora de la compra, y un número
	// identificativo del mismo.

	public String crearTicket() {
		Date fecha = new Date();
		Random random = new Random();
		long randomNumber = Math.abs(random.nextLong() % 10000000000L);// 15num
		return "Ticket creado el " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha) + "\nNumero ID: "
				+ randomNumber + "\n";
	}

	// Comprueba si el producto existe en el catálogo
	public boolean comprobarNombreProductoCatalogo(int KeyProducto) {
		return this.catalogo.containsKey(KeyProducto);
	}

	// Comprueba si hay suficiente cantidad del producto en el catálogo
	public boolean comprobarCantidadProductoCatalogo(int KeyProducto, int cantidad) {
		Producto producto = this.catalogo.get(KeyProducto);
		return producto != null && cantidad <= producto.getCantidad();
	}

	// Comprueba si el producto y la cantidad especificada existen en el catálogo
	public boolean comprobarPreAgregarCesta(int KeyProducto, int cantidad) {
		while (!comprobarNombreProductoCatalogo(KeyProducto)) {
			Menu.elegirProductoParaCesta();
			KeyProducto = Leer.datoInt();
		}
		while (!comprobarCantidadProductoCatalogo(KeyProducto, cantidad)) {
			int cantidadDisponible = this.catalogo.get(KeyProducto).getCantidad();
			System.out.println("Actualmente hay " + cantidadDisponible + " unidades disponibles\n");
			Menu.cantidadProductoParaCesta();
			cantidad = Leer.datoInt();
		}
		return true;
	}

	// Agrega un producto con su cantidad correspondiente a la cesta
	public void agregarCestaID_Cantidad(int KeyProducto, int cantidad) {
		if (comprobarPreAgregarCesta(KeyProducto, cantidad)) {
			Producto producto = this.catalogo.get(KeyProducto);
			if (producto != null) {
				Producto productoEnCesta = new Producto(producto.getNombre(), producto.getPrecioUnidad(), cantidad,
						producto.getStock(), producto.getGenero());
				this.cesta.put(KeyProducto, productoEnCesta);
				System.out.println("Producto agregado a la cesta: " + producto.getNombre() + " -ID: " + KeyProducto);
			} else {
				System.out.println("El producto no se encontró en el catálogo.");
			}
		}
	}

	// Vende los productos de la cesta y genera un ticket de compra
	public String venderProductosDesdeCesta() {
		StringBuilder resultado = new StringBuilder();
		resultado.append("************* TICKET DE COMPRA *************\n");
		resultado.append(crearTicket());
		resultado.append("*********************************************\n\n");
		float precioTotal = 0;
		float subtotal = 0;
		precioTotal += subtotal;

//		for (Map.Entry<Integer, Producto> entryCesta : this.cesta.entrySet()) {
//			Producto productoCesta = entryCesta.getValue();
//			
//		}
//		
//			for (Map.Entry<Integer, Producto> entryCatalogo : this.catalogo.entrySet()) {
//				Producto productoCatalogo = entryCatalogo.getValue();
//				
//		}

		// Iterar sobre los productos en la cesta
		for (Map.Entry<Integer, Producto> entryCesta : this.cesta.entrySet()) {
			Producto productoCesta = entryCesta.getValue();
			resultado.append("Nombre: ").append(productoCesta.getNombre()).append("\n");
			resultado.append("Unidades: ").append(productoCesta.getCantidad()).append("\n");
			resultado.append("Precio Unitario: ").append(productoCesta.getPrecioUnidad()).append(" euros.\n");

			if (this.catalogo.containsKey(productoCesta.getId()))

				for (Map.Entry<Integer, Producto> entryCatalogo : this.catalogo.entrySet()) {
					Producto productoCatalogo = entryCatalogo.getValue();
					if (productoCesta.getNombre().equalsIgnoreCase(productoCatalogo.getNombre())) {
						if (productoCatalogo.getCantidad() < productoCesta.getCantidad()) {
							resultado.append("No hay suficientes ").append(productoCatalogo.getNombre())
									.append(" disponibles.\n");
						} else {
							productoCatalogo.consumir(productoCesta.getCantidad());
							resultado.append("Cantidad vendida: ").append(productoCesta.getCantidad()).append("\n");
							subtotal = productoCesta.getCantidad() * productoCesta.getPrecioUnidad();
							resultado.append("Precio Total Producto: ").append(subtotal).append(" euros.\n");
							precioTotal += subtotal;
						}
					}
				}
			resultado.append("\n");
		}
		resultado.append("Precio Total de la Compra: ").append(precioTotal).append(" euros.\n");
		return resultado.toString();
	}

	// Muestra los productos que hay en la cesta con sus detalles
	public String mostrarProductosCesta(TreeMap<Integer, Producto> cesta) {
		StringBuilder resultado = new StringBuilder();
		float precioTotal = 0;
		for (Map.Entry<Integer, Producto> entry : this.cesta.entrySet()) {
			Producto producto = entry.getValue();
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Agregado: ").append(producto.getCantidad()).append("\n");
			resultado.append("ID: ").append(producto.getId()).append("\n");
			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n");
			resultado.append("Precio Total: ").append(producto.getPrecioUnidad() * producto.getCantidad())
					.append(" euros.\n\n");
			precioTotal += producto.getPrecioUnidad() * producto.getCantidad();
		}
		resultado.append("Precio Total de la Cesta: ").append(precioTotal).append(" euros.\n");
		return resultado.toString();
	}

	public String mostrarProductosCatalogo() {
		StringBuilder resultado = new StringBuilder();
		for (Map.Entry<Integer, Producto> entry : this.catalogo.entrySet()) {
			Producto producto = entry.getValue();
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Disponible: ").append(producto.getCantidad()).append("\n");
			resultado.append("ID: ").append(producto.getId()).append("\n");
			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n\n");
		}
		return resultado.toString();
	}

	// CARGA DE PRODUCTOS DESDE FUTURA BASE DE DATOS
	public void cargarProductos() {
		Producto cine1 = new Cine("Rambo", 4.95f, 5, true, "Accion", "Sylvester Stallone");
		Producto cine2 = new Cine("Willy Wonka", 30.2f, 5, true, "Fantasía", "Roberto");
		Producto juego1 = new Videojuego("Mafia", 9.95f, 2, true, "Shooter", "2K");
		Producto juego2 = new Videojuego("Mario Bross", 35.11f, 0, false, "Plataformas", "Tomas");
		Producto musica1 = new Musica("Portishead", 18.53f, 6, true, "Trip Hop", "Tito Gilito");
		Producto musica2 = new Musica("Radiohead", 21.2f, 20, true, "Rock", "Manolo");
		Producto musica3 = new Musica("Slayer", 6.66f, 6, true, "Thrash Metal", "Kerry Fuckin King");
		this.catalogo.put(cine1.getId(), cine1);
		this.catalogo.put(cine2.getId(), cine2);
		this.catalogo.put(juego1.getId(), juego1);
		this.catalogo.put(juego2.getId(), juego2);
		this.catalogo.put(musica1.getId(), musica1);
		this.catalogo.put(musica2.getId(), musica2);
		this.catalogo.put(musica3.getId(), musica3);
	}

}
