package com.techlab.pedidos;
import com.techlab.productos.Producto;

public class LineaPedido{
  private Producto producto;
  private int cantidad;

    public LineaPedido(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Producto getProducto(){
        return producto;
    }
    public int getCantidad(){
        return cantidad;
    }

    public void sumarCantidad(int extra){
        this.cantidad += extra;
    }

    public double getSubtotal(){
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString(){
        return "   - " + producto.getNombre() + " x" + cantidad + " ($" + producto.getPrecio() + " c/u) = $" + getSubtotal();
    }
}
