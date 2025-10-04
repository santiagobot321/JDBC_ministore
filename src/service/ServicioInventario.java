package service;
import java.util.*;
import model.Producto;

public interface ServicioInventario {
    Producto agregarProducto(String nombre, double precio, int stock);
    Producto actualizarPrecio(int id, double nuevoPrecio);
    Producto actualizarStock(int id, int nuevoStock);
    void eliminarProducto(int id);
    Producto buscarPorNombre(String nombre);
    List<Producto> listarProductos();
}
