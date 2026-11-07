package co.edu.uniquindio.poo.panaderiaeltrigal.model;

import java.util.ArrayList;

public class Panaderia {
    private String nombre;
    private ArrayList<Empleado> listaEmpleados= new ArrayList<>();;
    private ArrayList<Pedido> historialPedidos= new ArrayList<>();

    public Panaderia(String nombre) {
        this.nombre = nombre;
    }
    public void agregarEmpleado(Empleado empleado) {
        listaEmpleados.add(empleado);
    }

    public void eliminarEmpleado(Empleado empleado) {
        listaEmpleados.remove(empleado);
    }

    public void agregarPedido(Pedido pedido) {
        historialPedidos.add(pedido);
    }

    public Empleado buscarEmpleado(String codigo) {
        Empleado encontrado = null;
        for (Empleado empleado : listaEmpleados) {
            if (encontrado == null && empleado.getCodigo().equals(codigo)) {
                encontrado = empleado;
            }
        }
        return encontrado;
    }

    public Pedido buscarPedido(String codigo) {
        Pedido encontrado = null;
        for (Pedido pedido : historialPedidos) {
            if (encontrado == null && pedido.getCodigo().equals(codigo)) {
                encontrado = pedido;
            }
        }
        return encontrado;
    }

    public boolean tienePedidosAsignados(String codigoEmpleado) {
        boolean tienePedido = false;
        for (Pedido pedido : historialPedidos) {
            if (pedido.estaAsignado() && pedido.getCodigoEmpleado().equals(codigoEmpleado)) {
                tienePedido = true;
            }
        }
        return tienePedido;
    }

    public String getNombre() {
        return nombre;
    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }

    public ArrayList<Pedido> getHistorialPedidos() {
        return historialPedidos;
    }
}
