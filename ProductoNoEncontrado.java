package com.techlab.excepciones;

public class ProductoNoEncontrado extends RuntimeException{
    public ProductoNoEncontrado(int id){
        super("No se encontró ningún producto con ID: " + id);
    }
}
