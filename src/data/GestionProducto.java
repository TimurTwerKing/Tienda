package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import logic.Cine;
import logic.Musica;
import logic.PagarTarjeta;
import logic.Producto;
import logic.Videojuego;
import modelo.Cliente;
import mySQL_DB.Conexion;
import store.Fichero;

public class GestionProducto {

	private List<Producto> catalogo;
	private List<Producto> cesta;

	public GestionProducto() {
		this.catalogo = new ArrayList<>();
		this.cesta = new ArrayList<>();
	}

	// Método para cargar productos
	public List<Producto> cargarProductos() throws SQLException {
		String consulta = "SELECT * FROM PRODUCTO";
		Connection conn = Conexion.conectar(); // Obtener la conexion

		try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int idProducto = rs.getInt("id");
				String nombre = rs.getString("nombre");
				float precio = rs.getFloat("precio");
				int cantidad = rs.getInt("cantidad");
				boolean stock = rs.getBoolean("stock");
				String genero = rs.getString("genero");
				int idCategoria = rs.getInt("id_categoria");
				// int idProveedor = rs.getInt("id_proveedor");

				// Obtener atributos específicos según la categoría
				String atributoEspecifico = "";
				if (idCategoria == 1) {
					atributoEspecifico = rs.getString("actor");
				} else if (idCategoria == 2) {
					atributoEspecifico = rs.getString("desarrollador");
				} else if (idCategoria == 3) {
					atributoEspecifico = rs.getString("compositor");
				}

				Producto producto;

				// Crear el objeto Producto correspondiente
				if (stock) {
					// Crear el producto según la categoría
					switch (idCategoria) {
					case 1:
						producto = new Cine(nombre, precio, cantidad, stock, genero, idProducto, atributoEspecifico,
								idCategoria);
						break;
					case 2:
						producto = new Videojuego(nombre, precio, cantidad, stock, genero, idProducto, idCategoria,
								atributoEspecifico);
						break;
					case 3:
						producto = new Musica(nombre, precio, cantidad, stock, genero, idProducto, idCategoria,
								atributoEspecifico);
						break;
					default:
						producto = new Producto(nombre, precio, cantidad, stock, genero, idProducto, idCategoria);
					}
				} else {
					// Si el producto no está en stock
					producto = new Producto(nombre, precio, cantidad, stock, genero, idProducto, idCategoria);
				}

				// Agregar el producto al ArrayList
				this.catalogo.add(producto);
			}
		} finally {
			if (conn != null) {
				conn.close(); // Cerrar la conexión cuando hayamos terminado
			}
		}
		return this.catalogo;
	}

	// Método para agregar artículos a la cesta
	public void agregarCesta(int productoId, int cantidad) {
		Producto producto = buscarProductoPorId(productoId);
		if (producto != null) {
			if (producto.getCantidad() >= cantidad) {
				cesta.add(new Producto(producto.getNombre(), producto.getPrecioUnidad(), cantidad, producto.hayStock(),
						producto.getGenero(), producto.getId(), producto.getIdCategoria()));
			} else {
				System.out.println("No hay suficiente stock para el producto: " + producto.getNombre());
			}
		} else {
			System.out.println("Producto no encontrado en el catálogo.");
		}
	}

	// Método para buscar un producto por su ID en el catálogo
	private Producto buscarProductoPorId(int productoId) {
		for (Producto producto : catalogo) {
			if (producto.getId() == productoId) {
				return producto;
			}
		}
		return null;
	}

	public static void realizarCompra(GestionProducto gestionProductos, Fichero f, Scanner sc) {
		boolean seguirComprando = true;
		boolean pagar = false;

		while (seguirComprando && !pagar) {
			System.out.println(gestionProductos.mostrarProductosCatalogo());

			System.out.println("Escriba ID del producto: ");
			int productoID = sc.nextInt();

			System.out.println("Escriba la cantidad del producto seleccionado: ");
			int cantidadProducto = sc.nextInt();

			gestionProductos.agregarCesta(productoID, cantidadProducto);

			while (true) {
				System.out.println("¿Desea seguir comprando, pagar o cancelar?\n");
				String opcion = sc.next().trim().toLowerCase();

				if (opcion.equals("seguir comprando")) {
					break;
				} else if (opcion.equals("pagar")) {
					pagar = true;
					seguirComprando = false;
					Cliente cliente = new Cliente();
					PagarTarjeta.pagar(cliente);
					Tiket tiket = new Tiket();
					try {
						String tiketAUX = tiket.crearTicket(gestionProductos.cesta);// TIKET
						System.out.println(tiketAUX);
						System.out.println("¿Desea guardar el ticket? (si/no)");
						String opcionTiket = sc.next().trim().toLowerCase();

						if (opcionTiket.equals("si")) {
							f.escribirFichero(tiketAUX);
						}
					} catch (NoSuchElementException e) {
						System.out.println("Error element");
						e.getStackTrace();
					} catch (NumberFormatException e) {
						System.out.println("Error num Format");
						e.getStackTrace();
					}
					break;
				} else if (opcion.equals("cancelar")) {
					seguirComprando = false;
					pagar = false;
					System.out.println("Compra cancelada.");
					break;
				} else {
					System.out.println("Opción no válida, por favor elija 'seguir comprando', 'pagar' o 'cancelar'.");
				}
			}
		}
	}

	// Método para vender artículos y actualizar la base de datos
	public void venderArticulos() throws SQLException {
		Connection conn = Conexion.conectar();
		try {
			for (Producto productoEnCesta : cesta) {
				Producto producto = buscarProductoPorId(productoEnCesta.getId());
				if (producto != null) {
					int cantidadVendida = productoEnCesta.getCantidad();
					int nuevaCantidad = producto.getCantidad() - cantidadVendida;

					// Actualizar la cantidad en la base de datos
					String updateQuery = "UPDATE Producto SET cantidad = ? WHERE id = ?";
					try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
						pstmt.setInt(1, nuevaCantidad);
						pstmt.setInt(2, producto.getId());
						pstmt.executeUpdate();
					}

					// Actualizar la cantidad en el catálogo local
					producto.setCantidad(nuevaCantidad);
				}
			}

			// Limpiar la cesta después de vender
			cesta.clear();
		} finally {
			if (conn != null) {
				conn.close(); // Cerrar la conexión cuando hayamos terminado
			}
		}
	}

	// Método para mostrar los productos en la cesta
	public String mostrarProductosCesta() {
		StringBuilder resultado = new StringBuilder();
		float precioTotal = 0;

		for (Producto producto : cesta) {
			float precioTotalProducto = producto.getPrecioUnidad() * producto.getCantidad();
			precioTotal += precioTotalProducto;

			resultado.append("ID: ").append(producto.getId()).append("\n");
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Cantidad en cesta: ").append(producto.getCantidad()).append("\n");
			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n");
			resultado.append("Precio Total: ").append(precioTotalProducto).append(" euros.\n\n");
		}

		resultado.append("Precio Total de la Cesta: ").append(precioTotal).append(" euros.\n");
		return resultado.toString();
	}

	// Método para mostrar los productos en el catálogo
	public String mostrarProductosCatalogo() {
		StringBuilder resultado = new StringBuilder();

		for (Producto producto : catalogo) {
			int genero = producto.getIdCategoria();

			if (genero == 1) {
				resultado.append("Pelicula\n");
			} else if (genero == 2) {
				resultado.append("Videojuegos\n");
			} else {
				resultado.append("Musica\n");
			}

			resultado.append("ID: ").append(producto.getId()).append("\n");
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Disponible: ").append(producto.getCantidad()).append("\n");
			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n\n");
		}

		return resultado.toString();
	}

	// Método para calcular y mostrar el importe total de la compra actual
	public double mostrarImporteTotal() {
		double total = 0.0;
		for (Producto producto : cesta) {
			total += producto.getPrecioUnidad() * producto.getCantidad();
		}
		return total;
	}
}

