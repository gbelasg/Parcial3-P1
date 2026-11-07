package co.edu.uniquindio.poo.panaderiaeltrigal.controller;

import co.edu.uniquindio.poo.panaderiaeltrigal.model.Empleado;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.IProductor;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.TipoPan;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;

public class EmpleadoController {

    private ArrayList<Empleado> listaEmpleados;


    public EmpleadoController() {
        this.listaEmpleados = new ArrayList<>();

    }

    public ArrayList<Empleado> getListaEmpleados() {
        return listaEmpleados;
    }





    public void setListaEmpleados(ArrayList<Empleado> listaEmpleados) {
        this.listaEmpleados = listaEmpleados;
    }
    public void registrarEmpleado(Empleado empleado) {
        agregarEmpleado(empleado);
    }

    public String agregarEmpleado(Empleado empleado){
        if (empleado==null){
            throw new IllegalArgumentException("El empleado ingresado no es valido");
            }
            if (buscarEmpleado(empleado.getCodigo()) !=null){
                throw new IllegalArgumentException("Ya exixte un empleado con el codigo: " +empleado.getCodigo());
        }
            listaEmpleados.add(empleado);
            return "Se ha agregado al empreado con codigo: " +empleado.getCodigo();
    }

    private Empleado buscarEmpleado(String codigo) {
        return listaEmpleados.stream().filter(empleado -> empleado.getCodigo().equalsIgnoreCase(codigo))
                .findFirst().orElse(null);
    }
    public boolean eliminarEmpleado(String codigo){
        return listaEmpleados.removeIf(empleado -> empleado.getCodigo().equalsIgnoreCase(codigo));
    }



}


