package modelo;

import java.sql.Date;

/**
 * Clase Albaran que representa un albarán en la base de datos.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class Albaran {
	private int id; // ID del albarán
	private int idProveedor; // ID del proveedor asociado al albarán
	private Date fechaEntrega; // Fecha de entrega del albarán

	/**
	 * Constructor completo de la clase Albaran.
	 * 
	 * @param id           El ID del albarán.
	 * @param idProveedor  El ID del proveedor.
	 * @param fechaEntrega La fecha de entrega del albarán.
	 */
	public Albaran(int id, int idProveedor, Date fechaEntrega) {
		this.id = id;
		this.idProveedor = idProveedor;
		this.fechaEntrega = fechaEntrega;
	}

	/**
	 * Constructor parcial de la clase Albaran, sin el ID del albarán.
	 * 
	 * @param idProveedor  El ID del proveedor.
	 * @param fechaEntrega La fecha de entrega del albarán.
	 */
	public Albaran(int idProveedor, Date fechaEntrega) {
		this.idProveedor = idProveedor;
		this.fechaEntrega = fechaEntrega;
	}

	// Getters y Setters

	/**
	 * Obtiene el ID del albarán.
	 * 
	 * @return El ID del albarán.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Establece el ID del albarán.
	 * 
	 * @param id El ID del albarán.
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Obtiene el ID del proveedor.
	 * 
	 * @return El ID del proveedor.
	 */
	public int getIdProveedor() {
		return idProveedor;
	}

	/**
	 * Establece el ID del proveedor.
	 * 
	 * @param idProveedor El ID del proveedor.
	 */
	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	/**
	 * Obtiene la fecha de entrega del albarán.
	 * 
	 * @return La fecha de entrega del albarán.
	 */
	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	/**
	 * Establece la fecha de entrega del albarán.
	 * 
	 * @param fechaEntrega La fecha de entrega del albarán.
	 */
	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	/**
	 * Devuelve una representación en forma de cadena del albarán.
	 * 
	 * @return Una cadena con los detalles del albarán.
	 */
	@Override
	public String toString() {
		return "Albaran [id=" + id + ", idProveedor=" + idProveedor + ", fechaEntrega=" + fechaEntrega + "]";
	}
}
