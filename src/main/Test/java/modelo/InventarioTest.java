/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author israe
 */
package modelo;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class InventarioTest {

    private Inventario inventario;
    private MongoCollection<Producto> mockColeccion;
    
    @BeforeEach
    public void setUp() {
    mockColeccion = Mockito.mock(MongoCollection.class);
    inventario = new Inventario(mockColeccion); // ✅ ahora usas el constructor especial
    }

    @Test
    public void testAgregarProducto() {
    Producto producto = new Producto("Café", 10, 5.99);

    // Configurar el comportamiento simulado del método insertOne
    InsertOneResult mockResultado = Mockito.mock(InsertOneResult.class);
    
    // Aquí es donde corregimos: insertOne() es un método que devuelve un resultado
    Mockito.when(mockColeccion.insertOne(producto)).thenReturn(mockResultado);

    boolean resultado;resultado = inventario.agregarProducto(producto);

    assertTrue(resultado);
     }

    @Test
    public void testActualizarCantidad() {
        String nombre = "Teclado";
        int nuevaCantidad = 20;

        UpdateResult mockResult = mock(UpdateResult.class);
        when(mockResult.getModifiedCount()).thenReturn(1L);
        when(mockColeccion.updateOne(any(Bson.class), any(Bson.class))).thenReturn(mockResult);

        // Método que se está probando
        boolean actualizado = inventario.actualizarCantidad(nombre, nuevaCantidad);

        assertTrue(actualizado);
        verify(mockColeccion, times(1)).updateOne(any(Bson.class), any(Bson.class));
    }
}
