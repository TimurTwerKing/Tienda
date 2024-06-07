package logic;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Albaran;
import util.Leer;

/**
 * Clase para gestionar los albaranes.
 * 
 * @autor Timur Bogach
 * @date 19 may 2024
 */
public class GestionAlbaran {
	private Connection conn;

	public GestionAlbaran(Connection conn) {
		this.conn = conn;
	}

	/**
	 * Agrega un nuevo albarán a la base de datos.
	 * 
	 * @param albaran El albarán a agregar.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void agregarAlbaran(Albaran albaran) throws SQLException {
		String sql = "INSERT INTO Albaran (id_proveedor, fecha_entrega) VALUES (?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
			pstmt.setInt(1, albaran.getIdProveedor());
			pstmt.setDate(2, albaran.getFechaEntrega());
			pstmt.executeUpdate();

			try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					albaran.setId(generatedKeys.getInt(1));
				} else {
					throw new SQLException("No se pudo obtener el ID del albarán.");
				}
			}
		}
	}

	/**
	 * Crea un albarán a partir de los datos proporcionados y lo agrega a la base de
	 * datos.
	 *
	 * @param idProveedor La ID del proveedor.
	 * @param fecha       La fecha de entrega del albarán.
	 */
	public void crearAlbaran() {
		System.out.println("Ingrese el ID del proveedor: \n1. Cine\n2. Videojuego\n3. Música");
		int idProveedor = Leer.datoInt();

		System.out.println("Ingrese la fecha de entrega (YYYY-MM-DD):");
		String fecha = Leer.datoFechaString();

		String sql = "INSERT INTO Albaran (id_proveedor, fecha_entrega) VALUES (?, ?)";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, idProveedor);
			pstmt.setDate(2, Date.valueOf(fecha));
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("El albarán no se ha podido crear.");
			e.printStackTrace();
		}
	}

	/**
	 * Muestra todos los albaranes en un formato legible.
	 * 
	 * @return Una cadena con los detalles de los albaranes.
	 */
	public String mostrarAlbaranes() {
		StringBuilder resultado = new StringBuilder();
		String sql = "SELECT * FROM Albaran";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				resultado.append("ID: ").append(rs.getInt("id")).append("\n");
				resultado.append("ID Proveedor: ").append(rs.getInt("id_proveedor")).append("\n");
				resultado.append("Fecha Entrega: ").append(rs.getDate("fecha_entrega")).append("\n\n");
			}
		} catch (SQLException e) {
			System.out.println("El albarán no se ha podido mostrar.");
			e.printStackTrace();
		}
		return resultado.toString();
	}

	/**
	 * Obtiene una lista de todos los albaranes de la base de datos.
	 * 
	 * @return Lista de albaranes.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public List<Albaran> obtenerTodosLosAlbaranes() throws SQLException {
		List<Albaran> albaranes = new ArrayList<>();
		String sql = "SELECT * FROM Albaran";
		try (PreparedStatement pstmt = conn.prepareStatement(sql); ResultSet rs = pstmt.executeQuery()) {
			while (rs.next()) {
				Albaran albaran = new Albaran(rs.getInt("id"), rs.getInt("id_proveedor"), rs.getDate("fecha_entrega"));
				albaranes.add(albaran);
			}
		}
		return albaranes;
	}

	/**
	 * Actualiza un albarán en la base de datos.
	 * 
	 * @param albaran El albarán a actualizar.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void actualizarAlbaran(Albaran albaran) throws SQLException {
		String sql = "UPDATE Albaran SET id_proveedor = ?, fecha_entrega = ? WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, albaran.getIdProveedor());
			pstmt.setDate(2, albaran.getFechaEntrega());
			pstmt.setInt(3, albaran.getId());
			pstmt.executeUpdate();
		}
	}

	/**
	 * Elimina un albarán de la base de datos por su ID.
	 * 
	 * @param id El ID del albarán a eliminar.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public void eliminarAlbaran(int id) throws SQLException {
		String sql = "DELETE FROM Albaran WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			pstmt.executeUpdate();
		}
	}

	/**
	 * Obtiene un albarán por su ID.
	 * 
	 * @param id El ID del albarán.
	 * @return El albarán encontrado, o null si no se encuentra.
	 * @throws SQLException Si ocurre un error de acceso a la base de datos.
	 */
	public Albaran obtenerAlbaranPorId(int id) throws SQLException {
		String sql = "SELECT * FROM Albaran WHERE id = ?";
		try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rs = pstmt.executeQuery()) {
				if (rs.next()) {
					return new Albaran(rs.getInt("id"), rs.getInt("id_proveedor"), rs.getDate("fecha_entrega"));
				}
			}
		}
		return null;
	}
}
