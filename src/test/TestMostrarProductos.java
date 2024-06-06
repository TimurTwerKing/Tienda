package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import data.Producto;
import logic.GestionProducto;

/**
 * Pruebas unitarias para los métodos mostrarProductosActivos() y
 * mostrarProductosInactivos() de la clase GestionProducto.
 * 
 * @autor Timur Bogach
 * @date 16 may 2024
 */
public class TestMostrarProductos {

	private GestionProducto gestionProducto;
	private List<Producto> catalogoTest;

	/**
	 * Configuración inicial antes de cada prueba. Inicializa una instancia de
	 * GestionProducto y agrega productos de prueba.
	 */
	@BeforeEach
	public void setUp() {
		gestionProducto = new GestionProducto();
		catalogoTest = new ArrayList<>();

		// Agregar productos de prueba al catálogo
		catalogoTest.add(new Producto(1, "The Matrix", 12.99f, 65, true, "Sci-Fi", 1, 1, true));
		catalogoTest.add(new Producto(2, "The Godfather", 18.99f, 45, true, "Crime", 1, 1, true));
		catalogoTest.add(new Producto(3, "Gladiator", 15.99f, 75, true, "Action", 1, 1, true));
		catalogoTest.add(new Producto(4, "The Witcher 3: Wild Hunt", 29.99f, 100, true, "RPG", 2, 2, true));
		catalogoTest.add(new Producto(5, "Red Dead Redemption 2", 49.99f, 90, true, "Action", 2, 2, true));
		catalogoTest.add(new Producto(6, "Cyberpunk 2077", 49.99f, 55, true, "RPG", 2, 2, true));
		catalogoTest.add(new Producto(7, "Back in Black - AC/DC", 13.99f, 90, true, "Rock", 3, 3, true));
		catalogoTest
				.add(new Producto(8, "The Dark Side of the Moon - Pink Floyd", 17.99f, 80, true, "Rock", 3, 3, true));
		catalogoTest.add(new Producto(9, "The Wall - Pink Floyd", 18.99f, 70, true, "Rock", 3, 3, true));
		catalogoTest.add(new Producto(10, "Producto Inactivo", 19.99f, 50, true, "Misc", 4, 4, false));

		gestionProducto.setCatalogo(catalogoTest);
	}

	/**
	 * Prueba que el método mostrarProductosActivos() muestra correctamente los
	 * productos activos del catálogo.
	 */
	@Test
	public void testMostrarProductosActivos() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosActivos();

		// Verificar que la salida contiene los productos activos agregados
		assertTrue(resultado.contains("The Matrix"));
		assertTrue(resultado.contains("The Godfather"));
		assertTrue(resultado.contains("Gladiator"));
		assertTrue(resultado.contains("The Witcher 3: Wild Hunt"));
		assertTrue(resultado.contains("Red Dead Redemption 2"));
		assertTrue(resultado.contains("Cyberpunk 2077"));
		assertTrue(resultado.contains("Back in Black - AC/DC"));
		assertTrue(resultado.contains("The Dark Side of the Moon - Pink Floyd"));
		assertTrue(resultado.contains("The Wall - Pink Floyd"));

		// Verificar que la salida no contiene productos inactivos
		assertFalse(resultado.contains("Producto Inactivo"));
	}

	/**
	 * Prueba que el método mostrarProductosInactivos() muestra correctamente los
	 * productos inactivos del catálogo.
	 */
	@Test
	public void testMostrarProductosInactivos() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosInactivos();

		// Verificar que la salida contiene los productos inactivos agregados
		assertTrue(resultado.contains("Producto Inactivo"));

		// Verificar que la salida no contiene productos activos
		assertFalse(resultado.contains("The Matrix"));
		assertFalse(resultado.contains("The Godfather"));
		assertFalse(resultado.contains("Gladiator"));
		assertFalse(resultado.contains("The Witcher 3: Wild Hunt"));
		assertFalse(resultado.contains("Red Dead Redemption 2"));
		assertFalse(resultado.contains("Cyberpunk 2077"));
		assertFalse(resultado.contains("Back in Black - AC/DC"));
		assertFalse(resultado.contains("The Dark Side of the Moon - Pink Floyd"));
		assertFalse(resultado.contains("The Wall - Pink Floyd"));
	}

	/**
	 * Prueba que el método mostrarProductosActivos() muestra correctamente un
	 * catálogo vacío.
	 */
	@Test
	public void testMostrarProductosCatalogoVacio() {
		// Crear una instancia de GestionProducto con un catálogo vacío
		gestionProducto.setCatalogo(new ArrayList<>());

		// Obtener la salida del método cuando el catálogo está vacío
		String resultado = gestionProducto.mostrarProductosActivos();

		// Verificar que la salida está vacía
		assertEquals("", resultado.trim());
	}

	/**
	 * Prueba que el método mostrarProductosActivos() no muestra productos que no
	 * están en el catálogo.
	 */
	@Test
	public void testMostrarProductosCatalogoProductoNoExistente() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosActivos();

		// Verificar que la salida no contiene productos no agregados
		assertFalse(resultado.contains("Producto No Existente"));
	}

	/**
	 * Prueba que el método mostrarProductosActivos() muestra correctamente
	 * múltiples productos del mismo tipo.
	 */
	@Test
	public void testMostrarProductosCatalogoMultiplesProductos() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosActivos();

		// Verificar que la salida contiene múltiples productos
		assertTrue(resultado.contains("The Matrix"));
		assertTrue(resultado.contains("The Godfather"));
		assertTrue(resultado.contains("Gladiator"));
		assertTrue(resultado.contains("The Witcher 3: Wild Hunt"));
		assertTrue(resultado.contains("Red Dead Redemption 2"));
		assertTrue(resultado.contains("Cyberpunk 2077"));
		assertTrue(resultado.contains("Back in Black - AC/DC"));
		assertTrue(resultado.contains("The Dark Side of the Moon - Pink Floyd"));
		assertTrue(resultado.contains("The Wall - Pink Floyd"));
	}

	/**
	 * Prueba que el método mostrarProductosActivos() muestra correctamente los
	 * productos con y sin stock.
	 */
	@Test
	public void testMostrarProductosCatalogoConYSinStock() {
		// Agregar productos sin stock al catálogo
		catalogoTest.add(new Producto(11, "Producto Sin Stock", 19.99f, 0, false, "Misc", 4, 4, true));
		gestionProducto.setCatalogo(catalogoTest);

		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosActivos();

		// Verificar que la salida contiene los productos agregados
		assertTrue(resultado.contains("Producto Sin Stock"));
		assertTrue(resultado.contains("Cantidad: 0"));
	}

	/**
	 * Prueba que el método mostrarProductosActivos() muestra correctamente la
	 * cantidad de productos.
	 */
	@Test
	public void testCantidadMostrarProductosCatalogo() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosActivos();

		// Contar la cantidad de productos en el resultado
		int cantidadProductos = resultado.split("ID:").length - 1;

		// Verificar que la cantidad de productos es correcta
		assertEquals(9, cantidadProductos);

		// Agregar otro producto y verificar la cantidad nuevamente
		catalogoTest.add(new Producto(12, "Nuevo Producto", 9.99f, 10, true, "Misc", 4, 4, true));
		gestionProducto.setCatalogo(catalogoTest);
		resultado = gestionProducto.mostrarProductosActivos();
		cantidadProductos = resultado.split("ID:").length - 1;

		// Verificar que la cantidad de productos es correcta
		assertEquals(10, cantidadProductos);
	}
}
