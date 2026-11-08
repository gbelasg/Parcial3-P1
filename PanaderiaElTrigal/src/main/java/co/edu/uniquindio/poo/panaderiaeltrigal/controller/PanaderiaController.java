package co.edu.uniquindio.poo.panaderiaeltrigal.controller;

import co.edu.uniquindio.poo.panaderiaeltrigal.model.Cajero;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.PanaderoIndustrial;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.Panaderia;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.TipoPan;


public class PanaderiaController {
    private Panaderia panaderia;
    private EmpleadoController empleadoController;
    private PedidoController pedidoController;

    public PanaderiaController(String nombre) {
        panaderia = new Panaderia(nombre);
        empleadoController = new EmpleadoController(panaderia);
        //Empleados y pedidos trabajan sobre la lista de la panaderia
        empleadoController.setListaEmpleados(panaderia.getListaEmpleados());
        pedidoController = new PedidoController(panaderia);
    }

    // Datos para probar la ventana apenas se abre
    public void cargarDatosDePrueba() {
        empleadoController.registrarEmpleado(new PanaderoArtesanal("Ana Gómez", "1094000001", "E01", 100));
        empleadoController.registrarEmpleado(new PanaderoIndustrial("Carlos Ruiz", "1094000002", "E02", 120));
        empleadoController.registrarEmpleado(new PanaderoArtesanal("Diana López", "1094000003", "E03", 80));
        empleadoController.registrarEmpleado(new Cajero("Lucía Torres", "1094000004", "E04", 120, "Caja 1"));

        pedidoController.registrarPedido("P01", "Cafetería Central", TipoPan.TRADICIONAL, 25);
        pedidoController.registrarPedido("P02", "Restaurante La Casona", TipoPan.INTEGRAL, 13);
        pedidoController.registrarPedido("P03", "Hotel Armenia", TipoPan.TRADICIONAL, 31);
    }
    public Panaderia getPanaderia() {
        return panaderia;
    }

    public EmpleadoController getEmpleadoController() {
        return empleadoController;
    }

    public PedidoController getPedidoController() {
        return pedidoController;
    }
}
