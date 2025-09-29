package repository;

import model.Producto;
import util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositorioJDBC implements Repositorio<Producto> {

    @Override
    public void crear(Producto producto) {
        String sql = "INSERT INTO productos(nombre, precio, stock) VALUES (?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setBigDecimal(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error creando producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Producto buscarPorId(Integer id) {
        String sql = "SELECT id, nombre, precio, stock FROM productos WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error buscando producto por id: " + e.getMessage(), e);
        }
        return null;
    }

    @Override
    public List<Producto> buscarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, precio, stock FROM productos";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getBigDecimal("precio"),
                        rs.getInt("stock")
                ));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error listando productos: " + e.getMessage(), e);
        }
        return lista;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE productos SET nombre=?, precio=?, stock=? WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setBigDecimal(2, producto.getPrecio());
            stmt.setInt(3, producto.getStock());
            stmt.setInt(4, producto.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error actualizando producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(Integer id) {
        String sql = "DELETE FROM productos WHERE id=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error eliminando producto: " + e.getMessage(), e);
        }
    }
}
