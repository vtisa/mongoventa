
package controlador;

import modelo.Producto;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

@WebServlet(name = "MostrarProductosServlet", urlPatterns = {"/"})
public class MostrarProductosServlet extends HttpServlet {

    private MongoCollection<Document> collection;

    @Override
    public void init() throws ServletException {
        super.init();
        // Establecer la conexión con MongoDB
        try {
            String connectionString = "mongodb://localhost:27017";
            com.mongodb.client.MongoClient mongoClient = MongoClients.create(connectionString);
            MongoDatabase database = mongoClient.getDatabase("venta");
            collection = database.getCollection("producto");
        } catch (Exception e) {
            throw new ServletException("Error al conectar con MongoDB", e);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        // Obtener la lista de productos desde MongoDB
        List<Producto> productos = obtenerProductos();

        // Almacenar la lista de productos en el alcance de solicitud
        request.setAttribute("productos", productos);

        // Redirigir a la página JSP que muestra la lista de productos
        request.getRequestDispatcher("productos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    private List<Producto> obtenerProductos() {
        List<Producto> listaProductos = new ArrayList<>();

        // Realizar la consulta en MongoDB
        com.mongodb.client.FindIterable<Document> cursor = collection.find();

        // Iterar sobre los documentos obtenidos y crear objetos Producto
        for (Document doc : cursor) {
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

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
