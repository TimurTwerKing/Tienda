package data;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import logic.Cine;
import logic.Musica;
import logic.Producto;
import logic.Videojuego;

public class GestionProducto {

	private TreeMap<String, Producto> catalogoTreeMap = new TreeMap<>();
	private TreeMap<String, Producto> cesta = new TreeMap<>();

//CONOSTRUCTORES
	public GestionProducto() {
	}

	public GestionProducto(TreeMap<String, Producto> productos) {
		this.catalogoTreeMap = productos;
	}

	// METODOS
	public void cargarProductos() {
		Producto cine1 = new Cine("Rambo", 4.95f, 5, true, "Accion", "Sylvester Stallone");
		Producto cine2 = new Cine("Willy Wonka", 30.2f, 5, true, "Fantasía", "Roberto");
		Producto juego1 = new Videojuego("Mafia", 9.95f, 2, true, "Shooter", "2K");
		Producto juego2 = new Videojuego("Mario Bross", 35.11f, 0, false, "Plataformas", "Tomas");
		Producto musica1 = new Musica("Portishead", 18.53f, 6, true, "Trip Hop", "Tito Gilito");
		Producto musica2 = new Musica("Radiohead", 21.2f, 20, true, "Rock", "Manolo");
		Producto musica3 = new Musica("Slayer", 6.66f, 6, true, "Thrash Metal", "Kerry Fuckin King");
		this.catalogoTreeMap.put(cine1.getId(), cine1);
		this.catalogoTreeMap.put(cine2.getId(), cine2);
		this.catalogoTreeMap.put(juego1.getId(), juego1);
		this.catalogoTreeMap.put(juego2.getId(), juego2);
		this.catalogoTreeMap.put(musica1.getId(), musica1);
		this.catalogoTreeMap.put(musica2.getId(), musica2);
		this.catalogoTreeMap.put(musica3.getId(), musica3);
	}

	// Metodo para agregar elementos a la cesta por nombre del producto + su
	// cantidad.
	public void agregarA_CestaPorNombre(String nombreProducto, int cantidad) {
		TreeMap<String, Producto> copiaCatalogo = new TreeMap<>(this.catalogoTreeMap);
		for (Map.Entry<String, Producto> entry : copiaCatalogo.entrySet()) {
			Producto producto = entry.getValue();
			if (producto.getNombre().equals(nombreProducto)) {
				producto.setCantidad(cantidad);
				cesta.put(entry.getKey(), producto);
			}
		}
	}

	public void venderProductosDesdeCesta() {
		for (Map.Entry<String, Producto> entry : this.catalogoTreeMap.entrySet()) {
			Producto productoCatalogo = entry.getValue();
			
			for (Map.Entry<String, Producto> entryCesta : this.cesta.entrySet()) {
				Producto productoCesta = entryCesta.getValue();
				if (productoCatalogo.getNombre().equals(productoCesta.getNombre())) {
					productoCatalogo.setCantidad(productoCatalogo.getCantidad() - productoCesta.getCantidad());
					// productoCatalogo.consumir(productoCestaCantidad);
				}	
			}
		}
		

//		for (Map.Entry<String, Producto> entryCesta : cesta.entrySet()) {
//			Producto productoCesta = entryCesta.getValue();
//			
//			for (Map.Entry<String, Producto> entry : catalogoTreeMap.entrySet()) {
//				Producto productoCatalogo = entry.getValue();
//				if (productoCesta.getNombre().equals(productoCatalogo.getNombre())) {
//					productoCatalogo.setCantidad(productoCatalogo.getCantidad() - productoCesta.getCantidad());
//					// productoCatalogo.consumir(productoCestaCantidad);
//
//				}
//			}
//		}

	}

// Metodo para vender cantidad de productos deseada sin agregar a la cesta. 
	public void venderProductoUnico(String id, int cantidadConsumir) {
		Producto producto = this.catalogoTreeMap.get(id);
		producto.consumir(cantidadConsumir);
	}

	public String crearTiket() {
		Date fecha = new Date();
		long fechaAux = fecha.getTime();
		return Long.toString(fechaAux);
	}

	public void mostrarProductosCatalogo() {
		for (Map.Entry<String, Producto> entry : this.catalogoTreeMap.entrySet()) {
			Producto producto = entry.getValue();
			System.out.println("Nombre: " + producto.getNombre());
			System.out.println("Disponible: " + producto.getCantidad());
			System.out.println("Precio Unitario: " + producto.getPrecioUnidad() + " euros.\n");
		}
	}

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
