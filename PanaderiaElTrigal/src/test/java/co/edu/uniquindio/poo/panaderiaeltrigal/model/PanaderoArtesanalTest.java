package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.controller.PedidoController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PanaderoArtesanalTest {

    private EmpleadoController empleadoController;
    private PedidoController pedidoController;
    private Panaderia panaderia;

    @BeforeEach
    public void setUp() {
        panaderia = new Panaderia("El Trigal");
        empleadoController = new EmpleadoController(panaderia);
        pedidoController = new PedidoController(panaderia);
    }

    //Asignar el pedido al panadero con menor tiempo

    @Test
    public void testAsignarPedidoMenorTiempo() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 45);

        empleadoController.registrarEmpleado(ana);
        empleadoController.registrarEmpleado(carlos);

        pedidoController.registrarPedido("P01", "Cliente 1", TipoPan.TRADICIONAL, 25);

        Empleado asignado = pedidoController.asignarPedido("P01");

        assertNotNull(asignado);
        assertEquals(carlos, asignado);
        assertEquals("Carlos", asignado.getNombre());
        assertEquals(0, carlos.getMinutosDisponibles());
        assertEquals(100, ana.getMinutosDisponibles());
    }

    //Asignar panaderos con un orden especifico

    @Test
    public void testAsignarPedidoDescarteYDesempate() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 80);
        PanaderoArtesanal diana = new PanaderoArtesanal("Diana", "104", "E04", 80);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 54);

        empleadoController.registrarEmpleado(ana);
        empleadoController.registrarEmpleado(diana);
        empleadoController.registrarEmpleado(carlos);

        pedidoController.registrarPedido("P01", "Cliente 1", TipoPan.INTEGRAL, 13);

        Empleado asignado = pedidoController.asignarPedido("P01");

        assertNotNull(asignado);
        assertEquals(ana, asignado);
        assertEquals("Ana", asignado.getNombre());
        assertEquals(0, ana.getMinutosDisponibles());
        assertEquals(80, diana.getMinutosDisponibles());
        assertEquals(54, carlos.getMinutosDisponibles());
    }
}
