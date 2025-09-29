package com.tienda.app;

import model.Producto;
import service.ServicioInventario;
import service.ServicioInventarioImpl;

import javax.swing.*;
import java.util.List;

public class Main {
    private static final ServicioInventario servicio = new ServicioInventarioImpl();

    public static void main(String[] args) {
        while (true) {
            String menu = "Mini-Tienda - Elige opción:\n"
                    + "1. Agregar producto\n"
                    + "2. Listar inventario\n"
                    + "3. Actualizar precio\n"
                    + "4. Actualizar stock\n"
                    + "5. Eliminar producto\n"
                    + "6. Buscar producto por nombre\n"
                    + "7. Salir";

            String opt = JOptionPane.showInputDialog(null, menu, "Mini-Tienda", JOptionPane.QUESTION_MESSAGE);
            if (opt == null) { // usuario cerró el diálogo
                mostrarResumenYAySalir();
            }

            try {
                switch (opt) {
                    case "1" -> accionAgregar();
                    case "2" -> accionListar();
                    case "3" -> accionActualizarPrecio();
                    case "4" -> accionActualizarStock();
                    case "5" -> accionEliminar();
                    case "6" -> accionBuscarPorNombre();
                    case "7" -> { mostrarResumenYAySalir(); }
                    default -> JOptionPane.showMessageDialog(null, "Opción inválida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException nf) {
                JOptionPane.showMessageDialog(null, "Número inválido. Intenta de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private static void accionAgregar() throws Exception {
        String nombre = solicitarNoVacio("Nombre:");
        String sPrecio = JOptionPane.showInputDialog(null, "Precio (ej: 12.50):", "Agregar", JOptionPane.QUESTION_MESSAGE);
        if (sPrecio == null) return;
        double precio = Double.parseDouble(sPrecio);
        if (precio < 0) { JOptionPane.showMessageDialog(null, "Precio inválido", "Error", JOptionPane.ERROR_MESSAGE); return; }

        String sStock = JOptionPane.showInputDialog(null, "Stock (entero):", "Agregar", JOptionPane.QUESTION_MESSAGE);
        if (sStock == null) return;
        int stock = Integer.parseInt(sStock);
        if (stock < 0) { JOptionPane.showMessageDialog(null, "Stock inválido", "Error", JOptionPane.ERROR_MESSAGE); return; }

        servicio.agregarProducto(nombre, precio, stock);
        JOptionPane.showMessageDialog(null, "Producto agregado correctamente.", "OK", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void accionListar() throws Exception {
        List<Producto> lista = servicio.listarTodos();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos registrados.", "Inventario", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Producto p : lista) sb.append(p).append("\n");
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(area), "Inventario", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void accionActualizarPrecio() throws Exception {
        Integer id = solicitarId("ID del producto a actualizar precio:");
        if (id == null) return;
        String sPrecio = JOptionPane.showInputDialog(null, "Nuevo precio (ej: 12.50):", "Actualizar precio", JOptionPane.QUESTION_MESSAGE);
        if (sPrecio == null) return;
        double precio = Double.parseDouble(sPrecio);
        if (precio < 0) { JOptionPane.showMessageDialog(null, "Precio inválido", "Error", JOptionPane.ERROR_MESSAGE); return; }
        servicio.actualizarPrecio(id, precio);
        JOptionPane.showMessageDialog(null, "Precio actualizado.", "OK", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void accionActualizarStock() throws Exception {
        Integer id = solicitarId("ID del producto a actualizar stock:");
        if (id == null) return;
        String sStock = JOptionPane.showInputDialog(null, "Nuevo stock (entero):", "Actualizar stock", JOptionPane.QUESTION_MESSAGE);
        if (sStock == null) return;
        int stock = Integer.parseInt(sStock);
        if (stock < 0) { JOptionPane.showMessageDialog(null, "Stock inválido", "Error", JOptionPane.ERROR_MESSAGE); return; }
        servicio.actualizarStock(id, stock);
        JOptionPane.showMessageDialog(null, "Stock actualizado.", "OK", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void accionEliminar() throws Exception {
        Integer id = solicitarId("ID del producto a eliminar:");
        if (id == null) return;
        int confirm = JOptionPane.showConfirmDialog(null, "¿Confirma eliminar producto con id " + id + "?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        servicio.eliminarProducto(id);
        JOptionPane.showMessageDialog(null, "Producto eliminado.", "OK", JOptionPane.INFORMATION_MESSAGE);
    }

    private static void accionBuscarPorNombre() throws Exception {
        String texto = JOptionPane.showInputDialog(null, "Texto para buscar en nombre (parcial):", "Buscar", JOptionPane.QUESTION_MESSAGE);
        if (texto == null) return;
        List<Producto> resultados = servicio.buscarPorNombre(texto.trim());
        if (resultados.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No se encontraron coincidencias.", "Buscar", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (Producto p : resultados) sb.append(p).append("\n");
        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(area), "Resultados", JOptionPane.INFORMATION_MESSAGE);
    }

    private static Integer solicitarId(String mensaje) {
        String s = JOptionPane.showInputDialog(null, mensaje, "Input", JOptionPane.QUESTION_MESSAGE);
        if (s == null) return null;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "ID inválido", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }

    private static String solicitarNoVacio(String prompt) {
        while (true) {
            String s = JOptionPane.showInputDialog(null, prompt, "Input", JOptionPane.QUESTION_MESSAGE);
            if (s == null) return null;
            if (s.trim().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Campo requerido. Intenta de nuevo.", "Error", JOptionPane.ERROR_MESSAGE);
                continue;
            }
            return s.trim();
        }
    }

    private static void mostrarResumenYAySalir() {
        String resumen = String.format("Resumen de operaciones:\nAltas: %d\nBajas: %d\nActualizaciones: %d",
                servicio.getContadorAltas(), servicio.getContadorBajas(), servicio.getContadorActualizaciones());
        JOptionPane.showMessageDialog(null, resumen, "Resumen", JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }
}
