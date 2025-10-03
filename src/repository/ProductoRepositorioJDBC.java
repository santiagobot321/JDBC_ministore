package repository;

import model.Producto;
import util.ConnectionFactory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioJDBC implements Repositorio {

    @Override
    public Producto crearProducto(Producto producto) {
        String sql = "INSERT INTO productos (nombre,precio,stock) VALUES (?,?,?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new RuntimeException("No se agregó ningún producto");
            }

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int idGenerado = rs.getInt(1);
                    producto.setId(idGenerado);
                } else {
                    throw new RuntimeException("No se pudo obtener el ID generado");
                }
            }

            return producto;

        } catch (SQLException e) {
            throw new RuntimeException("Error al agregar un nuevo producto", e);
        }
    }


    @Override
    public Producto buscarPorId(Integer id) {
        String sql = "SELECT id, nombre, precio, stock FROM productos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
             stmt.setInt(1,id);

             try (ResultSet rs = stmt.executeQuery()) {
                 if (rs.next()) {
                     Integer pid = rs.getInt("id");
                     String nombre = rs.getString("nombre");
                     Double precio = rs.getDouble("precio");
                     Integer stock = rs.getInt("stock");

                     return new Producto(pid,nombre,precio,stock);
                 }
             }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar producto por id",e);
        }
        return null;
    }

    @Override
    public List<Producto> buscarTodos() {
        String sql = "SELECT id,nombre,precio,stock FROM productos";

        List<Producto> productos = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            try (ResultSet rs = stmt.executeQuery()){
                while(rs.next()) {
                    Integer pid = rs.getInt("id");
                    String nombre = rs.getString("nombre");
                    Double precio = rs.getDouble("precio");
                    Integer stock = rs.getInt("stock");

                    productos.add(new Producto(pid,nombre,precio,stock));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al encontrar los productos", e);
        }
        return productos;
    }

    @Override
    public Producto actualizarProducto(Producto producto) {
        String sql = "UPDATE productos SET nombre = ?, precio = ?, stock = ? WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.setInt(4, producto.getId());

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new RuntimeException("No se actualizó ningún producto");
            }

            // Refrescar el mismo objeto con los datos actuales de la BD
            String sqlSelect = "SELECT nombre, precio, stock FROM productos WHERE id = ?";
            try (PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect)) {
                stmtSelect.setInt(1, producto.getId());

                try (ResultSet rs = stmtSelect.executeQuery()) {
                    if (rs.next()) {
                        producto.setNombre(rs.getString("nombre"));
                        producto.setPrecio(rs.getDouble("precio"));
                        producto.setStock(rs.getInt("stock"));
                    } else {
                        throw new RuntimeException("El producto no fue encontrado después de la actualización");
                    }
                }
            }

            return producto;

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el producto", e);
        }
    }


    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM productos WHERE id = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas == 0) {
                throw new RuntimeException("No se eliminó ningún producto con id: " + id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar el producto con id: " + id, e);
        }
    }
}