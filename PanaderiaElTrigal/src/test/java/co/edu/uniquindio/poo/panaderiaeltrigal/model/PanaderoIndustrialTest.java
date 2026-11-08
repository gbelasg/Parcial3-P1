package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.controller.PedidoController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanaderoIndustrialTest {

    private EmpleadoController empleadoController;
    private PedidoController pedidoController;
    private Panaderia panaderia;

    @BeforeEach
    public void setUp() {
        panaderia = new Panaderia("El Trigal");
        empleadoController = new EmpleadoController(panaderia);
        pedidoController = new PedidoController(panaderia);
    }

    //cancelar un pedido con devolucion de dinero

    @Test
    public void testCancelarPedidoDevolucionParcial() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 120);
        empleadoController.registrarEmpleado(carlos);

        pedidoController.registrarPedido("P01", "Cliente 1", TipoPan.TRADICIONAL, 25);
        pedidoController.registrarPedido("P02", "Cliente 2", TipoPan.TRADICIONAL, 25);

        pedidoController.asignarPedido("P01");
        pedidoController.asignarPedido("P02");

        pedidoController.cancelarPedido("P01");

        assertEquals(75, carlos.getMinutosDisponibles());

        Pedido p1 = pedidoController.buscarPedido("P01");
        Pedido p2 = pedidoController.buscarPedido("P02");

        assertNotNull(p1);
        assertNotNull(p2);
        assertEquals(EstadoPedido.CANCELADO, p1.getEstado());
        assertEquals(EstadoPedido.ASIGNADO, p2.getEstado());
    }
}

