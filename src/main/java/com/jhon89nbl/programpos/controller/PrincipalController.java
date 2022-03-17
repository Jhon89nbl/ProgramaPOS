package com.jhon89nbl.programpos.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class PrincipalController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        panePrincipal.sceneProperty().addListener(((observableValue, oldScene, newScene) ->{
            if (oldScene == null && newScene !=null){
                newScene.windowProperty().addListener(((observableValue1, oldWindow, newWindow) ->{
                    if(oldWindow==null && newWindow != null){
                        ((Stage) newWindow).maximizedProperty().addListener((a,b,c)->{
                            if(c){
                                System.out.println(c);
                            }
                        });
                    }
                } ));
            }

        } ));
    }

    @FXML
    private AnchorPane panePrincipal;



}
