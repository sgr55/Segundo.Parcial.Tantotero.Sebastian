package utils;

import models.Proveedor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LectorJSON {

    public static List<Proveedor> leerProveedores(String ruta) {
        List<Proveedor> lista = new ArrayList<>();

        try {
            InputStream is = LectorJSON.class.getClassLoader().getResourceAsStream("proveedores.json");

            if (is == null) {
                System.out.println("No se encontró el archivo JSON");
                return lista;
            }

            String contenido = new String(is.readAllBytes());
            JSONArray jsonArray = new JSONArray(contenido);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                int id = obj.getInt("id");
                String nombre = obj.getString("razonSocial");
                String rubro = obj.getString("ciudad");

                Proveedor proveedor = new Proveedor(id, nombre, rubro);
                lista.add(proveedor);
            }

        } catch (Exception e) {
            System.out.println("Error leyendo el JSON: " + e.getMessage());
        }

        return lista;
    }
}