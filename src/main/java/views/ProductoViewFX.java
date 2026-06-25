package views;

import controllers.ProductoController;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import models.Producto;

public class ProductoViewFX extends VBox {

    private ProductoController controller
            = new ProductoController();

    private TextField txtNombre
            = new TextField();

    private TextField txtPrecio
            = new TextField();

    private TextField txtProveedor
            = new TextField();

    private TableView<Producto> tabla
            = new TableView<>();

    public ProductoViewFX() {

        setSpacing(10);

        setPadding(
                new Insets(10)
        );

        txtNombre.setPromptText(
                "Nombre"
        );

        txtPrecio.setPromptText(
                "Precio"
        );

        txtProveedor.setPromptText(
                "ID proveedor"
        );

        Button agregar
                = new Button("Agregar");

        Button listar
                = new Button("Listar");

        Button buscar
                = new Button("Buscar");

        Button eliminar
                = new Button("Eliminar");

        Button actualizar
                = new Button("Actualizar");

        Button filtrar
                = new Button("Filtrar > precio");

        TableColumn<Producto, String> nombre
                = new TableColumn<>("Nombre");

        nombre.setCellValueFactory(
                new PropertyValueFactory<>("nombre")
        );

        TableColumn<Producto, Double> precio
                = new TableColumn<>("Precio");

        precio.setCellValueFactory(
                new PropertyValueFactory<>("precio")
        );

        TableColumn<Producto, String> proveedor
                = new TableColumn<>("Proveedor");

        proveedor.setCellValueFactory(
                cell -> new javafx.beans.property.SimpleStringProperty(
                        cell.getValue()
                                .getProveedor()
                                .getNombre()
                )
        );

        tabla.getColumns().addAll(
                nombre,
                precio,
                proveedor
        );

        tabla.setPrefHeight(
                250
        );

        tabla.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );

        agregar.setOnAction(e -> {

            try {

                validarCampos();

                String mensaje
                        = controller.crearProducto(
                                txtNombre.getText(),
                                Double.parseDouble(
                                        txtPrecio.getText()
                                ),
                                Integer.parseInt(
                                        txtProveedor.getText()
                                )
                        );

                mostrar(
                        mensaje
                );

                actualizarTabla();

                limpiarCampos();

            } catch (Exception ex) {

                mostrar(
                        ex.getMessage()
                );

            }

        });

        listar.setOnAction(e -> {

            actualizarTabla();

        });

        buscar.setOnAction(e -> {

            try {

                Producto p
                        = controller.buscar(
                                txtNombre.getText()
                        );

                tabla.getSelectionModel()
                        .select(p);

                tabla.scrollTo(p);

                mostrar(
                        "Producto encontrado"
                );

            } catch (Exception ex) {

                mostrar(
                        ex.getMessage()
                );

            }

        });

        actualizar.setOnAction(e -> {

            try {

                validarCampos();

                String mensaje
                        = controller.modificar(
                                txtNombre.getText(),
                                Double.parseDouble(
                                        txtPrecio.getText()
                                ),
                                Integer.parseInt(
                                        txtProveedor.getText()
                                )
                        );

                mostrar(
                        mensaje
                );

                actualizarTabla();

                tabla.refresh();

                limpiarCampos();

            } catch (Exception ex) {

                mostrar(
                        ex.getMessage()
                );

            }

        });

        eliminar.setOnAction(e -> {

            try {

                controller.eliminar(
                        txtNombre.getText()
                );

                mostrar(
                        "Producto eliminado"
                );

                actualizarTabla();

                limpiarCampos();

            } catch (Exception ex) {

                mostrar(
                        ex.getMessage()
                );

            }

        });

        filtrar.setOnAction(e -> {

            try {

                double precioMinimo
                        = Double.parseDouble(
                                txtPrecio.getText()
                        );

                tabla.getItems().clear();

                for (Producto p
                        : controller.listar()) {

                    if (p.getPrecio()
                            > precioMinimo) {

                        tabla.getItems().add(
                                p
                        );

                    }

                }

            } catch (Exception ex) {

                mostrar(
                        "Ingrese precio válido"
                );

            }

        });

        tabla.setRowFactory(tv -> {

            TableRow<Producto> row
                    = new TableRow<>();

            row.setOnMouseClicked(event -> {

                if (event.getClickCount() == 2
                        && !row.isEmpty()) {

                    Producto p
                            = row.getItem();

                    txtNombre.setText(
                            p.getNombre()
                    );

                    txtPrecio.setText(
                            String.valueOf(
                                    p.getPrecio()
                            )
                    );

                }

            });

            return row;

        });

        HBox botones
                = new HBox(
                        10,
                        agregar,
                        listar,
                        buscar,
                        actualizar,
                        eliminar,
                        filtrar
                );

        getChildren().addAll(
                txtNombre,
                txtPrecio,
                txtProveedor,
                botones,
                tabla
        );

        actualizarTabla();

    }

    private void actualizarTabla() {

        tabla.getItems().clear();

        tabla.setItems(
                FXCollections.observableArrayList(
                        controller.listar()
                )
        );

        tabla.refresh();

    }

    private void limpiarCampos() {

        txtNombre.clear();

        txtPrecio.clear();

        txtProveedor.clear();

    }

    private void validarCampos() {

        if (txtNombre.getText().isEmpty()
                || txtPrecio.getText().isEmpty()
                || txtProveedor.getText().isEmpty()) {

            throw new RuntimeException(
                    "Complete todos los campos"
            );

        }

    }

    private void mostrar(
            String mensaje
    ) {

        Alert alerta
                = new Alert(
                        Alert.AlertType.INFORMATION
                );

        alerta.setHeaderText(
                "Sistema Productos"
        );

        alerta.setContentText(
                mensaje
        );

        alerta.showAndWait();

    }

    public Parent getRoot() {

        return this;

    }

}
