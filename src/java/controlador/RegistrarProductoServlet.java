
package controlador;

import modelo.Producto;
import modelo.ProductoService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RegistrarProductoServlet", urlPatterns = {"/RegistrarProductoServlet"})
public class RegistrarProductoServlet extends HttpServlet {

    private final ProductoService productoService;

    public RegistrarProductoServlet() {
        productoService = new ProductoService(); 
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

        // Recuperar los parámetros del formulario
        String codigo = request.getParameter("codigo");
        String nombre = request.getParameter("nombre");
        double precio = Double.parseDouble(request.getParameter("precio"));
        int stock = Integer.parseInt(request.getParameter("stock"));

        if (precio <= 0 || stock < 0) {
            // Los valores no son válidos
            String errorMessage = "Por favor, ingrese valores válidos para el precio y el stock.";
            request.setAttribute("error", errorMessage);
            request.getRequestDispatcher("formulario.jsp").forward(request, response);
            return;
        }

        // Crear un objeto Producto con los datos
        Producto producto = new Producto(codigo, nombre, precio, stock);

        // Agregar el producto utilizando el servicio
        productoService.agregarProducto(producto);

        // Redirigir a una página de éxito o mostrar un mensaje
        response.sendRedirect("MostrarProductosServlet");
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}
