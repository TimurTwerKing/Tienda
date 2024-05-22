package logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Clase para gestionar los albaranes.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class GestionAlbaran {

	/**
	 * Guarda un albarán en la base de datos.
	 * 
	 * @param idProveedor  El ID del proveedor.
	 * @param fechaEntrega La fecha de entrega del albarán.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void guardarAlbaranEnBaseDeDatos(int idProveedor, String fechaEntrega, Connection conn) throws SQLException {
		String sql = "INSERT INTO Albaran (id_proveedor, fecha_entrega) VALUES (?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idProveedor);
			pstmt.setString(2, fechaEntrega);
			pstmt.executeUpdate();
		} finally {
			if (conn != null) {
				conn.close();
			}
		}
	}
}