package model;

public class Producto {

    private Integer id;
    private String nombre;
    private double precio;
    private Integer stock;

    public Producto(Integer id, String nombre, double precio, Integer stock) {
        this.id = id;
        setNombre(nombre);
        setPrecio(precio);
        setStock(stock);
    }

    public Integer getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Nombre invalido");
        }
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        if (precio <= 0) {
            throw new IllegalArgumentException("Precio inválido");
        }
        this.precio = precio;
    }

    public void setStock(Integer stock) {
        if (stock <= 0) {
            throw new IllegalArgumentException("Stock invalido");
        }
        this.stock = stock;
    }

    public void setId(Integer idGenerado) {
        this.id = idGenerado;
    }

}