package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;

import javafx.scene.layout.Region;


public class PrincipalController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitle.setText(user.getName()+ " " + user.getLastName());
    }

    User user = User.getINSTANCE();

    @FXML
    private AnchorPane paneCenter;



    @FXML
    private Label lblTitle;

    @FXML
    void goAddProduct(ActionEvent event) throws MalformedURLException {
        URL fxmURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/add-product.fxml").toUri().toURL();
        try {
            Scene scene = paneCenter.getScene();
            paneCenter.setPrefSize(scene.getWidth(),scene.getHeight()*0.83);
            Region region = FXMLLoader.load(fxmURL);
            paneCenter.getChildren().add(region) ;
            region.prefWidthProperty().bind(paneCenter.widthProperty());
            region.prefHeightProperty().bind(paneCenter.heightProperty());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
