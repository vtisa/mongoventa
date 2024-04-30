package modelo;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {
    private final MongoCollection<Document> collection;

    public ProductoDAO() {
        // Conexión a MongoDB
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("venta");
        collection = database.getCollection("producto");
    }

    public void insertarProducto(Producto producto) {
        Document doc = new Document("codigo", producto.getCodigo())
                            .append("nombre", producto.getNombre())
                            .append("precio", producto.getPrecio())
                            .append("stock", producto.getStock());
        collection.insertOne(doc);
    }

    public List<Producto> obtenerTodosLosProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        for (Document doc : collection.find()) {
            Producto producto = new Producto(
                    doc.getString("codigo"),
                    doc.getString("nombre"),
                    doc.getDouble("precio"),
                    doc.getInteger("stock")
            );
            listaProductos.add(producto);
        }
        return listaProductos;
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> listaProductos = new ArrayList<>();
        Document query = new Document("nombre", java.util.regex.Pattern.compile(nombre));
        for (Document doc : collection.find(query)) {
            Producto producto = new Producto(
                    doc.getString("codigo"),
                    doc.getString("nombre"),
                    doc.getDouble("precio"),
                    doc.getInteger("stock")
            );
            listaProductos.add(producto);
        }
        return listaProductos;
    }
}
