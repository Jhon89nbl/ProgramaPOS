package com.jhon89nbl.programpos.controller;

import com.jhon89nbl.programpos.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javax.swing.JInternalFrame;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

public class PrincipalController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblTitle.setText(user.getName()+ " " + user.getLastName());
    }

    User user = User.getINSTANCE();

    @FXML
    private AnchorPane paneCenter;

    @FXML
    private Button btnAddProduct;

    @FXML
    private Label lblTitle;

    @FXML
    void goAddProduct(ActionEvent event) throws MalformedURLException {
        URL fxmURL = Paths.get("src/main/resources/com/jhon89nbl/programpos/add-product.fxml").toUri().toURL();
        try {
            Scene scene = paneCenter.getScene();
            System.out.println(scene.getWidth());
            System.out.println(scene.getHeight()*0.90);
            paneCenter.setPrefSize(scene.getWidth(),scene.getHeight()*0.85);
            Region region = (Region)FXMLLoader.load(fxmURL);
            paneCenter.getChildren().add(region) ;
            region.prefWidthProperty().bind(paneCenter.widthProperty());
            region.prefHeightProperty().bind(paneCenter.heightProperty());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
