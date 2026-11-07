package co.edu.uniquindio.poo.panaderiaeltrigal.viewController;

import co.edu.uniquindio.poo.panaderiaeltrigal.controller.EmpleadoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.controller.PanaderiaController;
import co.edu.uniquindio.poo.panaderiaeltrigal.controller.PedidoController;
import co.edu.uniquindio.poo.panaderiaeltrigal.model.*;
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

    @FXML
    private TextField txtCodigoPedido;
    @FXML
    private TextField txtCliente;
    @FXML
    private ComboBox<TipoPan> cbTipoPan;
    @FXML
    private TextField txtCantidad;
    @FXML
    private TextArea txtResultado;

    @FXML
    private TableView<Pedido> tblPedidos;
    @FXML
    private TableColumn<Pedido, String> colCodigoPedido;
    @FXML
    private TableColumn<Pedido, String> colCliente;
    @FXML
    private TableColumn<Pedido, String> colTipoPan;
    @FXML
    private TableColumn<Pedido, String> colCantidad;
    @FXML
    private TableColumn<Pedido, String> colEstado;
    @FXML
    private TableColumn<Pedido, String> colEmpleadoAsignado;
    @FXML
    private TableColumn<Pedido, String> colMinutosReservados;

    private PanaderiaController controlador;
    private EmpleadoController empleadoController;
    private PedidoController pedidoController;
    private ObservableList<Empleado> listaObservableEmpleados;
    private ObservableList<Pedido> listaObservablePedidos;

    @FXML
    public void initialize(){
        controlador = new PanaderiaController("Panadería El Trigal");
        empleadoController= new EmpleadoController();
        pedidoController = controlador.getPedidoController();
        listaObservableEmpleados= FXCollections.observableArrayList();
        listaObservablePedidos = FXCollections.observableArrayList();

        colCodigo.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colDocumento.setCellValueFactory(new PropertyValueFactory<>("documento"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colMinutos.setCellValueFactory(new PropertyValueFactory<>("minutosDisponibles"));
        tblEmpleados.setItems(listaObservableEmpleados);

        colCodigoPedido.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        colCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colTipoPan.setCellValueFactory(new PropertyValueFactory<>("tipoPan"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colEmpleadoAsignado.setCellValueFactory(new PropertyValueFactory<>("codigoEmpleado"));
        colMinutosReservados.setCellValueFactory(new PropertyValueFactory<>("minutosReservados"));
        tblPedidos.setItems(listaObservablePedidos);

        cbTipoEmpleado.setItems(FXCollections.observableArrayList("Panadero Artesanal", "Panadero Industrial", "Cajero"));
        cbTipoEmpleado.getSelectionModel().selectFirst();
        cbTipoPan.setItems(FXCollections.observableArrayList(TipoPan.values()));

        controlador.cargarDatosDePrueba();
        refrescarTablas();
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
    public void onActualizarEmpleado() {
        try {
            String codigo = txtCodigo.getText();
            if (codigo.isBlank()) {
                alertas("Selección requerida", "Selecciona un empleado de la tabla para actualizar.", Alert.AlertType.WARNING);
                return;
            }
            int minutos = leerEntero(txtMinutos.getText(), "Los minutos disponibles deben ser un número válido.");

            empleadoController.actualizarEmpleado(codigo, txtNombre.getText(), txtDocumento.getText(), minutos, txtCaja.getText());
            refrescarTablas();
            limpiarCampos();
            alertas("Éxito", "Empleado actualizado correctamente.", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException e) {
            alertas("Error al actualizar", e.getMessage(), Alert.AlertType.ERROR);
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
    // Al hacer clic en una fila se llenan los campos el código y el tipo no se pueden cambiar
    @FXML
    public void onSeleccionarEmpleado() {
        Empleado seleccionado = tblEmpleados.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtNombre.setText(seleccionado.getNombre());
            txtDocumento.setText(seleccionado.getDocumento());
            txtCodigo.setText(seleccionado.getCodigo());
            txtMinutos.setText("" + seleccionado.getMinutosDisponibles());
            cbTipoEmpleado.setValue(seleccionado.getTipo());
            txtCaja.clear();
            if (seleccionado instanceof Cajero) {
                txtCaja.setText(((Cajero) seleccionado).getCaja());
            }
            txtCodigo.setDisable(true);
            cbTipoEmpleado.setDisable(true);
        }
    }
    @FXML
    public void onLimpiarEmpleado() {
        limpiarCampos();
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtDocumento.clear();
        txtCodigo.clear();
        txtMinutos.clear();
        txtCaja.clear();
        txtCodigo.setDisable(false);
        cbTipoEmpleado.setDisable(false);
        cbTipoEmpleado.getSelectionModel().selectFirst();
        tblEmpleados.getSelectionModel().clearSelection();
    }

    @FXML
    public void onRegistrarPedido() {
        try {
            String codigo = txtCodigoPedido.getText();
            String cliente = txtCliente.getText();
            TipoPan tipoPan = cbTipoPan.getValue();
            int cantidad = leerEntero(txtCantidad.getText(), "La cantidad de panes debe ser un número válido.");

            Pedido pedido = pedidoController.registrarPedido(codigo, cliente, tipoPan, cantidad);
            refrescarTablas();
            onLimpiarPedido();
            alertas("Éxito", "Pedido " + pedido.getCodigo() + " registrado en estado " + pedido.getEstado() + ".",
                    Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException | IllegalStateException e) {
            alertas("Error al registrar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onActualizarPedido() {
        try {
            String codigo = txtCodigoPedido.getText();
            if (codigo.isBlank()) {
                alertas("Selección requerida", "Selecciona un pedido de la tabla para actualizar.", Alert.AlertType.WARNING);
                return;
            }
            int cantidad = leerEntero(txtCantidad.getText(), "La cantidad de panes debe ser un número válido.");

            pedidoController.actualizarPedido(codigo, txtCliente.getText(), cbTipoPan.getValue(), cantidad);
            refrescarTablas();
            onLimpiarPedido();
            alertas("Éxito", "Pedido " + codigo + " actualizado correctamente.", Alert.AlertType.INFORMATION);
        } catch (IllegalArgumentException | IllegalStateException e) {
            alertas("Error al actualizar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Busca el pedido por el código escrito y muestra sus datos
    @FXML
    public void onVerPedido() {
        try {
            String codigo = txtCodigoPedido.getText();
            if (codigo.isBlank()) {
                alertas("Campo vacío", "Escribe el código del pedido que quieres ver.", Alert.AlertType.WARNING);
                return;
            }
            Pedido pedido = pedidoController.buscarPedido(codigo);

            String texto = "Pedido: " + pedido.getCodigo() + "\n"
                    + "Cliente: " + pedido.getNombreCliente() + "\n"
                    + "Pan: " + pedido.getCantidad() + " " + pedido.getTipoPan() + "\n"
                    + "Estado: " + pedido.getEstado() + "\n";
            if (pedido.estaAsignado()) {
                texto = texto + "Empleado asignado: " + pedido.getCodigoEmpleado() + "\n"
                        + "Minutos reservados: " + pedido.getMinutosReservados();
            }
            txtResultado.setText(texto);
            tblPedidos.getSelectionModel().select(pedido);
        } catch (IllegalArgumentException e) {
            alertas("No encontrado", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Solo consulta cuánto tardaría cada panadero no descuenta minutos
    @FXML
    public void onEstimarTiempos() {
        try {
            String codigo = txtCodigoPedido.getText();
            if (codigo.isBlank()) {
                alertas("Selección requerida", "Selecciona un pedido de la tabla.", Alert.AlertType.WARNING);
                return;
            }
            txtResultado.setText(pedidoController.estimarTiempos(codigo));
        } catch (IllegalArgumentException | IllegalStateException e) {
            alertas("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onAsignarPedido() {
        try {
            String codigo = txtCodigoPedido.getText();
            if (codigo.isBlank()) {
                alertas("Selección requerida", "Selecciona un pedido de la tabla para asignar.", Alert.AlertType.WARNING);
                return;
            }
            Empleado empleado = pedidoController.asignarPedido(codigo);
            Pedido pedido = pedidoController.buscarPedido(codigo);
            refrescarTablas();
            onLimpiarPedido();
            txtResultado.setText("Pedido " + codigo + " asignado a " + empleado.getNombre()
                    + " (" + empleado.getTipo() + ").\n"
                    + "Minutos reservados: " + pedido.getMinutosReservados() + "\n"
                    + "Minutos que le quedan: " + empleado.getMinutosDisponibles());
        } catch (IllegalArgumentException | IllegalStateException e) {
            alertas("No se pudo asignar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void onCancelarPedido() {
        try {
            String codigo = txtCodigoPedido.getText();
            if (codigo.isBlank()) {
                alertas("Selección requerida", "Selecciona un pedido de la tabla para cancelar.", Alert.AlertType.WARNING);
                return;
            }
            pedidoController.cancelarPedido(codigo);
            refrescarTablas();
            onLimpiarPedido();
            txtResultado.setText("Pedido " + codigo + " cancelado. Sigue en el historial.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            alertas("No se pudo cancelar", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    // Al hacer clic en una fila se llenan los campos el código no se puede cambiar
    @FXML
    public void onSeleccionarPedido() {
        Pedido seleccionado = tblPedidos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            txtCodigoPedido.setText(seleccionado.getCodigo());
            txtCliente.setText(seleccionado.getNombreCliente());
            cbTipoPan.setValue(seleccionado.getTipoPan());
            txtCantidad.setText("" + seleccionado.getCantidad());
            txtCodigoPedido.setDisable(true);
        }
    }

    @FXML
    public void onLimpiarPedido() {
        txtCodigoPedido.clear();
        txtCliente.clear();
        cbTipoPan.setValue(null);
        txtCantidad.clear();
        txtCodigoPedido.setDisable(false);
        tblPedidos.getSelectionModel().clearSelection();
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

}
