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
        FXMLLoader fxmlLoader = new FXMLLoader(ProgramPos.class.getResource("login-view.fxml"));
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        int widthScreen = (int) screenBounds.getWidth();
        int heigthScreen = (int) screenBounds.getHeight();
        Scene scene = new Scene(fxmlLoader.load(), widthScreen*0.40, heigthScreen*0.40);
        stage.setTitle("Programa POS");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}