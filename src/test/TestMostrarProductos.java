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
	 * GestionProducto y agrega productos de prueba.
	 */
	@BeforeEach
	public void setUp() {
		gestionProducto = new GestionProducto();
		List<Producto> catalogoTest = new ArrayList<>();

		// Agregar productos de prueba al catálogo
		catalogoTest.add(new Producto("The Witcher 3: Wild Hunt", 29.99f, 100, true, "RPG", 1, 2));
		catalogoTest.add(new Producto("Red Dead Redemption 2", 49.99f, 90, true, "Action", 2, 2));
		catalogoTest.add(new Producto("Cyberpunk 2077", 49.99f, 55, true, "RPG", 3, 2));
		catalogoTest.add(new Producto("Back in Black - AC/DC", 13.99f, 90, true, "Rock", 4, 3));
		catalogoTest.add(new Producto("The Dark Side of the Moon - Pink Floyd", 17.99f, 80, true, "Rock", 5, 3));
		catalogoTest.add(new Producto("The Wall - Pink Floyd", 18.99f, 70, true, "Rock", 6, 3));
		catalogoTest.add(new Producto("The Matrix", 12.99f, 65, true, "Sci-Fi", 7, 1));
		catalogoTest.add(new Producto("The Godfather", 18.99f, 45, true, "Crime", 8, 1));
		catalogoTest.add(new Producto("Gladiator", 15.99f, 75, true, "Action", 9, 1));

		gestionProducto.setCatalogo(catalogoTest);
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente los
	 * productos del catálogo.
	 */
	@Test
	public void testMostrarProductosCatalogo() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida contiene los productos agregados
		assertTrue(resultado.contains("The Witcher 3: Wild Hunt"));
		assertTrue(resultado.contains("Red Dead Redemption 2"));
		assertTrue(resultado.contains("Cyberpunk 2077"));
		assertTrue(resultado.contains("Back in Black - AC/DC"));
		assertTrue(resultado.contains("The Dark Side of the Moon - Pink Floyd"));
		assertTrue(resultado.contains("The Wall - Pink Floyd"));
		assertTrue(resultado.contains("The Matrix"));
		assertTrue(resultado.contains("The Godfather"));
		assertTrue(resultado.contains("Gladiator"));
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente un
	 * catálogo vacío.
	 */
	@Test
	public void testMostrarProductosCatalogoVacio() {
		// Crear una instancia de GestionProducto con un catálogo vacío
		gestionProducto.setCatalogo(new ArrayList<>());

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
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida no contiene productos no agregados
		assertFalse(resultado.contains("Producto No Existente"));
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente
	 * múltiples productos del mismo tipo.
	 */
	@Test
	public void testMostrarProductosCatalogoMultiplesProductos() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida contiene múltiples productos
		assertTrue(resultado.contains("The Witcher 3: Wild Hunt"));
		assertTrue(resultado.contains("Red Dead Redemption 2"));
		assertTrue(resultado.contains("Cyberpunk 2077"));
		assertTrue(resultado.contains("Back in Black - AC/DC"));
		assertTrue(resultado.contains("The Dark Side of the Moon - Pink Floyd"));
		assertTrue(resultado.contains("The Wall - Pink Floyd"));
		assertTrue(resultado.contains("The Matrix"));
		assertTrue(resultado.contains("The Godfather"));
		assertTrue(resultado.contains("Gladiator"));
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente los
	 * productos con y sin stock.
	 */
	@Test
	public void testMostrarProductosCatalogoConYSinStock() {
		// Agregar productos sin stock al catálogo
		gestionProducto.agregarProductoTest(new Producto("Producto Sin Stock", 19.99f, 0, false, "Misc", 10, 4));

		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Verificar que la salida contiene los productos agregados
		assertTrue(resultado.contains("Producto Sin Stock"));
		assertTrue(resultado.contains("Cantidad: 0"));
	}

	/**
	 * Prueba que el método mostrarProductosCatalogo() muestra correctamente la
	 * cantidad de productos.
	 */
	@Test
	public void testCantidadMostrarProductosCatalogo() {
		// Obtener la salida del método
		String resultado = gestionProducto.mostrarProductosCatalogo();

		// Contar la cantidad de productos en el resultado
		int cantidadProductos = resultado.split("ID:").length - 1;

		// Verificar que la cantidad de productos es correcta
		assertEquals(9, cantidadProductos);

		// Agregar otro producto y verificar la cantidad nuevamente
		gestionProducto.agregarProductoTest(new Producto("Nuevo Producto", 9.99f, 10, true, "Misc", 10, 4));
		resultado = gestionProducto.mostrarProductosCatalogo();
		cantidadProductos = resultado.split("ID:").length - 1;

		// Verificar que la cantidad de productos es correcta
		assertEquals(10, cantidadProductos);
	}

}
