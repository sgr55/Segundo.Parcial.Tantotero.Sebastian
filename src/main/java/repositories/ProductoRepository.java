package repositories;

import models.Producto;
import java.util.List;

public interface ProductoRepository {
    void guardar(List<Producto> productos);
    List<Producto> cargar();
}