package co.edu.uniquindio.poo.panaderiaeltrigal.controller;

import co.edu.uniquindio.poo.panaderiaeltrigal.model.Cajero;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.Empleado;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.EstadoPedido;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.PanaderoIndustrial;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.Panaderia;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.Pedido;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.TipoPan;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoControllerTest {

    private Panaderia panaderia;
    private PedidoController pedidos;

    @BeforeEach
    public void setUp() {
        panaderia = new Panaderia("El Trigal");
        pedidos = new PedidoController(panaderia);
    }

    @Test
    public void registrarPedidoValido() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        panaderia.agregarEmpleado(ana);

        Pedido pedido = pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals(25, pedido.getCantidad());
        assertEquals(100, ana.getMinutosDisponibles());
        assertEquals(1, panaderia.getHistorialPedidos().size());
    }

    @Test
    public void registrarCodigoRepetidoDeUnCancelado() {
        pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);
        pedidos.cancelarPedido("P01");

        assertThrows(IllegalArgumentException.class,
                () -> pedidos.registrarPedido("P01", "Otro cliente", TipoPan.INTEGRAL, 10));

        assertEquals(1, panaderia.getHistorialPedidos().size());
        Pedido original = panaderia.getHistorialPedidos().get(0);
        assertEquals(EstadoPedido.CANCELADO, original.getEstado());
        assertEquals("Cafetería Central", original.getNombreCliente());
    }

    @Test
    public void registrarCeroPanes() {
        assertThrows(IllegalArgumentException.class,
                () -> pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 0));

        assertTrue(panaderia.getHistorialPedidos().isEmpty());
    }

    @Test
    public void asignarAlQueTardaMenos() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 45);
        panaderia.agregarEmpleado(ana);
        panaderia.agregarEmpleado(carlos);
        Pedido pedido = pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        Empleado elegido = pedidos.asignarPedido("P01");

        assertSame(carlos, elegido);
        assertEquals(EstadoPedido.ASIGNADO, pedido.getEstado());
        assertEquals("E02", pedido.getCodigoEmpleado());
        assertEquals(45, pedido.getMinutosReservados());
        assertEquals(0, carlos.getMinutosDisponibles());
        assertEquals(100, ana.getMinutosDisponibles());
    }

    @Test
    public void asignarEmpatePorOrdenDeRegistro() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 80);
        PanaderoArtesanal diana = new PanaderoArtesanal("Diana", "103", "E03", 80);
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 54);
        panaderia.agregarEmpleado(ana);
        panaderia.agregarEmpleado(diana);
        panaderia.agregarEmpleado(carlos);
        Pedido pedido = pedidos.registrarPedido("P01", "Restaurante La Casona", TipoPan.INTEGRAL, 13);

        Empleado elegido = pedidos.asignarPedido("P01");

        assertEquals(55, carlos.calcularMinutos(TipoPan.INTEGRAL, 13));
        assertSame(ana, elegido);
        assertEquals(80, pedido.getMinutosReservados());
        assertEquals(0, ana.getMinutosDisponibles());
        assertEquals(80, diana.getMinutosDisponibles());
        assertEquals(54, carlos.getMinutosDisponibles());
    }

    @Test
    public void asignarSinPanaderoConTiempo() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 44);
        Cajero lucia = new Cajero("Lucía", "104", "E04", 120, "Caja 1");
        panaderia.agregarEmpleado(carlos);
        panaderia.agregarEmpleado(lucia);
        Pedido pedido = pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        assertThrows(IllegalStateException.class, () -> pedidos.asignarPedido("P01"));

        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertEquals("", pedido.getCodigoEmpleado());
        assertEquals(44, carlos.getMinutosDisponibles());
        assertEquals(120, lucia.getMinutosDisponibles());
    }

    @Test
    public void cancelarAsignadoDevuelveMinutos() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 120);
        panaderia.agregarEmpleado(carlos);
        Pedido primero = pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);
        Pedido segundo = pedidos.registrarPedido("P02", "Hotel Armenia", TipoPan.TRADICIONAL, 25);
        pedidos.asignarPedido("P01");
        pedidos.asignarPedido("P02");
        assertEquals(30, carlos.getMinutosDisponibles());

        pedidos.cancelarPedido("P01");

        assertEquals(75, carlos.getMinutosDisponibles());
        assertEquals(EstadoPedido.CANCELADO, primero.getEstado());
        assertEquals(EstadoPedido.ASIGNADO, segundo.getEstado());
    }

    @Test
    public void cancelarPendiente() {
        PanaderoArtesanal ana = new PanaderoArtesanal("Ana", "101", "E01", 100);
        panaderia.agregarEmpleado(ana);
        Pedido pedido = pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        pedidos.cancelarPedido("P01");

        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
        assertTrue(panaderia.getHistorialPedidos().contains(pedido));
        assertEquals(100, ana.getMinutosDisponibles());
    }

    @Test
    public void cancelarDosVeces() {
        PanaderoIndustrial carlos = new PanaderoIndustrial("Carlos", "102", "E02", 120);
        panaderia.agregarEmpleado(carlos);
        Pedido pedido = pedidos.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);
        pedidos.asignarPedido("P01");
        pedidos.cancelarPedido("P01");

        assertThrows(IllegalStateException.class, () -> pedidos.cancelarPedido("P01"));

        assertEquals(120, carlos.getMinutosDisponibles());
        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
    }
    }