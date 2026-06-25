package repositories;

import models.Producto;
import models.Proveedor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoRepositoryJSON implements ProductoRepository {

    private static final String FILE="productos.json";

    @Override
    public void guardar(List<Producto> productos){

        JSONArray array=new JSONArray();

        for(Producto p:productos){

            JSONObject obj=new JSONObject();

            obj.put("nombre",p.getNombre());
            obj.put("precio",p.getPrecio());
            obj.put("proveedor",p.getProveedor().getNombre());

            array.put(obj);
        }

        try(FileWriter file=new FileWriter(FILE)){

            file.write(array.toString(4));

        }catch(IOException e){

            e.printStackTrace();

        }

    }

    @Override
    public List<Producto> cargar(){

        List<Producto> lista=new ArrayList<>();

        try{

            File file=new File(FILE);

            if(!file.exists()){
                return lista;
            }

            BufferedReader br=new BufferedReader(
                    new FileReader(file)
            );

            StringBuilder json=new StringBuilder();

            String linea;

            while((linea=br.readLine())!=null){

                json.append(linea);

            }

            JSONArray array=new JSONArray(
                    json.toString()
            );

            for(int i=0;i<array.length();i++){

                JSONObject obj=array.getJSONObject(i);

                Proveedor proveedor=new Proveedor(
                        0,
                        obj.getString("proveedor"),
                        ""
                );

                Producto producto=new Producto(
                        obj.getString("nombre"),
                        obj.getDouble("precio"),
                        proveedor
                );

                lista.add(producto);

            }

        }catch(Exception e){

            e.printStackTrace();

        }

        return lista;

    }
}