//	// Método para contar la cantidad de un producto en la cesta
//	public int contarCantidadEnCesta(int productoId) {
//	    int cantidad = 0;
//	    for (Integer id : cesta) {
//	        if (id == productoId) {
//	            cantidad++;
//	        }
//	    }
//	    return cantidad;
//	}

//	// Método para crear un ticket
//	public String crearTiket() {
//		String numeroTicket = generarNumeroTicket();
//		StringBuilder ticketBuilder = new StringBuilder();
//		ticketBuilder.append("Ticket Número: ").append(numeroTicket).append("\n");
//		ticketBuilder.append("Fecha: ").append(Calendar.getInstance().getTime()).append("\n\n");
//		ticketBuilder.append("Artículos:\n");
//
//		float total = 0;
//
//		for (int productoId : cesta) {
//			Producto producto = buscarProductoPorId(productoId);
//
//			if (producto != null) {
//				float precioTotalProducto = producto.getPrecioUnidad() * producto.getCantidad();
//				total += precioTotalProducto;
//
//				ticketBuilder.append("Producto: ").append(producto.getNombre()).append("\n").append("Cantidad: ")
//						.append(producto.getCantidad()).append("\n").append("Precio Unitario: ")
//						.append(producto.getPrecioUnidad()).append("\n").append("Precio Total: ")
//						.append(precioTotalProducto).append("\n\n");
//			}
//		}
//
//		ticketBuilder.append("Total a pagar: ").append(total).append("\n");
//
//		return ticketBuilder.toString();
//	}
//
//	// Método para generar un número de ticket único
//	private String generarNumeroTicket() {
//		return "TKT" + System.currentTimeMillis();
//	}

