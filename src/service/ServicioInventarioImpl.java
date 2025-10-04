package service;

import model.Producto;
import repository.Repositorio;
import java.util.List;

public class ServicioInventarioImpl implements ServicioInventario {
    private final Repositorio repositorio;

    public ServicioInventarioImpl(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    @Override
    public Producto agregarProducto(String nombre, double precio, int stock) {
        Producto p = new Producto(null, nombre, precio, stock);
        return repositorio.crearProducto(p);
    }

    @Override
    public Producto actualizarPrecio(int id, double nuevoPrecio) {
        Producto p = repositorio.buscarPorId(id);
        if (p == null) throw new RuntimeException("Producto no encontrado");
        p.setPrecio(nuevoPrecio);
        return repositorio.actualizarProducto(p);
    }

    @Override
    public Producto actualizarStock(int id, int nuevoStock) {
        Producto p = repositorio.buscarPorId(id);
        if (p == null) throw new RuntimeException("Producto no encontrado");
        p.setStock(nuevoStock);
        return repositorio.actualizarProducto(p);
    }

    @Override
    public void eliminarProducto(int id) {
        repositorio.eliminar(id);
    }

    @Override
    public Producto buscarPorNombre(String nombre) {
        // Aquí podrías hacer un SELECT con LIKE en el repositorio
        // o simplemente traer todo y filtrar
        return repositorio.buscarTodos()
                .stream()
                .filter(p -> p.getNombre().equalsIgnoreCase(nombre))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Producto> listarProductos() {
        return repositorio.buscarTodos();
    }
}
