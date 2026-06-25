package Principal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import views.ProductoViewFX;

public class MainFX extends Application {

    @Override
    public void start(Stage stage) {

        ProductoViewFX view = new ProductoViewFX();

        Scene scene = new Scene(
                view.getRoot(),
                500,
                400
        );

        stage.setTitle("Sistema Productos");

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);

    }
}