//	private TreeMap<Integer, Producto> catalogo = new TreeMap<>();
//
////CONOSTRUCTORES
//	public GestionProducto() {
//	}
//
//	public GestionProducto(TreeMap<Integer, Producto> catalogo) {
//		this.catalogo = catalogo;
//	}
//
//	// METODOS
//	// ==============================================
//
//	// AGREGA PRODUCTOS & CANTIDAD DEL CATALOGO POR NOMBRE A UN TREEMAP LLAMADO
//	// "CESTA".
//	public void agregarA_CestaPorNombre(TreeMap<Integer, Producto> cesta, TreeMap<Integer, Producto> catalogo,
//			String nombreProducto, int cantidad) {
//		for (Map.Entry<Integer, Producto> entryCatalogo : catalogo.entrySet()) {
//			Producto productoCatalogo = entryCatalogo.getValue();
//			if (productoCatalogo.getNombre().equalsIgnoreCase(nombreProducto)) {
//				// Creamos un nuevo producto con los mismos valores que el producto encontrado
//				// en el catálogo
//				Producto productoEnCesta = new Producto(productoCatalogo.getNombre(),
//						productoCatalogo.getPrecioUnidad(), productoCatalogo.getCantidad(), productoCatalogo.getStock(),
//						productoCatalogo.getGenero());
//				productoEnCesta.setCantidad(cantidad);
//				cesta.put(entryCatalogo.getKey(), productoEnCesta); // Agregar el nuevo objeto a la cesta
//			}
//		}
//	}
//
//	// CREA TIKET CON LA FECHA Y LA HORA DE LA COMPRA + UN NUMERO IDENTIFICATIVO DEL
//	// MISMO.
//	public String crearTicket() {
//		Date fecha = new Date();
//		Random random = new Random();
//		long randomNumber = Math.abs(random.nextLong() % 1000000000000000L);// 15num
//		return "Ticket creado el " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha) + "\nNumero ID: "
//				+ randomNumber + "\n";
//	}
//
//	// RESTA LA CANTIDAD DE LA CESTA AL CATALOGO DE PRODUCTOS
//	public String venderProductosDesdeCesta(TreeMap<Integer, Producto> cesta, TreeMap<Integer, Producto> catalogo) {
//		StringBuilder resultado = new StringBuilder();
//		resultado.append("************* TICKET DE COMPRA *************\n");
//		resultado.append(crearTicket());
//		resultado.append("*********************************************\n\n");
//		float precioTotal = 0;
//		float subtotal = 0;
//		precioTotal += subtotal;
//		// Iterar sobre los productos en la cesta
//		for (Map.Entry<Integer, Producto> entryCesta : cesta.entrySet()) {
//			Producto productoCesta = entryCesta.getValue();
//			resultado.append("Nombre: ").append(productoCesta.getNombre()).append("\n");
//			resultado.append("Unidades: ").append(productoCesta.getCantidad()).append("\n");
//			resultado.append("Precio Unitario: ").append(productoCesta.getPrecioUnidad()).append(" euros.\n");
//
//			for (Map.Entry<Integer, Producto> entryCatalogo : catalogo.entrySet()) {
//				Producto productoCatalogo = entryCatalogo.getValue();
//				if (productoCesta.getNombre().equalsIgnoreCase(productoCatalogo.getNombre())) {
//					if (productoCatalogo.getCantidad() < productoCesta.getCantidad()) {
//						resultado.append("No hay suficientes ").append(productoCatalogo.getNombre())
//								.append(" disponibles.\n");
//					} else {// TODO@ arreglar el finltrado de disponibilidad de productos ( si vendo mas de
//						// los que hay sigue siendo "no se encuentra en el catalogo")
//						productoCatalogo.consumir(productoCesta.getCantidad());
//						resultado.append("Cantidad vendida: ").append(productoCesta.getCantidad()).append("\n");
//						subtotal = productoCesta.getCantidad() * productoCesta.getPrecioUnidad();
//						resultado.append("Precio Total Producto: ").append(subtotal).append(" euros.\n");
//						precioTotal += subtotal;
//					}
//				}
//			}
//			resultado.append("\n");
//		}
//		resultado.append("Precio Total de la Compra: ").append(precioTotal).append(" euros.\n");
//		return resultado.toString();
//	}
//
//	// MUESTRA PRODUCTOS QUE HAY EN EL CATALOGO MOSTRANDO NOMBRE, CANTIDAD Y EL
//	// PRECIO POR UNIDAD.
//	public String mostrarProductosCesta(TreeMap<Integer, Producto> cesta) {
//		StringBuilder resultado = new StringBuilder();
//		float precioTotal = 0;
//		for (Map.Entry<Integer, Producto> entry : cesta.entrySet()) {
//			Producto producto = entry.getValue();
//			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
//			resultado.append("Disponible: ").append(producto.getCantidad()).append("\n");
//			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n");
//			resultado.append("Precio Total: ").append(producto.getPrecioUnidad() * producto.getCantidad())
//					.append(" euros.\n\n");
//			precioTotal += producto.getPrecioUnidad() * producto.getCantidad();
//		}
//		resultado.append("Precio Total de la Cesta: ").append(precioTotal).append(" euros.\n");
//		return resultado.toString();
//	}
//
//	public String mostrarProductosCatalogo(TreeMap<Integer, Producto> catalogo) {
//		StringBuilder resultado = new StringBuilder();
//		for (Map.Entry<Integer, Producto> entry : catalogo.entrySet()) {
//			Producto producto = entry.getValue();
//			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
//			resultado.append("Disponible: ").append(producto.getCantidad()).append("\n");
//			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n\n");
//		}
//		return resultado.toString();
//	}
//
//	// RESTA CANTIDAD DE PRODUCTOS AL CATALOGO POR EL ID.
//	public void venderProductoUnico(Integer id, int cantidadConsumir) {
//		Producto producto = this.catalogo.get(id);
//		producto.consumir(cantidadConsumir);
//	}
//
//
//
//	// CARGA DE PRODUCTOS DESDE FUTURA BASE DE DATOS
//	public void cargarProductos(TreeMap<String, Producto> catalogo) {
//		Producto cine1 = new Cine("Rambo", 4.95f, 5, true, "Accion", "Sylvester Stallone");
//		Producto cine2 = new Cine("Willy Wonka", 30.2f, 5, true, "Fantasía", "Roberto");
//		Producto juego1 = new Videojuego("Mafia", 9.95f, 2, true, "Shooter", "2K");
//		Producto juego2 = new Videojuego("Mario Bross", 35.11f, 0, false, "Plataformas", "Tomas");
//		Producto musica1 = new Musica("Portishead", 18.53f, 6, true, "Trip Hop", "Tito Gilito");
//		Producto musica2 = new Musica("Radiohead", 21.2f, 20, true, "Rock", "Manolo");
//		Producto musica3 = new Musica("Slayer", 6.66f, 6, true, "Thrash Metal", "Kerry Fuckin King");
//		catalogo.put(cine1.getId(), cine1);
//		catalogo.put(cine2.getId(), cine2);
//		catalogo.put(juego1.getId(), juego1);
//		catalogo.put(juego2.getId(), juego2);
//		catalogo.put(musica1.getId(), musica1);
//		catalogo.put(musica2.getId(), musica2);
//		catalogo.put(musica3.getId(), musica3);
//	}

