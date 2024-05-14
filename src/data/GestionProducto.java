package data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;
import java.util.Map;
import java.util.TreeMap;

import logic.Cine;
import logic.Musica;
import logic.Producto;
import logic.Videojuego;
import mySQL_DB.Conexion;

public class GestionProducto {

	private TreeMap<Integer, Producto> catalogo;
	private TreeMap<Integer, Integer> cesta;

	public GestionProducto() {
		this.catalogo = new TreeMap<>();
		this.cesta = new TreeMap<>();
	}

	// Método para cargar productos
	public TreeMap<Integer, Producto> cargarProductos() throws SQLException {
		String consulta = "SELECT * FROM PRODUCTO";
		Conexion conexion = new Conexion();
		Connection conn = conexion.conectar(); // Obtener la conexión desde la clase Conexion

		try (PreparedStatement pstmt = conn.prepareStatement(consulta)) {
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt("id");
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
						producto = new Cine(nombre, precio, cantidad, stock, genero, atributoEspecifico);
						break;
					case 2:
						producto = new Videojuego(nombre, precio, cantidad, stock, genero, atributoEspecifico);
						break;
					case 3:
						producto = new Musica(nombre, precio, cantidad, stock, genero, atributoEspecifico);
						break;
					default:
						producto = new Producto(nombre, precio, cantidad, stock, genero);
					}
				} else {
					// Si el producto no está en stock
					producto = new Producto(nombre, precio, cantidad, stock, genero);
				}

				// Agregar el producto al TreeMap
				this.catalogo.put(id, producto);
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
		if (catalogo.containsKey(productoId)) {
			Producto producto = catalogo.get(productoId);
			if (producto.getCantidad() >= cantidad) {
				cesta.put(productoId, cantidad);
			} else {
				System.out.println("No hay suficiente stock para el producto: " + producto.getNombre());
			}
		} else {
			System.out.println("Producto no encontrado en el catálogo.");
		}
	}

	// Método para vender artículos y actualizar la base de datos
	public void venderArticulos() throws SQLException {
		Conexion conexion = new Conexion();
		Connection conn = conexion.conectar();

		try {
			for (Map.Entry<Integer, Integer> entry : cesta.entrySet()) {
				int productoId = entry.getKey();
				int cantidadVendida = entry.getValue();

				// Obtener el producto del catálogo
				Producto producto = catalogo.get(productoId);
				int nuevaCantidad = producto.getCantidad() - cantidadVendida;

				// Actualizar la cantidad en la base de datos
				String updateQuery = "UPDATE Producto SET cantidad = ? WHERE id = ?";
				try (PreparedStatement pstmt = conn.prepareStatement(updateQuery)) {
					pstmt.setInt(1, nuevaCantidad);
					pstmt.setInt(2, productoId);
					pstmt.executeUpdate();
				}

				// Actualizar la cantidad en el catálogo local
				producto.setCantidad(nuevaCantidad);
			}

			// Limpiar la cesta después de vender
			cesta.clear();
		} finally {
			if (conn != null) {
				conn.close(); // Cerrar la conexión cuando hayamos terminado
			}
		}
	}

	// Método para crear un ticket
	public String crearTiket() {
		String numeroTicket = generarNumeroTicket();
		StringBuilder ticketBuilder = new StringBuilder();
		ticketBuilder.append("Ticket Número: ").append(numeroTicket).append("\n");
		ticketBuilder.append("Fecha: ").append(Calendar.getInstance().getTime()).append("\n\n");
		ticketBuilder.append("Artículos:\n");

		float total = 0;

		for (Map.Entry<Integer, Integer> entry : cesta.entrySet()) {
			int productoId = entry.getKey();
			int cantidad = entry.getValue();
			Producto producto = catalogo.get(productoId);

			float precioTotalProducto = producto.getPrecioUnidad() * cantidad;
			total += precioTotalProducto;

			ticketBuilder.append("Producto: ").append(producto.getNombre()).append("\n").append("Cantidad: ")
					.append(cantidad).append("\n").append("Precio Unitario: ").append(producto.getPrecioUnidad())
					.append("\n").append("Precio Total: ").append(precioTotalProducto).append("\n\n");
		}

		ticketBuilder.append("Total a pagar: ").append(total).append("\n");

		return ticketBuilder.toString();
	}

	// Método para generar un número de ticket único
	private String generarNumeroTicket() {
		return "TKT" + System.currentTimeMillis();
	}

	// Método para mostrar los productos en la cesta
	public String mostrarProductosCesta() {
		StringBuilder resultado = new StringBuilder();
		float precioTotal = 0;

		for (Map.Entry<Integer, Integer> entry : this.cesta.entrySet()) {
			int productoId = entry.getKey();
			int cantidad = entry.getValue();

			Producto producto = this.catalogo.get(productoId);

			if (producto != null) {
				float precioTotalProducto = producto.getPrecioUnidad() * cantidad;
				precioTotal += precioTotalProducto;

				resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
				resultado.append("Cantidad en cesta: ").append(cantidad).append("\n");
				resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n");
				resultado.append("Precio Total: ").append(precioTotalProducto).append(" euros.\n\n");
			}
		}

		resultado.append("Precio Total de la Cesta: ").append(precioTotal).append(" euros.\n");
		return resultado.toString();
	}

	// Método para mostrar los productos en el catálogo
	public String mostrarProductosCatalogo() {
		StringBuilder resultado = new StringBuilder();

		for (Map.Entry<Integer, Producto> entry : this.catalogo.entrySet()) {
			Producto producto = entry.getValue();

			resultado.append("ID: ").append(producto.getId()).append("\n");
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Disponible: ").append(producto.getCantidad()).append("\n");
			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n\n");
		}

		return resultado.toString();
	}

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
}
