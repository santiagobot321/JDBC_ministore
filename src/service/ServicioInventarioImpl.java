package service;

import model.Producto;
import repository.ProductoRepositorioJDBC;
import repository.Repositorio;

import java.sql.SQLException;
import java.util.List;

public class ServicioInventarioImpl implements ServicioInventario {
    private final ProductoRepositorioJDBC repo;
    private int contadorAltas = 0;
    private int contadorBajas = 0;
    private int contadorActualizaciones = 0;

    public ServicioInventarioImpl() {
        this.repo = new ProductoRepositorioJDBC();
    }

    @Override
    public void agregarProducto(String nombre, double precio, int stock) throws Exception {
        // Validaciones de negocio
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre no puede estar vacío");
        }
        if (precio < 0) {
            throw new IllegalArgumentException("Precio no puede ser negativo");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("Stock no puede ser negativo");
        }

        Producto p = new Producto(nombre, precio, stock);
        try {
            repo.crear(p);
            contadorAltas++;
        } catch (RuntimeException e) {
            // detectamos violación UNIQUE por SQLState «23...»
            Throwable cause = e.getCause();
            if (e.getCause() instanceof java.sql.SQLException) {
                java.sql.SQLException sq = (java.sql.SQLException) e.getCause();
                if (sq.getSQLState() != null && sq.getSQLState().startsWith("23")) {
                    throw new SQLException("Ya existe un producto con ese nombre (único)", sq);
                }
            }
            // si e itself es SQLException
            if (e.getCause() == null && e.getMessage() != null && e.getMessage().contains("Duplicate")) {
                throw new SQLException("Ya existe un producto con ese nombre (único)");
            }
            // rethrow para manejo en UI
            throw e;
        }
    }

    @Override
    public void actualizarPrecio(int id, double nuevoPrecio) throws Exception {
        if (nuevoPrecio < 0) throw new IllegalArgumentException("Precio no puede ser negativo");
        Producto existente = repo.buscarPorId(id);
        if (existente == null) throw new IllegalArgumentException("Producto no encontrado");
        existente.setPrecio(nuevoPrecio);
        repo.actualizar(existente);
        contadorActualizaciones++;
    }

    @Override
    public void actualizarStock(int id, int nuevoStock) throws Exception {
        if (nuevoStock < 0) throw new IllegalArgumentException("Stock no puede ser negativo");
        Producto existente = repo.buscarPorId(id);
        if (existente == null) throw new IllegalArgumentException("Producto no encontrado");
        existente.setStock(nuevoStock);
        repo.actualizar(existente);
        contadorActualizaciones++;
    }

    @Override
    public void eliminarProducto(int id) throws Exception {
        Producto existente = repo.buscarPorId(id);
        if (existente == null) throw new IllegalArgumentException("Producto no encontrado");
        repo.eliminar(id);
        contadorBajas++;
    }

    @Override
    public List<Producto> buscarPorNombre(String texto) throws Exception {
        if (texto == null) texto = "";
        return repo.buscarPorNombre(texto);
    }

    @Override
    public List<Producto> listarTodos() throws Exception {
        return repo.buscarTodos();
    }

    @Override
    public int getContadorAltas() {
        return contadorAltas;
    }

    @Override
    public int getContadorBajas() {
        return contadorBajas;
    }

    @Override
    public int getContadorActualizaciones() {
        return contadorActualizaciones;
    }
}
