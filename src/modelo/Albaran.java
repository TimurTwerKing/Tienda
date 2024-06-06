package modelo;

import java.sql.Date;

public class Albaran {
	private int id;
	private int idProveedor;
	private Date fechaEntrega;

	public Albaran(int id, int idProveedor, Date fechaEntrega) {
		this.id = id;
		this.idProveedor = idProveedor;
		this.fechaEntrega = fechaEntrega;
	}

	public Albaran(int idProveedor, Date fechaEntrega) {
		this.idProveedor = idProveedor;
		this.fechaEntrega = fechaEntrega;
	}

	// Getters y Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIdProveedor() {
		return idProveedor;
	}

	public void setIdProveedor(int idProveedor) {
		this.idProveedor = idProveedor;
	}

	public Date getFechaEntrega() {
		return fechaEntrega;
	}

	public void setFechaEntrega(Date fechaEntrega) {
		this.fechaEntrega = fechaEntrega;
	}

	@Override
	public String toString() {
		return "Albaran [id=" + id + ", idProveedor=" + idProveedor + ", fechaEntrega=" + fechaEntrega + "]";
	}
}
