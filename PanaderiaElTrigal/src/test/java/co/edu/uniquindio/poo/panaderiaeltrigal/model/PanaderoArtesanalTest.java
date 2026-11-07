package co.edu.uniquindio.poo.panaderiaeltrigal.model;
import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanaderoArtesanalTest {

    private EmpleadoController controller;

    @BeforeEach
    public void setUp() {
        controller = new EmpleadoController();
    }

    @Test
    //prueba descarte por registro
    public void testAsignarPedidoDescarteYDesempate() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 80);
        PanaderoArtesanal diana = new PanaderoArtesanal("Diana", "104", "E04", 80);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 54);

        controller.registrarEmpleado(ana);
        controller.registrarEmpleado(diana);
        controller.registrarEmpleado(carlos);

        controller.registrarPedido("P01", "Cliente 1", TipoPan.INTEGRAL, 13);

        // Salida: Asignado a Ana por ser la primera registrada y tener minutos suficientes
        Empleado asignado = controller.asignarPedido("P01");

        assertNotNull(asignado);
        assertEquals(ana, asignado);
        assertEquals("Ana", asignado.getNombre());
        assertEquals(0, ana.getMinutosDisponibles());
        assertEquals(80, diana.getMinutosDisponibles());
        assertEquals(54, carlos.getMinutosDisponibles());
    }
}
