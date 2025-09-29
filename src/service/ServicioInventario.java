package service;

import model.Producto;

import java.util.List;

public interface ServicioInventario {
    void agregarProducto(String nombre, double precio, int stock) throws Exception;
    void actualizarPrecio(int id, double nuevoPrecio) throws Exception;
    void actualizarStock(int id, int nuevoStock) throws Exception;
    void eliminarProducto(int id) throws Exception;
    List<Producto> buscarPorNombre(String texto) throws Exception;
    List<Producto> listarTodos() throws Exception;

    // Métodos para el resumen
    int getContadorAltas();
    int getContadorBajas();
    int getContadorActualizaciones();
}
