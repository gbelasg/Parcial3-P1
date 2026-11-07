package co.edu.uniquindio.poo.panaderiaeltrigal.controller;

import co.edu.uniquindio.poo.panaderiaeltrigal.model.Cajero;
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
            return "Se ha agregado al empleado con codigo: " +empleado.getCodigo();
    }

    private Empleado buscarEmpleado(String codigo) {
        return listaEmpleados.stream().filter(empleado -> empleado.getCodigo().equalsIgnoreCase(codigo))
                .findFirst().orElse(null);
    }
    public void actualizarEmpleado(String codigo, String nombre, String documento, int minutos, String caja){
        Empleado empleado = buscarEmpleado(codigo);
        if (empleado==null){
            throw new IllegalArgumentException("No existe un empleado con el codigo: " +codigo);
        }
        if (nombre==null || nombre.isBlank() || documento==null || documento.isBlank()){
            throw new IllegalArgumentException("El nombre y el documento son obligatorios");
        }
        if (minutos<0){
            throw new IllegalArgumentException("Los minutos disponibles no pueden ser negativos");
        }
        if (empleado instanceof Cajero && (caja==null || caja.isBlank())){
            throw new IllegalArgumentException("Debe ingresar el número o identificador de caja.");
        }
        empleado.setNombre(nombre);
        empleado.setDocumento(documento);
        empleado.setMinutosDisponibles(minutos);
        if (empleado instanceof Cajero){
            ((Cajero) empleado).setCaja(caja);
        }
    }
    public boolean eliminarEmpleado(String codigo){
        return listaEmpleados.removeIf(empleado -> empleado.getCodigo().equalsIgnoreCase(codigo));
    }



}


