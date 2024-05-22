package modelo;

import java.sql.Connection;
import java.util.Calendar;

/**
 * @autor Timur Bogach
 * @date 14 may 2024
 * 
 */
public class Albaraan {
	private int codigo;
	private int numeroAlbaran;
	private Calendar fechaAlbaran;
	private String producto;
	private int cantidad;

	// Constructor

	public int getCodigo(Connection conn) {
		return codigo;
	}

	public Albaraan(int codigo, int numeroAlbaran, Calendar fechaAlbaran, String producto, int cantidad) {
		super();
		this.codigo = codigo;
		this.numeroAlbaran = numeroAlbaran;
		this.fechaAlbaran = fechaAlbaran;
		this.producto = producto;
		this.cantidad = cantidad;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getNumeroAlbaran() {
		return numeroAlbaran;
	}

	public void setNumeroAlbaran(int numeroAlbaran) {
		this.numeroAlbaran = numeroAlbaran;
	}

	public Calendar getFechaAlbaran() {
		return fechaAlbaran;
	}

	public void setFechaAlbaran(Calendar fechaAlbaran) {
		this.fechaAlbaran = fechaAlbaran;
	}

	public String getProducto() {
		return producto;
	}

	public void setProducto(String producto) {
		this.producto = producto;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
