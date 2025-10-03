package repository;

import model.Producto;

import java.util.List;

public interface Repositorio {

    Producto crearProducto(Producto producto);

    Producto buscarPorId(Integer id);

    List<Producto> buscarTodos();

    Producto actualizarProducto(Producto producto);

    void eliminar(Integer id);

}