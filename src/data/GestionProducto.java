package data;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import logic.Cine;
import logic.Musica;
import logic.Producto;
import logic.Videojuego;

public class GestionProducto {

	private TreeMap<String, Producto> catalogo = new TreeMap<>();
	private TreeMap<String, Producto> cesta = new TreeMap<>();

//CONOSTRUCTORES
	public GestionProducto() {

	}

	public GestionProducto(TreeMap<String, Producto> catalogoTreeMap, TreeMap<String, Producto> cesta) {
		super();
		this.catalogo = catalogoTreeMap;
		this.cesta = cesta;
	}

	// METODOS
	// ==============================================
	// CARGA DE PRODUCTOS DESDE FUUTURA BASE DE DATOS
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

	// AGREGA PRODUCTOS & CANTIDAD DEL CATALOGO POR NOMBRE A UN TREEMAP LLAMADO
	// "CESTA".
	public void agregarA_CestaPorNombre(TreeMap<String, Producto> cesta, TreeMap<String, Producto> catalogo,
			String nombreProducto, int cantidad) {

		int aux = cantidad;
		for (Map.Entry<String, Producto> entry : catalogo.entrySet()) {
			Producto producto = entry.getValue();
			if (producto.getNombre().equals(nombreProducto)) {

				cesta.put(entry.getKey(), producto);

				for (Map.Entry<String, Producto> entryCesta : cesta.entrySet()) {
					Producto productoCesta = entryCesta.getValue();
					if (productoCesta.getNombre().equals(nombreProducto)) {
						productoCesta.setCantidad(aux);
					}
				}

			}

		}
	}

	// RESTA LA CANTIDAD DE LA CESTA AL CATALOGO DE PRODUCTOS
	public void venderProductosDesdeCesta(TreeMap<String, Producto> cesta, TreeMap<String, Producto> catalogo) {
		// TODO@: crearTiket() + mostrarProductosCesta() ¬_¬
		// TODO@: añadir condicion de cantidad de producto

		for (Map.Entry<String, Producto> entryCesta : cesta.entrySet()) {
			Producto productoCesta = entryCesta.getValue();

			for (Map.Entry<String, Producto> entry : catalogo.entrySet()) {
				Producto productoCatalogo = entry.getValue();

				if (productoCesta.getNombre().equals(productoCatalogo.getNombre())) {
					productoCatalogo.setCantidad(productoCatalogo.getCantidad() - productoCesta.getCantidad());
					// productoCatalogo.consumir(productoCestaCantidad);

				}
			}
		}

	}

	// RESTA CANTIDAD DE PRODUCTOS AL CATALOGO POR EL ID.
	public void venderProductoUnico(String id, int cantidadConsumir) {
		Producto producto = this.catalogo.get(id);
		producto.consumir(cantidadConsumir);
	}

	// CREA TIKET INICIAL CON LA FECHA DE ACTUAL
	public String crearTiket() {
		Date fecha = new Date();
		long fechaAux = fecha.getTime();
		return Long.toString(fechaAux);
		// TODO@: AÑADIR PRODUCTOS COMPRADOS CON LA INFORMACION CORRESPONDIENTE.
	}

	// MUESTRA PRODUCTOS QUE HAY EN EL CATALOGO MOSTRANDO NOMBRE, CANTIDAD Y EL
	// PRECIO POR UNIDAD.

	public void mostrarProductosCatalogo() {
		for (Map.Entry<String, Producto> entry : this.catalogo.entrySet()) {
			Producto producto = entry.getValue();
			System.out.println("Nombre: " + producto.getNombre());
			System.out.println("Disponible: " + producto.getCantidad());
			System.out.println("Precio Unitario: " + producto.getPrecioUnidad() + " euros.\n");
		}
	}

	// MUESTRA PRODUCTOS QUE HAY EN EL LA CESTA MOSTRANDO NOMBRE, CANTIDAD Y EL
	// PRECIO POR UNIDAD.
	public void mostrarProductosCesta() {
		for (Map.Entry<String, Producto> entry : this.cesta.entrySet()) {
			Producto producto = entry.getValue();
			System.out.println("Nombre: " + producto.getNombre());
			System.out.println("Disponible: " + producto.getCantidad());
			System.out.println("Precio Unitario: " + producto.getPrecioUnidad() + " euros.\n");
		}
	}

//	public void mostrarTiket(TreeMap<String, Producto> productos) {
//		for (Map.Entry<String, Producto> entry : productos.entrySet()) {
//			Producto producto = entry.getValue();
//			System.out.println("su compra: " + producto.getNombre());
//		}
//	}

}
