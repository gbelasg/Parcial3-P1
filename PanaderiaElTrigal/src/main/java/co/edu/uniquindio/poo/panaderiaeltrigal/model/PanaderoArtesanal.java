package co.edu.uniquindio.poo.panaderiaeltrigal.model;

public class PanaderoArtesanal extends Empleado implements IProductor {

    public static final int PANES_POR_BANDEJA = 12;
    public static final int MINUTOS_BANDEJA_TRADICIONAL = 30;
    public static final int MINUTOS_BANDEJA_INTEGRAL = 40;

    public PanaderoArtesanal(String nombre, String documento, String codigo, int minutosDisponibles) {
        super(nombre, documento, codigo, minutosDisponibles);
    }


    @Override
    public int calcularMinutos(TipoPan tipoPan, int cantidad) {

        int bandejas= (int) Math.ceil((double) cantidad/PANES_POR_BANDEJA);
        int minutosPorBandeja;
        if (tipoPan==TipoPan.TRADICIONAL){
            minutosPorBandeja= MINUTOS_BANDEJA_TRADICIONAL;
        }else{
            minutosPorBandeja=MINUTOS_BANDEJA_INTEGRAL;
        }
        return bandejas*minutosPorBandeja;
    }

    @Override
    public String getTipo() {
        return "Panadero Artesanal";
    }
}
