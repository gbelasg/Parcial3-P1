package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class PanaderiaTest {

    @Test
    // Los empleados quedan en el orden en que se registran
    public void ordenDeRegistro() {
        Panaderia panaderia = new Panaderia("El Trigal");
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 120);
        Cajero lucia = new Cajero("Lucía", "104", "E04", 120, "Caja 1");
        panaderia.agregarEmpleado(ana);
        panaderia.agregarEmpleado(carlos);
        panaderia.agregarEmpleado(lucia);

        ArrayList<Empleado> esperada = new ArrayList<>();
        esperada.add(ana);
        esperada.add(carlos);
        esperada.add(lucia);

        assertIterableEquals(esperada, panaderia.getListaEmpleados());
    }

    @Test
    // Buscar un empleado por codigo, si no existe devuelve null"
    public void buscarEmpleado() {
        Panaderia panaderia = new Panaderia("El Trigal");
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        panaderia.agregarEmpleado(ana);

        assertSame(ana, panaderia.buscarEmpleado("E01"));
        assertNull(panaderia.buscarEmpleado("E99"));
    }

    @Test
    // Buscar un pedido por codigo en el historial
    public void buscarPedido() {
        Panaderia panaderia = new Panaderia("El Trigal");
        Pedido pedido = new Pedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);
        panaderia.agregarPedido(pedido);

        assertSame(pedido, panaderia.buscarPedido("P01"));
        assertNull(panaderia.buscarPedido("P02"));
    }

    @Test
    // Solo cuentan como asignados los pedidos en estado ASIGNADO
    public void pedidosAsignados() {
        Panaderia panaderia = new Panaderia("El Trigal");
        Pedido pedido = new Pedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);
        panaderia.agregarPedido(pedido);

        assertFalse(panaderia.tienePedidosAsignados("E02"));
        pedido.asignar("E02", 45);
        assertTrue(panaderia.tienePedidosAsignados("E02"));
        pedido.cancelar();
        assertFalse(panaderia.tienePedidosAsignados("E02"));
    }

    @Test
    // Eliminar un empleado lo saca de la lista
    public void eliminarEmpleado() {
        Panaderia panaderia = new Panaderia("El Trigal");
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        panaderia.agregarEmpleado(ana);

        panaderia.eliminarEmpleado(ana);

        assertTrue(panaderia.getListaEmpleados().isEmpty());
        assertEquals("El Trigal", panaderia.getNombre());
    }
}
