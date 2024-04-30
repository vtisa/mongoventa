package modelo;

import java.util.List;

public class ProductoService {
    private final ProductoDAO productoDAO;

    public ProductoService() {
        productoDAO = new ProductoDAO();
    }

    public void agregarProducto(Producto producto) {
        productoDAO.insertarProducto(producto);
    }

    public List<Producto> obtenerProductos() {
        return productoDAO.obtenerTodosLosProductos();
    }

    public List<Producto> buscarProductosPorNombre(String nombre) {
        return productoDAO.buscarProductosPorNombre(nombre);
    }
}
