package app;

import model.Producto;
import repository.ProductoRepositorioJDBC;
import repository.Repositorio;
import service.ServicioInventario;
import service.ServicioInventarioImpl;

import javax.swing.*;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Repositorio repo = new ProductoRepositorioJDBC();
        ServicioInventario servicio = new ServicioInventarioImpl(repo);

        int operacionesAltas = 0;
        int operacionesBajas = 0;
        int operacionesActualizaciones = 0;

        while (true) {
            String opcion = JOptionPane.showInputDialog(null,
                    "=== Mini-Tienda ===\n" +
                            "1. Agregar producto\n" +
                            "2. Listar inventario\n" +
                            "3. Actualizar precio\n" +
                            "4. Actualizar stock\n" +
                            "5. Eliminar producto\n" +
                            "6. Buscar producto por nombre\n" +
                            "7. Salir\n" +
                            "Elige una opción:");

            if (opcion == null) break; // Si cierran el diálogo, salir

            try {
                switch (opcion) {
                    case "1": // Agregar producto
                        String nombre = JOptionPane.showInputDialog("Nombre:");
                        double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio:"));
                        int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock:"));
                        servicio.agregarProducto(nombre, precio, stock);
                        JOptionPane.showMessageDialog(null, "Producto agregado.");
                        operacionesAltas++;
                        break;

                    case "2": // Listar inventario
                        List<Producto> productos = servicio.listarProductos();
                        StringBuilder sb = new StringBuilder("Inventario:\n");
                        for (Producto p : productos) {
                            sb.append(p.getId()).append(" - ")
                                    .append(p.getNombre()).append(" - $")
                                    .append(p.getPrecio()).append(" - Stock: ")
                                    .append(p.getStock()).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                        break;

                    case "3": // Actualizar precio
                        int idPrecio = Integer.parseInt(JOptionPane.showInputDialog("ID del producto:"));
                        double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog("Nuevo precio:"));
                        servicio.actualizarPrecio(idPrecio, nuevoPrecio);
                        JOptionPane.showMessageDialog(null, "Precio actualizado.");
                        operacionesActualizaciones++;
                        break;

                    case "4": // Actualizar stock
                        int idStock = Integer.parseInt(JOptionPane.showInputDialog("ID del producto:"));
                        int nuevoStock = Integer.parseInt(JOptionPane.showInputDialog("Nuevo stock:"));
                        servicio.actualizarStock(idStock, nuevoStock);
                        JOptionPane.showMessageDialog(null, "Stock actualizado.");
                        operacionesActualizaciones++;
                        break;

                    case "5": // Eliminar producto
                        int idEliminar = Integer.parseInt(JOptionPane.showInputDialog("ID del producto:"));
                        servicio.eliminarProducto(idEliminar);
                        JOptionPane.showMessageDialog(null, "Producto eliminado.");
                        operacionesBajas++;
                        break;

                    case "6": // Buscar por nombre
                        String buscar = JOptionPane.showInputDialog("Texto a buscar:");
                        Producto encontrado = servicio.buscarPorNombre(buscar);
                        if (encontrado != null) {
                            JOptionPane.showMessageDialog(null,
                                    "Encontrado: " + encontrado.getId() + " - " +
                                            encontrado.getNombre() + " - $" + encontrado.getPrecio() +
                                            " - Stock: " + encontrado.getStock());
                        } else {
                            JOptionPane.showMessageDialog(null, "No se encontró ningún producto con ese nombre.");
                        }
                        break;

                    case "7": // Salir
                        JOptionPane.showMessageDialog(null,
                                "Resumen:\n" +
                                        "Altas: " + operacionesAltas + "\n" +
                                        "Bajas: " + operacionesBajas + "\n" +
                                        "Actualizaciones: " + operacionesActualizaciones);
                        System.exit(0);
                        break;

                    default:
                        JOptionPane.showMessageDialog(null, "Opción inválida.");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Entrada no válida, intenta de nuevo.");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }
}
