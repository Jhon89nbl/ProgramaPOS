package com.jhon89nbl.programpos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;


public class ProgramPos extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //se trae la vista para cargarla
        FXMLLoader fxmlLoader = new FXMLLoader(ProgramPos.class.getResource("login-view.fxml"));
        //se obtiene el tamño de pantalla
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int widthScreen = (int) screenBounds.getWidth();
        int heigthScreen = (int) screenBounds.getHeight();
        //se carga la vista y se ajusta al tamaño
        Scene scene = new Scene(fxmlLoader.load(), widthScreen*0.39, heigthScreen*0.36);
        //se crea y la vista y se muestra
        stage.setTitle("Programa POS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}