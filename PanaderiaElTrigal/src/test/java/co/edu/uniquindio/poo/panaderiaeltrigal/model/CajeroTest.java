package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.controller.PedidoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CajeroTest {

    private EmpleadoController empleadoController;
    private PedidoController pedidoController;
    private Panaderia panaderia;

    @BeforeEach
    public void setUp() {
        panaderia = new Panaderia("El Trigal");
        empleadoController = new EmpleadoController();
        pedidoController = new PedidoController(panaderia);
    }

    @Test
    public void testAsignarPedidoSinEmpleadoCapaz() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 44);
        Cajero lucia = new Cajero("Lucia", "103", "E03", 120, "Caja 01");

        empleadoController.registrarEmpleado(carlos);
        empleadoController.registrarEmpleado(lucia);

        // Llamada a través de pedidoController
        pedidoController.registrarPedido("P01", "Cliente 1", TipoPan.TRADICIONAL, 25);

        assertThrows(IllegalStateException.class, () -> {
            pedidoController.asignarPedido("P01");
        });

        Pedido p = pedidoController.buscarPedido("P01");
        assertNotNull(p);
        assertEquals(EstadoPedido.PENDIENTE, p.getEstado());
        assertEquals(44, carlos.getMinutosDisponibles());
        assertEquals(120, lucia.getMinutosDisponibles());
    }
}
