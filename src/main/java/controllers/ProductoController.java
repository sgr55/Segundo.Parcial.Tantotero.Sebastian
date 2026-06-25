package controllers;

import exceptions.ProductoNoEncontradoException;
import models.Producto;
import models.Proveedor;
import repositories.ProductoRepositoryJSON;
import utils.LectorJSON;

import java.util.List;

public class ProductoController {

    private List<Producto> productos;
    private List<Proveedor> proveedores;
    private ProductoRepositoryJSON repository;

    public ProductoController() {

        repository
                = new ProductoRepositoryJSON();

        productos
                = repository.cargar();

        cargarProveedores();

    }

    private void cargarProveedores() {

        proveedores
                = LectorJSON.leerProveedores(
                        "src/main/resources/proveedores.json"
                );

    }

    private Proveedor buscarProveedor(
            int id
    ) {

        for (Proveedor p
                : proveedores) {

            if (p.getId() == id) {

                return p;

            }

        }

        return null;

    }

    public String crearProducto(
            String nombre,
            double precio,
            int idProveedor
    ) {

        if (nombre.isEmpty()) {

            return "Ingrese nombre";

        }

        for (Producto p
                : productos) {

            if (p.getNombre()
                    .equalsIgnoreCase(nombre)) {

                return "Ya existe un producto con ese nombre";

            }

        }

        Proveedor proveedor
                = buscarProveedor(
                        idProveedor
                );

        if (proveedor == null) {

            return "Proveedor inexistente";

        }

        Producto producto
                = new Producto(
                        nombre,
                        precio,
                        proveedor
                );

        productos.add(
                producto
        );

        repository.guardar(
                productos
        );

        return "Producto agregado";

    }

    public String modificar(
            String nombre,
            double precio,
            int idProveedor
    ) {

        try {

            Producto producto
                    = buscar(
                            nombre
                    );

            Proveedor proveedor
                    = buscarProveedor(
                            idProveedor
                    );

            if (proveedor == null) {

                return "Proveedor inexistente";

            }

            producto.setPrecio(
                    precio
            );

            producto.setProveedor(
                    proveedor
            );

            repository.guardar(
                    productos
            );

            return "Producto modificado";

        } catch (ProductoNoEncontradoException e) {

            return e.getMessage();

        }

    }

    public List<Producto> listar() {

        return productos;

    }

    public Producto buscar(
            String nombre
    )
            throws ProductoNoEncontradoException {

        for (Producto p
                : productos) {

            if (p.getNombre()
                    .equalsIgnoreCase(nombre)) {

                return p;

            }

        }

        throw new ProductoNoEncontradoException(
                "Producto no encontrado"
        );

    }

    public void eliminar(
            String nombre
    )
            throws ProductoNoEncontradoException {

        Producto producto
                = buscar(
                        nombre
                );

        productos.remove(
                producto
        );

        repository.guardar(
                productos
        );

    }

}