//	public void mostrarTiket(TreeMap<String, Producto> productos) {
//		for (Map.Entry<String, Producto> entry : productos.entrySet()) {
//			Producto producto = entry.getValue();
//			System.out.println("su compra: " + producto.getNombre());
//		}
//	}

//	public void agregarA_CestaPorNombre(TreeMap<String, Producto> cesta, TreeMap<String, Producto> catalogo,
//	String nombreProducto, int cantidad) {
//for (Map.Entry<String, Producto> entryCatalogo : catalogo.entrySet()) {
//	Producto productoCatalogo = entryCatalogo.getValue();
//	if (productoCatalogo.getNombre().equals(nombreProducto)) {
//		cesta.put(entryCatalogo.getKey(), productoCatalogo);
//
//		for (Map.Entry<String, Producto> entryCesta : cesta.entrySet()) {
//			Producto productoCesta = entryCesta.getValue();
//			if (productoCesta.getNombre().equals(nombreProducto)) {
//				productoCesta.setCantidad(cantidad);
//			}
//		}
//
//	}
//
//}
//}

//// RESTA LA CANTIDAD DE LA CESTA AL CATALOGO DE PRODUCTOS
//public String venderProductosDesdeCesta(TreeMap<String, Producto> cesta, TreeMap<String, Producto> catalogo) {
//// TODO@: crearTiket() + mostrarProductosCesta() ¬_¬
//// TODO@: añadir condicion de cantidad de producto
//for (Map.Entry<String, Producto> entryCesta : cesta.entrySet()) {
//	Producto productoCesta = entryCesta.getValue();
//	System.out.println("Nombre: " + productoCesta.getNombre());
//	System.out.println("Disponible: " + productoCesta.getCantidad());
//	System.out.println("Precio Unitario: " + productoCesta.getPrecioUnidad() + " euros.\n");
//	for (Map.Entry<String, Producto> entry : catalogo.entrySet()) {
//		Producto productoCatalogo = entry.getValue();
//		if (productoCesta.getNombre().equals(productoCatalogo.getNombre())) {
//			if (productoCatalogo.getCantidad() < productoCesta.getCantidad()) {
//				System.out.println("no hay suficientes productos");
//			} else {
//				productoCatalogo.consumir(productoCesta.getCantidad());
//			}
//		}
//	}
//}
//return "Su compra: " + mostrarProductosCesta(cesta) + crearTiket();
//}

