package co.edu.uniquindio.poo.panaderiaeltrigal.viewController;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.Cajero;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.Empleado;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.PanaderoArtesanal;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.PanaderoIndustrial;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TextField;

import java.awt.*;

public class PanaderiaViewController {
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtDocumento;
    @FXML
    private TextField txtCodigo;
    @FXML
    private TextField txtMinutos;
    @FXML
    private TextField txtCaja;
    @FXML
    private ComboBox<String> cbTipoEmpleado;

    @FXML
    private TableView<Empleado> tblEmpleados;
    @FXML
    private TableColumn<Empleado, String> colCodigo;
    @FXML
    private TableColumn<Empleado, String> colNombre;
    @FXML
    private TableColumn<Empleado, String> colDocumento;
    @FXML
    private TableColumn<Empleado, String> colTipo;
    @FXML
    private TableColumn<Empleado, String> colMinutos;

    private EmpleadoController empleadoController;
    private ObservableList<Empleado> listaObservableEmpleados;

    @FXML
    public void initialize(){
        empleadoController= new EmpleadoController();
        listaObservableEmpleados= FXCollections.observableArrayList();

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colMinutos.setCellValueFactory(new PropertyValueFactory<>("minutosDisponibles"));
        tblEmpleados.setItems(listaObservableEmpleados);

        cbTipoEmpleado.setItems(FXCollections.observableArrayList("Panadero Artesanal", "Panadero Industrial", "Cajero"));
        cbTipoEmpleado.getSelectionModel().selectFirst();
    }
    @FXML
    public void onAgregarEmpleado() {
        try {
            String nombre = txtNombre.getText();
            String documento = txtDocumento.getText();
            String codigo = txtCodigo.getText();
            int minutos = leerEntero(txtMinutos.getText(), "Los minutos disponibles deben ser un número válido.");
            String tipo = cbTipoEmpleado.getValue();

            if (nombre.isBlank() || documento.isBlank() || codigo.isBlank()) {
                alertas("Campos vacíos", "Por favor completa todos los campos requeridos.", Alert.AlertType.WARNING);
                return;
            }

            Empleado nuevoEmpleado;
            if ("Panadero Artesanal".equals(tipo)) {
                nuevoEmpleado = new PanaderoArtesanal(nombre, documento, codigo, minutos);
            } else if ("Panadero Industrial".equals(tipo)) {
                nuevoEmpleado = new PanaderoIndustrial(nombre, documento, codigo, minutos);
            } else {
                String caja = txtCaja.getText();
                if (caja.isBlank()) {
                    alertas("Campo requerido", "Debe ingresar el número o identificador de caja.", Alert.AlertType.WARNING);
                    return;
                }
                nuevoEmpleado = new Cajero(nombre, documento, codigo, minutos, caja);
            }

            empleadoController.agregarEmpleado(nuevoEmpleado);
            refrescarTablas();
            limpiarCampos();
            alertas("Éxito", "Empleado registrado correctamente.", Alert.AlertType.INFORMATION);

        } catch (IllegalArgumentException e) {
            alertas("Error al agregar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onEliminarEmpleado() {
        Empleado seleccionado = tblEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            alertas("Selección requerida", "Selecciona un empleado de la tabla para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        boolean eliminado = empleadoController.eliminarEmpleado(seleccionado.getCodigo());
        if (eliminado) {
            refrescarTablas();
            alertas("Éxito", "Empleado eliminado correctamente.", Alert.AlertType.INFORMATION);
        } else {
            alertas("Error", "No se pudo eliminar el empleado.", Alert.AlertType.ERROR);
        }
    }

    // Métodos de apoyo
    public void refrescarTablas() {
        listaObservableEmpleados.setAll(empleadoController.getListaEmpleados());
        tblEmpleados.refresh();
    }

    public void alertas(String titulo, String contenido, Alert.AlertType tipoAlert) {
        Alert alert = new Alert(tipoAlert);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(contenido);
        alert.showAndWait();
    }

    public int leerEntero(String texto, String mensajeError) {
        try {
            return Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(mensajeError);
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDocumento.clear();
        txtCodigo.clear();
        txtMinutos.clear();
        txtCaja.clear();
    }

}
