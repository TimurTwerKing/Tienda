package data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import logic.Cine;
import logic.Musica;
import logic.Producto;
import logic.Videojuego;

public class GestionProducto {

	private TreeMap<String, Producto> catalogo = new TreeMap<>();

//CONOSTRUCTORES
	public GestionProducto() {
	}

	public GestionProducto(TreeMap<String, Producto> catalogo) {
		this.catalogo = catalogo;
	}

	// METODOS
	// ==============================================

	// AGREGA PRODUCTOS & CANTIDAD DEL CATALOGO POR NOMBRE A UN TREEMAP LLAMADO
	// "CESTA".
	public void agregarA_CestaPorNombre(TreeMap<String, Producto> cesta, TreeMap<String, Producto> catalogo,
			String nombreProducto, int cantidad) {
		for (Map.Entry<String, Producto> entryCatalogo : catalogo.entrySet()) {
			Producto productoCatalogo = entryCatalogo.getValue();
			if (productoCatalogo.getNombre().equals(nombreProducto)) {
				// Creamos un nuevo producto con los mismos valores que el producto encontrado
				// en el catálogo
				Producto productoEnCesta = new Producto(productoCatalogo.getNombre(),
						productoCatalogo.getPrecioUnidad(), productoCatalogo.getCantidad(), productoCatalogo.getStock(),
						productoCatalogo.getGenero());
				productoEnCesta.setCantidad(cantidad);
				cesta.put(entryCatalogo.getKey(), productoEnCesta); // Agregar el nuevo objeto a la cesta
			}
		}
	}

	// CREA TIKET CON LA FECHA Y LA HORA DE LA COMPRA + UN NUMERO IDENTIFICATIVO DEL
	// MISMO.
	public String crearTicket() {
		Date fecha = new Date();
		Random random = new Random();
		long randomNumber = Math.abs(random.nextLong() % 1000000000000000L);// 15num
		return "Ticket creado el " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(fecha) + "\nNumero ID: "
				+ randomNumber + "\n";
	}

	// RESTA LA CANTIDAD DE LA CESTA AL CATALOGO DE PRODUCTOS
	public String venderProductosDesdeCesta(TreeMap<String, Producto> cesta, TreeMap<String, Producto> catalogo) {
		StringBuilder resultado = new StringBuilder();
		resultado.append("************* TICKET DE COMPRA *************\n");
		resultado.append(crearTicket());
		resultado.append("*********************************************\n\n");
		float precioTotal = 0;
		float subtotal = 0;
		precioTotal += subtotal;
		// Iterar sobre los productos en la cesta
		for (Map.Entry<String, Producto> entryCesta : cesta.entrySet()) {
			Producto productoCesta = entryCesta.getValue();
			resultado.append("Nombre: ").append(productoCesta.getNombre()).append("\n");
			resultado.append("Unidades: ").append(productoCesta.getCantidad()).append("\n");
			resultado.append("Precio Unitario: ").append(productoCesta.getPrecioUnidad()).append(" euros.\n");

			for (Map.Entry<String, Producto> entryCatalogo : catalogo.entrySet()) {
				Producto productoCatalogo = entryCatalogo.getValue();
				if (productoCesta.getNombre().equals(productoCatalogo.getNombre())) {
					if (productoCatalogo.getCantidad() < productoCesta.getCantidad()) {
						resultado.append("No hay suficientes ").append(productoCatalogo.getNombre())
								.append(" disponibles.\n");
					} else {// TODO@ arreglar el finltrado de disponibilidad de productos ( si vendo mas de
						// los que hay sigue siendo "no se encuentra en el catalogo")
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

	// MUESTRA PRODUCTOS QUE HAY EN EL CATALOGO MOSTRANDO NOMBRE, CANTIDAD Y EL
	// PRECIO POR UNIDAD.
	public String mostrarProductosCesta(TreeMap<String, Producto> cesta) {
		StringBuilder resultado = new StringBuilder();
		float precioTotal = 0;
		for (Map.Entry<String, Producto> entry : cesta.entrySet()) {
			Producto producto = entry.getValue();
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Disponible: ").append(producto.getCantidad()).append("\n");
			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n");
			resultado.append("Precio Total: ").append(producto.getPrecioUnidad() * producto.getCantidad())
					.append(" euros.\n\n");
			precioTotal += producto.getPrecioUnidad() * producto.getCantidad();
		}
		resultado.append("Precio Total de la Cesta: ").append(precioTotal).append(" euros.\n");
		return resultado.toString();
	}

	public String mostrarProductosCatalogo(TreeMap<String, Producto> catalogo) {
		StringBuilder resultado = new StringBuilder();
		for (Map.Entry<String, Producto> entry : catalogo.entrySet()) {
			Producto producto = entry.getValue();
			resultado.append("Nombre: ").append(producto.getNombre()).append("\n");
			resultado.append("Disponible: ").append(producto.getCantidad()).append("\n");
			resultado.append("Precio Unitario: ").append(producto.getPrecioUnidad()).append(" euros.\n\n");
		}
		return resultado.toString();
	}

	// RESTA CANTIDAD DE PRODUCTOS AL CATALOGO POR EL ID.
	public void venderProductoUnico(String id, int cantidadConsumir) {
		Producto producto = this.catalogo.get(id);
		producto.consumir(cantidadConsumir);
	}

	// CARGA DE PRODUCTOS DESDE FUTURA BASE DE DATOS
	public void cargarProductos(TreeMap<String, Producto> catalogo) {
		Producto cine1 = new Cine("Rambo", 4.95f, 5, true, "Accion", "Sylvester Stallone");
		Producto cine2 = new Cine("Willy Wonka", 30.2f, 5, true, "Fantasía", "Roberto");
		Producto juego1 = new Videojuego("Mafia", 9.95f, 2, true, "Shooter", "2K");
		Producto juego2 = new Videojuego("Mario Bross", 35.11f, 0, false, "Plataformas", "Tomas");
		Producto musica1 = new Musica("Portishead", 18.53f, 6, true, "Trip Hop", "Tito Gilito");
		Producto musica2 = new Musica("Radiohead", 21.2f, 20, true, "Rock", "Manolo");
		Producto musica3 = new Musica("Slayer", 6.66f, 6, true, "Thrash Metal", "Kerry Fuckin King");
		catalogo.put(cine1.getId(), cine1);
		catalogo.put(cine2.getId(), cine2);
		catalogo.put(juego1.getId(), juego1);
		catalogo.put(juego2.getId(), juego2);
		catalogo.put(musica1.getId(), musica1);
		catalogo.put(musica2.getId(), musica2);
		catalogo.put(musica3.getId(), musica3);
	}

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