// CREA TIKET INICIAL CON LA FECHA DE ACTUAL
//public String crearTiket() {
//Date fecha = new Date();
//long fechaAux = fecha.getTime();
//return Long.toString(fechaAux);
//// TODO@: AÑADIR PRODUCTOS COMPRADOS CON LA INFORMACION CORRESPONDIENTE.
//}
//
//// MUESTRA PRODUCTOS QUE HAY EN EL LA CESTA MOSTRANDO NOMBRE, CANTIDAD Y EL
//// PRECIO POR UNIDAD.
//public String mostrarProductosCesta(TreeMap<String, Producto> cesta) {
//float precioTotal = 0;
//for (Map.Entry<String, Producto> entry : cesta.entrySet()) {
//	Producto producto = entry.getValue();
//	precioTotal += producto.getPrecioUnidad();
//	System.out.println("Nombre: " + producto.getNombre());
//	System.out.println("Disponible: " + producto.getCantidad());
//	System.out.println("Precio Unitario: " + producto.getPrecioUnidad() + " euros.\n");
//	System.out.println("Precio Total: " + precioTotal + " euros.\n");
//}
//return null;
//}
//
//// MUESTRA PRODUCTOS QUE HAY EN EL CATALOGO MOSTRANDO NOMBRE, CANTIDAD Y EL
//// PRECIO POR UNIDAD.
//
//public void mostrarProductosCatalogo(TreeMap<String, Producto> catalogo) {
//for (Map.Entry<String, Producto> entry : catalogo.entrySet()) {
//	Producto producto = entry.getValue();
//	System.out.println("Nombre: " + producto.getNombre());
//	System.out.println("Disponible: " + producto.getCantidad());
//	System.out.println("Precio Unitario: " + producto.getPrecioUnidad() + " euros.\n");
//}
//}
//
//
//public String venderProductosDesdeCesta(TreeMap<String, Producto> cesta, TreeMap<String, Producto> catalogo) {
//StringBuilder resultado = new StringBuilder();
//resultado.append("************* TICKET DE COMPRA *************\n");
//resultado.append("Fecha: ").append(crearTiket()).append("\n");
//resultado.append("*********************************************\n\n");
//
//float precioTotal = 0;
//for (Map.Entry<String, Producto> entryCesta : cesta.entrySet()) {
//    Producto productoCesta = entryCesta.getValue();
//    resultado.append("Nombre: ").append(productoCesta.getNombre()).append("\n");
//    resultado.append("Disponible: ").append(productoCesta.getCantidad()).append("\n");
//    resultado.append("Precio Unitario: ").append(productoCesta.getPrecioUnidad()).append(" euros.\n");
//
//    for (Map.Entry<String, Producto> entryCatalogo : catalogo.entrySet()) {
//        Producto productoCatalogo = entryCatalogo.getValue();
//        if (productoCesta.getNombre().equals(productoCatalogo.getNombre())) {
//            if (productoCatalogo.getCantidad() < productoCesta.getCantidad()) {
//                resultado.append("No hay suficientes ").append(productoCatalogo.getNombre()).append(" disponibles.\n");
//            } else {
//                productoCatalogo.consumir(productoCesta.getCantidad());
//                resultado.append("Cantidad vendida: ").append(productoCesta.getCantidad()).append("\n");
//                float subtotal = productoCesta.getCantidad() * productoCesta.getPrecioUnidad();
//                resultado.append("Precio Total: ").append(subtotal).append(" euros.\n");
//                precioTotal += subtotal;
//            }
//        }
//    }
//    resultado.append("\n");
//}
//resultado.append("Precio Total de la Compra: ").append(precioTotal).append(" euros.\n");
//return resultado.toString();
//}
