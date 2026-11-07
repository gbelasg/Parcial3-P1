package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PedidoTest {

    @Test
    //Un pedido nuevo queda PENDIENTE y sin empleado
    public void pedidoNuevo() {
        Pedido pedido = new Pedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        assertEquals(EstadoPedido.PENDIENTE, pedido.getEstado());
        assertTrue(pedido.estaPendiente());
        assertEquals("", pedido.getCodigoEmpleado());
        assertEquals(0, pedido.getMinutosReservados());
    }

    @Test
    //Asignar guarda el empleado y los minutos y lo deja ASIGNADO
    public void asignar() {
        Pedido pedido = new Pedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        pedido.asignar("E02", 45);

        assertEquals(EstadoPedido.ASIGNADO, pedido.getEstado());
        assertTrue(pedido.estaAsignado());
        assertFalse(pedido.estaPendiente());
        assertEquals("E02", pedido.getCodigoEmpleado());
        assertEquals(45, pedido.getMinutosReservados());
    }

    @Test
    //Cancelar un pedido pendiente lo deja CANCELADO
    public void cancelarPendiente() {
        Pedido pedido = new Pedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        pedido.cancelar();

        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
        assertTrue(pedido.estaCancelado());
    }

    @Test
    //Cancelar un pedido asignado conserva el empleado y los minutos en el historial
    public void cancelarAsignado() {
        Pedido pedido = new Pedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);
        pedido.asignar("E02", 45);

        pedido.cancelar();

        assertTrue(pedido.estaCancelado());
        assertEquals("E02", pedido.getCodigoEmpleado());
        assertEquals(45, pedido.getMinutosReservados());
    }

    @Test
    //Modificar cambia cliente, tipo de pan y cantidad
    public void modificar() {
        Pedido pedido = new Pedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);

        pedido.modificar("Cafetería Norte", TipoPan.INTEGRAL, 40);

        assertEquals("P01", pedido.getCodigo());
        assertEquals("Cafetería Norte", pedido.getNombreCliente());
        assertEquals(TipoPan.INTEGRAL, pedido.getTipoPan());
        assertEquals(40, pedido.getCantidad());
    }
}
