package com.techlab;
import com.techlab.excepciones.ProductoNoEncontrado;
import com.techlab.excepciones.StockInsuficienteException;
import com.techlab.pedidos.Pedido;
import com.techlab.productos.Producto;
import com.techlab.servicios.PedidoService;
import com.techlab.servicios.ProductoService;
import java.util.ArrayList;
import java.util.Scanner;

public class Main{

 static Scanner sc = new Scanner(System.in);
 static ProductoService servicio = new ProductoService();
 static PedidoService pedidoService = new PedidoService();

   public static void main(String[] args){
        int opcion = -1;

        while (opcion != 7) {

            System.out.println("\n=================================== Sistema de gestión ---- TechLab ==================================");
            System.out.println("1) Agregar producto");
            System.out.println("2) Listar productos");
            System.out.println("3) Buscar/Actualizar producto");
            System.out.println("4) Eliminar producto");
            System.out.println("5) Crear un pedido");
            System.out.println("6) Listar pedidos");
            System.out.println("7) Salir");

            opcion = leerEntero("\nElija una opción: ");

            if (opcion == 1) {
                agregarProducto();
            } else if (opcion == 2) {
                servicio.listar();
            } else if (opcion == 3) {
                buscarActualizar();
            } else if (opcion == 4) {
                eliminarProducto();
            } else if (opcion == 5) {
                crearPedido();
            } else if (opcion == 6) {
                pedidoService.listar();
            } else if (opcion != 7) {
                System.out.println("Opción no válida. Elegí entre 1 y 7.");
            }
        }

        System.out.println("¡Hasta luego!");
        sc.close();
    }

    static int leerEntero(String mensaje){
        while (true){
            System.out.print(mensaje);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e){
                System.out.println("Ingresá un número entero válido.");
            }
        }
    }

    static double leerDecimal(String mensaje){
        while (true) {
            System.out.print(mensaje);
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                System.out.println("Ingresá un número válido (ej: 1500.50).");
            }
        }
    }

    static boolean confirmar(String mensaje) {
        System.out.print(mensaje + " (s/n): ");
        String r = sc.nextLine().trim().toLowerCase();
        return r.equals("s") || r.equals("si") || r.equals("sí");
    }

    static void agregarProducto() {
        System.out.print("Nombre: ");
        String nombre = sc.nextLine();

        if (nombre.isBlank()) {
            System.out.println("El nombre no puede estar vacío.");
            return;
        }

        double precio = leerDecimal("Precio: ");
        if (precio <= 0) {
            System.out.println("El precio debe ser mayor a cero.");
            return;
        }

        int stock = leerEntero("Stock: ");
        if (stock < 0) {
            System.out.println("El stock no puede ser negativo.");
            return;
        }

        if (confirmar("¿Es una bebida?")) {
            double volumen = leerDecimal("Volumen en litros: ");
            if (volumen <= 0) {
                System.out.println("El volumen debe ser mayor a cero.");
                return;
            }
            servicio.agregarBebida(nombre, precio, stock, volumen);
        } else {
            servicio.agregar(nombre, precio, stock);
        }
    }

    static void buscarActualizar() {
        System.out.println("Buscar por: 1) ID   2) Nombre");
        int modo = leerEntero("Opción: ");
        Producto p = null;

        if (modo == 1) {
            int id = leerEntero("ID a buscar: ");
            try {
                p = servicio.buscarPorId(id);
            } catch (ProductoNoEncontrado e) {
                System.out.println(e.getMessage());
                return;
            }
        } else if (modo == 2) {
            System.out.print("Nombre (o parte): ");
            ArrayList<Producto> encontrados = servicio.buscarPorNombre(sc.nextLine());
            if (encontrados.isEmpty()) {
                System.out.println("No se encontraron productos con ese nombre.");
                return;
            }
            for (Producto x : encontrados) {
                System.out.println(x);
            }
            if (encontrados.size() == 1) {
                p = encontrados.get(0);
            } else {
                int id = leerEntero("ID del producto a gestionar: ");
                try {
                    p = servicio.buscarPorId(id);
                } catch (ProductoNoEncontrado e) {
                    System.out.println(e.getMessage());
                    return;
                }
            }
        } else {
            System.out.println("Opción no válida.");
            return;
        }

        System.out.println("Encontrado: " + p);

        if (!confirmar("¿Querés actualizarlo?")) {
            return;
        }

        System.out.println("Actualizar: 1) Precio   2) Stock   3) Ambos");
        int campo = leerEntero("Opción: ");

        if (campo < 1 || campo > 3) {
            System.out.println("Opción no válida.");
            return;
        }

        if (campo == 1 || campo == 3) {
            double nuevoPrecio = leerDecimal("Nuevo precio: ");
            if (nuevoPrecio <= 0) {
                System.out.println("El precio debe ser mayor a cero. No se modificó.");
            } else {
                p.setPrecio(nuevoPrecio);
            }
        }
        if (campo == 2 || campo == 3) {
            int nuevoStock = leerEntero("Nuevo stock: ");
            if (nuevoStock < 0) {
                System.out.println("El stock no puede ser negativo. No se modificó.");
            } else {
                p.setStock(nuevoStock);
            }
        }
        System.out.println("Producto ahora: " + p);
    }

    static void eliminarProducto() {
        if (servicio.estaVacio()) {
            System.out.println("No hay productos cargados.");
            return;
        }
        servicio.listar();
        int id = leerEntero("ID a eliminar: ");

        try {
            Producto p = servicio.buscarPorId(id);
            if (confirmar("¿Seguro que querés eliminar '" + p.getNombre() + "'?")) {
                servicio.eliminar(id);
            } else {
                System.out.println("Operación cancelada.");
            }
        } catch (ProductoNoEncontrado e) {
            System.out.println(e.getMessage());
        }
    }

    static void crearPedido() {
        if (servicio.estaVacio()) {
            System.out.println("No hay productos cargados. Agregá productos primero.");
            return;
        }
        servicio.listar();

        int items = leerEntero("¿Cuántos productos distintos querés agregar? ");
        Pedido pedido = new Pedido();

        for (int i = 1; i <= items; i++) {
            System.out.println("Producto " + i + " de " + items);
            int id = leerEntero("  ID del producto: ");
            int cantidad = leerEntero("  Cantidad: ");

            if (cantidad <= 0) {
                System.out.println("  La cantidad debe ser mayor a cero.");
                continue;
            }

            try {
                Producto p = servicio.buscarPorId(id);
                pedidoService.agregarLinea(pedido, p, cantidad);
                System.out.println("  Agregado: " + p.getNombre() + " x" + cantidad);
            } catch (ProductoNoEncontrado | StockInsuficienteException e) {
                System.out.println("  " + e.getMessage());
            }
        }

        if (pedido.estaVacio()) {
            System.out.println("El pedido quedó vacío. Se cancela.");
            return;
        }

        System.out.println("\nResumen:\n" + pedido);
        if (confirmar("¿Confirmar pedido?")) {
            pedidoService.confirmar(pedido);
            System.out.println("Pedido #" + pedido.getId() + " confirmado. Stock actualizado.");
        } else {
            System.out.println("Pedido cancelado. El stock no se modificó.");
        }
    }
}
