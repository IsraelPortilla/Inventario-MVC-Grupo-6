package modelo;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;
public class Inventario {
     private MongoCollection<Producto> coleccionProductos;

    public Inventario(MongoDatabase database) {
        this.coleccionProductos = database.getCollection("productos", Producto.class);
    }

    public void agregarProducto(Producto producto) {
        coleccionProductos.insertOne(producto);
    }

    public boolean actualizarCantidad(String nombre, int cantidad) {
        Bson filtro = Filters.eq("nombre", nombre);
        Bson update = Updates.set("cantidad", cantidad);
        UpdateResult result = coleccionProductos.updateOne(filtro, update);
        return result.getModifiedCount() > 0;
    }
    public boolean actualizarProducto(String nombreOriginal, Producto productoActualizado) {
        Bson filtro = Filters.eq("nombre", nombreOriginal);
        Bson update = Updates.combine(
            Updates.set("nombre", productoActualizado.getNombre()),
            Updates.set("cantidad", productoActualizado.getCantidad()),
            Updates.set("precio", productoActualizado.getPrecio())
        );
        UpdateResult result = coleccionProductos.updateOne(filtro, update);
        return result.getModifiedCount() > 0;
    }
    public List<Producto> consultarProductos() {
        return coleccionProductos.find().into(new ArrayList<>());
    }

    public double calcularValorTotal() {
        double total = 0;
        for (Producto producto : consultarProductos()) {
            total += producto.getCantidad() * producto.getPrecio();
        }
        return total;
    }
}