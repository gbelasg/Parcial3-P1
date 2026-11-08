package co.edu.uniquindio.poo.panaderiaeltrigal.controller;


import co.edu.uniquindio.poo.panaderiaeltrigal.model.*;
import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EmpleadoControllerTest {

    private EmpleadoController empleadoController;
    private PedidoController pedidoController;
    private Panaderia panaderia;

    @BeforeEach
    public void setUp() {
        panaderia = new Panaderia("El Trigal");
        empleadoController = new EmpleadoController(panaderia);
        pedidoController = new PedidoController(panaderia);
    }

    @Test
    public void testCancelarPedidoPendiente() {

        PanaderoArtesanal juan = new PanaderoArtesanal("Juan", "105", "E05", 100);
        empleadoController.registrarEmpleado(juan);

        pedidoController.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 10);

        pedidoController.cancelarPedido("P01");
        List<Pedido> historial = pedidoController.getHistorialPedidos();
        assertNotNull(historial);
        assertFalse(historial.isEmpty());

        Pedido pedidoCancelado = pedidoController.buscarPedido("P01"); // o desde el historial
        assertNotNull(pedidoCancelado);
        assertEquals(EstadoPedido.CANCELADO, pedidoCancelado.getEstado());


        assertEquals(100, juan.getMinutosDisponibles());
    }
}

