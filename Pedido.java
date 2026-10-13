package com.techlab.pedidos;
import java.util.ArrayList;

  public class Pedido{
  private static int contadorId = 1;
  private int id;
  private ArrayList<LineaPedido> lineas = new ArrayList<>();
  public Pedido(){
     this.id = contadorId++;
    }
  public int getId() {
    return id;
 }
  public ArrayList<LineaPedido> getLineas(){
    return lineas;
 }
  public boolean estaVacio(){
    return lineas.isEmpty();
 }
  public void agregarLinea(LineaPedido nueva){
     for (LineaPedido l : lineas){
       if (l.getProducto().getId() == nueva.getProducto().getId()){
       l.sumarCantidad(nueva.getCantidad());
                return;
            }
        }
        lineas.add(nueva);
    }
    public int cantidadDe(int idProducto){
        for (LineaPedido l : lineas){
            if (l.getProducto().getId() == idProducto){
                return l.getCantidad();
            }
        }
        return 0;
    }

    public double calcularTotal(){
        double total = 0;
        for (LineaPedido l : lineas){
            total += l.getSubtotal();
        }
        return total;
    }

    @Override
    public String toString(){
        String texto = "Pedido #" + id + "\n";
        for (LineaPedido l : lineas){
            texto += l + "\n";
        }
        texto += "   TOTAL: $" + calcularTotal();
        return texto;
    }
}
