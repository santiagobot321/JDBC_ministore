package repository;

import java.util.List;

public interface Repositorio<T> {
    void crear(T entidad);
    T buscarPorId(Integer id);
    List<T> buscarTodos();
    void actualizar(T entidad);
    void eliminar(Integer id);
}
