package app;
import model.Producto;
import repository.ProductoRepositorioJDBC;
import repository.Repositorio;

public class Main {
    public static void main(String[] args) {
        Repositorio repo = new ProductoRepositorioJDBC();

        // Crear producto
        Producto nuevo = new Producto(null, "Laptop Gamer", 2500.0, 5);
        repo.crearProducto(nuevo);
        System.out.println("Producto creado con id: " + nuevo.getId());

        // Buscar por ID
        Producto buscado = repo.buscarPorId(nuevo.getId());
        System.out.println("Buscado: " + buscado.getNombre());

        // Actualizar
        buscado.setPrecio(2700.0);
        repo.actualizarProducto(buscado);
        System.out.println("Actualizado: " + buscado.getPrecio());

        // Listar todos
        System.out.println("Todos los productos:");
        for (Producto p : repo.buscarTodos()) {
            System.out.println(p.getId() + " - " + p.getNombre() + " - $" + p.getPrecio());
        }

        // Eliminar
        repo.eliminar(buscado.getId());
        System.out.println("Producto eliminado");
    }
}
