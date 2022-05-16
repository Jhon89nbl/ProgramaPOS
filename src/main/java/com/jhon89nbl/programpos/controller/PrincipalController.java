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

    //se crea lista para cargar las ventanas
    private final List<Region> window= new ArrayList<>();


    @FXML
    private Label lblTitle;

    @FXML
    void goAddProduct(ActionEvent event) throws MalformedURLException {
        //se carga la uri en un string
        String PRODUCT = "src/main/resources/com/jhon89nbl/programpos/add-product.fxml";
        //validamos si la lista de ventana esta vacia
        if(window.isEmpty()){
            //si esta vacia se carga la ventana de producto
            chargeWindow(Paths.get(PRODUCT).toUri().toURL(),
                    "product");
        }else {
            //se valida por el id si ya esta creada en la ventana
            if(searchWindow("product")){
                //si esta abierta muestra error
                errorWindow();
            }else {
                //en caso contrario se remueve todas las ventanas
                paneCenter.getChildren().removeAll();
                //se limpia el pane center
                paneCenter.getChildren().clear();
                //se carga nueva mente la ventana
                chargeWindow(Paths.get(PRODUCT).toUri().toURL(),
                        "product");
            }
        }
    }

    @FXML
    void goAddCategory(ActionEvent event) throws MalformedURLException {
        //se carga la uri en un string
        String CATEGORY = "src/main/resources/com/jhon89nbl/programpos/category.fxml";
        //validamos si la lista de ventana esta vacia
        if(window.isEmpty()){
            //si esta vacia se carga la ventana de producto
            chargeWindow(Paths.get(CATEGORY).toUri().toURL(),
                    "category");
        }else {
            //se valida por el id si ya esta creada en la ventana
            if(searchWindow("category")){
                //si esta abierta muestra error
                errorWindow();
            }else {
                //en caso contrario se remueve todas las ventanas
                paneCenter.getChildren().removeAll();
                //se limpia el pane center
                paneCenter.getChildren().clear();
                //se carga nueva mente la ventana
                chargeWindow(Paths.get(CATEGORY).toUri().toURL(),
                        "category");
            }
        }

    }
    @FXML
    void goAddSales(ActionEvent event) throws MalformedURLException {
        //se carga la uri en un string
        String SALES = "src/main/resources/com/jhon89nbl/programpos/sales.fxml";
        //validamos si la lista de ventana esta vacia
        if(window.isEmpty()){
            //si esta vacia se carga la ventana de producto
            chargeWindow(Paths.get(SALES).toUri().toURL(),
                    "sales");
        }else {
            //se valida por el id si ya esta creada en la ventana
            if(searchWindow("sales")){
                //si esta abierta muestra error
                errorWindow();
            }else {
                //en caso contrario se remueve todas las ventanas
                paneCenter.getChildren().removeAll();
                //se limpia el pane center
                paneCenter.getChildren().clear();
                //se carga nueva mente la ventana
                chargeWindow(Paths.get(SALES).toUri().toURL(),
                        "sales");
            }
        }
    }

    @FXML
    void goAddProvider(ActionEvent event) throws MalformedURLException {
        //se carga la uri en un string
        String PROVIDER = "src/main/resources/com/jhon89nbl/programpos/add-provider.fxml";
        //validamos si la lista de ventana esta vacia
        if(window.isEmpty()){
            //si esta vacia se carga la ventana de producto
            chargeWindow(Paths.get(PROVIDER).toUri().toURL(),
                    "provider");
        }else {
            //se valida por el id si ya esta creada en la ventana
            if(searchWindow("provider")){
                //si esta abierta muestra error
                errorWindow();
            }else {
                //en caso contrario se remueve todas las ventanas
                paneCenter.getChildren().removeAll();
                //se limpia el pane center
                paneCenter.getChildren().clear();
                //se carga nueva mente la ventana
                chargeWindow(Paths.get(PROVIDER).toUri().toURL(),
                        "provider");
            }
        }
    }

    @FXML
    void goOrders(ActionEvent event) {

    }

    private void chargeWindow(URL fxmURL, String id){
        try {
            // se obtiene la escena del pane
            Scene scene = paneCenter.getScene();
            //se elecciona el tamaño preferido del pane
            paneCenter.setPrefSize(scene.getWidth(),scene.getHeight()*0.83);
            //se carga la url en la region
            Region region = FXMLLoader.load(fxmURL);
            //se le asigna un id para identificarla
            region.setId(id);
            //se añade la region al pane
            paneCenter.getChildren().add(region);
            // se ajusta el tamaño de la region al tamañp del pane
            region.prefWidthProperty().bind(paneCenter.widthProperty());
            region.prefHeightProperty().bind(paneCenter.heightProperty());
            // se añade la region a la a la lista de ventana
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
        //se crea variable para validar
        boolean ischarge = false;
        // se busca en la lista de ventanas si la region ya esta creada por medio del ID
        for (Region region: window) {
            ischarge = region.getId().equals(id);
        }
        //se retorna la validacion
        return ischarge;
    }


}
