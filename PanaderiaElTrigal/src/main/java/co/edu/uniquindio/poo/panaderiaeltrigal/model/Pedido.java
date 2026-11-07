package co.edu.uniquindio.poo.panaderiaeltrigal.model;

public class Pedido {
    private String codigo;
    private String nombreCliente;
    private TipoPan tipoPan;
    private int cantidad;
    private EstadoPedido estado;
    private String codigoEmpleado;
    private int minutosReservados;

    public Pedido(String codigo, String nombreCliente, TipoPan tipoPan,
                  int cantidad) {
        this.codigo = codigo;
        this.nombreCliente = nombreCliente;
        this.tipoPan = tipoPan;
        this.cantidad = cantidad;
        this.estado = EstadoPedido.PENDIENTE;
        this.codigoEmpleado = "";
        this.minutosReservados = 0;
    }

    public boolean estaPendiente() {
        return estado == EstadoPedido.PENDIENTE;
    }

    public boolean estaAsignado() {
        return estado == EstadoPedido.ASIGNADO;
    }

    public boolean estaCancelado() {
        return estado == EstadoPedido.CANCELADO;
    }

    public void asignar(String codigoEmpleado, int minutosReservados) {
        this.codigoEmpleado = codigoEmpleado;
        this.minutosReservados = minutosReservados;
        this.estado = EstadoPedido.ASIGNADO;
    }

    public void cancelar() {
        estado = EstadoPedido.CANCELADO;
    }

    public void modificar(String nombreCliente, TipoPan tipoPan, int cantidad) {
        this.nombreCliente = nombreCliente;
        this.tipoPan = tipoPan;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public TipoPan getTipoPan() {
        return tipoPan;
    }

    public int getCantidad() {
        return cantidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public String getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public int getMinutosReservados() {
        return minutosReservados;
    }
}
