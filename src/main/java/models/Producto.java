package models;

public class Producto {

    private String nombre;
    private double precio;
    private Proveedor proveedor;

    public Producto(
            String nombre,
            double precio,
            Proveedor proveedor
    ) {

        this.nombre = nombre;
        this.precio = precio;
        this.proveedor = proveedor;

    }

    public String getNombre() {

        return nombre;

    }

    public double getPrecio() {

        return precio;

    }

    public Proveedor getProveedor() {

        return proveedor;

    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    @Override
    public String toString() {

        return "Producto{"
                + "nombre='" + nombre + '\''
                + ", precio=" + precio
                + ", proveedor='"
                + proveedor.getNombre()
                + '\''
                + '}';

    }

}
