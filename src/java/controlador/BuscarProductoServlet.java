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
import com.mongodb.client.model.Filters;
import org.bson.Document;

@WebServlet(name = "BuscarProductoServlet", urlPatterns = {"/BuscarProductoServlet"})
public class BuscarProductoServlet extends HttpServlet {

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
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);

        String nombre = request.getParameter("nombre");

        // Buscar productos por nombre en MongoDB
        List<Producto> productos = buscarProductosPorNombre(nombre);

        // Almacenar la lista de productos encontrados en el alcance de solicitud
        request.setAttribute("productosEncontrados", productos);

        // Redirigir a la página JSP que muestra los productos encontrados
        request.getRequestDispatcher("productosEncontrados.jsp").forward(request, response);
    }

    private List<Producto> buscarProductosPorNombre(String nombre) {
        List<Producto> listaProductos = new ArrayList<>();

        // Realizar la consulta en MongoDB
        com.mongodb.client.FindIterable<Document> cursor = collection.find(Filters.regex("nombre", nombre));

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
