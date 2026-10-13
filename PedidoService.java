package com.techlab.servicios;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.LineaPedido;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Producto;
import java.util.ArrayList;

public class PedidoService {
  
    private ArrayList<Pedido> pedidos = new ArrayList<>();

    public void agregarLinea(Pedido pedido, Producto producto, int cantidad){
        int totalPedido = cantidad + pedido.cantidadDe(producto.getId());
        if (totalPedido > producto.getStock()) {
            throw new StockInsuficienteException(producto.getNombre(), totalPedido, producto.getStock());
        }
        pedido.agregarLinea(new LineaPedido(producto, cantidad));
    }

    public void confirmar(Pedido pedido){
        for (LineaPedido l : pedido.getLineas()) {
            Producto p = l.getProducto();
            p.setStock(p.getStock() - l.getCantidad());
        }
        pedidos.add(pedido);
    }

    public void listar(){
        if (pedidos.isEmpty()) {
            System.out.println("No hay pedidos realizados.");
            return;
        }
        System.out.println("\n--- Pedidos realizados ---");
        for (Pedido p : pedidos) {
            System.out.println(p + "\n");
        }
    }
}
