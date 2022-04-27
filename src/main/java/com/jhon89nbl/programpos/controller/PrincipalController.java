package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;
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


    private final List<Region> window= new ArrayList<>();


    @FXML
    private Label lblTitle;

    @FXML
    void goAddProduct(ActionEvent event) throws MalformedURLException {
        String PRODUCT = "src/main/resources/com/jhon89nbl/programpos/add-product.fxml";
        if(window.isEmpty()){
            chargeWindow(Paths.get(PRODUCT).toUri().toURL(),
                    "product");
        }else {
            if(searchWindow("product")){
                errorWindow();
            }else {
                paneCenter.getChildren().removeAll();
                paneCenter.getChildren().clear();
                chargeWindow(Paths.get(PRODUCT).toUri().toURL(),
                        "product");
            }
        }
    }

    @FXML
    void goAddCategory(ActionEvent event) throws MalformedURLException {
        String CATEGORY = "src/main/resources/com/jhon89nbl/programpos/category.fxml";
        if(window.isEmpty()){
            chargeWindow(Paths.get(CATEGORY).toUri().toURL(),
                    "category");
        }else {
            if(searchWindow("category")){
                errorWindow();
            }else {
                paneCenter.getChildren().removeAll();
                paneCenter.getChildren().clear();
                chargeWindow(Paths.get(CATEGORY).toUri().toURL(),
                        "category");
            }
        }

    }
    @FXML
    void goAddSales(ActionEvent event) throws MalformedURLException {
        String SALES = "src/main/resources/com/jhon89nbl/programpos/sales.fxml";
        if(window.isEmpty()){
            chargeWindow(Paths.get(SALES).toUri().toURL(),
                    "sales");
        }else {
            if(searchWindow("sales")){
                errorWindow();
            }else {
                paneCenter.getChildren().removeAll();
                paneCenter.getChildren().clear();
                chargeWindow(Paths.get(SALES).toUri().toURL(),
                        "sales");
            }
        }
    }

    private void chargeWindow(URL fxmURL, String id){
        try {
            Scene scene = paneCenter.getScene();
            paneCenter.setPrefSize(scene.getWidth(),scene.getHeight()*0.83);
            Region region = FXMLLoader.load(fxmURL);
            region.setId(id);
            paneCenter.getChildren().add(region);
            region.prefWidthProperty().bind(paneCenter.widthProperty());
            region.prefHeightProperty().bind(paneCenter.heightProperty());
            window.add(region);



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void errorWindow(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("Informacion");
        alert.setContentText("Ya se encuentra abierta la ventana ");
        alert.show();
    }

    private boolean searchWindow(String id){
        boolean ischarge = false;
        for (Region region: window) {
            ischarge = region.getId().equals(id);
        }
        return ischarge;
    }


}
