package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanaderoIndustrialTest {

    private EmpleadoController controller;

    @BeforeEach
    public void setUp() {
        controller = new EmpleadoController();
    }

    @Test
    //Asignación por menor tiempo
    public void testAsignarPedidoMenorTiempo() {

        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 45);

        controller.registrarEmpleado(ana);
        controller.registrarEmpleado(carlos);
        controller.registrarPedido("P01", "Cliente 1", TipoPan.TRADICIONAL, 25);

        // Salida: Asignado Carlos, reservando sus 45 minutos y Ana conserva sus 100 minutos
        Empleado asignado = controller.asignarPedido("P01");

        assertNotNull(asignado);
        assertEquals(carlos, asignado);
        assertEquals("Carlos", asignado.getNombre());
        assertEquals(0, carlos.getMinutosDisponibles());
        assertEquals(100, ana.getMinutosDisponibles());
    }

    @Test
    //Cancelación con devolución parcial
    public void testCancelarPedidoDevolucionParcial() {
        // Entrada de datos: Carlos (Industrial 120m), P01 y P02 de 25 TRADICIONAL
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 120);
        controller.registrarEmpleado(carlos);

        controller.registrarPedido("P01", "Cliente 1", TipoPan.TRADICIONAL, 25);
        controller.registrarPedido("P02", "Cliente 2", TipoPan.TRADICIONAL, 25);

        controller.asignarPedido("P01");
        controller.asignarPedido("P02");

        controller.cancelarPedido("P01");

        assertEquals(75, carlos.getMinutosDisponibles());

        Pedido p1 = controller.buscarPedidoPorCodigo("P01");
        Pedido p2 = controller.buscarPedidoPorCodigo("P02");

        assertNotNull(p1);
        assertNotNull(p2);
        assertEquals(EstadoPedido.CANCELADO, p1.getEstado());
        assertEquals(EstadoPedido.ASIGNADO, p2.getEstado());
    }
}

}