package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Connection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data.Producto;
import logic.GestionProducto;

/**
 * Pruebas unitarias para el método mostrarProductosCatalogo() de la clase
 * GestionProducto.
 * 
 * @autor Timur Bogach
 * @date 16 may 2024
 */
public class TestMostrarProductos {

	private GestionProducto gestionProducto;

	/**
	 * Configuración inicial antes de cada prueba. Inicializa una instancia de
	 * GestionProducto.
	 */
	@BeforeEach
	public void setUp() {
		gestionProducto = new GestionProducto();
	}


	
	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente los
	 * productos del catálogo.
	 */
	@Test
	public void testMostrarProductosCatalogo() {
	
		gestionProducto.mostrarProductosCatalogo();

		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida contiene los productos agregados
		assertTrue(resultado.contains("Producto 1"));
		assertTrue(resultado.contains("Producto 2"));

		// Verificar detalles específicos del primer producto
		assertTrue(resultado.contains("ID: 1"));
		assertTrue(resultado.contains("Nombre: Producto 1"));
		assertTrue(resultado.contains("Disponible: 5"));
		assertTrue(resultado.contains("Precio Unitario: 10.0 euros"));

		// Verificar detalles específicos del segundo producto
		assertTrue(resultado.contains("ID: 2"));
		assertTrue(resultado.contains("Nombre: Producto 2"));
		assertTrue(resultado.contains("Disponible: 3"));
		assertTrue(resultado.contains("Precio Unitario: 15.0 euros"));
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente un
	 * catálogo vacío.
	 */
	@Test
	public void testMostrarProductosCatalogoVacio() {
		// Obtener la salida del método cuando el catálogo está vacío
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida está vacía
		assertEquals("", resultado.trim());
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() no muestra productos que no
	 * están en el catálogo.
	 */
	@Test
	public void testMostrarProductosCatalogoProductoNoExistente() {
		// Crear un producto de prueba
		Producto producto1 = new Producto("Producto 1", 10.0f, 5, true, "Genero 1", 1, 1);

		// Agregar el producto al catálogo
		gestionProducto.agregarProducto(producto1);

		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida no contiene productos no agregados
		assertFalse(resultado.contains("Producto 2"));
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente
	 * múltiples productos del mismo tipo.
	 */
	@Test
	public void testMostrarProductosCatalogoMultiplesProductos() {
		// Crear productos de prueba
		Producto producto1 = new Producto("Producto 1", 10.0f, 5, true, "Genero 1", 1, 1);
		Producto producto2 = new Producto("Producto 2", 15.0f, 3, true, "Genero 2", 2, 2);
		Producto producto3 = new Producto("Producto 3", 20.0f, 7, true, "Genero 3", 3, 3);

		// Agregar productos al catálogo
		gestionProducto.agregarProductoTest(producto1);
		gestionProducto.agregarProductoTest(producto2);
		gestionProducto.agregarProductoTest(producto3);

		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida contiene todos los productos agregados
		assertTrue(resultado.contains("Producto 1"));
		assertTrue(resultado.contains("Producto 2"));
		assertTrue(resultado.contains("Producto 3"));
	}

	// Prueba que el método maneja productos con nombre vacío
	@Test
	public void testProductoConNombreVacio() {
		Producto producto = new Producto("", 10.0f, 5, true, "Genero 1", 1, 1);
		gestionProducto.agregarProductoTest(producto);
		String resultado = gestionProducto.mostrarProductosCatalogo();
		assertTrue(resultado.contains("ID: 1"));
		assertTrue(resultado.contains("Nombre: "));
	}

	// Prueba que el método maneja productos con nombre nulo
	@Test
	public void testProductoConNombreNulo() {
		Producto producto = new Producto(null, 10.0f, 5, true, "Genero 1", 1, 1);
		gestionProducto.agregarProductoTest(producto);
		String resultado = gestionProducto.mostrarProductosCatalogo();
		assertTrue(resultado.contains("ID: 1"));
		assertTrue(resultado.contains("Nombre: null"));
	}

	// Prueba que el método maneja productos con cantidad disponible cero
	@Test
	public void testProductoConCantidadCero() {
		Producto producto = new Producto("Producto 1", 10.0f, 0, true, "Genero 1", 1, 1);
		gestionProducto.agregarProductoTest(producto);
		String resultado = gestionProducto.mostrarProductosCatalogo();
		assertTrue(resultado.contains("Disponible: 0"));
	}

	// Prueba que el método maneja productos con precio negativo
	@Test
	public void testProductoConPrecioNegativo() {
		Producto producto = new Producto("Producto 1", -10.0f, 5, true, "Genero 1", 1, 1);
		gestionProducto.agregarProductoTest(producto);
		String resultado = gestionProducto.mostrarProductosCatalogo();
		assertTrue(resultado.contains("Precio Unitario: -10.0 euros"));
	}

	// Prueba que el método maneja productos no disponibles
	@Test
	public void testProductoNoDisponible() {
		Producto producto = new Producto("Producto 1", 10.0f, 5, false, "Genero 1", 1, 1);
		gestionProducto.agregarProductoTest(producto);
		String resultado = gestionProducto.mostrarProductosCatalogo();
		assertTrue(resultado.contains("Disponible: No"));
	}

	// Prueba que el método muestra el formato correcto de salida
	@Test
	public void testFormatoSalida() {
		Producto producto1 = new Producto("Producto 1", 10.0f, 5, true, "Genero 1", 1, 1);
		Producto producto2 = new Producto("Producto 2", 15.0f, 3, true, "Genero 2", 2, 2);
		gestionProducto.agregarProductoTest(producto1);
		gestionProducto.agregarProductoTest(producto2);
		String resultado = gestionProducto.mostrarProductosCatalogo();
		assertTrue(resultado.contains("ID: 1"));
		assertTrue(resultado.contains("Nombre: Producto 1"));
		assertTrue(resultado.contains("ID: 2"));
		assertTrue(resultado.contains("Nombre: Producto 2"));
	}
}