package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CajeroTest {

    private EmpleadoController controller;

    @BeforeEach
    public void setUp() {
        controller = new EmpleadoController();
    }

    @Test
    // Asignación sin empleado
    public void testAsignarPedidoSinEmpleadoCapaz() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 44);
        Cajero lucia = new Cajero("Lucia", "103", "E03", 120, "Caja 01");

        controller.registrarEmpleado(carlos);
        controller.registrarEmpleado(lucia);
        controller.registrarPedido("P01", "Cliente 1", TipoPan.TRADICIONAL, 25);

        assertThrows(IllegalStateException.class, () -> {
            controller.asignarPedido("P01");});

        Pedido p = controller.buscarPedidoPorCodigo("P01");
        assertNotNull(p);
        assertEquals(EstadoPedido.PENDIENTE, p.getEstado());
        assertEquals(44, carlos.getMinutosDisponibles());
        assertEquals(120, lucia.getMinutosDisponibles());
    }
}

}