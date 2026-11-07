package co.edu.uniquindio.poo.panaderiaeltrigal.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
        @Override
        public void start(Stage ventana) throws Exception {
            Parent raiz = FXMLLoader.load
                    (Objects.requireNonNull(getClass().getResource
                            ("/co/edu/uniquindio/poo/panaderiaeltrigal/PanaderiaVista.fxml")));
            ventana.setTitle("Panadería El Trigal");
            ventana.setScene(new Scene(raiz));
            ventana.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }
