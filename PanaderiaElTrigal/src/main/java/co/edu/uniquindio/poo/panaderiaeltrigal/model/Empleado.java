package co.edu.uniquindio.poo.panaderiaeltrigal.model;

public abstract class Empleado extends Persona {

    protected String codigo;
    protected int minutosDisponibles;

    public Empleado(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento);
        this.codigo = codigo;
        this.minutosDisponibles = minutosDisponibles;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public int getMinutosDisponibles() {
        return minutosDisponibles;
    }

    public void setMinutosDisponibles(int minutosDisponibles) {
        this.minutosDisponibles = minutosDisponibles;
    }

    public boolean tieneMinutos(int minutos){
        return this.minutosDisponibles>= minutos;
    }

    public void descontarMinutos(int minutos){
        this.minutosDisponibles-=minutos;
    }

    public void devolverMinutos(int minutos){
        this.minutosDisponibles+=minutos;
    }

    public abstract String getTipo();

}
