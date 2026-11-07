package co.edu.uniquindio.poo.panaderiaeltrigal.controller;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmpleadoControllerTest {

    private EmpleadoController controller;

    @BeforeEach
    public void setUp() {
        controller = new EmpleadoController();
    }

    @Test
    //Cancelación de pedido pendiente
    public void testCancelarPedidoPendiente() {
        // Entrada de datos: Juan (Artesanal 100m), Pedido P01 de 10 TRADICIONAL
        PanaderoArtesanal juan = new PanaderoArtesanal("Juan", "105", "E05", 100);
        controller.registrarEmpleado(juan);

        Pedido p = controller.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 10);
        controller.cancelarPedido("P01");

        // Salida: Historial no vacío, p queda CANCELADO y Juan conserva sus 100 minutos
        List<Pedido> historial = controller.obtenerHistorialPedidos();
        assertNotNull(historial);
        assertFalse(historial.isEmpty());
        assertEquals(EstadoPedido.CANCELADO, p.getEstado());
        assertEquals(100, juan.getMinutosDisponibles());
    }
}

}