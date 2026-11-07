package co.edu.uniquindio.poo.panaderiaeltrigal.controller;

import co.edu.uniquindio.poo.panaderiaeltrigal.model.*;

import java.util.ArrayList;

public class PedidoController {
    private Panaderia panaderia;

    public PedidoController(Panaderia panaderia) {
        this.panaderia = panaderia;
    }

    public Pedido registrarPedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        if (estaVacio(codigo)) {
            throw new IllegalArgumentException("El codigo del pedido es obligatorio");
        }
        validarDatos(nombreCliente, tipoPan, cantidad);
        if (panaderia.buscarPedido(codigo) != null) {
            throw new IllegalArgumentException("Ya existe un pedido con el codigo " + codigo);
        }
        Pedido pedido = new Pedido(codigo, nombreCliente, tipoPan, cantidad);
        panaderia.agregarPedido(pedido);
        return pedido;
    }

    public Pedido buscarPedido(String codigo) {
        Pedido pedido = panaderia.buscarPedido(codigo);
        if (pedido == null) {
            throw new IllegalArgumentException("No existe un pedido con el codigo " + codigo);
        }
        return pedido;
    }

    public void actualizarPedido(String codigo, String nombreCliente, TipoPan tipoPan, int cantidad) {
        Pedido pedido = buscarPedido(codigo);
        if (!pedido.estaPendiente()) {
            throw new IllegalStateException("Solo se pueden modificar pedidos pendientes. El pedido " + codigo
                    + " esta " + pedido.getEstado());
        }
        validarDatos(nombreCliente, tipoPan, cantidad);
        pedido.modificar(nombreCliente, tipoPan, cantidad);
    }

    public String estimarTiempos(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        String texto = "Pedido " + codigo + ": " + pedido.getCantidad() + " panes " + pedido.getTipoPan() + "\n";
        int panaderos = 0;
        for (Empleado empleado : panaderia.getListaEmpleados()) {
            if (empleado instanceof IProductor) {
                int minutos = calcularMinutos(empleado, pedido);
                String resultado = "sin tiempo suficiente";
                if (empleado.tieneMinutos(minutos)) {
                    resultado = "puede atenderlo";
                }
                texto = texto + "- " + empleado.getNombre() + " (" + empleado.getTipo() + "): necesita " + minutos
                        + " min, tiene " + empleado.getMinutosDisponibles() + " min " + resultado + "\n";
                panaderos++;
            }
        }
        if (panaderos == 0) {
            texto = texto + "No hay panaderos registrados.";
        }
        return texto;
    }

    public Empleado asignarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (!pedido.estaPendiente()) {
            throw new IllegalStateException("El pedido " + codigo + " esta " + pedido.getEstado() + " y no se puede asignar");
        }
        Empleado elegido= null;
        int menorTiempo = 0;
        for (Empleado empleado : panaderia.getListaEmpleados()) {
            if (empleado instanceof IProductor) {
                int minutos = calcularMinutos(empleado, pedido);
                if (empleado.tieneMinutos(minutos) && (elegido == null || minutos < menorTiempo)) {
                    elegido = empleado;
                    menorTiempo = minutos;
                }
            }
        }
        if (elegido == null) {
            throw new IllegalStateException("Ningun panadero tiene tiempo para el pedido " + codigo);
        }
        elegido.descontarMinutos(menorTiempo);
        pedido.asignar(elegido.getCodigo(), menorTiempo);
        return elegido;
    }

    public void cancelarPedido(String codigo) {
        Pedido pedido = buscarPedido(codigo);
        if (pedido.estaCancelado()) {
            throw new IllegalStateException("El pedido " + codigo + " ya esta cancelado");
        }
        if (pedido.estaAsignado()) {
            Empleado empleado = panaderia.buscarEmpleado(pedido.getCodigoEmpleado());
            if (empleado != null) {
                empleado.devolverMinutos(pedido.getMinutosReservados());
            }
        }
        pedido.cancelar();
    }

    public ArrayList<Pedido> getHistorialPedidos() {
        return panaderia.getHistorialPedidos();
    }

    private int calcularMinutos(Empleado empleado, Pedido pedido) {
        IProductor productor = (IProductor) empleado;
        return productor.calcularMinutos(pedido.getTipoPan(), pedido.getCantidad());
    }

    private void validarDatos(String nombreCliente, TipoPan tipoPan, int cantidad) {
        if (estaVacio(nombreCliente)) {
            throw new IllegalArgumentException("El nombre del cliente es obligatorio");
        }
        if (tipoPan == null) {
            throw new IllegalArgumentException("Seleccione el tipo de pan");
        }
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad de panes debe ser mayor que cero");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

}
