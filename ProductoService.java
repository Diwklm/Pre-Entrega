package com.techlab.servicios;
import com.techlab.excepciones.ProductoNoEncontrado;
import com.techlab.productos.Bebida;
import com.techlab.productos.Producto;
import java.util.ArrayList;

public class ProductoService {
    private ArrayList<Producto> productos = new ArrayList<>();

    public void agregar(String nombre, double precio, int stock){
        Producto nuevo = new Producto(nombre, precio, stock);
        productos.add(nuevo);
        System.out.println("Producto agregado: " + nuevo);
    }

    public void agregarBebida(String nombre, double precio, int stock, double volumen){
        Producto nuevo = new Bebida(nombre, precio, stock, volumen);
        productos.add(nuevo);
        System.out.println("Producto agregado: " + nuevo);
    }

    public boolean estaVacio(){
        return productos.isEmpty();
    }

    public void listar(){
        if (productos.isEmpty()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        System.out.println("\n--- Listado de productos ---");
        for (Producto p : productos) {
            System.out.println(p);
        }
    }

    public Producto buscarPorId(int id){
        for (Producto p : productos){
            if (p.getId() == id) {
                return p;
            }
        }
        throw new ProductoNoEncontrado(id);
    }

    public ArrayList<Producto> buscarPorNombre(String texto){
        ArrayList<Producto> encontrados = new ArrayList<>();
        for (Producto p : productos){
            if (p.getNombre().toLowerCase().contains(texto.toLowerCase().trim())) {
                encontrados.add(p);
            }
        }
        return encontrados;
    }

    public void eliminar(int id){
        Producto p = buscarPorId(id);
        productos.remove(p);
        System.out.println("Producto eliminado: " + p.getNombre());
    }
}
