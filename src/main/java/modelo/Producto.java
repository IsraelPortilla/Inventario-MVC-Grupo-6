package modelo;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;
public class Producto {
    private ObjectId id;
    
    @BsonProperty("nombre")
    public String nombre;
    
    @BsonProperty("cantidad")
    public int cantidad;
    
    @BsonProperty("precio")
    public double precio;

    public Producto() {
        // Constructor vacío necesario para MongoDB
    }

    public Producto(String nombre, int cantidad, double precio) {
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
    }

    // Getters y setters
    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double calcularValor() {
        return cantidad * precio;
    }
}