package co.edu.uniquindio.poo.panaderiaeltrigal.model;

public class PanaderoIndustrial extends Empleado implements IProductor{

    public static final int PANES_POR_LOTE= 30;
    public static final int MINUTOS_LOTE_TRADICIONAL= 25;
    public static final int MINUTOS_LOTE_INTEGRAL= 35;
    public static final int MINUTOS_PREPARACION= 20;

    public PanaderoIndustrial(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }

    @Override
    public int calcularMinutos(TipoPan tipoPan, int cantidad) {

        int lotes= (int) Math.ceil((double) cantidad/PANES_POR_LOTE); //el Math.Ceil es para redondear al entero mas cercano

        int minutosPorLote;
        if (tipoPan== TipoPan.TRADICIONAL){
            minutosPorLote=MINUTOS_LOTE_TRADICIONAL;
        }else {
            minutosPorLote=MINUTOS_LOTE_INTEGRAL;
        }
        return (lotes*minutosPorLote)+ MINUTOS_PREPARACION;
    }

    @Override
    public String getTipo() {
        return "Panadero Industrial";
    }
}
