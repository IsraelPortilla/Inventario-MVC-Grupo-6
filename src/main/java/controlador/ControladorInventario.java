package controlador;
import com.mycompany.control_inventario.a;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Inventario;
import modelo.Producto;
/**
 *
 * @author jair
 */
public class ControladorInventario {
    public  Inventario model;
    public a vista;
public ControladorInventario(Inventario modelo, a vista) {
        this.model = modelo;
        this.vista = vista;
        // Configurar listeners
        this.vista.agregarListenerAgregar(new ListenerAgregar());
        this.vista.agregarListenerActualizar(new ListenerActualizar());
        this.vista.agregarListenerConsultar(new ListenerConsultar());
        this.vista.agregarListenerCalcularTotal(new ListenerCalcularTotal());
    }

    class ListenerAgregar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = vista.getNombre();
                String cantidadTexto = vista.getCantidadTexto();
                String precioTexto = vista.getPrecioTexto();

                if (nombre.isEmpty() || cantidadTexto.isEmpty() || precioTexto.isEmpty()) {
                    vista.mostrarMensaje("Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int cantidad = Integer.parseInt(cantidadTexto);
                double precio = Double.parseDouble(precioTexto);

                model.agregarProducto(new Producto(nombre, cantidad, precio));
                vista.mostrarProductos(model.consultarProductos());
                vista.limpiarCampos();
                vista.mostrarMensaje("Producto agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                vista.mostrarMensaje("Valores numéricos inválidos", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                vista.mostrarMensaje("Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ListenerActualizar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String nombre = vista.getNombre();
                String cantidadTexto = vista.getCantidadTexto();

                if (nombre.isEmpty() || cantidadTexto.isEmpty()) {
                    vista.mostrarMensaje("Nombre y cantidad son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int cantidad = Integer.parseInt(cantidadTexto);

                if (model.actualizarCantidad(nombre, cantidad)) {
                    vista.mostrarProductos(model.consultarProductos());
                    vista.mostrarMensaje("Cantidad actualizada", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    vista.mostrarMensaje("Producto no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                vista.mostrarMensaje("Cantidad inválida", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class ListenerConsultar implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<Producto> productos = model.consultarProductos();
            vista.mostrarProductos(productos);
            
            if (productos.isEmpty()) {
                vista.mostrarMensaje("No hay productos", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    class ListenerCalcularTotal implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            double total = model.calcularValorTotal();
            vista.mostrarValorTotal(total);
        }
    }
}