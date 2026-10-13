package com.techlab.excepciones;

public class StockInsuficienteException extends RuntimeException{
 public StockInsuficienteException(String nombre, int pedido, int disponible){
   super("Stock insuficiente para '" + nombre + "': pediste " + pedido + " y hay " + disponible + ".");
}
}
