package data;

import java.util.ArrayList;

import logic.Producto;

public class GestionProducto {

	private ArrayList<Producto> productosList = null;
//	public double caja;

	public GestionProducto() {

	}

	public GestionProducto(ArrayList<Producto> productos) {
		this.productosList = productos;
	}
	
	/**
	 * @return the productos
	 */
	public ArrayList<Producto> getProductos() {
		return productosList;
	}


	public void agregarProductos(ArrayList<Producto> productos){
		productos.add(new Producto());		
	}
//	/**
//	 * @return the caja
//	 */
//	public double getCaja() {
//		return caja;
//	}
//
//	/**
//	 * @param caja the caja to set
//	 */
//	public void setCaja(double caja) {
//		this.caja = caja;
//	}

	public void mostrarProductos(ArrayList<Producto> productos) {

		for (int i = 0; i < productos.size(); i++) {
//"\n" + productos.get(i)+ 
			System.out.print("\n" + "Nombre: " + productos.get(i).getNombre() + "\n" + "Disponible: "
					+ productos.get(i).getCantidad() + "\n" + "Precio Unitario: " + productos.get(i).getPrecioUnidad()
					+ " euros." + "\n");
		}
	}
}
